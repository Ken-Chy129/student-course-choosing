package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.service.ICollegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 学院表 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/college")
@Api(tags = "学院管理")
public class CollegeController {

    @Autowired
    private ICollegeService collegeService;
    
    @GetMapping("list")
    @ApiOperation("获取学院表")
    public Result getCollegeList(HttpServletRequest httpServletRequest) {
        return collegeService.getCollegeList(httpServletRequest);
    }

    @PostMapping("addCollege")
    @ApiOperation("新增学院")
    public Result addCollege(HttpServletRequest httpServletRequest, String collegeName) {
        return collegeService.addCollege(httpServletRequest, collegeName);
    }
}
