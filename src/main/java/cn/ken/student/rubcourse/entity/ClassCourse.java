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
 * 方案内课程
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_class_course")
@ApiModel(value = "ClassCourse对象", description = "方案内课程")
public class ClassCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("班级编号")
    private Integer classId;

    @ApiModelProperty("课程编号")
    private Integer courseId;

    @ApiModelProperty("推荐选课时间")
    private Integer commendedTime;

    @ApiModelProperty("是否必修")
    private Boolean isMust;

    @ApiModelProperty("逻辑删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
}
