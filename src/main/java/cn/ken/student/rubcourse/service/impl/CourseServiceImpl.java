package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.DayNoConstant;
import cn.ken.student.rubcourse.common.constant.ExamTypeConstant;
import cn.ken.student.rubcourse.common.constant.LanguageTypeConstant;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.dto.req.CourseClassAddReq;
import cn.ken.student.rubcourse.dto.resp.CourseNameListResp;
import cn.ken.student.rubcourse.dto.sys.req.CourseAddReq;
import cn.ken.student.rubcourse.dto.sys.req.CoursePageReq;
import cn.ken.student.rubcourse.entity.*;
import cn.ken.student.rubcourse.mapper.*;
import cn.ken.student.rubcourse.service.ICourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 课程信息表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private CourseMapper courseMapper;
    
    @Autowired
    private CourseTimeplaceMapper courseTimeplaceMapper;
    
    @Autowired
    private ChooseRoundMapper chooseRoundMapper;
    
    @Autowired
    private CourseClassMapper courseClassMapper;
    
    @Autowired
    private CourseDependenceMapper courseDependenceMapper;
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;
    
    @Autowired
    private StudentCreditsMapper studentCreditsMapper;
    
    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    @Override
    public Result getCourseList(HttpServletRequest httpServletRequest, String searchContent) {
        List<CourseNameListResp> courseList = courseMapper.getCourseList(searchContent);
        return Result.success(courseList);
    }

    @Override
    public Result getCoursePage(HttpServletRequest httpServletRequest, CoursePageReq coursePageReq) {
        List<Course> courseNameList = courseMapper.getCoursePage(coursePageReq);
        IPage<Course> page = PageUtil.getPage(new Page<>(), coursePageReq.getPageNo(), coursePageReq.getPageSize(), courseNameList);
        return Result.success(page);
    }

    @Override
    @Transactional
    public Result addCourse(HttpServletRequest httpServletRequest, CourseAddReq courseAddReq) {
        String courseNum = redisTemplate.opsForValue().get("course_num");
        assert courseNum != null;
        redisTemplate.opsForValue().set(RedisConstant.COURSE_NUM, String.valueOf(Integer.parseInt(courseNum)+1));
        Course course = courseAddReq.getCourse();
        String id = course.getCampus() + (course.getCollege().length() == 1 ? "0" : "") + course.getCollege() + course.getType() + (course.getGeneralType() == null ? "0" : course.getGeneralType()+1) + courseNum;
        course.setId(id);
        courseMapper.insert(course);

        for (String preCourseId : courseAddReq.getPreCourseIdList()) {
            CourseDependence courseDependence = new CourseDependence();
            courseDependence.setId(SnowflakeUtil.nextId());
            courseDependence.setCourseId(course.getId());
            courseDependence.setPreCourseId(preCourseId);
            courseDependenceMapper.insert(courseDependence);
        }
        return Result.success(course);
    }

    @Override
    @Transactional
    public Result addCourseClass(HttpServletRequest httpServletRequest, CourseClassAddReq courseClassAddReq) {
        CourseClass courseClass = getCourseInfo(courseClassAddReq);
        for (int i = 0; i< courseClassAddReq.getDurationList().size(); i++) {
            CourseTimeplace courseTimeplace = new CourseTimeplace();
            courseTimeplace.setId(SnowflakeUtil.nextId());
            courseTimeplace.setCourseClassId(courseClass.getId());
            courseTimeplace.setDurationTime(courseClassAddReq.getDurationList().get(i));
            courseTimeplace.setPlace(courseClassAddReq.getPlaceList().get(i));
            courseTimeplace.setWeekDay(courseClassAddReq.getWeekDayList().get(i));
            courseTimeplace.setDayNo(DayNoConstant.INSTANCE.get(courseClassAddReq.getDayNoList().get(i)));
            courseTimeplaceMapper.insert(courseTimeplace);
        }
        
        courseClassMapper.insert(courseClass);
        return Result.success(courseClass);
    }

    @Override
    @Transactional
    public Result removeCourse(HttpServletRequest httpServletRequest, String id) {        
        // 删除课程
        courseMapper.deleteById(id);
        // 删除所有课程班
        List<CourseClass> courseClassList = courseClassMapper.selectByCourseId(id);
        for (CourseClass courseClass : courseClassList) {
            removeClass(courseClass.getId());
        }
        // 删除课程依赖关系
        List<CourseDependence> courseDependenceList = courseDependenceMapper.selectCourseDependence(id);
        for (CourseDependence courseDependence : courseDependenceList) {
            courseDependence.setIsDeleted(true);
            courseDependenceMapper.updateById(courseDependence);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result removeCourseClass(HttpServletRequest httpServletRequest, Long id) {
        removeClass(id);
        return Result.success();
    }

    private CourseClass getCourseInfo(CourseClassAddReq courseClassAddReq) {
        CourseClass courseClass = new CourseClass();
        courseClass.setId(SnowflakeUtil.nextId());
        courseClass.setCourseId(courseClassAddReq.getCourseId());
        courseClass.setIsMooc(courseClassAddReq.getIsMooc());
        courseClass.setLanguage(LanguageTypeConstant.INSTANCE.get(courseClassAddReq.getLanguage()));
        courseClass.setChoosingNum(0);
        courseClass.setCapacity(courseClassAddReq.getCapacity());
        courseClass.setExamType(ExamTypeConstant.INSTANCE.get(courseClassAddReq.getExamType()));
        courseClass.setExamTime(courseClassAddReq.getExamTime());
        courseClass.setTeacher(courseClassAddReq.getTeacher());
        return courseClass;
    }
    
    private void removeClass(Long id) {
        // 更改课程班状态
        CourseClass courseClass = courseClassMapper.selectById(id);
        courseClass.setIsDeleted(true);
        String courseId = courseClass.getCourseId();
        courseClassMapper.updateById(courseClass);
        // 删除课程上课地点
        List<CourseTimeplace> courseTimeplaceList = courseTimeplaceMapper.selectByCourseClassId(id);
        for (CourseTimeplace courseTimeplace : courseTimeplaceList) {
            courseTimeplace.setIsDeleted(true);
            courseTimeplaceMapper.updateById(courseTimeplace);
        }
        // 减少课程的班级数量
        Course course = courseMapper.selectById(courseId);
        course.setClassNum(course.getClassNum() - 1);
        courseMapper.updateById(course);

        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<ChooseRound> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(ChooseRound::getStartTime, now)
                .gt(ChooseRound::getEndTime, now);
        ChooseRound chooseRound = chooseRoundMapper.selectOne(queryWrapper);
        // 当前属于选课期间
        if (chooseRound != null) {
            List<StudentCourse> studentCourseList = studentCourseMapper.selectByCourseClassAndSemester(id, chooseRound.getSemester());
            for (StudentCourse studentCourse : studentCourseList) {
                // 移除当前学期所有选择了该课程班的学生选课
                studentCourse.setIsDeleted(true);
                studentCourseMapper.updateById(studentCourse);
                // 修改这些学生的学分
                StudentCredits studentCredits = studentCreditsMapper.selectByStudentAndSemester(studentCourse.getStudentId(), chooseRound.getSemester());
                studentCredits.setChooseSubjectCredit(studentCredits.getChooseSubjectCredit().subtract(studentCourse.getCredits()));
                studentCreditsMapper.updateById(studentCredits);
                // 向这些学生发送通知
                SysNotice sysNotice = new SysNotice(SnowflakeUtil.nextId(), studentCourse.getStudentId(), "编号为" + id + "的课程班停止开课，系统已自动为您退选.", Short.valueOf("1"));
                sysNoticeMapper.insert(sysNotice);
            }
        }
    }
}
