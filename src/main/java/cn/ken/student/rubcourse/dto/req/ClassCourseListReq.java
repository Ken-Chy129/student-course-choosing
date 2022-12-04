package cn.ken.student.rubcourse.dto.req;

import cn.ken.student.rubcourse.common.entity.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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
@AllArgsConstructor
@NoArgsConstructor
public class ClassCourseListReq extends Page implements Serializable {
    
    private Long studentId;
    
    private Integer semester;
    
    private String searchContent;
    
    @ApiModelProperty("班级id")
    private Integer classId;
    
    @ApiModelProperty("推荐选课时间")
    private Integer recommendedTime;
    
    @ApiModelProperty("是否必修")
    private Boolean isMust;

    @ApiModelProperty("课程类别")
    private String type;
    
}
