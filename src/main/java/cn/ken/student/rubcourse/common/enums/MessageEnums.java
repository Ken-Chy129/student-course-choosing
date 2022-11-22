package cn.ken.student.rubcourse.common.enums;

import lombok.Getter;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/22 21:07
 */
@Getter
public enum MessageEnums {
    
    /**
     * 发送成功
     */
    SEND_SUCCESS(1, "发送成功"),
    
    /**
     *发送用户未指定，进行群发
     */
    PERSON_NULL_GROUP_SEND(3, "发送用户未指定，进行群发"),
    
    /**
     * 发送的消息为空,不进行发送
     */
    SEND_NULL(5, "消息为空,不进行发送");

    /**
     * 编码
     */
    private int code;
    
    /**
     * 内容
     */
    private String title;

    MessageEnums(int code, String title) {
        this.code = code;
        this.title = title;
    }
}
