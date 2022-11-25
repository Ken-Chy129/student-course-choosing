package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统管理员表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_sys_manager")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SysManager对象", description = "系统管理员表")
public class SysManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("管理员名称")
    private String managerName;

    @ApiModelProperty("管理员类型(0-普通,1-超级)")
    private Integer type;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("密码盐")
    private String salt;

    @ApiModelProperty("手机号码")
    private String mobilePhone;

    @ApiModelProperty("上次登录时间")
    private LocalDateTime lastLogin;

    @ApiModelProperty("逻辑删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
