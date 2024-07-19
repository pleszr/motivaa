package com.motivaa.boundary.validators;

import static com.motivaa.control.utility.MessageBundle.INVALID_RECURRING_DAYS_RESPONSE;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = RecurringDaysValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidListOfRecurringDays {
    String message() default INVALID_RECURRING_DAYS_RESPONSE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}