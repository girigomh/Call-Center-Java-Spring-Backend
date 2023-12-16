package org.comcom.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotBlank
    private String houseNo;

    @NotBlank
    private String street;

    @NotBlank
    private String location;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotBlank
    @Size(min = 4, max = 10)
    private String zip;

    @Override
    public String toString() {
        return "AddressDto{" +
            "houseNo='" + houseNo + '\'' +
            ", street='" + street + '\'' +
            ", location='" + location + '\'' +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", country='" + country + '\'' +
            ", zip='" + zip + '\'' +
            '}';
    }
}
