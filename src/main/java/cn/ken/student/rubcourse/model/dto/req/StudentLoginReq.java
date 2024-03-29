package cn.ken.student.rubcourse.model.dto.req;

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
 * @date 2022/11/20 17:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentLoginReq implements Serializable {
    
    private Long id;
    
    private String password;
    
}
