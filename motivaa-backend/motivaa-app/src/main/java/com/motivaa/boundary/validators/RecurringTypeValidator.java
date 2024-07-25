package com.motivaa.boundary.validators;

import com.motivaa.control.utility.PossibleRecurringTypes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

public class RecurringTypeValidator implements ConstraintValidator<ValidRecurringType, String> {

    @Override
    public boolean isValid(String recurringType, ConstraintValidatorContext context) {
        return EnumUtils.isValidEnum(PossibleRecurringTypes.class, recurringType);
    }
}