package org.comcom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.comcom.dto.AddressDto;
import org.comcom.dto.ApiResponse;
import org.comcom.dto.UserDto;
import org.comcom.dto.UserUpdateRequest;
import org.comcom.model.Gender;
import org.comcom.service.UserService;
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

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.comcom.utils.ObjectMapperUtils.asJsonString;
import static org.comcom.utils.ObjectMapperUtils.fromJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Disabled
@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static ObjectMapper objectMapper;
    private final String baseUrl = "/api/v1/users";

    @MockBean
    private UserService userService;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    void expectsUserUpdated_whenUpdatingUser_withAllFieldsValid() throws Exception {
        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
            .firstName("New first name")
            .lastName("New last name")
            .gender("M")
            .dob(LocalDate.now())
            .phoneNumber(12345678)
            .build();
        UserDto userDto = UserDto.builder()
            .firstName("New first name")
            .lastName("New last name")
            .gender(Gender.MALE)
            .phoneNumber(12345678)
            .build();

        when(userService.updateUser(any())).thenReturn(userDto);

        mockMvc.perform(put(baseUrl)
                .content(asJsonString(updateRequest, objectMapper))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andExpect(result -> assertThat(fromJson(result.getResponse().getContentAsString(), ApiResponse.class, objectMapper))
                .isEqualTo(new ApiResponse<>(true, HttpStatus.OK.value(), userDto)));
        verify(userService, times(1)).updateUser(updateRequest);
    }

    @Test
    void expectsAddressUpdated_whenUpdatingUserAddress_withAllFieldsValid() throws Exception {
        AddressDto addressDto = AddressDto.builder()
            .houseNo("New house number")
            .street("New street")
            .location("New location")
            .city("New city")
            .state("new-user-name")
            .country("New country")
            .zip("New zip")
            .build();

       // when(userService.updateUserAddress(any())).thenReturn(addressDto);

        mockMvc.perform(put(baseUrl + "/address")
                .content(asJsonString(addressDto, objectMapper))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andExpect(result -> assertThat(fromJson(result.getResponse().getContentAsString(), ApiResponse.class, objectMapper))
                .isEqualTo(new ApiResponse<>(true, HttpStatus.OK.value(), addressDto)));
        verify(userService, times(1)).updateUserAddress(addressDto);
    }
}