package cn.ken.student.rubcourse.dto.resp;

import cn.ken.student.rubcourse.entity.CourseClass;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/27 16:39
 */
@Data
public class ClassCourseListResp {
    
    private Long id;

    private String courseId;

    private String courseName;

    private Integer classNum;

    private String type;

    private Boolean isMust;

    private String collegeName;

    private BigDecimal credit;
    
    private List<CourseClassInfoResp> courseClassInfoResps;
}
