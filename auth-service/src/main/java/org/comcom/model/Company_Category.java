package org.comcom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.comcom.dto.CompanyCategoryDataDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "company_category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company_Category {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "com_cate_gen")
    @SequenceGenerator(name="com_cate_gen", sequenceName = "com_cate_gen_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    private String name;

    private String description;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "update_on")
    private LocalDateTime updatedOn;

    //=========== Foreign Keys ================

    @JsonIgnore
    @OneToMany(mappedBy = "companyCategory")
    private List<Company> company;

    /**
     * Created Company Category
     * @param type
     * @param desc
     */
    public Company_Category(String type, String desc) {
        this.name = type;
        this.description = desc;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    public CompanyCategoryDataDto toDto(){
        return CompanyCategoryDataDto
                .builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }
}
