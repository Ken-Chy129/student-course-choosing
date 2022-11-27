package cn.ken.student.rubcourse.dto.resp;

import cn.ken.student.rubcourse.entity.CourseTimeplace;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/28 0:10
 */
@Data
public class StudentChooseLogResp implements Serializable {
    
    private Integer courseId;
    
    private String courseName;
    
    private String teacher;
    
    private String placeTime;
    
    private BigDecimal credits;
    
    private String type;
    
    private String language;
    
    private Integer chooseNum;
    
    private Boolean isConflict;
    
    private List<CourseTimeplace> courseTimeplaceList;
}
