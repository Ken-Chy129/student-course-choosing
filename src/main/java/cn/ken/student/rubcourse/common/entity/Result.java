package cn.ken.student.rubcourse.common.entity;

import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/16 18:07
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String desc;

    private Object data;

    public Result(String code, String desc, Object data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public static Result success() {
        return new Result(ErrorCodeEnums.SUCCESS.getCode(), ErrorCodeEnums.SUCCESS.getDesc(), null);
    }

    public static Result success(Object data) {
        return new Result(ErrorCodeEnums.SUCCESS.getCode(), ErrorCodeEnums.SUCCESS.getDesc(), data);
    }

    public static Result fail() {
        return new Result(ErrorCodeEnums.ERROR.getCode(), ErrorCodeEnums.ERROR.getDesc(), null);
    }

    public static Result fail(ErrorCodeEnums enums) {
        return new Result(enums.getCode(), enums.getDesc(), null);
    }

    public static Result fail(String code, String desc) {
        return new Result(code, desc, null);
    }
    
    public static Result fail(Object exception) {
        return new Result(ErrorCodeEnums.ERROR.getCode(), ErrorCodeEnums.ERROR.getDesc(), exception);
    }
}
