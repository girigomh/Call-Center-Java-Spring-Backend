package org.comcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank(message = "Gender can not be empty")
    @Pattern(regexp = "^[MFO]$", message = "Invalid gender value. Valid values are 'M' for Male, 'F' for Female, and 'O' for Other.")
    private String gender;

    @NotBlank
    private String email;

    @NotNull
    private LocalDate dob;

    @NotBlank
    private String username;

    @NotNull
    private String password;

    @NotNull
    private int phone;

    private AddressDto addressDto;

    private String profilePhoto;

    private String titlePhoto;

    private String videoCallUrl;

    private CompanyDto companyDto;

}
