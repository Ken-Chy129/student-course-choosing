package cn.ken.student.rubcourse.dto.chooseRound;

import cn.ken.student.rubcourse.common.entity.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/18 17:09
 */
@Getter
@Setter
public class ChooseRoundListReq extends Page {

    
    Integer presentRoundId;
    
    Boolean showAll;
}
