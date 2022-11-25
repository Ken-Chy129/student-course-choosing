package cn.ken.student.rubcourse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/11 17:56
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket backendDocket() {
        //指定使用Swagger2规范
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //描述字段支持Markdown语法
                        .description("# 后台管理")
                        .termsOfServiceUrl("https://doc.xiaominfo.com/")
                        .contact(new Contact("ken", "www.ken-chy129.cn", "1294967895@qq.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("后台管理")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("cn.ken.student.rubcourse.controller.sys"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket frontendDocket() {
        //指定使用Swagger2规范
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //描述字段支持Markdown语法
                        .description("# 前台管理")
                        .termsOfServiceUrl("https://doc.xiaominfo.com/")
                        .contact(new Contact("ken", "www.ken-chy129.cn", "1294967895@qq.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("前台管理")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("cn.ken.student.rubcourse.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    
    
    
}
