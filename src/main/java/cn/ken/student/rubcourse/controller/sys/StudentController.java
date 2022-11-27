package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.StudentOnClassReq;
import cn.ken.student.rubcourse.dto.req.StudentOnConditionReq;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/student")
@Api(tags = "学生管理")
public class StudentController {
    
    @Autowired
    private IStudentService studentService;
    
    @PostMapping("add")
    @ApiOperation("新增学生")
    public Result addStudent(HttpServletRequest httpServletRequest, @RequestBody @Valid Student student) throws Exception {
        return studentService.addStudent(httpServletRequest, student);
    }
    
    @GetMapping("/{id}")
    @ApiOperation("通过学号查询学生具体信息")
    @Deprecated
    public Result getStudentById(HttpServletRequest httpServletRequest, @PathVariable("id") Long id) throws Exception {
        return studentService.getStudentById(httpServletRequest, id);
    }

    @GetMapping("class")
    @ApiOperation("查询班级所有学生")
    public Result getStudentByClassId(HttpServletRequest httpServletRequest, StudentOnClassReq studentOnClassReq) throws Exception {
        return studentService.getStudentByClassId(httpServletRequest, studentOnClassReq);
    }

    @GetMapping("condition")
    @ApiOperation("具体条件查询")
    public Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentOnConditionReq studentOnConditionReq) throws Exception {
        return studentService.getStudentOnCondition(httpServletRequest, studentOnConditionReq);
    }

}
