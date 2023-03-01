package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.constant.ComboBoxConstant;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.SnowflakeUtil;
import cn.ken.student.rubcourse.mapper.*;
import cn.ken.student.rubcourse.model.dto.req.CourseClassAddReq;
import cn.ken.student.rubcourse.model.dto.resp.CourseNameListResp;
import cn.ken.student.rubcourse.model.dto.sys.req.CourseAddReq;
import cn.ken.student.rubcourse.model.dto.sys.req.CoursePageReq;
import cn.ken.student.rubcourse.model.entity.*;
import cn.ken.student.rubcourse.service.ICourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
    private CollegeMapper collegeMapper;

    @Autowired
    private CourseDependenceMapper courseDependenceMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private StudentCreditsMapper studentCreditsMapper;

    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    
    @Autowired
    private RedisTemplate<String, BigDecimal> numbRedisTemplate;

    @Override
    public Result getCourseList(HttpServletRequest httpServletRequest, String searchContent) {
        List<CourseNameListResp> courseList = courseMapper.getCourseList(searchContent);
        return Result.success(courseList);
    }

    @Override
    public Result getCoursePage(HttpServletRequest httpServletRequest, CoursePageReq req) {
        String searchContent = req.getSearchContent();
        Page<Course> page = new Page<>(req.getPageNo(), req.getPageSize());
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.or(qw -> qw
                        .eq(StringUtils.isNotBlank(searchContent), Course::getId, searchContent)
                        .or()
                        .like(StringUtils.isNotBlank(searchContent), Course::getCourseName, searchContent))
                .eq(StringUtils.isNotBlank(req.getCampus()), Course::getCampus, req.getCampus())
                .eq(StringUtils.isNotBlank(req.getCollege()), Course::getCollege, req.getCollege())
                .eq(req.getCredit() != null, Course::getCredit, req.getCredit())
                .eq(StringUtils.isNotBlank(req.getType()), Course::getType, req.getType());
        page = courseMapper.selectPage(page, queryWrapper);
        return Result.success(page);
    }

    @Override
    @Transactional
    public Result addCourse(HttpServletRequest httpServletRequest, CourseAddReq courseAddReq) {
        String courseNum = redisTemplate.opsForValue().get("course_num");
        assert courseNum != null;
        redisTemplate.opsForValue().set(RedisConstant.COURSE_NUM, String.valueOf(Integer.parseInt(courseNum) + 1));
        Course course = courseAddReq.getCourse();
        String id = course.getCampus() + (course.getCollege().length() == 1 ? "0" : "") + course.getCollege() + course.getType() + ((course.getGeneralType() == null) ? "0" : course.getGeneralType()) + courseNum;
        course.setId(id);
        course.setCampus(ComboBoxConstant.CampusConstant.INSTANCE.get(Integer.parseInt(course.getCampus())));
        course.setCollege(collegeMapper.selectById(Integer.parseInt(course.getCollege())).getCollegeName());
        course.setType(ComboBoxConstant.CourseTypeConstant.INSTANCE.get(Integer.parseInt(course.getType())));
        if (course.getGeneralType() != null) {
            course.setGeneralType(ComboBoxConstant.GeneralTypeConstant.INSTANCE.get(Integer.parseInt(course.getGeneralType())));
        }
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
        CourseTimeplace courseTimeplace = new CourseTimeplace();
        courseTimeplace.setId(SnowflakeUtil.nextId());
        courseTimeplace.setCourseClassId(courseClass.getId());
        courseTimeplace.setDurationTime(courseClassAddReq.getDuration());
        courseTimeplace.setPlace(courseClassAddReq.getPlace());
        courseTimeplace.setWeekDay(courseClassAddReq.getWeekDay());
        courseTimeplace.setDayNo(ComboBoxConstant.DayNoConstant.INSTANCE.get(courseClassAddReq.getDayNo()));
        courseTimeplaceMapper.insert(courseTimeplace);

        courseClassMapper.insert(courseClass);
        Course course = courseMapper.selectById(courseClass.getCourseId());
        course.setClassNum(course.getClassNum() + 1);
        courseMapper.updateById(course);
        return Result.success(courseClass);
    }

    @Override
    @Transactional
    public Result removeCourse(HttpServletRequest httpServletRequest, String id) {
        // 删除所有课程班
        List<CourseClass> courseClassList = courseClassMapper.selectByCourseId(id);
        for (CourseClass courseClass : courseClassList) {
            removeCourseClass(courseClass.getId());
        }
        // 删除课程依赖关系
        List<CourseDependence> courseDependenceList = courseDependenceMapper.selectCourseDependence(id);
        for (CourseDependence courseDependence : courseDependenceList) {
            courseDependence.setIsDeleted(true);
            courseDependenceMapper.updateById(courseDependence);
        }
        // 删除课程
        courseMapper.deleteById(id);
        return Result.success();
    }

    @Override
    @Transactional
    public Result removeCourseClass(HttpServletRequest httpServletRequest, Long id) {
        removeCourseClass(id);
        return Result.success();
    }

    @Override
    public void preheatCourseClassInfo() {
        List<CourseClass> courseClasses = courseClassMapper.selectList(new LambdaQueryWrapper<CourseClass>().eq(CourseClass::getIsDeleted, 0));
        for (CourseClass courseClass : courseClasses) {
            // 缓存课程已选人数
            numbRedisTemplate.opsForValue().set(RedisConstant.COURSE_CHOSEN + courseClass.getId(), BigDecimal.valueOf(courseClass.getChoosingNum()));
            // 缓存课程总容量
            numbRedisTemplate.opsForValue().set(RedisConstant.COURSE_MAX + courseClass.getId(), BigDecimal.valueOf(courseClass.getCapacity()));
        }
    }

    private CourseClass getCourseInfo(CourseClassAddReq courseClassAddReq) {
        CourseClass courseClass = new CourseClass();
        courseClass.setId(SnowflakeUtil.nextId());
        courseClass.setCourseId(courseClassAddReq.getCourseId());
        courseClass.setIsMooc(courseClassAddReq.getIsMooc());
        courseClass.setLanguage(ComboBoxConstant.LanguageTypeConstant.INSTANCE.get(courseClassAddReq.getLanguage()));
        courseClass.setChoosingNum(0);
        courseClass.setCapacity(courseClassAddReq.getCapacity());
        courseClass.setExamType(ComboBoxConstant.ExamTypeConstant.INSTANCE.get(courseClassAddReq.getExamType()));
        courseClass.setExamTime(courseClassAddReq.getExamTime());
        courseClass.setTeacher(courseClassAddReq.getTeacher());
        return courseClass;
    }

    private void removeCourseClass(Long id) {
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
