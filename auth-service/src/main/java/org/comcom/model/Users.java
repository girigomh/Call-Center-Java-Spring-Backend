package org.comcom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.comcom.dto.UserDto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_gen_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "login_attempts")
    private Integer loginAttempts;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "username")
    private String username;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "title_photo")
    private String titlePhoto;

    @Column(name = "online_status")
    private OnlineStatus status;

    @Column(name = "video_call_url")
    private String videoCallUrl;

    @Column(name = "verified")
    private Boolean verified = false;     // Email Verified == Account Verified

    @Column(name = "verification_code")
    @JsonIgnore
    private Integer verificationCode;    // Verification Code

    private Integer resetKey;

    private Instant resetDate;

    @Column(name = "sms_verified")
    @JsonIgnore
    private Boolean smsVerified = false;        // SMS Verified == Double Account Verified

    @Column(name = "sms_verification_code")
    @JsonIgnore
    private Integer smsVerificationCode;    // Verification Code for SMS

    @Column(name = "last_login")
    private LocalDate lastLogin;

    //============= Address Info =================


    @Column(name = "house_no")
    private String houseNo;

    @Column(name = "street")
    private String street;

    @Column(name = "location")
    private String location;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zip")
    private String zip;

    //========= For Audit ===============

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "update_on")
    private LocalDateTime updatedOn;

    //=========== Foreign Keys ================

    @JoinColumn(name = "profile", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Role profile;


    @JoinColumn(name = "company", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Company company;


    public UserDto toDto() {
        return UserDto.builder()
            .userId(id)
            .firstName(firstName)
            .lastName(lastName)
            .gender(gender)
            .email(email)
            .dob(dob)
            .username(username)
            .phoneNumber(phone)
            .houseNo(houseNo)
            .street(street)
            .location(location)
            .city(city)
            .state(state)
            .country(country)
            .profilePhoto(profilePhoto)
            .titlePhoto(titlePhoto)
            .videoCallUrl(videoCallUrl)
            .status(status)
            .verified(verified)
            .smsVerified(smsVerified)
            .profile(profile.toDto())
            .build();
    }

    @Override
    public String toString() {
        return "Users{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", loginAttempts=" + loginAttempts +
            ", gender=" + gender +
            ", dob=" + dob +
            ", username='" + username + '\'' +
            ", phone=" + phone +
            ", profilePhoto='" + profilePhoto + '\'' +
            ", titlePhoto='" + titlePhoto + '\'' +
            ", status=" + status +
            ", videoCallUrl='" + videoCallUrl + '\'' +
            ", verified=" + verified +
            ", verificationCode=" + verificationCode +
            ", smsVerified=" + smsVerified +
            ", smsVerificationCode=" + smsVerificationCode +
            ", lastLogin=" + lastLogin +
            ", createdOn=" + createdOn +
            ", updatedOn=" + updatedOn +
            ", profile=" + profile +
            ", company=" + company +
            '}';
    }
}
