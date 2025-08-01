package in.zeta.academy.capstone.plas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EitherEmailOrMobileValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EitherEmailOrMobileRequired {
    String message() default "Either email or mobile must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
