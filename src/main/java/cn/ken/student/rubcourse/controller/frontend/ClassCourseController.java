package cn.ken.student.rubcourse.controller.frontend;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.ClassCourseListReq;
import cn.ken.student.rubcourse.entity.ClassCourse;
import cn.ken.student.rubcourse.service.IClassCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 方案内课程 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/classCourse")
@Api(tags = "方案内课程管理")
public class ClassCourseController {

    @Autowired
    private IClassCourseService classCourseService;

    @PostMapping("add")
    @ApiOperation("添加班级方案内课程")
    public Result addClassCourse(HttpServletRequest httpServletRequest, @RequestBody ClassCourse classCourse) {
        return classCourseService.addClassCourse(httpServletRequest, classCourse);
    }
    
    @PutMapping("update")
    @ApiOperation("修改班级方案内课程")
    public Result updateClassCourse(HttpServletRequest httpServletRequest, @RequestBody ClassCourse classCourse) {
        return classCourseService.updateClassCourse(httpServletRequest, classCourse);
    }

    @DeleteMapping("remove")
    @ApiOperation("删除班级方案内课程")
    public Result removeClassCourse(HttpServletRequest httpServletRequest, @RequestParam List<Integer> ids) {
        return classCourseService.removeClassCourse(httpServletRequest, ids);
    }

    @GetMapping("/recommendedCoursePage")
    @ApiOperation("查看推荐班课程")
    public Result getRecommendedCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq) {
        return classCourseService.getRecommendedCoursePage(httpServletRequest, classCourseListReq);
    }

    @GetMapping("/classCoursePage")
    @ApiOperation("查看班级方案内课程")
    public Result getClassCoursePage(HttpServletRequest httpServletRequest, ClassCourseListReq classCourseListReq) {
        return classCourseService.getClassCoursePage(httpServletRequest, classCourseListReq);
    }
    
}
