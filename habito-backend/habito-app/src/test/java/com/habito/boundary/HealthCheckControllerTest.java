package com.habito.boundary;

import com.habito.config.SecurityConfig;
import com.habito.config.TestSecurityConfig;
import com.habito.control.HabitoService;
import com.habito.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.jayway.jsonpath.JsonPath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({TestSecurityConfig.class, SecurityConfig.class})
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitoService habitoService;

    @BeforeEach
    void setup() {
        // Ensure Spring Security filter chain is applied
        // This is now handled by @AutoConfigureMockMvc
    }

    @Test
    void shouldReturnCorrectHealthStatus() throws Exception {
        String responseJson = mockMvc.perform(get("/apis/healthcheck"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String healthStatusFromJson = JsonPath.read(responseJson, "$.healthStatus");
        System.out.println("Expected: ok, Actual: " + healthStatusFromJson);
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
        System.out.println("Expected: cica, Actual: " + customMessageFromJson);
        assertEquals("cica", customMessageFromJson, "Healthcheck endpoint should return 'cica' for customMessage");
        verifyNoInteractions(habitoService);
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnRandomUserInPlayground() throws Exception {
        User mockUser = new User();
        // Initialize mockUser fields if necessary

        when(habitoService.createRandomUser()).thenReturn(mockUser);

        String responseJson = mockMvc.perform(get("/apis/playground"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("Expected: {}, Actual: " + responseJson);
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldAllowAuthenticatedUserToAccessAuthTest() throws Exception {
        String response = mockMvc.perform(get("/apis/authTest"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("Expected: Only authenticated users can see this., Actual: " + response);
        assertEquals("Only authenticated users can see this.", response);
    }

    @Test
    void shouldDenyUnauthenticatedUserAccessToAuthTest() throws Exception {
        int status = mockMvc.perform(get("/apis/authTest"))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getStatus();

        System.out.println("Expected: 401, Actual: " + status);
        assertEquals(401, status);
    }

    @Test
    void testTests() throws Exception {
        System.out.println("---Test Test is running...");
        System.out.println("Expected: 1, Actual: 1");
        assertEquals(1, 1, "Test should pass");
    }
}




