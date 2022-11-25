package cn.ken.student.rubcourse.dto.sys.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/25 20:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentAddReq implements Serializable {

    private Integer collegeId;
    
    private String departmentName;
}
