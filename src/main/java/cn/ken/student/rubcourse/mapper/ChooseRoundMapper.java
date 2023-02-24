package cn.ken.student.rubcourse.mapper;

import cn.ken.student.rubcourse.model.entity.ChooseRound;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 选课轮次 Mapper 接口
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Mapper
public interface ChooseRoundMapper extends BaseMapper<ChooseRound> {

    List<ChooseRound> selectTimePeriod(ChooseRound chooseRound);
}
