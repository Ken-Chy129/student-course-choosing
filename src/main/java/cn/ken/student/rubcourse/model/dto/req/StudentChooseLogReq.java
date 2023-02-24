package cn.ken.student.rubcourse.model.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/29 13:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentChooseLogReq implements Serializable {
    
    private Long studentId;
    
    private Integer semester;
    
    private Boolean isChosen;
    
    private Short type;
}
