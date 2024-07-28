package com.motivaa.boundary.validators;


import com.motivaa.control.error_handling.exceptions.FieldCustomValidationException;
import com.motivaa.control.utility.MessageBundle;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
@Value
public class UuidValidator {

    String userUuid;

    public static void validateUuid(String userUuid) {
        validateLength(userUuid);
        validateUuidParsing(userUuid);
    }

    private static void validateLength(String userUuid) {
        if (userUuid.length() != 36) {
            throw new FieldCustomValidationException(MessageBundle.INVALID_UUID_FORMAT_ERROR_MESSAGE);
        }
    }

    private static void validateUuidParsing(String userUuid) {
        try {
            UUID.fromString(userUuid);
        } catch (IllegalArgumentException e) {
            throw new FieldCustomValidationException(MessageBundle.INVALID_UUID_FORMAT_ERROR_MESSAGE);
        }
    }

}
