package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 教师表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_teacher")
@ApiModel(value = "Teacher对象", description = "教师表")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("教师编号")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("所属院系")
    private Integer collegeId;

    @ApiModelProperty("状态(0:正常,1:离校)")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
