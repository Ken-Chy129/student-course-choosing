package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.CourseInfoAddReq;
import cn.ken.student.rubcourse.dto.CourseInfoListReq;
import cn.ken.student.rubcourse.entity.CourseInfo;
import cn.ken.student.rubcourse.service.ICourseInfoService;
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
    public Result getCourseInfoList(HttpServletRequest httpServletRequest, CourseInfoListReq courseInfoListReq) {
        return courseInfoService.getCourseInfoList(httpServletRequest, courseInfoListReq);
    }
    
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(HttpServletRequest httpServletRequest, CourseInfoAddReq courseInfoAddReq) {
        return courseInfoService.addCourseInfo(httpServletRequest, courseInfoAddReq);
    }

    @PostMapping("removeCourseInfo")
    public Result removeCourseInfo(HttpServletRequest httpServletRequest, @RequestParam List<String> courseInfoIds) {
        return courseInfoService.removeCourseInfo(httpServletRequest, courseInfoIds);
    }

}
