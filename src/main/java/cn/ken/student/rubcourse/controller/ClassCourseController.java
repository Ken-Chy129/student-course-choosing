package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.entity.ClassCourse;
import cn.ken.student.rubcourse.service.IClassCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 方案内课程 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/classCourse")
public class ClassCourseController {

    @Autowired
    private IClassCourseService classCourseService;

    @PostMapping("addClassCourse")
    @ApiOperation("添加班级方案内课程")
    public Result addClassCourse(HttpServletRequest httpServletRequest, ClassCourse classCourse) {
        return classCourseService.addClassCourse(httpServletRequest, classCourse);
    }
    
    
}
