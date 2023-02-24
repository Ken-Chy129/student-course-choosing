package cn.ken.student.rubcourse.model.dto.resp;

import cn.ken.student.rubcourse.model.entity.CourseTimeplace;
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
    
    private Long courseClassId;
    
    private String courseName;
    
    private String teacher;
    
    private String placeTime;
    
    private BigDecimal credits;
    
    private String type;
    
    private String language;
    
    private Integer chooseNum;
    
    private Boolean isConflict;
    
    private Boolean isChoose;
    
    private Integer choosingNum;
    
    private Integer capacity;
    
    private Integer semester;
    
    private List<CourseTimeplace> courseTimeplaceList;
}
