package org.comcom.config.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "address")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @SequenceGenerator(name="address_gen", sequenceName = "address_gen_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    private LocalTime travelTime;

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

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "update_on")
    private LocalDateTime updatedOn;

}
