package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 学生选课表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_student_course")
@ApiModel(value = "StudentCourse对象", description = "学生选课表")
public class StudentCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("学生学号")
    private Integer studentId;

    @ApiModelProperty("课程编号")
    private Integer courseId;

    @ApiModelProperty("逻辑删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
