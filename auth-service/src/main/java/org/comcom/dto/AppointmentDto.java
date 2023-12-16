package org.comcom.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotNull
    private Boolean confirmation;

    @NotBlank
    private String country;

    @NotNull
    private LocalDateTime endDate;

    @NotBlank
    private String endTime;

    @NotNull
    private Boolean interpreterType;

    private String note;

    @NotBlank
    private String purpose;

    @NotNull
    private LocalDateTime startDate;

    @NotBlank
    private String startTime;

    @NotBlank
    private String street;

    private String topic;

    @NotBlank
    private String zip;

}

