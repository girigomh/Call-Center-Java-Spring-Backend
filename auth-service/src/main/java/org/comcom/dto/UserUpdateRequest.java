package org.comcom.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
public class UserUpdateRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank(message = "Gender can not be empty")
    @Pattern(regexp = "^[MFO]$", message = "Invalid gender value. Valid values are 'M' for Male, 'F' for Female, and 'O' for Other.")
    private String gender;

    @NotNull
    private LocalDate dob;

    @NotNull
    private int phoneNumber;

}
