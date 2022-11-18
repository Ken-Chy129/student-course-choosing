package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.service.ICourseGeneralTypeService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 通识课类别表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/courseGeneralType")
public class CourseGeneralTypeController {
    
    @Autowired
    private ICourseGeneralTypeService courseGeneralTypeService;
    
    @GetMapping("list")
    @ApiModelProperty("查看通识选修课类别表")
    public Result getCourseGeneralTypeList(HttpServletRequest httpServletRequest) {
        return courseGeneralTypeService.getCourseGeneralTypeList(httpServletRequest);
    }

    @PostMapping("addCourseGeneralType")
    @ApiModelProperty("增加通识选修课类别")
    public Result addCourseGeneralType(HttpServletRequest httpServletRequest, String generalTypeName) {
        return courseGeneralTypeService.addCourseGeneralType(httpServletRequest, generalTypeName);
    }

}
