package com.motivaa.boundary;

import com.motivaa.config.SecurityConfig;
import com.motivaa.config.SecurityConfigTest;
import com.motivaa.control.MotivaaService;
import com.motivaa.entity.User;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({SecurityConfigTest.class, SecurityConfig.class})
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MotivaaService motivaaService;


    @Test
    void shouldReturnCorrectHealthStatus() throws Exception {
        String responseJson = mockMvc.perform(get("/apis/healthcheck"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String healthStatusFromJson = JsonPath.read(responseJson, "$.healthStatus");
        assertEquals("ok", healthStatusFromJson, "Healthcheck endpoint should return 'ok' for healthStatus");

    }

    @Test
    void shouldReturnCustomMessage() throws Exception {
        String responseJson = mockMvc.perform(get("/apis/healthcheck"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String customMessageFromJson = JsonPath.read(responseJson, "$.customMessage");
        assertEquals("cica", customMessageFromJson, "Healthcheck endpoint should return 'cica' for customMessage");

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnRandomUserInPlayground() throws Exception {
        User mockUser = new User();
        when(motivaaService.createRandomUser()).thenReturn(mockUser);
        mockMvc.perform(get("/apis/playground"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldAllowAuthenticatedUserToAccessAuthTest() throws Exception {
        mockMvc.perform(get("/apis/authTest"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void shouldDenyUnauthenticatedUserAccessToAuthTest() throws Exception {
        mockMvc.perform(get("/apis/authTest"))
                .andExpect(status().isUnauthorized());
    }


}




