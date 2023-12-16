package org.comcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class CompanyDataDto {

    private Long id;

    private String name;

    private String email;

    private String websiteUrl;

    private String videoCallUrl;

    private String description;

    private LocalTime openHours;

    private String type;

    private String videoCallEmployee;

    private String videoCallLeader;

    private String categoryName;


}
