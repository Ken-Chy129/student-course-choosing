package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.SysLogPageReq;
import cn.ken.student.rubcourse.service.sys.ISysBackendLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 后台日志表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/backendLog")
@Api(tags = "后台日志管理")
public class SysLogBackendController {
    
    @Autowired
    private ISysBackendLogService sysBackendLogService;
    
    @GetMapping("page")
    @ApiOperation("查看后台日志")
    public Result getBackendLogPage(HttpServletRequest httpServletRequest, SysLogPageReq sysLogPageReq) {
        return sysBackendLogService.getBackendLogPage(httpServletRequest, sysLogPageReq);
    }
    

}
