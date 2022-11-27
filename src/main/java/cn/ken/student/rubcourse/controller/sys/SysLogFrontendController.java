package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.sys.req.SysLogPageReq;
import cn.ken.student.rubcourse.service.sys.ISysFrontendLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前台日志表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/frontendLog")
@Api(tags = "前台日志管理")
public class SysLogFrontendController {
    
    @Autowired
    private ISysFrontendLogService sysFrontendLogService;

    @GetMapping("page")
    @ApiOperation("查看前台日志")
    public Result getFrontendLogPage(HttpServletRequest httpServletRequest, SysLogPageReq sysLogPageReq) {
        return sysFrontendLogService.getFrontendLogPage(httpServletRequest, sysLogPageReq);
    }
    

}
