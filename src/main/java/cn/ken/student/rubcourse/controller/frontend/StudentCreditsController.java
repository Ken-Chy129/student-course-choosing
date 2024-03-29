package cn.ken.student.rubcourse.controller.frontend;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.service.IStudentCreditsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    
    @GetMapping("get")
    @ApiOperation("查看学生某个学期学分")
    public Result getStudentCredits(HttpServletRequest httpServletRequest, Integer studentId, Integer semester) {
        return studentCreditsService.getStudentCreditOnSemester(httpServletRequest, studentId, semester);
    }
    
}
