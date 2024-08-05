package com.motivaa.control;

import com.motivaa.control.error_handling.exceptions.FieldCustomValidationException;
import com.motivaa.control.error_handling.exceptions.NotFoundException;
import com.motivaa.control.error_handling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.entity.User;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class CommonHabitServicesTest {

    @Autowired
    CommonHabitServices commonHabitServices;

    @MockBean
    MotivaaRepository motivaaRepository;

    @Nested
    class ValidateIfUserExists {

        @Test
        void if_user_is_found_it_should_not_throw_any_exception() throws Exception {
            String UUID_OF_EXISTING_USER = "9e86bb88-1b4e-4097-b966-2f882f717b41";
            User user = createValidUser();
            user.setUuid(UUID.fromString(UUID_OF_EXISTING_USER));

            when(motivaaRepository.findUserByUuid(UUID_OF_EXISTING_USER))
                    .thenReturn(user);

            assertDoesNotThrow(() -> commonHabitServices.validateIfUserExists(UUID_OF_EXISTING_USER));
        }

        @Test
        void if_user_is_not_found_it_should_throw_NotFoundException() throws Exception {
            String UUID_OF_NON_EXISTING_USER = "9e86bb88-1b4e-4097-b966-2f882f717b42";

            when(motivaaRepository.findUserByUuid(UUID_OF_NON_EXISTING_USER))
                    .thenReturn(null);

            assertThrows(NotFoundException.class, () -> commonHabitServices.validateIfUserExists(UUID_OF_NON_EXISTING_USER));
        }

        @Test
        void if_FindUserByUuid_throws_IOException_it_should_throw_RepositoryException() throws Exception {

            when(motivaaRepository.findUserByUuid(any()))
                    .thenThrow(new IOException());

            Exception exception = assertThrows(
                    RepositoryException.class, () ->
                            commonHabitServices.validateIfUserExists(any()));

            assertEquals(MessageBundle.INTERNAL_ERROR_RESPONSE, exception.getMessage());
        }
    }

    @Nested
    class ValidateRecurringFieldsForType {
        List<String> VALID_LIST_OF_RECURRING_DAYS = Arrays.asList("MONDAY","TUESDAY","WEDNESDAY");
        Integer VALID_NUMBER_OF_OCCASIONS_IN_WEEK = 3;
        String VALID_RECURRING_TYPE_SPECIFIC_DAY = "SPECIFIC_DAY";
        String VALID_RECURRING_TYPE_NON_SPECIFIC_DAY = "NON_SPECIFIC_DAY";
        List<String> NULL_LIST_OF_RECURRING_DAYS = null;
        Integer NULL_NUMBER_OF_OCCASIONS_IN_WEEK = null;

        @Nested
        class Success_scenarios {

            @Test
            void given_recurringType_is_specific_day_it_should_not_throw_exception_if_listOfRecurringDays_is_not_null_and_numberOfOccasionsInWeek_is_null() {
                assertDoesNotThrow(() -> commonHabitServices.validateRecurringFieldsForType(
                        VALID_RECURRING_TYPE_SPECIFIC_DAY,
                        VALID_LIST_OF_RECURRING_DAYS,
                        NULL_NUMBER_OF_OCCASIONS_IN_WEEK));
            }

            @Test
            void given_recurringType_is_non_specific_day_it_should_not_throw_exception_if_listOfRecurringDays_is_null_and_numberOfOccasionsInWeek_is_not_null() {
                assertDoesNotThrow(() -> commonHabitServices.validateRecurringFieldsForType(
                        VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                        NULL_LIST_OF_RECURRING_DAYS,
                        VALID_NUMBER_OF_OCCASIONS_IN_WEEK));
            }
        }

        @Nested
        class Failure_scenarios {

            @Test
            void given_recurringType_is_specific_day_it_should_throw_FieldCustomValidationException_if_listOfRecurringDays_is_null() {
                Exception exception = assertThrows(
                        FieldCustomValidationException.class, () ->
                                commonHabitServices.validateRecurringFieldsForType(
                                        VALID_RECURRING_TYPE_SPECIFIC_DAY,
                                        NULL_LIST_OF_RECURRING_DAYS,
                                        NULL_NUMBER_OF_OCCASIONS_IN_WEEK));

                assertEquals(MessageBundle.RECURRING_DAYS_MANDATORY_SPECIFIC_DAY, exception.getMessage());
            }

            @Test
            void given_recurringType_is_specific_day_it_should_throw_FieldCustomValidationException_if_numberOfOccasionsInWeek_is_not_null() {
                Exception exception = assertThrows(
                        FieldCustomValidationException.class, () ->
                                commonHabitServices.validateRecurringFieldsForType(
                                        VALID_RECURRING_TYPE_SPECIFIC_DAY,
                                        VALID_LIST_OF_RECURRING_DAYS,
                                        VALID_NUMBER_OF_OCCASIONS_IN_WEEK));

                assertEquals(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY, exception.getMessage());
            }

            @Test
            void given_recurringType_is_non_specific_day_it_should_throw_FieldCustomValidationException_if_listOfRecurringDays_is_not_null() {
                Exception exception = assertThrows(
                        FieldCustomValidationException.class, () ->
                                commonHabitServices.validateRecurringFieldsForType(
                                        VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                                        VALID_LIST_OF_RECURRING_DAYS,
                                        VALID_NUMBER_OF_OCCASIONS_IN_WEEK));

                assertEquals(MessageBundle.RECURRING_DAYS_IS_NOT_ALLOWED_SPECIFIC_DAY, exception.getMessage());
            }

            @Test
            void given_recurringType_is_non_specific_day_it_should_throw_FieldCustomValidationException_if_numberOfOccasionsInWeek_is_null() {
                Exception exception = assertThrows(
                        FieldCustomValidationException.class, () ->
                                commonHabitServices.validateRecurringFieldsForType(
                                        VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                                        NULL_LIST_OF_RECURRING_DAYS,
                                        NULL_NUMBER_OF_OCCASIONS_IN_WEEK));

                assertEquals(MessageBundle.NUMBER_OF_OCCASIONS_MANDATORY_NON_SPECIFIC_DAY, exception.getMessage());
            }


        }
    }

    private User createValidUser() {
        String USER_EMAIL = "unit@test.com";
        String USER_FIRST_NAME = "Unit";
        String USER_LAST_NAME = "Test";
        return new User(USER_EMAIL, USER_FIRST_NAME, USER_LAST_NAME);
    }
}
