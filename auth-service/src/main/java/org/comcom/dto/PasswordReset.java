package org.comcom.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record PasswordReset(
    @NotBlank int resetKey,
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$",
        message = "Password must be minimum eight characters, having at least one uppercase letter, one lowercase letter, one digit and a special character"
    )
    String newPassword
) {
}
