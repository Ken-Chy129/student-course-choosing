package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.util.CourseUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.mapper.*;
import cn.ken.student.rubcourse.model.dto.req.StudentChooseLogReq;
import cn.ken.student.rubcourse.model.dto.resp.StudentChooseLogResp;
import cn.ken.student.rubcourse.model.entity.*;
import cn.ken.student.rubcourse.service.IStudentCourseService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 学生选课表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements IStudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private StudentCreditsMapper studentCreditsMapper;

    @Autowired
    private CourseDependenceMapper courseDependenceMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private CourseEmergencyMapper courseEmergencyMapper;

    @Autowired
    private CourseClassMapper courseClassMapper;

    @Autowired
    private CourseUtil courseUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result getStudentChooseLog(HttpServletRequest httpServletRequest, StudentChooseLogReq studentChooseLogReq) {
        // 获取学生已选课程表
        List<StudentCourse> studentCourses = studentCourseMapper.getStudentCourse(studentChooseLogReq.getStudentId(), studentChooseLogReq.getSemester());

        List<StudentChooseLogResp> studentCourseLogs = studentCourseMapper.getStudentChooseLogs(studentChooseLogReq);

        for (StudentChooseLogResp studentChooseLogResp : studentCourseLogs) {
            studentChooseLogResp.setIsChoose(studentChooseLogReq.getIsChosen());
        }

        courseUtil.setPlaceTimeAndIsConflict2(studentCourseLogs, studentCourses);

        return Result.success(studentCourseLogs);
    }

    @Override
    @Transactional
    public Result chooseCourse(HttpServletRequest httpServletRequest, StudentCourse studentCourse) {
        // 只有已经确定了学分充足，没有冲突的时候才可以进入选课
        String token = httpServletRequest.getHeader("token");
        HashMap<String, String> hashMap = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstant.STUDENT_TOKEN_PREFIX + token), HashMap.class);
        Long studentId = studentCourse.getStudentId();
        Integer classId = Integer.valueOf(hashMap.get("classId"));
        Integer semester = studentCourse.getSemester();

        // 遍历该课程的应急设置
        List<CourseEmergency> courseEmergencyList = courseEmergencyMapper.selectByCourseId(studentCourse.getCourseClassId());
        Integer year = classMapper.selectById(classId).getYear();
        for (CourseEmergency courseEmergency : courseEmergencyList) {
            // 如果课程只开设给某个班级而且学生所在班级与允许选择班级不同则抛出异常
            if (courseEmergency.getOnlyToClass() != null && !courseEmergency.getOnlyToClass().equals(classId)) {
                return Result.fail(ErrorCodeEnums.CONDITION_NOT_SATISFIED);
            }
            // 如果课程只开设给某个年级而且学生所在班级与允许选择年级不同则抛出异常
            if (courseEmergency.getOnlyToGrade() != null && !courseEmergency.getOnlyToGrade().equals(year)) {
                return Result.fail(ErrorCodeEnums.CONDITION_NOT_SATISFIED);
            }
        }

        // 查询课程班信息
        CourseClass courseClass = courseClassMapper.selectById(studentCourse.getCourseClassId());
        // 通过课程id查询依赖课程
        List<CourseDependence> courseDependenceList = courseDependenceMapper.selectCourseDependence(courseClass.getCourseId());
        // 遍历判断是否已经选择了(之前学期就已选择)依赖课程
        for (CourseDependence courseDependence : courseDependenceList) {
            StudentCourse isCourseChoose = studentCourseMapper.getIsCourseChoose(courseDependence.getPreCourseId(), studentId, semester);
            if (isCourseChoose == null) {
                return Result.fail(ErrorCodeEnums.PRECOURSE_NOT_CHOOSE);
            }
        }

        // 更新所选学分
        StudentCredits studentCredits = studentCreditsMapper.selectByStudentAndSemester(studentId, semester);
        studentCredits.setChooseSubjectCredit(studentCredits.getChooseSubjectCredit().add(studentCourse.getCredits()));
        studentCreditsMapper.updateById(studentCredits);

        // 增加课程选择人数
        courseClass.setChoosingNum(courseClass.getChoosingNum() + 1);
        courseClassMapper.updateById(courseClass);

        // 新增选课
        StudentCourse chooseCourse = studentCourseMapper.selectByStudentAndSemesterAndCourseClass(studentCourse.getStudentId(), studentCourse.getSemester(), studentCourse.getCourseClassId());
        if (chooseCourse == null) {
            // 第一次选择
            studentCourse.setId(SnowflakeUtil.nextId());
            studentCourseMapper.insert(studentCourse);
            return Result.success(studentCourse);
        }
        // 已选择过该课
        chooseCourse.setIsDeleted(false);
        studentCourseMapper.updateById(chooseCourse);
        return Result.success(chooseCourse);
    }

    @Override
    @Transactional
    public Result unChooseCourse(HttpServletRequest httpServletRequest, StudentCourse studentCourse) {

        // 查询到选择的记录
        StudentCourse chooseCourse = studentCourseMapper.selectByStudentAndSemesterAndCourseClass(studentCourse.getStudentId(), studentCourse.getSemester(), studentCourse.getCourseClassId());

        // 设置退选
        chooseCourse.setIsDeleted(true);
        studentCourseMapper.updateById(chooseCourse);

        // 恢复课程容量
        CourseClass courseClass = courseClassMapper.selectById(studentCourse.getCourseClassId());
        courseClass.setChoosingNum(courseClass.getChoosingNum() - 1);
        courseClassMapper.updateById(courseClass);

        // 恢复学分
        StudentCredits studentCredits = studentCreditsMapper.selectByStudentAndSemester(studentCourse.getStudentId(), studentCourse.getSemester());
        studentCredits.setChooseSubjectCredit(studentCredits.getChooseSubjectCredit().subtract(studentCourse.getCredits()));
        studentCreditsMapper.updateById(studentCredits);

        return Result.success();
    }

    @Override
    public void downloadStudentCourse(HttpServletRequest httpServletRequest, HttpServletResponse response, StudentChooseLogReq studentChooseLogReq) throws IOException {
        try {
            // 设置响应类型为excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String filename = studentChooseLogReq.getStudentId() + "-" + studentChooseLogReq.getSemester() + URLEncoder.encode("选课.xlsx", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            /*
             * 设置响应头以及文件名称
             *      Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
             *      浏览器接收到头时，它会激活文件下载对话框
             *      attachment 附件
             *      filename 附件名
             */
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            List<StudentCourse> studentCourse = studentCourseMapper.getStudentCourse(studentChooseLogReq.getStudentId(), studentChooseLogReq.getSemester());
            EasyExcel.write(response.getOutputStream(), StudentCourse.class).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("学生选课").doWrite(studentCourse);
        } catch (IOException e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(JSON.toJSONString(Result.fail(ErrorCodeEnums.DOWNLOAD_ERROR)));
        }
    }
}
