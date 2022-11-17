package cn.ken.student.rubcourse.config;

import cn.ken.student.rubcourse.common.entity.Result;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import red.zyc.desensitization.resolver.TypeResolver;
import red.zyc.desensitization.resolver.TypeResolvers;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/17 14:51
 */
@Configuration
public class DesensitizationConfig {
    
    @Bean
    public TypeResolver<Result, AnnotatedParameterizedType> typeResolver() {
        return new ResultTypeResolver();
    }

    public static class ResultTypeResolver implements TypeResolver<Result, AnnotatedParameterizedType>, AopInfrastructureBean {

        private final int order = TypeResolvers.randomOrder();

        @Override
        public Result resolve(Result result, AnnotatedParameterizedType annotatedParameterizedType) {
            AnnotatedType typeArgument = annotatedParameterizedType.getAnnotatedActualTypeArguments()[0];
            Object erased = TypeResolvers.resolve(result.getData(), typeArgument);
            return new Result(result.getCode(), result.getDesc(), erased);
        }

        @Override
        public boolean support(Object value, AnnotatedType annotatedType) {
            return value instanceof Result && annotatedType instanceof AnnotatedParameterizedType;
        }

        @Override
        public int order() {
            return order;
        }
    }
}
