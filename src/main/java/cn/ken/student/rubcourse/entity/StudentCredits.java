package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 学生学分表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_student_credits")
@ApiModel(value = "StudentCredits对象", description = "学生学分表")
public class StudentCredits implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("学生学号")
    private Long studentId;

    @ApiModelProperty("学期")
    private Integer semester;

    @ApiModelProperty("最高主修学分")
    private BigDecimal maxSubjectCredit;

    @ApiModelProperty("已选主修学分")
    private BigDecimal chooseSubjectCredit;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
