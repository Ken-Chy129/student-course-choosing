package cn.ken.student.rubcourse.dto;

import cn.ken.student.rubcourse.common.entity.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/21 11:23
 */
@Getter
@Setter
public class ClassCourseRecommendedListReq extends Page implements Serializable {
    
    @ApiModelProperty("班级id")
    private Integer classId;

    @ApiModelProperty("是否已满")
    private Boolean isFull;

    @ApiModelProperty("课程性质")
    private String isMust;

    @ApiModelProperty("课程类别")
    private String type;
}
