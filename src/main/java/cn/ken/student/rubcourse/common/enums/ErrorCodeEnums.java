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
    
    SYS_UN_LOGIN("10001", "登录凭证已过期"),
    SYS_NO_PERMISSION("10002", "管理员权限不足"),
    
    STUDENT_EXISTS("20001", "该学生已存在"),
    CODE_EXPIRED("20002", "验证码已过期"),
    CODE_ERROR("20003", "验证码错误"),
    ACCOUNT_PASSWORD_ERROR("20004", "用户不存在或密码错误"),
    LOGIN_CREDENTIAL_EXPIRED("20005", "登录凭证已过期"),
    
    
    CHOOSE_ROUND_INVALID("30001", "轮次信息不合法"),
    CHOOSE_ROUND_TIME_REPEAT("30002", "轮次时间段重叠"),
    CHOOSE_ROUND_TIME_INVALID("30003", "轮次时间不合法"),
    NO_ROUND_PRESENT("30004", "当前不在选课轮次内"),
    
    CLASS_COURSE_ID_NULL("40001", "方案内课程主键为空"),
    
    COURSE_DEPENDENCE_INVALID("50001", "依赖课程不合法"),
    
    CLASS_NOT_EXISTS("60001", "班级不存在"),
    
    CREDITS_NOT_ENOUGH("70001", "学分不足"),
    PRECOURSE_NOT_CHOOSE("70002", "尚未修得先修课程"),
    CONDITION_NOT_SATISFIED("70003", "不满足选择条件"),
    
    DOWNLOAD_ERROR("80001", "文件下载失败"),
    ;

    private final String code;
    private final String desc;

    private ErrorCodeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
