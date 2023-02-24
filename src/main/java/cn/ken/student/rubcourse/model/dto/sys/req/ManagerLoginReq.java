package cn.ken.student.rubcourse.model.dto.sys.req;

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
 * @date 2022/11/22 11:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerLoginReq implements Serializable {
    
    private String username;
    
    private String password;
}
