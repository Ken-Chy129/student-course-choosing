package cn.ken.student.rubcourse.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/23 19:59
 */
@Data
public class RedisData {
    
    private Object value;
    
    private LocalDateTime expireTime;
}
