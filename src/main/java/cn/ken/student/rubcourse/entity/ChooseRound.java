package cn.ken.student.rubcourse.entity;

import cn.ken.student.rubcourse.annotation.ValueInIntegers;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 选课轮次
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@AllArgsConstructor
@TableName("scc_choose_round")
@ApiModel(value = "ChooseRound对象", description = "选课轮次")
public class ChooseRound implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "选课轮次")
    private Integer id;

    @ApiModelProperty("年份")
    private Integer year;
    
    @ValueInIntegers(value = {1, 2}, message = "学期只能选择1或2")
    @ApiModelProperty("学期")
    private Integer semester;
    
    @ApiModelProperty("轮次")
    private Integer roundNo;

    @NotNull(message = "轮次时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("起始时间")
    private LocalDateTime startTime;

    @NotNull(message = "轮次时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("终止时间")
    private LocalDateTime endTime;

    @ApiModelProperty("提示信息")
    private String tips;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
