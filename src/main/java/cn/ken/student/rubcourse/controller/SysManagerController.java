package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.SysManager;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
public class SysManagerController {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @PostMapping("login")
    public Result login() {
        SysManager sysManager = new SysManager();
        sysManager.setId(1);
        sysManager.setManagerName("root");
        sysManager.setType(1);
        redisTemplate.opsForValue().set("10000", JSON.toJSONString(sysManager));
        return Result.success();
    }

}
