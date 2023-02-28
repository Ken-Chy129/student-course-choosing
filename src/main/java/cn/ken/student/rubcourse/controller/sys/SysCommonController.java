package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.constant.*;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.util.ConstantUtil;
import cn.ken.student.rubcourse.service.IChooseRoundService;
import cn.ken.student.rubcourse.service.ICollegeService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 14:39
 */
@RestController
@RequestMapping("/sys/common")
@Api(tags = "通用下拉框选项")
public class SysCommonController {

    @Autowired
    private ICollegeService collegeService;

    @Autowired
    private IChooseRoundService chooseRoundService;

    @GetMapping("list")
    @ApiOperation("获取学院表")
    public Result getCollegeList(HttpServletRequest httpServletRequest) {
        return collegeService.getCollegeList(httpServletRequest);
    }

    @GetMapping("present")
    @ApiOperation("通过当前时间自动获取当前选课轮次")
    public Result getPresentRound(HttpServletRequest httpServletRequest) throws Exception {
        return Result.success(JSON.toJSONString(chooseRoundService.getPresentRound()));
    }

    @GetMapping("getCampusList")
    @ApiOperation("获取校区列表")
    public Result getCampusList() {
        return Result.success(ConstantUtil.getHashMap(ComboBoxConstant.CampusConstant.INSTANCE));
    }

    @GetMapping("getCourseTypeList")
    @ApiOperation("获取课程类别表")
    public Result getCourseTypeList() {
        return Result.success(ConstantUtil.getHashMap(ComboBoxConstant.CourseTypeConstant.INSTANCE));
    }

    @GetMapping("getExamTypeList")
    @ApiOperation("获取考试类型列表")
    public Result getExamTypeList() {
        return Result.success(ConstantUtil.getHashMap(ComboBoxConstant.ExamTypeConstant.INSTANCE));
    }

    @GetMapping("getGeneralTypeList")
    @ApiOperation("获取通识课类别表")
    public Result getGeneralTypeList() {
        return Result.success(ConstantUtil.getHashMap(ComboBoxConstant.GeneralTypeConstant.INSTANCE));
    }

    @GetMapping("getLanguageTypeList")
    @ApiOperation("获取授课语言类型列表")
    public Result getLanguageTypeList() {
        return Result.success(ConstantUtil.getHashMap(ComboBoxConstant.LanguageTypeConstant.INSTANCE));
    }

    @GetMapping("getDayNoList")
    @ApiOperation("获取课程节数列表")
    public Result getDayNoList() {
        return Result.success(ConstantUtil.getHashMap(ComboBoxConstant.DayNoConstant.INSTANCE));
    }

}
