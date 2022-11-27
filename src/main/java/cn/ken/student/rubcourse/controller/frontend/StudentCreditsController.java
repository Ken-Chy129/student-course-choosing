package cn.ken.student.rubcourse.controller.frontend;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.StudentCredits;
import cn.ken.student.rubcourse.service.IStudentCreditsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 学生学分表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/studentCredits")
@Api(tags = "学生学分")
public class StudentCreditsController {

    @Autowired
    private IStudentCreditsService studentCreditsService;
    
    @GetMapping("getStudentCredits")
    @ApiOperation("查看学生学分")
    public Result getStudentCredits(HttpServletRequest httpServletRequest, Integer studentId, Integer semester) {
        return studentCreditsService.getStudentCredits(httpServletRequest, studentId, semester);
    }

    @PostMapping("updateStudentCredits")
    @ApiOperation("修改学生学分")
    public Result updateStudentCredits(HttpServletRequest httpServletRequest, @RequestBody StudentCredits studentCredits) {
        return studentCreditsService.updateStudentCredits(httpServletRequest, studentCredits);
    }

    @GetMapping("/getCredit")
    @ApiOperation("获取学分信息")
    public Result getCredit(HttpServletRequest httpServletRequest) throws Exception {
        return studentCreditsService.getCredit(httpServletRequest);
    }
}
