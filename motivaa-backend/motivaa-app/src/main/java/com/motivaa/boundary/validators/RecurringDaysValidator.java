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
            if (!isValidEnum(day))
                return false;
            }
        return true;
    }
    private boolean isValidEnum(String day) {
        return EnumUtils.isValidEnum(PossibleDays.class, day);
    }
}