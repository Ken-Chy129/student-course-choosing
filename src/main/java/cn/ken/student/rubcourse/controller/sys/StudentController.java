package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.StudentChooseLogReq;
import cn.ken.student.rubcourse.dto.req.StudentOnConditionReq;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.entity.StudentCredits;
import cn.ken.student.rubcourse.service.IStudentCourseService;
import cn.ken.student.rubcourse.service.IStudentCreditsService;
import cn.ken.student.rubcourse.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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
    
    @Autowired
    private IStudentCreditsService studentCreditsService;

    @Autowired
    private IStudentCourseService studentCourseService;
    
    @PostMapping("add")
    @ApiOperation("新增学生")
    public Result addStudent(HttpServletRequest httpServletRequest, @RequestBody @Valid Student student) throws Exception {
        return studentService.addStudent(httpServletRequest, student);
    }
    
    @PostMapping("batchAdd")
    @ApiOperation("批量新增学生")
    public Result batchAddStudent(HttpServletRequest httpServletRequest, MultipartFile file) throws IOException {
        return studentService.batchAddStudent(httpServletRequest, file);
    }
    
    @GetMapping("/{id}")
    @ApiOperation("通过学号查询学生具体信息")
    @Deprecated
    public Result getStudentById(HttpServletRequest httpServletRequest, @PathVariable("id") Long id) throws Exception {
        return studentService.getStudentById(httpServletRequest, id);
    }

    @GetMapping("condition")
    @ApiOperation("具体条件查询")
    public Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentOnConditionReq studentOnConditionReq) throws Exception {
        return studentService.getStudentOnCondition(httpServletRequest, studentOnConditionReq);
    }

    @GetMapping("getStudentCredits")
    @ApiOperation("查看学生学分")
    public Result getStudentCredits(HttpServletRequest httpServletRequest, Integer studentId) {
        return studentCreditsService.getStudentCredits(httpServletRequest, studentId);
    }

    @PostMapping("updateStudentCredits")
    @ApiOperation("修改学生学分")
    public Result updateStudentCredits(HttpServletRequest httpServletRequest, @RequestBody StudentCredits studentCredits) {
        return studentCreditsService.updateStudentCredits(httpServletRequest, studentCredits);
    }

    @GetMapping("getStudentChooseLog")
    @ApiOperation("查询学生选课日志")
    public Result getStudentChooseLog(HttpServletRequest httpServletRequest, StudentChooseLogReq studentChooseLogReq) {
        return studentCourseService.getStudentChooseLog(httpServletRequest, studentChooseLogReq);
    }

    @GetMapping("downloadStudentCourse")
    @ApiOperation("下载学生已选课程excel文件")
    public void downloadStudentCourse(HttpServletRequest httpServletRequest, HttpServletResponse response, StudentChooseLogReq studentChooseLogReq) throws IOException {
        studentCourseService.downloadStudentCourse(httpServletRequest, response, studentChooseLogReq);
    }

}
