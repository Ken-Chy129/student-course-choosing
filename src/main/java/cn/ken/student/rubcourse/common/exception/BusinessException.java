package cn.ken.student.rubcourse.common.exception;

import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/17 15:20
 */
@Getter
@Setter
public class BusinessException extends Exception {
    
    private String code;
    
    private String desc;
    
    public BusinessException(ErrorCodeEnums errorCodeEnums) {
        this.code = errorCodeEnums.getCode();
        this.desc = errorCodeEnums.getDesc();
    }

    public BusinessException(String desc) {
        this.code = ErrorCodeEnums.ERROR.getCode();
        this.desc = desc;
    }
}
