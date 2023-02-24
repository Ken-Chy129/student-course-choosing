package cn.ken.student.rubcourse.model.dto.req;

import cn.ken.student.rubcourse.common.entity.Page;
import lombok.*;

import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 16:33
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassListReq extends Page implements Serializable {
    
    private String collegeName;

    private String departmentName;
    
    private String subjectName;
    
    private String searchContent;
    
    private Integer year;
}
