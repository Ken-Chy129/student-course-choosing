package cn.ken.student.rubcourse.annotation;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
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
 * @date 2022/11/25 16:34
 */
@Target({ METHOD, FIELD, TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {ValueInIntegersValidator.class})
@Documented
public @interface Administrator {
    
    
}
