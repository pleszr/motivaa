package com.motivaa.boundary.validators;

import com.motivaa.control.utility.PossibleColors;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

public class ColorValidator implements ConstraintValidator<ValidColor, String> {

    @Override
    public boolean isValid(String colorValue, ConstraintValidatorContext context) {
        return EnumUtils.isValidEnum(PossibleColors.class, colorValue);
    }
}