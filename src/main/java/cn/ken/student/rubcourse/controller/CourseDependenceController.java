package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.CourseDependencyAddReq;
import cn.ken.student.rubcourse.service.ICourseDependenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "课程依赖管理")
public class CourseDependenceController {
    
    @Autowired
    private ICourseDependenceService courseDependenceService;
    
    @GetMapping("getCourseDependence")
    @ApiOperation("查询课程依赖")
    public Result getCourseDependence(HttpServletRequest httpServletRequest, String courseId) {
        return courseDependenceService.getCourseDependence(httpServletRequest, courseId);
    }
    
    @PostMapping("add")
    @ApiOperation("新增课程依赖")
    public Result addCourseDependence(HttpServletRequest httpServletRequest, @RequestBody CourseDependencyAddReq courseDependencyAddReq) {
        return courseDependenceService.addCourseDependence(httpServletRequest, courseDependencyAddReq);
    }

}
