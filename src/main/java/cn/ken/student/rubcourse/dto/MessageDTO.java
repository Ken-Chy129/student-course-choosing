package cn.ken.student.rubcourse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/22 20:51
 */
@Data
@AllArgsConstructor
public class MessageDTO implements Serializable {
    
    @ApiModelProperty(hidden = true)
    private Long id;
    
    private Long studentId;
    
    private String message;
}
