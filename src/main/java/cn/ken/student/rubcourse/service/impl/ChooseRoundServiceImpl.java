package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.dto.chooseRound.ChooseRoundListReq;
import cn.ken.student.rubcourse.entity.ChooseRound;
import cn.ken.student.rubcourse.mapper.ChooseRoundMapper;
import cn.ken.student.rubcourse.service.IChooseRoundService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 选课轮次 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class ChooseRoundServiceImpl extends ServiceImpl<ChooseRoundMapper, ChooseRound> implements IChooseRoundService {
    
    @Autowired
    private ChooseRoundMapper chooseRoundMapper;

    @Override
    public Result getPresentRound(HttpServletRequest httpServletRequest) {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<ChooseRound> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(ChooseRound::getStartTime, now)
                .gt(ChooseRound::getEndTime, now);
        ChooseRound chooseRound = chooseRoundMapper.selectOne(queryWrapper);
        return Result.success(Objects.requireNonNullElse(chooseRound, "当前不在选课轮次内"));
    }

    @Override
    public Result getRoundList(HttpServletRequest httpServletRequest, ChooseRoundListReq chooseRoundListReq) {
        Integer presentRoundId = chooseRoundListReq.getPresentRoundId();
        Boolean showAll = chooseRoundListReq.getShowAll();
        
        LambdaQueryWrapper<ChooseRound> queryWrapper = new LambdaQueryWrapper<>();
        if (!showAll) {
            Integer prefix = Integer.valueOf(presentRoundId.toString().substring(0, presentRoundId.toString().length() - 1));
            queryWrapper.likeRight(ChooseRound::getId, prefix);
        }
        List<ChooseRound> list = chooseRoundMapper.selectList(queryWrapper);
        
        IPage<ChooseRound> chooseRoundPage = new Page<>();
        Integer currentPage = chooseRoundListReq.getPageNo();
        Integer pageSize = chooseRoundListReq.getPageSize();
        chooseRoundPage.setTotal(list.size());
        chooseRoundPage.setCurrent(currentPage);
        chooseRoundPage.setPages((list.size() / pageSize) + ((list.size() % pageSize == 0) ? 0 : 1));
        chooseRoundPage.setRecords(list.subList(pageSize * (currentPage-1), pageSize * currentPage));
        return Result.success(chooseRoundPage);
    }

    @Override
    public Result addChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound) {
        if (chooseRound.getStartTime().isAfter(chooseRound.getEndTime())) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_UNAVAILABLE);
        }
        List<ChooseRound> list = chooseRoundMapper.selectTimePeriod(chooseRound);
        if (list.size() != 0) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_REPEAT);
        }
        Integer id = Integer.valueOf(chooseRound.getYear().toString() + chooseRound.getSemester().toString() + chooseRound.getRoundNo().toString());
        chooseRound.setId(id);
        try {
            chooseRoundMapper.insert(chooseRound);
        } catch (Exception e) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_UNAVAILABLE);
        }
        return Result.success();
    }

    @Override
    public Result updateChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound) {
        if (chooseRound.getStartTime().isAfter(chooseRound.getEndTime())) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_UNAVAILABLE);
        }
        List<ChooseRound> list = chooseRoundMapper.selectTimePeriod(chooseRound);
        if (list.size() > 1) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_REPEAT);
        } else if (list.size() == 1) {
            if (!list.get(0).getId().equals(chooseRound.getId())) {
                return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_TIME_REPEAT);
            }
        }
        try {
            chooseRoundMapper.updateById(chooseRound);
        } catch (Exception e) {
            return Result.fail(ErrorCodeEnums.CHOOSE_ROUND_UNAVAILABLE);
        }
        return Result.success();
    }

    @Override
    public Result removeChooseRound(HttpServletRequest httpServletRequest, Integer chooseRoundId) {
        try {
            chooseRoundMapper.deleteById(chooseRoundId);
        } catch (Exception e) {
            return Result.fail();
        }
        return Result.success();
    }
}
