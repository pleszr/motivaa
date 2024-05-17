package com.habito.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    private MockMvc mockMvc;

    @Test
    public void whenAccessPublicEndpoint_thenOk() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(get("/apis/healthcheck"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void whenAuthenticatedUserAccessProtectedEndpoint_thenOk() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(get("/apis/authTest").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAnonymousUserAccessProtectedEndpoint_thenUnauthorized() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(get("/apis/authTest").with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}