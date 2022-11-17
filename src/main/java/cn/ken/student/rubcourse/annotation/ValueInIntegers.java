package cn.ken.student.rubcourse.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/17 16:42
 */
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(ValueInIntegers.List.class)
@Constraint(validatedBy = {ValueInIntegersValidator.class})
@Documented
public @interface ValueInIntegers {

    String message() default "value must match one of the values in the list: {value}";

    int[] value();

    // 允许值为null, 默认允许
    boolean allowNull() default true;
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        ValueInIntegers[] value();

    }
    
}
