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
 * @date 2022/11/20 14:54
 */
@Getter
@Setter
public class ClassCourseListReq extends Page implements Serializable {
    
    @ApiModelProperty("班级id")
    private Integer classId;
    
    @ApiModelProperty("推荐选课时间")
    private Integer recommendedTime;
    
    @ApiModelProperty("是否必修")
    private Boolean isMust;
    
}
