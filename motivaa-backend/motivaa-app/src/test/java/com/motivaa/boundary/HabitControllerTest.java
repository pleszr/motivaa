package com.motivaa.boundary;

import com.motivaa.TestUtils.HabitRequestCreator;
import com.motivaa.TestUtils.TestUtils;
import com.motivaa.control.HabitCreationService;
import com.motivaa.control.UserFinder;
import com.motivaa.control.error_handling.exceptions.FieldCustomValidationException;
import com.motivaa.control.error_handling.exceptions.NotFoundException;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.entity.DaySpecificHabit;
import com.motivaa.entity.Habit;
import com.motivaa.entity.NotDaySpecificHabit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;

@Log4j2
@WebMvcTest(HabitController.class)
@ActiveProfiles("test")
public class HabitControllerTest {
    @MockBean
    HabitCreationService habitCreationService;
    @MockBean
    UserFinder userFinder;
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class Habits_POST_CreateHabit {

        @Nested
        class success_scenarios {

            @Test
            void valid_specific_day_request_should_be_success() throws Exception {

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();

                Habit habit = new DaySpecificHabit(
                        HabitRequestCreator.VALID_USER_UUID,
                        HabitRequestCreator.VALID_NAME,
                        HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                        Arrays.asList(HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS.split(";")),
                        Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                        HabitRequestCreator.VALID_COLOR
                );
                when(
                        habitCreationService.createHabit(
                                HabitRequestCreator.VALID_USER_UUID,
                                HabitRequestCreator.VALID_NAME,
                                HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                                HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                                null,
                                Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                                HabitRequestCreator.VALID_COLOR))
                        .thenReturn(
                                habit);

                performPostRequest(request)
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.userUuid").value(HabitRequestCreator.VALID_USER_UUID))
                        .andExpect(jsonPath("$.name").value(HabitRequestCreator.VALID_NAME))
                        .andExpect(jsonPath("$.recurringType").value(HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY))
                        .andExpect(jsonPath("$.recurringDetails").value(HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS))
                        .andExpect(jsonPath("$.priority").value(HabitRequestCreator.VALID_PRIORITY))
                        .andExpect(jsonPath("$.color").value(HabitRequestCreator.VALID_COLOR));
            }

            @Test
            void valid_non_specific_day_request_should_be_success() throws Exception {

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_NON_SPECIFIC_DAY();

                Habit habit = new NotDaySpecificHabit(
                        HabitRequestCreator.VALID_USER_UUID,
                        HabitRequestCreator.VALID_NAME,
                        HabitRequestCreator.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                        Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                        Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                        HabitRequestCreator.VALID_COLOR
                );
                when(
                        habitCreationService.createHabit(
                                HabitRequestCreator.VALID_USER_UUID,
                                HabitRequestCreator.VALID_NAME,
                                HabitRequestCreator.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                                null,
                                Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                                Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                                HabitRequestCreator.VALID_COLOR))
                        .thenReturn(
                                habit);

                performPostRequest(request)
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.userUuid").value(HabitRequestCreator.VALID_USER_UUID))
                        .andExpect(jsonPath("$.name").value(HabitRequestCreator.VALID_NAME))
                        .andExpect(jsonPath("$.recurringType").value(HabitRequestCreator.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY))
                        .andExpect(jsonPath("$.recurringDetails").value(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK))
                        .andExpect(jsonPath("$.priority").value(HabitRequestCreator.VALID_PRIORITY))
                        .andExpect(jsonPath("$.color").value(HabitRequestCreator.VALID_COLOR));
            }
        }

        @Nested
        class MISSING_FIELDS {

            @Test
            void missing_userUuid_should_give_error() throws Exception {

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.remove("userUuid");

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='userUuid')].value").value(MessageBundle.MISSING_UUID_ERROR_MESSAGE));
            }

            @Test
            void missing_name_should_give_error() throws Exception {

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.remove("name");

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='name')].value").value(MessageBundle.MISSING_NAME_ERROR_MESSAGE));
            }

            @Test
            void missing_recurringType_should_give_error() throws Exception {

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.remove("recurringType");

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='recurringType')].value").value(MessageBundle.MISSING_OR_INVALID_RECURRING_TYPE_ERROR_MESSAGE));
            }

            @Test
            void missing_all_fields_should_give_all_errors() throws Exception {

                HashMap<String, String> request = new HashMap<>();

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='userUuid')].value").value(MessageBundle.MISSING_UUID_ERROR_MESSAGE))
                        .andExpect(jsonPath("$.errors[?(@.key=='name')].value").value(MessageBundle.MISSING_NAME_ERROR_MESSAGE))
                        .andExpect(jsonPath("$.errors[?(@.key=='recurringType')].value").value(MessageBundle.MISSING_OR_INVALID_RECURRING_TYPE_ERROR_MESSAGE))
                        .andExpect(jsonPath("$.errors[?(@.key=='priority')].value").value(MessageBundle.MISSING_PRIORITY_ERROR_MESSAGE))
                        .andExpect(jsonPath("$.errors[?(@.key=='color')].value").value(MessageBundle.MISSING_OR_INVALID_COLOR_RESPONSE));
            }
        }
        @Nested
        class error_scenarios {

            @Test
            void non_existing_userUuid_should_give_notFound() throws Exception {

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("numberOfOccasionsInWeek", HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK);

                when(
                        habitCreationService.createHabit(
                                HabitRequestCreator.VALID_USER_UUID,
                                HabitRequestCreator.VALID_NAME,
                                HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                                HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                                Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                                Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                                HabitRequestCreator.VALID_COLOR))
                        .thenThrow(new NotFoundException(MessageBundle.USER_NOT_FOUND));

                performPostRequest(request)
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.USER_NOT_FOUND));
            }

            @Test
            void invalid_name_should_give_error() throws Exception {

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("name", "123");

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='name')].value").value(MessageBundle.INVALID_NAME_FORMAT_ERROR_MESSAGE));
            }

            @Test
            void invalid_recurringType_should_give_error() throws Exception {
                String INVALID_RECURRING_TYPE = "123";

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("recurringType", INVALID_RECURRING_TYPE);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='recurringType')].value").value(MessageBundle.MISSING_OR_INVALID_RECURRING_TYPE_ERROR_MESSAGE));
            }

            @Test
            void invalid_listOfRecurringDays_should_give_error() throws Exception {
                String INVALID_LIST_OF_RECURRING_DAYS = "monday;123";

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("listOfRecurringDays", INVALID_LIST_OF_RECURRING_DAYS);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='listOfRecurringDays')].value").value(MessageBundle.INVALID_RECURRING_DAYS_RESPONSE));
            }

            @Test
            void invalid_NumberOfOccasions_should_give_error() throws Exception {
                String INVALID_NUMBER_OF_OCCASIONS_IN_WEEK = "8";

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_NON_SPECIFIC_DAY();
                request.put("numberOfOccasionsInWeek", INVALID_NUMBER_OF_OCCASIONS_IN_WEEK);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='numberOfOccasionsInWeek')].value").value(MessageBundle.INVALID_NUMBER_OF_OCCASIONS_IN_WEEK_ERROR_MESSAGE));
            }

            @Test
            void empty_listOfRecurringDays_should_give_error_for_specific_day_request() throws Exception {
                String EMPTY_LIST_OF_RECURRING_DAYS = "";

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("listOfRecurringDays", EMPTY_LIST_OF_RECURRING_DAYS);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='listOfRecurringDays')].value").value(MessageBundle.INVALID_RECURRING_DAYS_RESPONSE));
            }

            @Test
            void filled_numberOfOccasions_should_give_error_for_specific_day_request() throws Exception {

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("numberOfOccasionsInWeek", HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK);

                when(
                        habitCreationService.createHabit(
                                HabitRequestCreator.VALID_USER_UUID,
                                HabitRequestCreator.VALID_NAME,
                                HabitRequestCreator.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                                HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS,
                                Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                                Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                                HabitRequestCreator.VALID_COLOR))
                        .thenThrow(new FieldCustomValidationException(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY));


                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY));
            }

            @Test
            void empty_numberOfOccasions_should_give_error_for_non_specific_day_request() throws Exception {
                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_NON_SPECIFIC_DAY();
                request.put("numberOfOccasionsInWeek", null);

                when(
                        habitCreationService.createHabit(
                                HabitRequestCreator.VALID_USER_UUID,
                                HabitRequestCreator.VALID_NAME,
                                HabitRequestCreator.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                                null,
                                null,
                                Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                                HabitRequestCreator.VALID_COLOR))
                        .thenThrow(new FieldCustomValidationException(MessageBundle.INVALID_NUMBER_OF_OCCASIONS_IN_WEEK_ERROR_MESSAGE));

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.INVALID_NUMBER_OF_OCCASIONS_IN_WEEK_ERROR_MESSAGE));
            }

            @Test
            void filled_listOfRecurringDays_should_give_error_for_non_specific_day_request() throws Exception {
                String FILLED_LIST_OF_RECURRING_DAYS = HabitRequestCreator.VALID_LIST_OF_RECURRING_DAYS;

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_NON_SPECIFIC_DAY();
                request.put("listOfRecurringDays", FILLED_LIST_OF_RECURRING_DAYS);

                when(
                        habitCreationService.createHabit(
                                HabitRequestCreator.VALID_USER_UUID,
                                HabitRequestCreator.VALID_NAME,
                                HabitRequestCreator.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                                FILLED_LIST_OF_RECURRING_DAYS,
                                Integer.parseInt(HabitRequestCreator.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                                Integer.parseInt(HabitRequestCreator.VALID_PRIORITY),
                                HabitRequestCreator.VALID_COLOR))
                        .thenThrow(new FieldCustomValidationException(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY));

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY));
            }

            @Test
            void invalid_priority_should_give_error() throws Exception {
                String INVALID_PRIORITY = "6";

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("priority", INVALID_PRIORITY);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='priority')].value").value(MessageBundle.INVALID_PRIORITY_ERROR_MESSAGE));
            }

            @Test
            void invalid_color_should_give_error() throws Exception {
                String INVALID_COLOR = "123";

                HashMap<String, String> request = HabitRequestCreator.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("color", INVALID_COLOR);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='color')].value").value(MessageBundle.MISSING_OR_INVALID_COLOR_RESPONSE));
            }
        }
    }
    private ResultActions performPostRequest(HashMap<String, String> request) throws Exception {
        String POST_HABIT_ENDPOINT = "/habit-apis/habits";
        return mockMvc.perform(post(POST_HABIT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(request))
                .accept(MediaType.APPLICATION_JSON));
    }
}