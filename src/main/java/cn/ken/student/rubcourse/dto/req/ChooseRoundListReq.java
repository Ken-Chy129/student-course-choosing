package cn.ken.student.rubcourse.dto.req;

import cn.ken.student.rubcourse.common.entity.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
@AllArgsConstructor
@NoArgsConstructor
public class ChooseRoundListReq extends Page implements Serializable {

    @ApiModelProperty("当前选课轮次Id")
    Integer presentRoundId;
    
    @ApiModelProperty("是否展示所有选课轮次,是则可以不填当前选课轮次id")
    Boolean showAll;
}
