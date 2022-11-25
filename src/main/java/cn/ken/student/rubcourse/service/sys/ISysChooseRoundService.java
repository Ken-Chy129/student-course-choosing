package cn.ken.student.rubcourse.service.sys;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.dto.req.ChooseRoundListReq;
import cn.ken.student.rubcourse.entity.ChooseRound;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 选课轮次 服务类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
public interface ISysChooseRoundService extends IService<ChooseRound> {

    Result getPresentRound(HttpServletRequest httpServletRequest);

    Result getRoundList(HttpServletRequest httpServletRequest, ChooseRoundListReq chooseRoundListReq);

    Result addChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound);

    Result updateChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound);

    Result removeChooseRound(HttpServletRequest httpServletRequest, Integer chooseRoundId);
}
