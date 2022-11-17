package cn.ken.student.rubcourse.controller;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.ChooseRound;
import cn.ken.student.rubcourse.service.IChooseRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 选课轮次 前端控制器
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/chooseRound")
public class ChooseRoundController {

    @Autowired
    private IChooseRoundService chooseRoundService;
    
    @GetMapping("/")
    public Result getChooseRound(HttpServletRequest httpServletRequest) throws Exception {
        return chooseRoundService.getChooseRound(httpServletRequest);
    }
    
    @PostMapping
    public Result addChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound) throws Exception {
        return chooseRoundService.addChooseRound(httpServletRequest, chooseRound);
    }
    
}
