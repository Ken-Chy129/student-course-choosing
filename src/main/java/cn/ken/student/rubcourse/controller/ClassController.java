package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.ClassListReq;
import cn.ken.student.rubcourse.entity.Class;
import cn.ken.student.rubcourse.service.IClassService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/class")
public class ClassController {
    
    @Autowired
    private IClassService classService;
    
    @GetMapping("list")
    @ApiOperation("获取班级列表")
    public Result getClassList(HttpServletRequest httpServletRequest, ClassListReq classListReq) {
        return classService.getClassList(httpServletRequest, classListReq);
    }

    @PostMapping("addClass")
    @ApiOperation("添加班级")
    public Result addClass(HttpServletRequest httpServletRequest, Class clazz) {
        return classService.addClass(httpServletRequest, clazz);
    }

}
