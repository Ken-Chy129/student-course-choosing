package cn.ken.student.rubcourse.service;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.model.dto.sys.req.ChooseRoundListReq;
import cn.ken.student.rubcourse.model.entity.ChooseRound;
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
public interface IChooseRoundService extends IService<ChooseRound> {

    ChooseRound getPresentRound() throws BusinessException;

    Result getRoundList(HttpServletRequest httpServletRequest, ChooseRoundListReq chooseRoundListReq);

    Result addChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound);

    Result updateChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound);

    Result removeChooseRound(HttpServletRequest httpServletRequest, Integer chooseRoundId);
}
