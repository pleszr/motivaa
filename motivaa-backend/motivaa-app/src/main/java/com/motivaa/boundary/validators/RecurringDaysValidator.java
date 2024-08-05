package com.motivaa.boundary.validators;

import com.motivaa.control.utility.PossibleDays;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

public class RecurringDaysValidator implements ConstraintValidator<ValidListOfRecurringDays, List<String>> {

    @Override
    public boolean isValid(List<String> recurringDays, ConstraintValidatorContext context) {
        if (recurringDays == null || recurringDays.isEmpty())
            return true;
        for (String day : recurringDays) {
            if (!isValidEnum(day))
                return false;
            }
        return true;
    }
    private boolean isValidEnum(String day) {
        return EnumUtils.isValidEnum(PossibleDays.class, day);
    }
}