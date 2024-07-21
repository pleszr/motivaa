package com.motivaa.control;

import com.motivaa.TestUtils.HabitRequestCreator;
import com.motivaa.control.error_handling.exceptions.FieldCustomValidationException;
import com.motivaa.control.error_handling.exceptions.NotFoundException;
import com.motivaa.control.error_handling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.control.utility.MessageBundle;
import static com.motivaa.control.utility.MessageBundle.INVALID_RECURRING_TYPE_ERROR_MESSAGE;
import com.motivaa.entity.DaySpecificHabit;
import com.motivaa.entity.Habit;
import com.motivaa.entity.NotDaySpecificHabit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("test")
public class HabitCreationServiceTest {

    @MockBean
    private MotivaaRepository motivaaRepository;
    @MockBean
    private CommonHabitServices commonHabitServices;

    @Autowired
    HabitCreationService habitCreationService;

    @Nested
    class Success_Scenarios {

        @Test
        void valid_daySpecificHabit_should_be_successful() throws Exception {

            doNothingMockOfValidateUsers(HabitRequestCreator.VALID_USER_UUID);

            doNothing().when(commonHabitServices).validateRecurringFieldsForType(
                            HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                            HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                            null);

            doNothing().when(motivaaRepository).saveHabit(new DaySpecificHabit(
                    HabitRequestCreator.VALID_USER_UUID,
                    HabitRequestCreator.VALID_NAME,
                    HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                    Arrays.asList(HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS.split(";")),
                    Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                    HabitRequestCreator.VALID_COLOR));

            Habit habit = habitCreationService.createHabit(
                    HabitRequestCreator.VALID_USER_UUID,
                    HabitRequestCreator.VALID_NAME,
                    HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                    HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                    null,
                    Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                    HabitRequestCreator.VALID_COLOR);

            assertEquals(
                    DaySpecificHabit.class,
                    habit.getClass());
            assertEquals(
                    HabitRequestCreator.VALID_USER_UUID,
                    habit.getUserUuid());
            assertEquals(
                    HabitRequestCreator.VALID_NAME,
                    habit.getName());
            assertEquals(
                    HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                    habit.getRecurringType());
            assertEquals(
                    HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                    habit.getRecurringTypeDetails());
            assertEquals(
                    Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                    habit.getPriority());
            assertEquals(
                    HabitRequestCreator.VALID_COLOR,
                    habit.getColor());
        }

        @Test
        void valid_notDaySpecificHabit_should_be_successful() throws Exception {

            doNothingMockOfValidateUsers(HabitRequestCreator.VALID_USER_UUID);

            doNothing().when(commonHabitServices).validateRecurringFieldsForType(
                    HabitRequestCreator.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                    null,
                    Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK));

            doNothing().when(motivaaRepository).saveHabit(new NotDaySpecificHabit(
                    HabitRequestCreator.VALID_USER_UUID,
                    HabitRequestCreator.VALID_NAME,
                    HabitRequestCreator.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                    Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                    Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                    HabitRequestCreator.VALID_COLOR));

            Habit habit = habitCreationService.createHabit(
                    HabitRequestCreator.VALID_USER_UUID,
                    HabitRequestCreator.VALID_NAME,
                    HabitRequestCreator.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                    null,
                    Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                    Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                    HabitRequestCreator.VALID_COLOR);

            assertEquals(
                    NotDaySpecificHabit.class,
                    habit.getClass());
            assertEquals(
                    HabitRequestCreator.VALID_USER_UUID,
                    habit.getUserUuid());
            assertEquals(
                    HabitRequestCreator.VALID_NAME,
                    habit.getName());
            assertEquals(
                    HabitRequestCreator.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                    habit.getRecurringType());
            assertEquals(
                    HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK,
                    habit.getRecurringTypeDetails());
            assertEquals(
                    Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                    habit.getPriority());
            assertEquals(
                    HabitRequestCreator.VALID_COLOR,
                    habit.getColor());
        }

    }

    @Nested
    class Failure_Scenarios {

        @Test
        void if_user_doesnt_exists_it_should_throw_NotFoundException() {
            String NOT_EXISTING_USERS_UUID = "invalid_user_uuid";

            Mockito.doThrow(new NotFoundException(MessageBundle.USER_NOT_FOUND))
                    .when(commonHabitServices).validateIfUserExists(
                            NOT_EXISTING_USERS_UUID);

            Exception exception = assertThrows(NotFoundException.class, () ->
                habitCreationService.createHabit(
                        NOT_EXISTING_USERS_UUID,
                        HabitRequestCreator.VALID_NAME,
                        HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                        HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                        null,
                        Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                        HabitRequestCreator.VALID_COLOR
                ));


            assertEquals(MessageBundle.USER_NOT_FOUND, exception.getMessage());
        }

        @Test
        void if_validateIfUserExists_gives_RepositoryException_it_should_be_thrown() {
            Mockito.doThrow(new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE))
                    .when(commonHabitServices).validateIfUserExists(
                            HabitRequestCreator.VALID_USER_UUID);

            Exception exception = assertThrows(RepositoryException.class, () ->
                habitCreationService.createHabit(
                        HabitRequestCreator.VALID_USER_UUID,
                        HabitRequestCreator.VALID_NAME,
                        HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                        HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                        null,
                        Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                        HabitRequestCreator.VALID_COLOR
                ));

            assertEquals(MessageBundle.INTERNAL_ERROR_RESPONSE, exception.getMessage());
        }

        @Test
        void if_validateRecurringFieldsForType_throws_FieldCustomValidationException() {
            Mockito.doThrow(new FieldCustomValidationException(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY))
                    .when(commonHabitServices).validateRecurringFieldsForType(
                            HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                            HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                            Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK));

            Exception exception = assertThrows(FieldCustomValidationException.class, () ->
                habitCreationService.createHabit(
                        HabitRequestCreator.VALID_USER_UUID,
                        HabitRequestCreator.VALID_NAME,
                        HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                        HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                        Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                        Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                        HabitRequestCreator.VALID_COLOR
                ));

            assertEquals(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY, exception.getMessage());
        }

        @Test
        void if_invalid_recurringType_is_given_it_should_throw_FieldCustomValidationException() {
            String INVALID_RECURRING_TYPE = "invalid_recurring_type";

            doNothingMockOfValidateUsers(HabitRequestCreator.VALID_USER_UUID);

            Exception exception = assertThrows(FieldCustomValidationException.class, () ->
                habitCreationService.createHabit(
                        HabitRequestCreator.VALID_USER_UUID,
                        HabitRequestCreator.VALID_NAME,
                        INVALID_RECURRING_TYPE,
                        HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                        null,
                        Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                        HabitRequestCreator.VALID_COLOR
                ));

            assertEquals(INVALID_RECURRING_TYPE_ERROR_MESSAGE, exception.getMessage());
        }

        @Test
        void if_saveHabit_gives_IOException_it_should_throw_RepositoryException() throws Exception {
            doNothingMockOfValidateUsers(HabitRequestCreator.VALID_USER_UUID);

            doNothing().when(commonHabitServices).validateRecurringFieldsForType(
                    HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                    HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                    null);

            Mockito.doThrow(new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE))
                    .when(motivaaRepository).saveHabit(any(DaySpecificHabit.class));

            Exception exception = assertThrows(RepositoryException.class, () ->
                habitCreationService.createHabit(
                        HabitRequestCreator.VALID_USER_UUID,
                        HabitRequestCreator.VALID_NAME,
                        HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                        HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                        null,
                        Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                        HabitRequestCreator.VALID_COLOR
                ));

            assertEquals(MessageBundle.INTERNAL_ERROR_RESPONSE, exception.getMessage());
        }
    }

    private void doNothingMockOfValidateUsers(String userUuid) {
        doNothing().when(commonHabitServices).validateIfUserExists(
                userUuid);
    }
}
