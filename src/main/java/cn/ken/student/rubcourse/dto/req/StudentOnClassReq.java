package cn.ken.student.rubcourse.dto.req;

import cn.ken.student.rubcourse.common.entity.Page;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/27 23:29
 */
@Data
public class StudentOnClassReq extends Page implements Serializable {
    
    private Long classId;
}
