package cn.ken.student.rubcourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 专业表
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Data
@TableName("scc_subject")
@ApiModel(value = "Subject对象", description = "专业表")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("学院id")
    private Integer collegeId;

    @ApiModelProperty("系id")
    private Integer departmentId;

    @ApiModelProperty("专业id")
    private Integer subjectId;

    @ApiModelProperty("专业名")
    private String subjectName;

}
