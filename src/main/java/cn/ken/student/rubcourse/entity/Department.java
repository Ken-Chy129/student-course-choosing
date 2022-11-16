package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 系表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_department")
@ApiModel(value = "Department对象", description = "系表")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("学院id")
    private Integer collegeId;

    @ApiModelProperty("系id")
    private Integer departmentId;

    @ApiModelProperty("系名")
    private String departmentName;
    
}
