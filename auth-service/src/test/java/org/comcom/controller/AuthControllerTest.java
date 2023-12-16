package org.comcom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.comcom.dto.*;
import org.comcom.service.AuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.comcom.utils.ObjectMapperUtils.asJsonString;
import static org.comcom.utils.ObjectMapperUtils.fromJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Disabled
@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static ObjectMapper objectMapper;
    private final String baseUrl = "/api/v1/users";

    @MockBean
    private AuthService authService;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    void expectsUserAuthenticated_whenAuthenticating_withAllFieldsValid() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user@domain.com", "1234");
        LoginResponse loginResponse = new LoginResponse("xyz", "zyx", UserDto.builder().email("user@domain.com").build());

        when(authService.authenticate(any())).thenReturn(loginResponse);

        mockMvc.perform(post(baseUrl + "/auth")
                .content(asJsonString(loginRequest, objectMapper))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andExpect(result -> assertThat(fromJson(result.getResponse().getContentAsString(), ApiResponse.class, objectMapper))
                .isEqualTo(new ApiResponse<>(true, HttpStatus.OK.value(), loginResponse)));

        verify(authService, times(1)).authenticate(loginRequest);
    }

    @Test
    void expectsPasswordResetRequested_whenRequestingOne_withAllFieldsValid() throws Exception {
        PasswordResetRequest resetRequest = new PasswordResetRequest("user@domain.com");

        doNothing().when(authService).requestPasswordReset(resetRequest);

        mockMvc.perform(put(baseUrl + "/request-password-reset")
                .content(asJsonString(resetRequest, objectMapper))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andExpect(result -> assertThat(fromJson(result.getResponse().getContentAsString(), ApiResponse.class, objectMapper))
                .isEqualTo(new ApiResponse<>(true, HttpStatus.OK.value(), null)));

        verify(authService, times(1)).requestPasswordReset(resetRequest);
    }

    @Test
    void expectsPasswordReset_whenResettingOne_withAllFieldsValid() throws Exception {
        PasswordReset passwordReset = new PasswordReset(123456, "new-password");
        LoginResponse loginResponse = new LoginResponse("xyz", "zyx", UserDto.builder().email("user@domain.com").build());

        when(authService.resetPassword(any())).thenReturn(loginResponse);

        mockMvc.perform(put(baseUrl + "/reset-password")
                .content(asJsonString(passwordReset, objectMapper))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andExpect(result -> assertThat(fromJson(result.getResponse().getContentAsString(), ApiResponse.class, objectMapper))
                .isEqualTo(new ApiResponse<>(true, HttpStatus.OK.value(), loginResponse)));

        verify(authService, times(1)).resetPassword(passwordReset);
    }
}