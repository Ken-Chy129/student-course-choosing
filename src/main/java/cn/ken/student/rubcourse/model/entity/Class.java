package cn.ken.student.rubcourse.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 班级表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_class")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Class对象", description = "班级表")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty("学院名称")
    private String collegeName;

    @ApiModelProperty("系名")
    private String departmentName;

    @ApiModelProperty("专业名")
    private String subjectName;

    @ApiModelProperty("年份")
    private Integer year;

    @ApiModelProperty("班级序号")
    private Integer classNo;

    @ApiModelProperty("班级名称")
    private String className;

    @ApiModelProperty("毕业所需学分")
    private Integer graduationCredits;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
