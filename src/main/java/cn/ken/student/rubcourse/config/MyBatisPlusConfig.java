package cn.ken.student.rubcourse.config;

import cn.ken.student.rubcourse.handle.FillMetaObjectHandle;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/17 23:09
 */
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new FillMetaObjectHandle();
    }
}
