package cn.ken.student.rubcourse.controller.frontend;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.StudentLoginReq;
import cn.ken.student.rubcourse.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/student")
@Api(tags = "登录注销")
public class LoginController {
    
    @Autowired
    private IStudentService studentService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(HttpServletRequest httpServletRequest, @RequestBody StudentLoginReq studentLoginReq) throws Exception {
        return studentService.login(httpServletRequest, studentLoginReq);
    }
    
    @GetMapping("/getCode")
    @ApiOperation("获取验证码")
    public Result getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Long studentId) throws Exception {
        return studentService.getCode(httpServletRequest, httpServletResponse, studentId);
    }

    @GetMapping("/github/url")
    @ApiOperation("获取github登录地址")
    public Result getGithubUrl(HttpServletRequest httpServletRequest) throws Exception {
        return studentService.getGithubUrl(httpServletRequest);
    }

    @GetMapping("/github/callback")
    @ApiOperation("github回调登录")
    public Result githubCallback(HttpServletRequest httpServletRequest, String code, String state) throws Exception {
        return studentService.githubCallback(httpServletRequest, code, state);
    }

    @GetMapping("logout")
    @ApiOperation("退出登录")
    public Result logout(HttpServletRequest httpServletRequest, Long token) {
        return studentService.logout(httpServletRequest, token);
    }

}
