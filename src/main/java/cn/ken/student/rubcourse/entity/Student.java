package cn.ken.student.rubcourse.entity;

import cn.ken.student.rubcourse.annotation.ValueInIntegers;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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

    @NotNull(message = "学号不得为空")
    @ApiModelProperty("学生学号")
    private Long id;
    
    @Length(min = 2, max = 50, message = "学生姓名长度必须大于2小于50")
    @ApiModelProperty("学生姓名")
    private String name;

    @ValueInIntegers(value = {0, 1, 2}, message = "性别只允许为:0-女,1-男")
    @ApiModelProperty("性别(0-女,1-男)")
    private Integer gender;

    @ApiModelProperty("班级代码")
    private Integer classId;

    @ValueInIntegers(value = {0, 1, 2}, message = "学生状态只允许为:0-正常,1-毕业,2-其他")
    @ApiModelProperty("学生状态(0-正常,1-毕业,2-其他)")
    private Integer status;

    @Email(message = "请输入正确的邮箱")
    @ApiModelProperty("学生邮箱")
    private String email;

    @Length(min = 11, max = 11, message = "请输入正确的手机号码")
    @ApiModelProperty("手机号码")
    private String phone;

    @Length(min = 18, max = 18, message = "请输入正确的身份证号码")
    @ApiModelProperty("身份证")
    private String idCard;
    
    @Length(min = 6, max = 18)
    @ApiModelProperty(value = "密码", name = "密码长度必须大于6小于18")
    private String password;
    
    @ApiModelProperty(value = "密码盐", hidden = true)
    private String salt;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
