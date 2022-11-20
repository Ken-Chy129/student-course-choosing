package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.StudentCourse;
import cn.ken.student.rubcourse.service.IStudentCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 学生选课表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/studentCourse")
public class StudentCourseController {

    @Autowired
    private IStudentCourseService studentCourseService;
    
    @GetMapping
    @ApiOperation("")
    public Result getStudentCourse(HttpServletRequest httpServletRequest) {
        return null;
    }

    @PostMapping("chooseCourse")
    @ApiOperation("学生选课")
    public Result addStudentCourse(HttpServletRequest httpServletRequest, StudentCourse studentCourse) {
        return null;
    }
}
