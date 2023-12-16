package org.comcom.model;

import lombok.*;
import org.comcom.dto.AppointmentDto;

import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Serializable{

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appoint_gen")
    @SequenceGenerator(name="appoint_gen", sequenceName = "appoint_sequence", initialValue = 1, allocationSize = 1)
    private Long id;


    @NotBlank
    @Column (name = "address")
    private String address;

    @NotBlank
    @Column (name = "city")
    private String city;

    @NotNull
    @Column (name = "confirmation")
    private Boolean confirmation;

    @NotBlank
    @Column (name = "country")
    private String country;

    @NotNull
    @Column (name = "endDate")
    private LocalDateTime endDate;

    @NotBlank
    @Column (name = "endTime")
    private String endTime;

    @NotNull
    @Column (name = "interpreterType")
    private Boolean interpreterType;

    @Column (name = "note")
    private String note;

    @NotBlank
    @Column (name = "purpose")
    private String purpose;

    @NotNull
    @Column (name = "startDate")
    private LocalDateTime startDate;

    @NotBlank
    @Column (name = "startTime")
    private String startTime;

    @NotBlank
    @Column (name = "street")
    private String street;

    @Column (name = "topic")
    private String topic;

    @NotBlank
    @Column (name = "zip")
    private String zip;

    public Appointment(String address, String city, Boolean confirmation, String country, LocalDateTime endDate, String endTime, Boolean interpretertype, String note, String purpose, LocalDateTime startDate, String startTime, String street, String topic, String zip){
        this.address = address;
        this.city = city;
        this.confirmation = confirmation;
        this.country = country;
        this.endDate = endDate;
        this.endTime = endTime;
        this.interpreterType = interpretertype;
        this.note = note;
        this.purpose = purpose;
        this.startDate = startDate;
        this.startTime = startTime;
        this.street = street;
        this.topic = topic;
        this.zip = zip;
    }
    public AppointmentDto toDto(){
        return AppointmentDto.builder()
                .address(address)
                .city(city)
                .confirmation(confirmation)
                .country(country)
                .endDate(endDate)
                .endTime(endTime)
                .interpreterType(interpreterType)
                .note(note)
                .purpose(purpose)
                .startDate(startDate)
                .startTime(startTime)
                .street(street)
                .topic(topic)
                .zip(zip)
                .build();
    }

}
