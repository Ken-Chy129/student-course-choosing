package cn.ken.student.rubcourse.controller.system;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.ClassListReq;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.service.sys.ISysClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/class")
@Api(tags = "班级管理")
public class SysClassController {
    
    @Autowired
    private ISysClassService sysClassService;
    
    @GetMapping("list")
    @ApiOperation("获取班级列表")
    public Result getClassList(HttpServletRequest httpServletRequest, ClassListReq classListReq) {
        return sysClassService.getClassList(httpServletRequest, classListReq);
    }

    @PostMapping("add")
    @ApiOperation("添加班级")
    public Result addClass(HttpServletRequest httpServletRequest, @RequestBody Class clazz) {
        return sysClassService.addClass(httpServletRequest, clazz);
    }

}
