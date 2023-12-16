package org.comcom.config.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_list")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceList implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pl_gen")
    @SequenceGenerator(name="pl_gen", sequenceName = "pl_gen_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    private String state;

    private Double pricePerHour;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "update_on")
    private LocalDateTime updatedOn;

}
