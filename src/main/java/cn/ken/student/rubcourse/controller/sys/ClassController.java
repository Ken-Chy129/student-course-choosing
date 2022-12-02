package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.ClassCourseListReq;
import cn.ken.student.rubcourse.dto.req.ClassListReq;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.entity.ClassCourse;
import cn.ken.student.rubcourse.service.IClassCourseService;
import cn.ken.student.rubcourse.service.sys.IClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/class")
@Api(tags = "班级管理")
public class ClassController {
    
    @Autowired
    private IClassService classService;

    @Autowired
    private IClassCourseService classCourseService;
    
    @GetMapping("list")
    @ApiOperation("获取班级列表")
    public Result getClassList(HttpServletRequest httpServletRequest, ClassListReq classListReq) {
        return classService.getClassList(httpServletRequest, classListReq);
    }

    @PostMapping("add")
    @ApiOperation("添加班级")
    public Result addClass(HttpServletRequest httpServletRequest, @RequestBody Class clazz) {
        return classService.addClass(httpServletRequest, clazz);
    }

    @GetMapping("/classCoursePage")
    @ApiOperation("查看班级方案内课程")
    public Result getClassCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq) {
        return classCourseService.getClassCoursePage(httpServletRequest, classCourseListReq);
    }

    @PostMapping("addClassCourse")
    @ApiOperation("添加班级方案内课程")
    public Result addClassCourse(HttpServletRequest httpServletRequest, @RequestBody ClassCourse classCourse) {
        return classCourseService.addClassCourse(httpServletRequest, classCourse);
    }

    @PutMapping("updateClassCourse")
    @ApiOperation("修改班级方案内课程")
    public Result updateClassCourse(HttpServletRequest httpServletRequest, @RequestBody ClassCourse classCourse) {
        return classCourseService.updateClassCourse(httpServletRequest, classCourse);
    }

    @DeleteMapping("removeClassCourse")
    @ApiOperation("删除班级方案内课程")
    public Result removeClassCourse(HttpServletRequest httpServletRequest, Integer id) {
        return classCourseService.removeClassCourse(httpServletRequest, id);
    }
    
}
