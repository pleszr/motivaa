package com.motivaa.boundary;

import com.motivaa.TestUtils.HabitFactory;
import com.motivaa.TestUtils.TestUtils;
import com.motivaa.control.HabitCreationService;
import com.motivaa.control.HabitFinderService;
import com.motivaa.control.UserFinder;
import com.motivaa.control.error_handling.exceptions.FieldCustomValidationException;
import com.motivaa.control.error_handling.exceptions.NotFoundException;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.entity.DaySpecificHabit;
import com.motivaa.entity.Habit;
import com.motivaa.entity.NotDaySpecificHabit;
import lombok.extern.log4j.Log4j2;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Log4j2
@WebMvcTest(HabitController.class)
@ActiveProfiles("test")
public class HabitControllerTest {
    @MockBean
    HabitCreationService habitCreationService;
    @MockBean
    UserFinder userFinder;
    @MockBean
    HabitFinderService habitFinderService;
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class Habits_POST_CreateHabit {

        @Nested
        class success_scenarios {

            @Test
            void valid_specific_day_request_should_be_success() throws Exception {

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();

                Habit habit = new DaySpecificHabit(
                        HabitFactory.VALID_USER_UUID,
                        HabitFactory.VALID_NAME,
                        HabitFactory.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                        HabitFactory.VALID_LIST_OF_RECURRING_DAYS,
                        Integer.parseInt(HabitFactory.VALID_PRIORITY),
                        HabitFactory.VALID_COLOR
                );
                when(
                        habitCreationService.createHabit(
                                HabitFactory.VALID_USER_UUID,
                                HabitFactory.VALID_NAME,
                                HabitFactory.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                                HabitFactory.VALID_LIST_OF_RECURRING_DAYS,
                                null,
                                Integer.parseInt(HabitFactory.VALID_PRIORITY),
                                HabitFactory.VALID_COLOR))
                        .thenReturn(
                                habit);

                performPostRequest(request)
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.userUuid").value(HabitFactory.VALID_USER_UUID))
                        .andExpect(jsonPath("$.name").value(HabitFactory.VALID_NAME))
                        .andExpect(jsonPath("$.recurringType").value(HabitFactory.VALID_RECURRING_TYPE_SPECIFIC_DAY))
                        .andExpect(jsonPath("$.recurringDetails").value(containsInAnyOrder(HabitFactory.VALID_LIST_OF_RECURRING_DAYS.toArray())))                        .andExpect(jsonPath("$.priority").value(HabitFactory.VALID_PRIORITY))
                        .andExpect(jsonPath("$.color").value(HabitFactory.VALID_COLOR))
                        .andReturn().getResponse().getContentAsString();
            }

            @Test
            void valid_non_specific_day_request_should_be_success() throws Exception {

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_NON_SPECIFIC_DAY();

                Habit habit = new NotDaySpecificHabit(
                        HabitFactory.VALID_USER_UUID,
                        HabitFactory.VALID_NAME,
                        HabitFactory.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                        Integer.parseInt(HabitFactory.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                        Integer.parseInt(HabitFactory.VALID_PRIORITY),
                        HabitFactory.VALID_COLOR
                );
                when(
                        habitCreationService.createHabit(
                                HabitFactory.VALID_USER_UUID,
                                HabitFactory.VALID_NAME,
                                HabitFactory.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                                null,
                                Integer.parseInt(HabitFactory.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                                Integer.parseInt(HabitFactory.VALID_PRIORITY),
                                HabitFactory.VALID_COLOR))
                        .thenReturn(
                                habit);

                performPostRequest(request)
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.userUuid").value(HabitFactory.VALID_USER_UUID))
                        .andExpect(jsonPath("$.name").value(HabitFactory.VALID_NAME))
                        .andExpect(jsonPath("$.recurringType").value(HabitFactory.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY))
                        .andExpect(jsonPath("$.recurringDetails").value(HabitFactory.VALID_NUMBER_OF_OCCASIONS_IN_WEEK))
                        .andExpect(jsonPath("$.priority").value(HabitFactory.VALID_PRIORITY))
                        .andExpect(jsonPath("$.color").value(HabitFactory.VALID_COLOR));
            }
        }

        @Nested
        class MISSING_FIELDS {

            @Test
            void missing_userUuid_should_give_error() throws Exception {

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.remove("userUuid");

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='userUuid')].value").value(MessageBundle.MISSING_UUID_ERROR_MESSAGE));
            }

            @Test
            void missing_name_should_give_error() throws Exception {

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.remove("name");

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='name')].value").value(MessageBundle.MISSING_NAME_ERROR_MESSAGE));
            }

            @Test
            void missing_recurringType_should_give_error() throws Exception {

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.remove("recurringType");

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='recurringType')].value").value(MessageBundle.MISSING_OR_INVALID_RECURRING_TYPE_ERROR_MESSAGE));
            }

            @Test
            void missing_all_fields_should_give_all_errors() throws Exception {

                HashMap<String, Object> request = new HashMap<>();

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

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("numberOfOccasionsInWeek", HabitFactory.VALID_NUMBER_OF_OCCASIONS_IN_WEEK);

                when(
                        habitCreationService.createHabit(
                                HabitFactory.VALID_USER_UUID,
                                HabitFactory.VALID_NAME,
                                HabitFactory.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                                HabitFactory.VALID_LIST_OF_RECURRING_DAYS,
                                Integer.parseInt(HabitFactory.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                                Integer.parseInt(HabitFactory.VALID_PRIORITY),
                                HabitFactory.VALID_COLOR))
                        .thenThrow(new NotFoundException(MessageBundle.USER_NOT_FOUND));

                performPostRequest(request)
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.USER_NOT_FOUND));
            }

            @Test
            void invalid_name_should_give_error() throws Exception {

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("name", "123#@!#@!#!@#!@#@!#@!###############################");

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='name')].value").value(MessageBundle.INVALID_NAME_FORMAT_ERROR_MESSAGE));
            }

            @Test
            void invalid_recurringType_should_give_error() throws Exception {
                String INVALID_RECURRING_TYPE = "123";

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("recurringType", INVALID_RECURRING_TYPE);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='recurringType')].value").value(MessageBundle.MISSING_OR_INVALID_RECURRING_TYPE_ERROR_MESSAGE));
            }

            @Test
            void invalid_listOfRecurringDays_should_give_error() throws Exception {
                List<String> INVALID_LIST_OF_RECURRING_DAYS = Arrays.asList("MONDAY","123");

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("listOfRecurringDays", INVALID_LIST_OF_RECURRING_DAYS);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='listOfRecurringDays')].value").value(MessageBundle.INVALID_RECURRING_DAYS_RESPONSE));
            }

            @Test
            void invalid_NumberOfOccasions_should_give_error() throws Exception {
                String INVALID_NUMBER_OF_OCCASIONS_IN_WEEK = "8";

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_NON_SPECIFIC_DAY();
                request.put("numberOfOccasionsInWeek", INVALID_NUMBER_OF_OCCASIONS_IN_WEEK);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='numberOfOccasionsInWeek')].value").value(MessageBundle.INVALID_NUMBER_OF_OCCASIONS_IN_WEEK_ERROR_MESSAGE));
            }

            @Test
            void empty_listOfRecurringDays_should_give_error_for_specific_day_request() throws Exception {
                List<String> EMPTY_LIST_OF_RECURRING_DAYS = Collections.emptyList();

                when(
                        habitCreationService.createHabit(
                                HabitFactory.VALID_USER_UUID,
                                HabitFactory.VALID_NAME,
                                HabitFactory.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                                EMPTY_LIST_OF_RECURRING_DAYS,
                                null,
                                Integer.parseInt(HabitFactory.VALID_PRIORITY),
                                HabitFactory.VALID_COLOR))
                        .thenThrow(new FieldCustomValidationException(MessageBundle.RECURRING_DAYS_MANDATORY_SPECIFIC_DAY));

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("listOfRecurringDays", EMPTY_LIST_OF_RECURRING_DAYS);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.RECURRING_DAYS_MANDATORY_SPECIFIC_DAY));
            }

            @Test
            void filled_numberOfOccasions_should_give_error_for_specific_day_request() throws Exception {

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("numberOfOccasionsInWeek", HabitFactory.VALID_NUMBER_OF_OCCASIONS_IN_WEEK);

                when(
                        habitCreationService.createHabit(
                                HabitFactory.VALID_USER_UUID,
                                HabitFactory.VALID_NAME,
                                HabitFactory.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                                HabitFactory.VALID_LIST_OF_RECURRING_DAYS,
                                Integer.parseInt(HabitFactory.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                                Integer.parseInt(HabitFactory.VALID_PRIORITY),
                                HabitFactory.VALID_COLOR))
                        .thenThrow(new FieldCustomValidationException(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY));


                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY));
            }

            @Test
            void empty_numberOfOccasions_should_give_error_for_non_specific_day_request() throws Exception {
                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_NON_SPECIFIC_DAY();
                request.put("numberOfOccasionsInWeek", null);

                when(
                        habitCreationService.createHabit(
                                HabitFactory.VALID_USER_UUID,
                                HabitFactory.VALID_NAME,
                                HabitFactory.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                                null,
                                null,
                                Integer.parseInt(HabitFactory.VALID_PRIORITY),
                                HabitFactory.VALID_COLOR))
                        .thenThrow(new FieldCustomValidationException(MessageBundle.INVALID_NUMBER_OF_OCCASIONS_IN_WEEK_ERROR_MESSAGE));

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.INVALID_NUMBER_OF_OCCASIONS_IN_WEEK_ERROR_MESSAGE));
            }

            @Test
            void filled_listOfRecurringDays_should_give_error_for_non_specific_day_request() throws Exception {
                List<String> FILLED_LIST_OF_RECURRING_DAYS = HabitFactory.VALID_LIST_OF_RECURRING_DAYS;

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_NON_SPECIFIC_DAY();
                request.put("listOfRecurringDays", FILLED_LIST_OF_RECURRING_DAYS);

                when(
                        habitCreationService.createHabit(
                                HabitFactory.VALID_USER_UUID,
                                HabitFactory.VALID_NAME,
                                HabitFactory.VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                                FILLED_LIST_OF_RECURRING_DAYS,
                                Integer.parseInt(HabitFactory.VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                                Integer.parseInt(HabitFactory.VALID_PRIORITY),
                                HabitFactory.VALID_COLOR))
                        .thenThrow(new FieldCustomValidationException(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY));

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY));
            }

            @Test
            void invalid_priority_should_give_error() throws Exception {
                String INVALID_PRIORITY = "6";

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("priority", INVALID_PRIORITY);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='priority')].value").value(MessageBundle.INVALID_PRIORITY_ERROR_MESSAGE));
            }

            @Test
            void invalid_color_should_give_error() throws Exception {
                String INVALID_COLOR = "123";

                HashMap<String, Object> request = HabitFactory.createRequest_SUCCESS_SPECIFIC_DAY();
                request.put("color", INVALID_COLOR);

                performPostRequest(request)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[?(@.key=='color')].value").value(MessageBundle.MISSING_OR_INVALID_COLOR_RESPONSE));
            }
        }

    }


    @Nested
    class Habits_GET_SearchHabitsByUserUuid {
        //test cases
        //valid uuid and two habits for the user should give back both habits
        //valid uuid and no habits for the user should give back empty list
        //invalid uuid should give back 400
        @Nested
        class Success_scenarios {
            @Test
            void valid_uuid_and_two_habits_for_the_user_should_give_back_both_habits() throws Exception {
                Habit habit1 = HabitFactory.createDaySpecificHabit();
                Habit habit2 = HabitFactory.createNotDaySpecificHabit();
                when(habitFinderService.habitFinder(HabitFactory.VALID_USER_UUID))
                        .thenReturn(Arrays.asList(habit1, habit2));

                mockMvc.perform(get("/habit-apis/habits?userUuid=" + HabitFactory.VALID_USER_UUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].userUuid").value(habit1.getUserUuid()))
                        .andExpect(jsonPath("$[0].name").value(habit1.getName()))
                        .andExpect(jsonPath("$[0].recurringType").value(habit1.getRecurringType()))
                        .andExpect(jsonPath("$[0].recurringDetails").value(containsInAnyOrder(habit1.getRecurringTypeDetails().toArray())))
                        .andExpect(jsonPath("$[0].priority").value(habit1.getPriority()))
                        .andExpect(jsonPath("$[0].color").value(habit1.getColor()))

                        .andExpect(jsonPath("$[1].userUuid").value(habit2.getUserUuid()))
                        .andExpect(jsonPath("$[1].name").value(habit2.getName()))
                        .andExpect(jsonPath("$[1].recurringType").value(habit2.getRecurringType()))
                        .andExpect(jsonPath("$[1].recurringDetails").value(containsInAnyOrder(habit2.getRecurringTypeDetails().toArray())))
                        .andExpect(jsonPath("$[1].priority").value(habit2.getPriority()))
                        .andExpect(jsonPath("$[1].color").value(habit2.getColor()));
            }
        }
        @Test
        void valid_uuid_and_no_habits_for_the_user_should_give_back_empty_list() throws Exception {
            String VALID_USER_UUID_NO_SAVED_HABITS = "a691ab6b-0f76-46a3-ba17-52bfa2bbcb5f";
            when(habitFinderService.habitFinder(VALID_USER_UUID_NO_SAVED_HABITS))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get("/habit-apis/habits?userUuid=" + HabitFactory.VALID_USER_UUID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isEmpty());
        }



        @Test
        void invalid_uuid_should_give_back_400() throws Exception {
            String INVALID_USER_UUID = "123";
            mockMvc.perform(get("/habit-apis/habits?userUuid=" + INVALID_USER_UUID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors[?(@.key=='errorMessage')].value").value(MessageBundle.INVALID_UUID_FORMAT_ERROR_MESSAGE));
        }

    }

    private ResultActions performPostRequest(HashMap<String, Object> request) throws Exception {
        String POST_HABIT_ENDPOINT = "/habit-apis/habits";
        return mockMvc.perform(post(POST_HABIT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(request))
                .accept(MediaType.APPLICATION_JSON));
    }
}