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
 * 课程应急设置
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_course_emergency")
@ApiModel(value = "CourseEmergency对象", description = "课程应急设置")
public class CourseEmergency implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("课程编号")
    private String courseId;

    @ApiModelProperty("仅开放给班级")
    private Integer onlyToClass;

    @ApiModelProperty("只开放给年级")
    private Integer onlyToGrade;

    @ApiModelProperty("逻辑删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
