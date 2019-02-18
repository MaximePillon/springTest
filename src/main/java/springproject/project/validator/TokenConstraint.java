package springproject.project.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TokenValidator.class)
@Target( { ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenConstraint {
    String message() default "Invalid token";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}