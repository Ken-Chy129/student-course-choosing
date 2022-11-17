package cn.ken.student.rubcourse.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>{@code
 * }
 * <p></p>
 * </pre>
 * @author Schilings
*/
public class ValueInIntegersValidator implements ConstraintValidator<ValueInIntegers, Integer> {
    
    private int[] integerList;

    private boolean allowNull;
    
    @Override
    public void initialize(ValueInIntegers constraintAnnotation) {
        integerList = constraintAnnotation.value();
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return allowNull;
        }
        for (Integer integer : integerList) {
            if (integer.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
