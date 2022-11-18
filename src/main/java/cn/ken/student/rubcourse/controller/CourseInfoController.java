package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.CourseInfoAddReq;
import cn.ken.student.rubcourse.dto.CourseInfoListReq;
import cn.ken.student.rubcourse.entity.CourseInfo;
import cn.ken.student.rubcourse.service.ICourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    
    

}
