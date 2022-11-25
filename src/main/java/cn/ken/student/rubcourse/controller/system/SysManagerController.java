package cn.ken.student.rubcourse.controller.system;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.ManagerLoginReq;
import cn.ken.student.rubcourse.entity.SysManager;
import cn.ken.student.rubcourse.service.sys.ISysManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统管理员表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/manager")
@Api(tags = "管理员")
public class SysManagerController {
    
    @Autowired
    private ISysManagerService sysManagerService;
    
    @PostMapping("login")
    @ApiOperation("后台系统登入")
    public Result login(HttpServletRequest httpServletRequest, @RequestBody ManagerLoginReq managerLoginReq) {
        return sysManagerService.login(httpServletRequest, managerLoginReq);
    }

    @GetMapping("logout")
    @ApiOperation("后台系统登出")
    public Result logout(HttpServletRequest httpServletRequest, Long token) {
        return sysManagerService.logout(httpServletRequest, token);
    }

    @GetMapping("list")
    @ApiOperation("管理员列表-需要超管权限")
    public Result getManagerList(HttpServletRequest httpServletRequest) {
        return sysManagerService.getManagerList(httpServletRequest);
    }

    @PostMapping("add")
    @ApiOperation("新增管理员-需要超管权限")
    public Result addManager(HttpServletRequest httpServletRequest, @RequestBody SysManager sysManager) throws Exception {
        return sysManagerService.addManager(httpServletRequest, sysManager);
    }

    @PostMapping("update")
    @ApiOperation("修改管理员权限(状态)-需要超管权限")
    public Result updateManager(HttpServletRequest httpServletRequest, @RequestBody SysManager sysManager) {
        return sysManagerService.updateManager(httpServletRequest, sysManager);
    }
}
