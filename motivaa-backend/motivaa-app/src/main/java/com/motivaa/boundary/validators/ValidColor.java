package com.motivaa.boundary.validators;

import static com.motivaa.control.utility.MessageBundle.MISSING_OR_INVALID_COLOR_RESPONSE;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ColorValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidColor {
    String message() default MISSING_OR_INVALID_COLOR_RESPONSE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}