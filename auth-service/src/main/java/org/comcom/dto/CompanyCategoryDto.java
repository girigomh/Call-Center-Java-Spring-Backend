package org.comcom.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCategoryDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

}
