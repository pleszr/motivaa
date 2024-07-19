package com.motivaa.control;

import com.motivaa.control.errorHandling.exceptions.NotFoundException;
import com.motivaa.control.errorHandling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.entity.User;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
@Log4j2
public class HabitUtils {
    public static void validateIfUserExists(String userUuid, MotivaaRepository motivaaRepository) {
        User user;
        try {
            user = motivaaRepository.findUserByUuid(userUuid);
            log.info("User found with userUuid: {}",user);
        } catch (IOException e) {
            throw new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE);
        }
        if (user == null) {
            throw new NotFoundException(String.format("User with uuid: %s not found", userUuid));
        }
    }

    public static void validateRecurringFieldsForType(
            String recurringType,
            String recurringTypeDetails) {
        switch (recurringType) {
            case "specific_day":
                //validateSpecificDay(listOfRecurringDays, numberOfOccasionsInWeek);
                break;
            case "non_specific_day":
                //validateNonSpecificDay(listOfRecurringDays, numberOfOccasionsInWeek);
                break;
        }
    }
}
