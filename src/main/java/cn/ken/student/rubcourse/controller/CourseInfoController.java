package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.CourseInfoAddReq;
import cn.ken.student.rubcourse.dto.CourseInfoListReq;
import cn.ken.student.rubcourse.entity.CourseInfo;
import cn.ken.student.rubcourse.service.ICourseInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程信息表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/courseInfo")
public class CourseInfoController {
    
    @Autowired
    private ICourseInfoService courseInfoService;

    @GetMapping("list")
    @ApiOperation("下拉框课程名(输入id或课程名)")
    public Result getCourseInfoList(HttpServletRequest httpServletRequest, String searchContent) {
        return courseInfoService.getCourseInfoList(httpServletRequest, searchContent);
    }

    @GetMapping("page")
    @ApiOperation("前台分页显示课程详细信息")
    public Result getCourseInfoPage(HttpServletRequest httpServletRequest, CourseInfoListReq courseInfoListReq) {
        return courseInfoService.getCourseInfoPage(httpServletRequest, courseInfoListReq);
    }
    
    @PostMapping("addCourseInfo")
    @ApiOperation("添加课程信息")
    public Result addCourseInfo(HttpServletRequest httpServletRequest, CourseInfoAddReq courseInfoAddReq) {
        return courseInfoService.addCourseInfo(httpServletRequest, courseInfoAddReq);
    }

    @PostMapping("removeCourseInfo")
    @ApiOperation("删除课程信息")
    public Result removeCourseInfo(HttpServletRequest httpServletRequest, @RequestParam List<String> courseInfoIds) {
        return courseInfoService.removeCourseInfo(httpServletRequest, courseInfoIds);
    }

}
