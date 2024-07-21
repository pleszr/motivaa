package com.motivaa.boundary;

import com.jayway.jsonpath.JsonPath;
import com.motivaa.TestUtils.TestUtils;
import com.motivaa.TestUtils.UserFromJson;
import com.motivaa.control.UserCreationService;
import com.motivaa.control.UserFinder;
import com.motivaa.control.error_handling.exceptions.FieldCustomValidationException;
import com.motivaa.control.error_handling.exceptions.NotFoundException;
import com.motivaa.control.error_handling.exceptions.RepositoryException;
import com.motivaa.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.HashMap;
import java.util.List;

import static com.motivaa.TestUtils.TestUtils.createValidUser1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class UserControllerTest {


    //options to consider: create a method to create the test data OR maybe create a utility class

    @MockBean
    UserCreationService userCreationService;
    @MockBean
    UserFinder userFinder;
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class Post_Users {
        String validEmail = "unit@test.com";
        String validFirstName = "Unit";
        String validLastName = "Test";

        @Test
        void post_Users_Success() throws Exception {


            HashMap<String, String> request = new HashMap<>();
            request.put("email", validEmail);
            request.put("firstName", validFirstName);
            request.put("lastName", validLastName);

            User user = createValidUser1();
            when(
                    userCreationService.createUser(
                            validEmail,
                            validFirstName,
                            validLastName))
                    .thenReturn(
                            user);

            String responseJson = mockMvc.perform(post("/user-apis/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtils.asJsonString(request))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            UserFromJson userFromJson = JsonPath.parse(responseJson).read("$", UserFromJson.class);

            TestUtils.validateIfJsonTimestampIsToday(userFromJson.getCreatedTimestamp());

            if (TestUtils.isJsonUserDifferentThenUser(userFromJson, user)) {
                throw new Exception("Expected user to be " + user + ", but it was " + userFromJson);
            }
        }

        @Test
        void post_Users_MissingEmail() throws Exception {
            HashMap<String, String> request = new HashMap<>();
            request.put("firstName", validFirstName);
            request.put("lastName", validLastName);

            String responseJson =  mockMvc.perform(post("/user-apis/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtils.asJsonString(request))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    400,
                    httpCodeFromJson);
            TestUtils.assertMissingField(responseJson, "email", "Email is mandatory");
        }

        @Test
        void post_Users_MissingFirstName() throws Exception {
            HashMap<String, String> request = new HashMap<>();
            request.put("email", validEmail);
            request.put("lastName", validLastName);

            String responseJson = mockMvc.perform(post("/user-apis/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtils.asJsonString(request))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    400,
                    httpCodeFromJson);
            TestUtils.assertMissingField(responseJson, "firstName", "First name is mandatory");

        }

        @Test
        void post_Users_MissingLastName() throws Exception {
            HashMap<String, String> request = new HashMap<>();
            request.put("email", validEmail);
            request.put("firstName", validFirstName);

            String responseJson =  mockMvc.perform(post("/user-apis/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtils.asJsonString(request))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    400,
                    httpCodeFromJson);

            TestUtils.assertMissingField(responseJson, "lastName", "Last name is mandatory");

        }

        @Test
        void post_Users_blankEmail() throws Exception {
            HashMap<String, String> request = new HashMap<>();
            request.put("email", "");
            request.put("firstName", validFirstName);
            request.put("lastName", validLastName);

            String responseJson = mockMvc.perform(post("/user-apis/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtils.asJsonString(request))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    400,
                    httpCodeFromJson);
            TestUtils.assertMissingField(responseJson, "email", "Email is mandatory");
        }

        @Test
        void post_Users_allFieldsMissing() throws Exception {
            HashMap<String, String> request = new HashMap<>();

            String responseJson = mockMvc.perform(post("/user-apis/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtils.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    400,
                    httpCodeFromJson);
            TestUtils.assertMissingField(responseJson, "email", "Email is mandatory");
            TestUtils.assertMissingField(responseJson, "firstName", "First name is mandatory");
            TestUtils.assertMissingField(responseJson, "lastName", "Last name is mandatory");

        }

        @Test
        void post_Users_InternalException() throws Exception {
            HashMap<String, String> request = new HashMap<>();
            request.put("email", validEmail);
            request.put("firstName", validFirstName);
            request.put("lastName", validLastName);

            when(
                    userCreationService.createUser(
                            validEmail,
                            validFirstName,
                            validLastName))
                    .thenThrow(new RepositoryException("Error while saving user to the database"));

            String responseJson =  mockMvc.perform(post("/user-apis/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtils.asJsonString(request))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andReturn().getResponse().getContentAsString();

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    500,
                    httpCodeFromJson);
        }
    }
    @Nested
    class get_Users {
        @Test
        void get_Users_Success2Users() throws Exception {
            User user1 = TestUtils.createValidUser1();
            User user2 = TestUtils.createValidUser2();

            when(userFinder.searchAllUsers())
                    .thenReturn(
                            List.of(
                                    user1,
                                    user2
                            )
                    );
            String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/user-apis/users")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            UserFromJson userFromJson1 = JsonPath.parse(responseJson).read("$.[0]", UserFromJson.class);
            if (TestUtils.isJsonUserDifferentThenUser(userFromJson1, user1)
                    && TestUtils.isJsonUserDifferentThenUser(userFromJson1, user2)) {
                throw new Exception("Expected first user to be " + user1 + " OR " + user2 + ", but it was " + userFromJson1);
            }
            UserFromJson userFromJson2 = JsonPath.parse(responseJson).read("$.[1]", UserFromJson.class);
            if (TestUtils.isJsonUserDifferentThenUser(userFromJson2, user1)
                    && TestUtils.isJsonUserDifferentThenUser(userFromJson2, user2)) {
                throw new Exception("Expected second user to be " + user1 + " OR " + user2 + ", but it was " + userFromJson2);
            }
        }
        @Test
        void get_Users_Success0Users() throws Exception {
            when(userFinder.searchAllUsers())
                    .thenReturn(
                            List.of()
                    );
            mockMvc.perform(MockMvcRequestBuilders.get("/user-apis/users")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        void get_Users_InternalException() throws Exception {
            when(userFinder.searchAllUsers())
                    .thenThrow(new RepositoryException("Some error happened. Please try again later."));

            String responseJson =  mockMvc.perform(MockMvcRequestBuilders.get("/user-apis/users")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andReturn().getResponse().getContentAsString();

            String errorFromJson = JsonPath.parse(responseJson).read("$.errors[0].value");
            assertEquals(
                    "Some error happened. Please try again later.",
                    errorFromJson);

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    500,
                    httpCodeFromJson);
        }
    }
    @Nested
    class get_Users_userUuid {
        @Test
        void get_Users_userUuid_SuccessFound() throws Exception {
            User user = TestUtils.createValidUser1();
            when(userFinder.findUserByUuid(user.getUuid().toString()))
                    .thenReturn(user);
            String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/user-apis/users/" + user.getUuid())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            UserFromJson userFromJson = JsonPath.parse(responseJson).read("$", UserFromJson.class);
            if (TestUtils.isJsonUserDifferentThenUser(userFromJson, user)) {
                throw new Exception("Expected user to be " + user + ", but it was " + userFromJson);
            }
        }

        @Test
        void get_Users_userUuid_NotFound() throws Exception {
            User user = TestUtils.createValidUser1();
            when(userFinder.findUserByUuid(user.getUuid().toString()))
                    .thenThrow(new NotFoundException(String.format("User with uuid: %s, not found", user.getUuid())));

            String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/user-apis/users/" + user.getUuid().toString())
                        .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn().getResponse().getContentAsString();

            String errorFromJson = JsonPath.parse(responseJson).read("$.errors[0].value");
            assertEquals(
                    String.format("User with uuid: %s, not found", user.getUuid()),
                    errorFromJson);

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    404,
                    httpCodeFromJson);
        }

        @Test
        void get_Users_userUuid_InvalidUuidLength() throws Exception {
            String uuid_invalid_length = "invalid-uuid-length";
            when(userFinder.findUserByUuid(uuid_invalid_length))
                    .thenThrow(new FieldCustomValidationException("Invalid UUID length"));

            String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/user-apis/users/" + uuid_invalid_length)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            String errorFromJson = JsonPath.parse(responseJson).read("$.errors[0].value");
            assertEquals(
                    "Invalid UUID length",
                    errorFromJson);

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    400,
                    httpCodeFromJson);
        }

        @Test
        void get_Users_userUuid_InvalidUuidFormat() throws Exception {
            String uuid_valid_length_invalid_format = "g17d96d0-a6ab-1234-k1ll-8j10934k5678";
            when(userFinder.findUserByUuid(uuid_valid_length_invalid_format))
                    .thenThrow(new FieldCustomValidationException("Invalid UUID format"));

            String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/user-apis/users/" + uuid_valid_length_invalid_format)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            String errorFromJson = JsonPath.parse(responseJson).read("$.errors[0].value");
            assertEquals(
                    "Invalid UUID format",
                    errorFromJson);

            Integer httpCodeFromJson = JsonPath.parse(responseJson).read("$.httpCode");
            assertEquals(
                    400,
                    httpCodeFromJson);
        }
    }
}