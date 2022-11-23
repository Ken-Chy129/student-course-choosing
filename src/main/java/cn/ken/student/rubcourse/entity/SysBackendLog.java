package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_sys_backend_log")
@ApiModel(value = "SysBackendLog对象", description = "后台日志表")
public class SysBackendLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("请求类型(0-正常,1-异常)")
    private Integer type;
    
    @ApiModelProperty("请求ip")
    private String requestIp;

    @ApiModelProperty("学生学号")
    private Integer studentId;

    @ApiModelProperty("请求接口")
    private String requestApi;

    @ApiModelProperty("请求体")
    private String requestBody;

    @ApiModelProperty("响应体")
    private String responseBody;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public SysBackendLog(Long id, Integer type, String requestIp, Integer studentId, String requestApi, String requestBody, String responseBody) {
        this.id = id;
        this.type = type;
        this.requestIp = requestIp;
        this.studentId = studentId;
        this.requestApi = requestApi;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
    }
}
