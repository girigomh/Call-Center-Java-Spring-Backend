package org.comcom.config.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Events implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_gen")
    @SequenceGenerator(name="event_gen", sequenceName = "event_gen_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    private String name;

    private String title;

    private String type;

    private LocalTime duration;

    private LocalDate date;

    private String actors;

    private String owner;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "update_on")
    private LocalDateTime updatedOn;

}
