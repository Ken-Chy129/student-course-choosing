package cn.ken.student.rubcourse.controller.frontend;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.model.dto.req.StudentChooseLogReq;
import cn.ken.student.rubcourse.model.entity.StudentCourse;
import cn.ken.student.rubcourse.service.IStudentCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    
    @GetMapping("getStudentChooseLog")
    @ApiOperation("查询学生选课日志")
    public Result getStudentChooseLog(HttpServletRequest httpServletRequest, StudentChooseLogReq studentChooseLogReq) {
        return studentCourseService.getStudentChooseLog(httpServletRequest, studentChooseLogReq);
    }

    @PostMapping("chooseCourse")
    @ApiOperation("学生选课")
    public Result chooseCourse(HttpServletRequest httpServletRequest, @RequestBody StudentCourse studentCourse) throws BusinessException {
        return studentCourseService.chooseCourse(httpServletRequest, studentCourse);
    }

    @PostMapping("unChooseCourse")
    @ApiOperation("学生退选")
    public Result unChooseCourse(HttpServletRequest httpServletRequest, @RequestBody StudentCourse studentCourse) {
        return studentCourseService.unChooseCourse(httpServletRequest, studentCourse);
    }
    
}
