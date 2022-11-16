package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.StudentReq;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Api("学生管理")
@RestController
@RequestMapping("/rubcourse/student")
public class StudentController {
    
    @Autowired
    private IStudentService studentService;
    
    @PostMapping("add")
    @ApiOperation("新增学生")
    public Result addStudent(HttpServletRequest httpServletRequest, Student student) {
        return studentService.addStudent(httpServletRequest, student);
    }
    
    @GetMapping("/{id}")
    @ApiOperation("通过学号查询学生具体信息")
    public Result getStudentById(HttpServletRequest httpServletRequest, @PathVariable("id") Long id) {
        return studentService.getStudentById(httpServletRequest, id);
    }

    @GetMapping("/class/{classId}")
    @ApiOperation("查询班级所有学生")
    public Result getStudentByClassId(HttpServletRequest httpServletRequest, @PathVariable("classId") Long classId, Integer pageNo, Integer pageSize) {
        return studentService.getStudentByClassId(httpServletRequest, classId, pageNo, pageSize);
    }

    @GetMapping("/condition")
    @ApiOperation("具体条件查询")
    public Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentReq studentReq) {
        return studentService.getStudentOnCondition(httpServletRequest, studentReq);
    }

}
