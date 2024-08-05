package com.motivaa.TestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.motivaa.entity.User;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TestUtils {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void validateIfJsonTimestampIsToday(String timestampString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        LocalDateTime dateTime = LocalDateTime.parse(timestampString, formatter);
        LocalDate date = dateTime.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        if (!date.equals(today)) {
            throw new RuntimeException("Expected timestamp to be today, but it was " + timestampString);
        }
    }

    public static boolean isJsonUserDifferentThenUser(UserFromJson userFromJson, User user) {
        return !userFromJson.getUuid().equals(user.getUuid().toString()) &&
                !userFromJson.getEmail().equals(user.getEmail()) &&
                !userFromJson.getFirstName().equals(user.getFirstName()) &&
                !userFromJson.getLastName().equals(user.getLastName()) &&
                !userFromJson.getStatus().equals(user.getStatus());
    }

    public static User createValidUser1() {
        String validEmail = "unit@test.com";
        String validFirstName = "Unit";
        String validLastName = "Test";
        return new User(validEmail, validFirstName, validLastName);
    }

    public static User createValidUser2() {
        String validEmail = "unit2@test.com";
        String validFirstName = "Unit2";
        String validLastName = "Test2";
        return new User(validEmail, validFirstName, validLastName);
    }

    public static void assertMissingField(String responseJson, String fieldName, String expectedMessage) {
        String missingFieldFromJson = JsonPath.parse(responseJson)
                .read("$.errors[?(@.key == '" + fieldName + "')].value")
                .toString()
                .replace("[\"", "")
                .replace("\"]", "");
        assertEquals(
                expectedMessage,
                missingFieldFromJson);
    }
}
