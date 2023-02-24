package cn.ken.student.rubcourse.model.dto.req;

import cn.ken.student.rubcourse.common.entity.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class StudentOnConditionReq extends Page implements Serializable {
    
    String name;
    
    Integer id;
    
    Integer gender;
    
    Integer status;
    
    Long classId;
    
}
