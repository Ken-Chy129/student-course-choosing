package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_student")
@ApiModel(value = "Student对象", description = "学生表")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty("学生学号")
    private Long id;

    @Length(min = 2, max = 50)
    @ApiModelProperty("学生姓名")
    private String name;

    @Pattern(regexp = "[0,1]")
    @ApiModelProperty("性别(0-女,1-男)")
    private Integer gender;

    @ApiModelProperty("班级代码")
    private Integer classId;

    @Pattern(regexp = "[0,1,2]")
    @ApiModelProperty("学生状态(0-正常,1-毕业,2-其他)")
    private Integer status;

    @Email
    @ApiModelProperty("学生邮箱")
    private String email;

    @Length(min = 6, max = 18)
    @ApiModelProperty("密码")
    private String password;
    
    @ApiModelProperty(value = "密码盐", hidden = true)
    private String salt;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    private LocalDateTime updateTime;

}
