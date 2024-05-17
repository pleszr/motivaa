package com.habito.boundary;

import com.habito.control.HabitoService;
import com.habito.entity.User;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.jayway.jsonpath.JsonPath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthCheckController.class)
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitoService habitoService;

    @Test
    void shouldReturnCorrectHealthStatus() throws Exception {
        String responseJson = mockMvc.perform(get("/apis/healthcheck"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String healthStatusFromJson = JsonPath.read(responseJson, "$.healthStatus");
        assertEquals("ok", healthStatusFromJson, "Healthcheck endpoint should return 'ok' for healthStatus");
        verifyNoInteractions(habitoService);
    }

    @Test
    void shouldReturnCustomMessage() throws Exception {
        String responseJson = mockMvc.perform(get("/apis/healthcheck"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String customMessageFromJson = JsonPath.read(responseJson, "$.customMessage");
        assertEquals("cica", customMessageFromJson, "Healthcheck endpoint should return 'cica' for customMessage");
        verifyNoInteractions(habitoService);
    }

    @Test
    void shouldReturnRandomUserInPlayground() throws Exception {
        User mockUser = new User();
        // Initialize mockUser fields if necessary

        when(habitoService.createRandomUser()).thenReturn(mockUser);

        mockMvc.perform(get("/apis/playground"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}")); // Adjust this to match your expected JSON structure
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldAllowAuthenticatedUserToAccessAuthTest() throws Exception {
        mockMvc.perform(get("/apis/authTest"))
                .andExpect(status().isOk())
                .andExpect(content().string("Only authenticated users can see this."));
    }

    @Test
    void shouldDenyUnauthenticatedUserAccessToAuthTest() throws Exception {
        mockMvc.perform(get("/apis/authTest"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testTests() throws Exception {
        System.out.println("---Test Test is running...");
        assertEquals(1, 1, "Test should pass");
    }
}
