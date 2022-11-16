package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value = "SysManager对象", description = "系统管理员表")
public class SysManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

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

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
