package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.chooseRound.ChooseRoundListReq;
import cn.ken.student.rubcourse.entity.ChooseRound;
import cn.ken.student.rubcourse.service.IChooseRoundService;
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
@RequestMapping("/round")
public class ChooseRoundController {

    @Autowired
    private IChooseRoundService chooseRoundService;
    
    @GetMapping("/presentRound")
    public Result getPresentRound(HttpServletRequest httpServletRequest) throws Exception {
        return chooseRoundService.getPresentRound(httpServletRequest);
    }

    @GetMapping("/roundList")
    public Result getRoundList(HttpServletRequest httpServletRequest, ChooseRoundListReq chooseRoundListReq) throws Exception {
        return chooseRoundService.getRoundList(httpServletRequest, chooseRoundListReq);
    }
    
    @PostMapping("addChooseRound")
    public Result addChooseRound(HttpServletRequest httpServletRequest, @Valid ChooseRound chooseRound) throws Exception {
        return chooseRoundService.addChooseRound(httpServletRequest, chooseRound);
    }

    @PutMapping("updateChooseRound")
    public Result updateChooseRound(HttpServletRequest httpServletRequest, @Valid ChooseRound chooseRound) throws Exception {
        return chooseRoundService.updateChooseRound(httpServletRequest, chooseRound);
    }
    
    @DeleteMapping("removeChooseRound")
    public Result removeChooseRound(HttpServletRequest httpServletRequest, Integer chooseRoundId) throws Exception {
        return chooseRoundService.removeChooseRound(httpServletRequest, chooseRoundId);
    }
    
}
