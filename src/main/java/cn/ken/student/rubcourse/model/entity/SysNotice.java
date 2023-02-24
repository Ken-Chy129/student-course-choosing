package cn.ken.student.rubcourse.model.entity;

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
 * 系统通知表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_sys_notice")
@ApiModel(value = "SysNotice对象", description = "系统通知表")
@AllArgsConstructor
@NoArgsConstructor
public class SysNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    @ApiModelProperty("接收方id,-1表示发送给全体")
    private Long studentId;

    @ApiModelProperty("消息体")
    private String message;

    @ApiModelProperty("状态")
    private Short status;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public SysNotice(Long id, Long studentId, String message, Short status) {
        this.id = id;
        this.studentId = studentId;
        this.message = message;
        this.status = status;
    }
}
