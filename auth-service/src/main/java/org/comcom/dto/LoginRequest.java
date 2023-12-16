package org.comcom.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email can not be empty")
        @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email format is incorrect")
        String email,
        @NotBlank(message = "Password can not be empty") String pwd
) {
}
