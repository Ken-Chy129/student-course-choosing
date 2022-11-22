package cn.ken.student.rubcourse.dto.req;

import lombok.Data;

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
public class ManagerLoginReq implements Serializable {
    
    private String username;
    
    private String password;
}
