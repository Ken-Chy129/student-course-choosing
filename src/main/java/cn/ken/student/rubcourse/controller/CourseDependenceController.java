package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.CourseDependencyAddReq;
import cn.ken.student.rubcourse.service.ICourseDependenceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 课程依赖表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/courseDependence")
public class CourseDependenceController {
    
    @Autowired
    private ICourseDependenceService courseDependenceService;
    
    @GetMapping("getCourseDependence")
    @ApiOperation("查询课程依赖")
    public Result getCourseDependence(HttpServletRequest httpServletRequest, String courseId) {
        return courseDependenceService.getCourseDependence(httpServletRequest, courseId);
    }
    
    @PostMapping("addCourseDependence")
    @ApiOperation("新增课程依赖")
    public Result addCourseDependence(HttpServletRequest httpServletRequest, CourseDependencyAddReq courseDependencyAddReq) {
        return courseDependenceService.addCourseDependence(httpServletRequest, courseDependencyAddReq);
    }

}
