package org.comcom.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comcom.dto.*;
import org.comcom.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.comcom.utils.ApiResponseUtils.buildResponse;

@CrossOrigin
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@Slf4j(topic = "AuthController")
public class AuthController {

    private final AuthService authService;


    @PostMapping(path = "/{role}/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> registration(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest, @PathVariable String role) {

        return buildResponse(authService.registerUser(userRegistrationRequest, Roles.valueOf(role.toUpperCase())));
    }

    @PostMapping(path = "/auth",produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        System.out.println(loginRequest);
        return buildResponse(authService.authenticate(loginRequest));
    }

    @PutMapping("/request-password-reset")
    ApiResponse<?> requestPasswordReset(@RequestBody @Valid PasswordResetRequest resetRequest) {
        authService.requestPasswordReset(resetRequest);
        return buildResponse(null);
    }

    @PutMapping("/reset-password")
    ApiResponse<?> resetPassword(@RequestBody @Valid PasswordReset passwordReset) {
        return buildResponse(authService.resetPassword(passwordReset));
    }
}
