package cn.ken.student.rubcourse.common.util;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/16 22:15
 */
public class SnowflakeUtil {

    private static final SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
    
    public static Long nextId() {
        return snowflakeGenerator.next();
    }
}
