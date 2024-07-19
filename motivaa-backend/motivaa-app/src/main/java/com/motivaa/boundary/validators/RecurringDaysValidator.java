package com.motivaa.boundary.validators;

import com.motivaa.control.utility.PossibleDays;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

public class RecurringDaysValidator implements ConstraintValidator<ValidListOfRecurringDays, String> {

    @Override
    public boolean isValid(String recurringDays, ConstraintValidatorContext context) {
        if (recurringDays == null)
            return true;
        String[] recurringDaysList = recurringDays.split(";");
        for (String day : recurringDaysList) {
            if (isInvalidEnum(day))
                return false;
            }
        return true;
    }
    private boolean isInvalidEnum(String day) {
        return !EnumUtils.isValidEnum(PossibleDays.class, day);
    }
}