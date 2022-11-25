package cn.ken.student.rubcourse.dto.req;

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
 * @date 2022/11/19 15:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDependencyAddReq implements Serializable {
    
    private String courseId;
    
    private List<String> preCourseIdList;
}
