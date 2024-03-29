package cn.ken.student.rubcourse.controller.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.model.dto.sys.req.ChooseRoundListReq;
import cn.ken.student.rubcourse.model.entity.ChooseRound;
import cn.ken.student.rubcourse.service.IChooseRoundService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 选课轮次 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/sys/round")
@Api(tags = "选课轮次管理")
public class ChooseRoundController {

    @Autowired
    private IChooseRoundService chooseRoundService;
    
    @GetMapping("present")
    @ApiOperation("通过当前时间自动获取当前选课轮次")
    public Result getPresentRound(HttpServletRequest httpServletRequest) throws Exception {
        return Result.success(JSON.toJSONString(chooseRoundService.getPresentRound()));
    }

    @GetMapping("list")
    @ApiOperation("获取选课轮次列表")
    public Result getRoundList(HttpServletRequest httpServletRequest, ChooseRoundListReq chooseRoundListReq) throws Exception {
        return chooseRoundService.getRoundList(httpServletRequest, chooseRoundListReq);
    }
    
    @PostMapping("add")
    @ApiOperation("添加选课轮次，时间段不可重复")
    public Result addChooseRound(HttpServletRequest httpServletRequest, @RequestBody @Valid ChooseRound chooseRound) throws Exception {
        return chooseRoundService.addChooseRound(httpServletRequest, chooseRound);
    }

    @PutMapping("update")
    @ApiOperation("修改选课轮次信息")
    @Deprecated
    public Result updateChooseRound(HttpServletRequest httpServletRequest, @RequestBody @Valid ChooseRound chooseRound) throws Exception {
        return chooseRoundService.updateChooseRound(httpServletRequest, chooseRound);
    }
    
    @DeleteMapping("remove")
    @ApiOperation("删除选课轮次")
    public Result removeChooseRound(HttpServletRequest httpServletRequest, Integer chooseRoundId) throws Exception {
        return chooseRoundService.removeChooseRound(httpServletRequest, chooseRoundId);
    }
    
}
