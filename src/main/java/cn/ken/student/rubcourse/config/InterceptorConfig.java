package cn.ken.student.rubcourse.config;

import cn.ken.student.rubcourse.interceptor.RefreshTokenInterceptor;
import cn.ken.student.rubcourse.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/12/10 14:43
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/doc.html", "/webjars/**", "favicon.ico","/swagger-resources", "/v2/api-docs", "error", "/student/login", "/sys/manager/login", "static/test.html")
                .order(1);
        registry.addInterceptor(new RefreshTokenInterceptor(redisTemplate))
                .addPathPatterns("/**")
                .order(0);
    }
}
