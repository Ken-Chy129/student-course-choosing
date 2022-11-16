package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 选课轮次
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_choose_round")
@ApiModel(value = "ChooseRound对象", description = "选课轮次")
public class ChooseRound implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("选课轮次")
    private Integer id;

    @ApiModelProperty("起始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("终止时间")
    private LocalDateTime endTime;

    @ApiModelProperty("轮次")
    private Integer roundNo;

    @ApiModelProperty("提示信息")
    private String tips;
}
