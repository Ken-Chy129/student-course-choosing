package cn.ken.student.rubcourse.dto;

import lombok.Data;

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
public class CourseDependencyAddReq implements Serializable {
    
    private String courseId;
    
    private List<String> preCourseIdList;
}
