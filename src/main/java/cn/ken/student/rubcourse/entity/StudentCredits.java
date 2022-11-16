package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
    private Integer grade;

    @ApiModelProperty("最高主修学分")
    private Integer maxSubjectCredit;

    @ApiModelProperty("已选主修学分")
    private Integer chooseSubjectCredit;

    @ApiModelProperty("最高辅修学分")
    private Integer maxMinorCredit;

    @ApiModelProperty("已选辅修学分")
    private Integer chooseMinorCredit;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
