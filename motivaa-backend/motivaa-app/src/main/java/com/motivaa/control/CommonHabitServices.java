package com.motivaa.control;

import com.motivaa.control.error_handling.exceptions.FieldCustomValidationException;
import com.motivaa.control.error_handling.exceptions.NotFoundException;
import com.motivaa.control.error_handling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;



import java.io.IOException;
@Service
@Log4j2
public class CommonHabitServices {
    private final MotivaaRepository motivaaRepository;

    CommonHabitServices(MotivaaRepository motivaaRepository) {
        this.motivaaRepository = motivaaRepository;
    }

    public void validateIfUserExists(String userUuid) {
        User user;
        try {
            user = motivaaRepository.findUserByUuid(userUuid);
        } catch (IOException e) {
            throw new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE);
        }
        if (user == null) {
            throw new NotFoundException(String.format(MessageBundle.USER_NOT_FOUND));
        }
    }

    public void validateRecurringFieldsForType(
            String recurringType,
            String listOfRecurringDays,
            Integer numberOfOccasionsInWeek) {
        switch (recurringType) {
            case "SPECIFIC_DAY":
                validateSpecificDay(listOfRecurringDays, numberOfOccasionsInWeek);
                break;
            case "NON_SPECIFIC_DAY":
                validateNonSpecificDay(listOfRecurringDays, numberOfOccasionsInWeek);
                break;
            default:
                throw new FieldCustomValidationException(MessageBundle.MISSING_OR_INVALID_RECURRING_TYPE_ERROR_MESSAGE);
        }
    }

    private void validateSpecificDay(String listOfRecurringDays, Integer numberOfOccasionsInWeek) {
        if (listOfRecurringDays == null || listOfRecurringDays.isEmpty()) {
            throw new FieldCustomValidationException(MessageBundle.RECURRING_DAYS_MANDATORY_SPECIFIC_DAY);
        }
        if (numberOfOccasionsInWeek != null) {
            throw new FieldCustomValidationException(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY);
        }
    }
    private void validateNonSpecificDay(String listOfRecurringDays, Integer numberOfOccasionsInWeek) {
        if (listOfRecurringDays != null) {
            throw new FieldCustomValidationException(MessageBundle.RECURRING_DAYS_IS_NOT_ALLOWED_SPECIFIC_DAY);
        }
        if (numberOfOccasionsInWeek == null) {
            throw new FieldCustomValidationException(MessageBundle.NUMBER_OF_OCCASIONS_MANDATORY_NON_SPECIFIC_DAY);
        }
    }

}
