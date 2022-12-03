package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.CourseClassAddReq;
import cn.ken.student.rubcourse.dto.req.CourseDependencyAddReq;
import cn.ken.student.rubcourse.dto.sys.req.CourseAddReq;
import cn.ken.student.rubcourse.dto.sys.req.CoursePageReq;
import cn.ken.student.rubcourse.entity.Course;
import cn.ken.student.rubcourse.service.ICourseClassService;
import cn.ken.student.rubcourse.service.ICourseDependenceService;
import cn.ken.student.rubcourse.service.ICourseService;
import cn.ken.student.rubcourse.service.impl.CourseDependenceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程信息表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/course")
@Api(tags = "课程管理")
public class CourseController {
    
    @Autowired
    private ICourseService courseService;
    
    @Autowired
    private ICourseDependenceService courseDependenceService;
    
    @Autowired
    private ICourseClassService courseClassService;

    @GetMapping("list")
    @ApiOperation("下拉框课程列表")
    public Result getCourseNameList(HttpServletRequest httpServletRequest, String searchContent) {
        return courseService.getCourseList(httpServletRequest, searchContent);
    }

    @GetMapping("page")
    @ApiOperation("课程分页显示")
    public Result getCoursePage(HttpServletRequest httpServletRequest, CoursePageReq coursePageReq) {
        return courseService.getCoursePage(httpServletRequest, coursePageReq);
    }

    @GetMapping("getCourseDependence")
    @ApiOperation("查询课程依赖")
    public Result getCourseDependence(HttpServletRequest httpServletRequest, String courseId) {
        return courseDependenceService.getCourseDependence(httpServletRequest, courseId);
    }
    
    @GetMapping("getCourseClass")
    @ApiOperation("查询课程班")
    public Result getCourseClass(HttpServletRequest httpServletRequest, String courseId) {
        return courseClassService.getCourseClass(httpServletRequest, courseId);
    }
    
    @PostMapping("addCourse")
    @ApiOperation("添加课程")
    public Result addCourse(HttpServletRequest httpServletRequest, @RequestBody CourseAddReq courseAddReq) {
        return courseService.addCourse(httpServletRequest, courseAddReq);
    }

    @PostMapping("addCourseClass")
    @ApiOperation("添加课程班")
    public Result addCourseClass(HttpServletRequest httpServletRequest, @RequestBody CourseClassAddReq courseClassAddReq) {
        return courseService.addCourseClass(httpServletRequest, courseClassAddReq);
    }
    
    @DeleteMapping("removeCourseClass")
    @ApiOperation("删除课程班")
    public Result removeCourseClass(HttpServletRequest httpServletRequest, Long id) {
        return courseService.removeCourseClass(httpServletRequest, id);
    }

    @PostMapping("addCourseDependence")
    @ApiOperation("新增课程依赖")
    public Result addCourseDependence(HttpServletRequest httpServletRequest, @RequestBody CourseDependencyAddReq courseDependencyAddReq) {
        return courseDependenceService.addCourseDependence(httpServletRequest, courseDependencyAddReq);
    }

    @DeleteMapping("removeCourseDependence")
    @ApiOperation("删除课程依赖")
    public Result removeCourseDependence(HttpServletRequest httpServletRequest, Long id) {
        return courseDependenceService.removeCourseDependence(httpServletRequest, id);
    }
    
    @PostMapping("removeCourse")
    @ApiOperation("删除课程")
    public Result removeCourseInfo(HttpServletRequest httpServletRequest, String id) {
        return courseService.removeCourse(httpServletRequest, id);
    }

}
