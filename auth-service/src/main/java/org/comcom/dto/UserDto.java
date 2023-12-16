package org.comcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.comcom.model.Gender;
import org.comcom.model.OnlineStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class UserDto {

    private long userId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private LocalDate dob;
    private String username;
    private int phoneNumber;

    private String houseNo;
    private String street;
    private String location;
    private String city;
    private String state;
    private String country;
    private String zip;

    private String profilePhoto;
    private String titlePhoto;
    private String videoCallUrl;
    private OnlineStatus status;
    private boolean verified;
    private Boolean smsVerified;
    private RoleDto profile;
}
