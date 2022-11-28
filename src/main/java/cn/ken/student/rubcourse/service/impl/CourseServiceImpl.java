package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.PageUtil;
import cn.ken.student.rubcourse.dto.req.CourseInfoAddReq;
import cn.ken.student.rubcourse.dto.resp.CourseNameListResp;
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
    public Result addCourseInfo(HttpServletRequest httpServletRequest, CourseInfoAddReq courseInfoAddReq) {
//        CourseClass courseClass = getCourseInfo(courseInfoAddReq);
//        for (int i=0; i<courseInfoAddReq.getDurationList().size(); i++) {
//            CourseTimeplace courseTimeplace = new CourseTimeplace();
//            courseTimeplace.setId(SnowflakeUtil.nextId());
//            courseTimeplace.setCourseClassId(courseClass.getId());
//            courseTimeplace.setDurationTime(courseInfoAddReq.getDurationList().get(i));
//            courseTimeplace.setPlace(courseInfoAddReq.getPlaceList().get(i));
//            courseTimeplace.setWeekDay(courseInfoAddReq.getWeekDayList().get(i));
//            courseTimeplace.setDayNo(DayNoConstant.INSTANCE.get(courseInfoAddReq.getDayNoList().get(i)));
//            courseTimeplaceMapper.insert(courseTimeplace);
//        }
//        for (String preCourseId : courseInfoAddReq.getPreCourseIdList()) {
//            CourseDependence courseDependence = new CourseDependence();
//            courseDependence.setId(SnowflakeUtil.nextId());
//            courseDependence.setCourseId(courseClass.getId());
//            courseDependence.setPreCourseId(preCourseId);
//            courseDependenceMapper.insert(courseDependence);
//        }
//        courseMapper.insert(courseClass);
        return Result.success();
    }

    @Override
    public Result removeCourseInfo(HttpServletRequest httpServletRequest, List<String> courseInfoIds) {
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
        return Result.success();
    }

    private CourseClass getCourseInfo(CourseInfoAddReq courseInfoAddReq) {
//        CourseClass courseClass = new CourseClass();
//        courseClass.setCourseName(courseInfoAddReq.getCourseName());
//        courseClass.setType(CourseTypeConstant.INSTANCE.get(courseInfoAddReq.getType()));
//        courseClass.setCampus(CampusConstant.INSTANCE.get(courseInfoAddReq.getCampus()));
//        courseClass.setCapacity(courseInfoAddReq.getCapacity());
//        courseClass.setCredit(courseInfoAddReq.getCredit());
//        courseClass.setCollege(collegeMapper.selectById(courseInfoAddReq.getCollege()).getCollegeName());
//        if (courseInfoAddReq.getGeneralType() != null) {
//            courseClass.setGeneralType(GeneralTypeConstant.INSTANCE.get(courseInfoAddReq.getGeneralType()));
//        }
//        courseClass.setIsMooc(courseInfoAddReq.getIsMooc());
//        courseClass.setExamTime(courseInfoAddReq.getExamTime());
//        courseClass.setExamType(ExamTypeConstant.INSTANCE.get(courseInfoAddReq.getExamType()));
//        courseClass.setLanguage(LanguageTypeConstant.INSTANCE.get(courseInfoAddReq.getLanguage()));
//        courseClass.setTeacher(courseInfoAddReq.getTeacher());
//        String courseNum = redisTemplate.opsForValue().get("course_num");
//        assert courseNum != null;
//        redisTemplate.opsForValue().set("courseNum", String.valueOf(Integer.parseInt(courseNum)+1));
//        String id = courseInfoAddReq.getCampus() + (courseInfoAddReq.getCollege().toString().length() == 1 ? "0" : "") + courseInfoAddReq.getCollege().toString() + courseInfoAddReq.getType().toString() + (courseInfoAddReq.getGeneralType() == null ? "0" : String.valueOf(courseInfoAddReq.getGeneralType()+1)) + courseNum;
//        courseClass.setId(id);
//        return courseClass;
        return null;
    }
}
