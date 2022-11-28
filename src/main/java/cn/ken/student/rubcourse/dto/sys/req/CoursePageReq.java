package cn.ken.student.rubcourse.dto.sys.req;

import cn.ken.student.rubcourse.common.entity.Page;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/28 16:12
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursePageReq extends Page implements Serializable {
    
    private String searchContent;
    
    private String campus;
    
    private String college;
    
    private BigDecimal credit;
    
    private String type;
}
