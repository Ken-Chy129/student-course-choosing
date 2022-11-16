package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/rubcourse/student")
public class StudentController {
    
    @Autowired
    private IStudentService studentService;
    
    @PostMapping("add")
    public Result addStudent(Student student, HttpServletRequest httpServletRequest) {
        return studentService.addStudent(student, httpServletRequest);
    }

}
