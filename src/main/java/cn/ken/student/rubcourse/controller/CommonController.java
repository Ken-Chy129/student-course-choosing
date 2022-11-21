package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.constant.*;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.ConstantUtil;
import cn.ken.student.rubcourse.common.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 14:39
 */
@RestController
@RequestMapping("/common")
@Api(tags = "通用下拉框选项")
public class CommonController {
    
    @GetMapping("getCampusList")
    @ApiModelProperty("获取校区列表")
    public Result getCampusList() {
        return Result.success(ConstantUtil.getHashMap(CampusConstant.INSTANCE));
    }
    
    @GetMapping("getCourseTypeList")
    @ApiModelProperty("获取课程类别表")
    public Result getCourseTypeList() {
        return Result.success(ConstantUtil.getHashMap(CourseTypeConstant.INSTANCE));
    }

    @GetMapping("getExamTypeList")
    @ApiModelProperty("获取考试类型列表")
    public Result getExamTypeList() {
        return Result.success(ConstantUtil.getHashMap(ExamTypeConstant.INSTANCE));
    }
    @GetMapping("getGeneralTypeList")
    @ApiModelProperty("获取通识课类别表")
    public Result getGeneralTypeList() {
        return Result.success(ConstantUtil.getHashMap(GeneralTypeConstant.INSTANCE));
    }

    @GetMapping("getLanguageTypeList")
    @ApiModelProperty("获取授课语言类型列表")
    public Result getLanguageTypeList() {
        return Result.success(ConstantUtil.getHashMap(LanguageTypeConstant.INSTANCE));
    }
    
}
