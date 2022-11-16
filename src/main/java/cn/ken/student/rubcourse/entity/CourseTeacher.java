package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程-教师表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_course_teacher")
@ApiModel(value = "CourseTeacher对象", description = "课程-教师表")
public class CourseTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("教师编号")
    private Integer teacherId;

    @ApiModelProperty("课程编号")
    private Integer courseId;

}
