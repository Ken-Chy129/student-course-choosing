package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.ManagerLoginReq;
import cn.ken.student.rubcourse.entity.SysManager;
import cn.ken.student.rubcourse.service.ISysManagerService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/sysManager")
@Api(tags = "管理员")
public class SysManagerController {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private ISysManagerService sysManagerService;
    
    @PostMapping("login")
    public Result login(HttpServletRequest httpServletRequest, ManagerLoginReq managerLoginReq) {
        return sysManagerService.login(httpServletRequest, managerLoginReq);
    }

}
