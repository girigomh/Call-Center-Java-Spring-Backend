package org.comcom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.comcom.dto.RoleDto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
    @SequenceGenerator(name = "role_gen", sequenceName = "role_gen_sequence", allocationSize = 1)
    private Integer id;

    @Column(unique=true)
    private String name;

    private String scopes;

    private String description;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "update_on")
    private LocalDateTime updatedOn;

    //=========== Foreign Keys ================

    @JsonIgnore
    @OneToMany(mappedBy = "profile")
    private List<Users> users;

    public Role(String name, String scopes, String description) {
        this.name = name;
        this.scopes = scopes;
        this.description = description;
        this.users = null;
    }

    public RoleDto toDto() {
        return RoleDto.builder()
                .roleId(id)
                .name(name)
                .description(description)
                .scopes(scopes)
                .build();
    }
}
