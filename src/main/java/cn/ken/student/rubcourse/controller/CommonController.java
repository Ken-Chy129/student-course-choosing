package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.constant.*;
import cn.ken.student.rubcourse.common.entity.Result;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 14:39
 */
@RestController
@RequestMapping("common")
public class CommonController {
    
    @GetMapping("getCampusList")
    @ApiModelProperty("获取校区列表")
    public Result getCampusList() {
        return Result.success(CampusConstant.INSTANCE.toString());
    }

    @GetMapping("getCourseTypeList")
    @ApiModelProperty("获取课程类别表")
    public Result getCourseTypeList() {
        return Result.success(CourseTypeConstant.INSTANCE.toString());
    }

    @GetMapping("getExamTypeList")
    @ApiModelProperty("获取考试类型列表")
    public Result getExamTypeList() {
        return Result.success(ExamTypeConstant.INSTANCE.toString());
    }
    @GetMapping("getGeneralTypeList")
    @ApiModelProperty("获取通识课类别表")
    public Result getGeneralTypeList() {
        return Result.success(GeneralTypeConstant.INSTANCE.toString());
    }

    @GetMapping("getLanguageTypeList")
    @ApiModelProperty("获取授课语言类型列表")
    public Result getLanguageTypeList() {
        return Result.success(LanguageTypeConstant.INSTANCE.toString());
    }
    
}
