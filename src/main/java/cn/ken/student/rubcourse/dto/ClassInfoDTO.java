package cn.ken.student.rubcourse.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/21 20:25
 */
@Data
public class ClassInfoDTO implements Serializable {
    
    private String courseId;
    
    private String courseName;
    
    private Integer classNum;
    
    private String type;
    
    private Boolean isMust;
    
    private String collegeName;
    
    private BigDecimal credit;
    
}
