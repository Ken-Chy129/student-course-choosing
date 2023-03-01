package cn.ken.student.rubcourse.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/22 19:04
 */
@Configuration
public class RabbitMQConfig {

    public static final String NOTICE_EXCHANGE = "NoticeExchange";

    public static final String CHOOSE_EXCHANGE = "ChooseExchange";

    @Bean
    public FanoutExchange noticeExchange() {
        return new FanoutExchange(NOTICE_EXCHANGE);
    }

    @Bean
    public FanoutExchange chooseExchange() {
        return new FanoutExchange(CHOOSE_EXCHANGE);
    }
}
