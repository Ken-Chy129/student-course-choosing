package cn.ken.student.rubcourse.listener;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.config.RabbitMQConfig;
import cn.ken.student.rubcourse.mapper.CourseClassMapper;
import cn.ken.student.rubcourse.mapper.StudentCourseMapper;
import cn.ken.student.rubcourse.mapper.StudentCreditsMapper;
import cn.ken.student.rubcourse.model.entity.CourseClass;
import cn.ken.student.rubcourse.model.entity.StudentCourse;
import cn.ken.student.rubcourse.model.entity.StudentCredits;
import cn.ken.student.rubcourse.model.entity.SysNotice;
import cn.ken.student.rubcourse.mapper.SysNoticeMapper;
import cn.ken.student.rubcourse.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/22 20:55
 */
@Slf4j
@Service
public class RabbitMsgListener {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;
    
    @Autowired
    private StudentCreditsMapper studentCreditsMapper;
    
    @Autowired
    private CourseClassMapper courseClassMapper;

    @RabbitListener(bindings = {
            // @QueueBinding注解要完成队列和交换机的
            @QueueBinding(
                    // @Queue创建一个队列（没有指定参数则表示创建一个随机队列）
                    value = @Queue(),
                    // 创建一个交换机
                    exchange = @Exchange(name = RabbitMQConfig.NOTICE_EXCHANGE, type = "fanout")
            )
    })
    public void noticeReceive(Long id) {
        SysNotice sysNotice = sysNoticeMapper.selectById(id);
        if (sysNotice.getStudentId() == -1) {
            WebSocketServer.batchSend(sysNotice);
        } else {
            WebSocketServer.send(sysNotice);
        }
        sysNoticeMapper.updateById(sysNotice);
        log.info("{}", "监听并推送消息");
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(),
                    exchange = @Exchange(name = RabbitMQConfig.CHOOSE_EXCHANGE, type = "fanout")
            )
    })
    @Transactional
    public void chooseReceive(StudentCourse studentCourse) {
        
        Long studentId = studentCourse.getStudentId();
        Integer semester = studentCourse.getSemester();
        Long courseClassId = studentCourse.getCourseClassId();
        
        // 更新所选学分
        StudentCredits studentCredits = studentCreditsMapper.selectByStudentAndSemester(studentId, semester);
        studentCredits.setChooseSubjectCredit(studentCredits.getChooseSubjectCredit().add(studentCourse.getCredits()));
        studentCreditsMapper.updateById(studentCredits);

        // 增加课程选择人数
        CourseClass courseClass = courseClassMapper.selectById(courseClassId);
        courseClass.setChoosingNum(courseClass.getChoosingNum() + 1);
        courseClassMapper.updateById(courseClass);

        // 新增选课
        StudentCourse chooseCourse = studentCourseMapper.selectByStudentAndSemesterAndCourseClass(studentCourse.getStudentId(), studentCourse.getSemester(), studentCourse.getCourseClassId());
        if (chooseCourse == null) {
            // 第一次选择
            studentCourse.setId(SnowflakeUtil.nextId());
            studentCourseMapper.insert(studentCourse);
            return;
        }
        // 已选择过该课
        chooseCourse.setIsDeleted(false);
        studentCourseMapper.updateById(chooseCourse);
    }
}
