package cn.ken.student.rubcourse.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/16 18:07
 */
@Getter
@ToString
public enum ErrorCodeEnums {

    SUCCESS("200", "success"),
    ERROR("500", "error"),
    REQUEST_PARAM_ERROR("415", "请求参数异常"),
    
    SYS_UN_LOGIN("10001", "管理员未登录"),
    SYS_NO_PERMISSION("10001", "管理员权限不足"),
    
    STUDENT_EXISTS("20001", "该学生已存在"),
    
    
    CHOOSE_ROUND_INVALID("30001", "轮次信息不合法"),
    CHOOSE_ROUND_TIME_REPEAT("30002", "轮次时间段重叠"),
    CHOOSE_ROUND_TIME_INVALID("30003", "轮次时间不合法"),
    
    CLASS_COURSE_ID_NULL("40001", "方案内课程主键为空"),
    
    COURSE_DEPENDENCE_INVALID("50001", "依赖课程不合法"),
    
    CLASS_NOT_EXISTS("60001", "班级不存在");
    ;

    private final String code;
    private final String desc;

    private ErrorCodeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
