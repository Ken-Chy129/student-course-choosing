package cn.ken.student.rubcourse.dto.sys.req;

import cn.ken.student.rubcourse.entity.Course;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/28 17:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseAddReq implements Serializable {
    
    @ApiModelProperty("课程信息")
    private Course course;

    @ApiModelProperty("先修课程id")
    private List<String> preCourseIdList;
}
