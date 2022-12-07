package cn.ken.student.rubcourse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/12/5 22:52
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许的请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许的请求源 （如：http://localhost:8080）
        corsConfiguration.addAllowedOrigin("*");
        // 允许的请求方法 ==> GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
        corsConfiguration.addAllowedMethod("*");
        // URL 映射 （如： /admin/**）
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
