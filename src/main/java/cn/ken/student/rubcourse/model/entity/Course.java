package cn.ken.student.rubcourse.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程信息表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_course")
@ApiModel(value = "Course对象", description = "课程表")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程编号")
    private String id;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("所在校区")
    private String campus;

    @ApiModelProperty("排课单位")
    private String college;

    @ApiModelProperty("课程类别[基础必修,专业必修,基础选修,专业选修,通识选修]")
    private String type;

    @ApiModelProperty("通识课类别")
    private String generalType;

    @ApiModelProperty("课程学分")
    private BigDecimal credit;
    
    @ApiModelProperty(value = "可选班级数", hidden = true)
    private Integer classNum;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
