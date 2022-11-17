package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程-时间地点表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_course_timeplace")
@ApiModel(value = "CourseTimeplace对象", description = "课程-时间地点表")
public class CourseTimeplace implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("课程编号")
    private Integer courseId;

    @ApiModelProperty("课程持续时间")
    private String durationTime;

    @ApiModelProperty("每周星期几上课")
    private Integer weekDay;

    @ApiModelProperty("每天第几节课上课")
    private Integer dayNo;

    @ApiModelProperty("上课地点")
    private String place;

    @ApiModelProperty("逻辑删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
