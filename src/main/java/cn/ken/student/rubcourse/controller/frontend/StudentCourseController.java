package cn.ken.student.rubcourse.controller.frontend;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.StudentCourse;
import cn.ken.student.rubcourse.service.IStudentCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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
@Api(tags = "学生选课")
public class StudentCourseController {

    @Autowired
    private IStudentCourseService studentCourseService;
    
    @GetMapping("getStudentChoose")
    @ApiOperation("查询学生选课")
    public Result getStudentChoose(HttpServletRequest httpServletRequest, Long studentId, Integer semester, Boolean isChosen) {
        return studentCourseService.getStudentChoose(httpServletRequest, studentId, semester, isChosen);
    }

    @PostMapping("chooseCourse")
    @ApiOperation("学生选课")
    public Result chooseCourse(HttpServletRequest httpServletRequest, @RequestBody StudentCourse studentCourse) {
        return studentCourseService.chooseCourse(httpServletRequest, studentCourse);
    }
}
