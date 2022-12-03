package cn.ken.student.rubcourse.dto.resp;

import cn.ken.student.rubcourse.entity.CourseTimeplace;
import io.swagger.annotations.ApiModelProperty;
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
 * @date 2022/11/19 21:09
 */
@Data
public class CourseClassInfoResp implements Serializable {
    
    private Long id;
    
    private String courseId;
    
    private String courseName;
    
    private String campus;
    
    private String placeTime;
    
    private String college;
    
    private String type;
    
    private String generalType;
    
    private Boolean isMooc;
    
    private String language;
    
    private Integer choosingNum;
    
    private Integer capacity;
    
    private BigDecimal credit;
    
    private String examType;

    private String examTime;
    
    private String teacher;
    
    private Boolean isConflict;
    
    private Boolean isChoose;
    
    @ApiModelProperty(hidden = true)
    private List<CourseTimeplace> courseTimeplaceList;
    
}
