package com.motivaa.boundary.dto;

import com.motivaa.control.errorHandling.MotivaaException;
import com.motivaa.control.errorHandling.ErrorCode;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
@Value
public class FindUserByUuidRequestValidator {

    String userUuid;

    public static void validateRequest(String userUuid) {
        validateLength(userUuid);
        validateUuidParsing(userUuid);
    }

    private static void validateLength(String userUuid) {
        if (userUuid.length() != 36) {
            throw new MotivaaException(ErrorCode.BAD_REQUEST, "Invalid UUID length");
        }
    }

    private static void validateUuidParsing(String userUuid) {
        try {
            UUID.fromString(userUuid);
        } catch (IllegalArgumentException e) {
            throw new MotivaaException(ErrorCode.BAD_REQUEST, "Invalid UUID");
        }
    }

}
