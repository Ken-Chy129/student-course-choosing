package cn.ken.student.rubcourse.dto.req;

import lombok.Data;

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
public class StudentLoginReq implements Serializable {
    
    private Long id;
    
    private String password;
    
    private String code;
}
