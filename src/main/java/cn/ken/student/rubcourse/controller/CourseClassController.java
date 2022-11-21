package cn.ken.student.rubcourse.controller;



import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.AllCourseListReq;
import cn.ken.student.rubcourse.service.CourseClassService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * <p>课程信息表(SccCourseInfo)表控制层</p>
 * </pre>
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date  ${DATE} ${TIME}
 */
@RestController
@RequestMapping("/courseClass")
public class CourseClassController {
    
    @Autowired
    private CourseClassService courseClassService;

    @GetMapping("allCoursePage")
    @ApiOperation("前台分页显示全校课程详细信息")
    public Result getAllCourseInfoPage(HttpServletRequest httpServletRequest, AllCourseListReq allCourseListReq) {
        return courseClassService.getAllCourseInfoPage(httpServletRequest, allCourseListReq);
    }
}

