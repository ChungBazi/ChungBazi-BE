package chungbazi.chungbazi_be.global.validation.annotation;

import chungbazi.chungbazi_be.global.validation.validator.EntityExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = EntityExistValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistEntity {
    String message() default "";
    Class<?> entityType();
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
