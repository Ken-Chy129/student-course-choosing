package cn.ken.student.rubcourse.dto.req;

import cn.ken.student.rubcourse.common.entity.Page;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/16 21:28
 */
@Getter
@Setter
public class StudentReq extends Page implements Serializable {
    
    Integer id;
    
    Integer gender;
    
    Integer status;
    
}
