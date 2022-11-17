package cn.ken.student.rubcourse.service.impl;

import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.entity.ChooseRound;
import cn.ken.student.rubcourse.mapper.ChooseRoundMapper;
import cn.ken.student.rubcourse.service.IChooseRoundService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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
    public Result getChooseRound(HttpServletRequest httpServletRequest) {
        
        return Result.success(chooseRoundMapper.selectById(202512));
    }

    @Override
    public Result addChooseRound(HttpServletRequest httpServletRequest, ChooseRound chooseRound) {
        Integer id = Integer.valueOf(chooseRound.getYear().toString() + chooseRound.getSemester().toString() + chooseRound.getRoundNo().toString());
        chooseRound.setId(id);
        chooseRoundMapper.insert(chooseRound);
        return Result.success(chooseRound);
    }
}
