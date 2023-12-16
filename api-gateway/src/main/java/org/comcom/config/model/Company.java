package org.comcom.config.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "company")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "com_gen")
    @SequenceGenerator(name="com_gen", sequenceName = "com_gen_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "video_call_url")
    private String videoCallUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "open_hours")
    private LocalTime openHours;

    @Column(name = "type")
    private String type;

    @Column(name = "video_call_employee")
    private String videoCallEmployee;

    @Column(name = "video_call_leader")
    private String videoCallLeader;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "update_on")
    private LocalDateTime updatedOn;

    //=========== Foreign Keys ================

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Users> users;

    @JoinColumn(name = "companyCategory", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Company_Category companyCategory;
}
