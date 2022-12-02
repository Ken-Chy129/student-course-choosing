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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    private CollegeMapper collegeMapper;
    
    @Autowired
    private CourseClassMapper courseClassMapper;
    
    @Autowired
    private CourseDependenceMapper courseDependenceMapper;
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;

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
        return Result.success();
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
        return null;
    }

    @Override
    public Result removeCourse(HttpServletRequest httpServletRequest, String id) {
//        LambdaUpdateWrapper<CourseClass> updateWrapper0 = new LambdaUpdateWrapper<>();
//        updateWrapper0.in(CourseClass::getId, courseInfoIds)
//                .set(CourseClass::getStatus, 1);
//        courseMapper.update(null, updateWrapper0);
//        LambdaUpdateWrapper<CourseTimeplace> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.in(CourseTimeplace::getCourseId, courseInfoIds)
//                .set(CourseTimeplace::getIsDeleted, true);
//        courseTimeplaceMapper.update(null, updateWrapper);
//        LambdaUpdateWrapper<CourseDependence> updateWrapper1 = new LambdaUpdateWrapper<>();
//        updateWrapper1.in(CourseDependence::getCourseId, courseInfoIds)
//                .set(CourseDependence::getIsDeleted, true);
//        courseDependenceMapper.update(null, updateWrapper1);
//        // todo:更新学生学分表以及删除学生选课表记录并发出通告
        
        // 删除课程
        
        // 删除所有课程班
        
        // 删除课程依赖关系
        
        return Result.success();
    }

    @Override
    public Result removeCourseClass(HttpServletRequest httpServletRequest, Integer id) {
        // 更改课程班状态
        
        // 删除课程上课地点
        
        // 减少课程的班级数量
        
        // 移除所有选择了该课程班的学生选课
        
        // 修改这些学生的学分
        
        // 向这些学生发送通知
        return null;
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
}
