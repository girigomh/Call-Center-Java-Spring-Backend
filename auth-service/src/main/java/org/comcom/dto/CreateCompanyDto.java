package org.comcom.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.comcom.utils.CustomLocalTimeDeserializer;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class CreateCompanyDto {

    private Long companyCategoryId;

    private String name;

     private String email;

    private String websiteUrl;

    private String videoCallUrl;

    private String description;

    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonDeserialize(using = CustomLocalTimeDeserializer.class)
    private LocalTime openHours;

    private String type;

    private String videoCallEmployee;

    private String videoCallLeader;

}
