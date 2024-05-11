package com.habito.boundary;

import com.habito.control.HabitoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.jayway.jsonpath.JsonPath;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthCheckController.class)
public class HealthCheckControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitoService habitoService;

    @Test
    void shouldReturnCorrectVersionForExistingFullTextId() throws Exception {
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/apis/healthcheck"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String healthStatusFromJson = JsonPath.read(responseJson, "$.healthStatus");
        assertEquals("ok", healthStatusFromJson, "Healthcheck endpoint should give ok, but it didnt");
        verifyNoInteractions(habitoService);
    }

    @Test
    void testTests() throws Exception {
        System.out.println("---Test Test is running...");
        assertEquals(1, 1, "Test should pass");
    }
}
