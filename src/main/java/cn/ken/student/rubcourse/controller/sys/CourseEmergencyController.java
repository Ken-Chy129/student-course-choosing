package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.CourseEmergency;
import cn.ken.student.rubcourse.service.ICourseEmergencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 课程应急设置 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/courseEmergency")
@Api(tags = "课程紧急设置")
public class CourseEmergencyController {
    
    @Autowired
    private ICourseEmergencyService courseEmergencyService;
    
    @PostMapping("add")
    @ApiOperation("新增课程应急设置")
    public Result addCourseEmergency(HttpServletRequest httpServletRequest, CourseEmergency courseEmergency) {
        return courseEmergencyService.addCourseEmergency(httpServletRequest, courseEmergency);
    }
    
    @DeleteMapping("delete/{id}")
    @ApiOperation("删除课程应急设置")
    public Result addCourseEmergency(HttpServletRequest httpServletRequest, @PathVariable("id") Long id) {
        return courseEmergencyService.deleteCourseEmergency(httpServletRequest, id);
    }

}
