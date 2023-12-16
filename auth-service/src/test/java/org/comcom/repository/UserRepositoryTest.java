package org.comcom.repository;

import org.comcom.model.*;
import org.comcom.utils.FakeClock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Disabled
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private final FakeClock clock = new FakeClock();
    private Users savedEntity;

    @BeforeEach
    void setUp() {
        Role role = new Role("ADMIN", "account:all", "Administrator");
        Company_Category companyCategory = Company_Category.builder()
            .name("My Category")
            .description("My Category")
            .createdOn(LocalDateTime.now(clock))
            .build();
        Company company = Company.builder()
            .name("My company")
            .description("My company")
            .email("info@mycompany.com")
            .companyCategory(companyCategory)
            .createdOn(LocalDateTime.now(clock))
            .build();
        Users userEntity = new Users();
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("user@domain.com");
        userEntity.setGender(Gender.MALE);
        userEntity.setDob(LocalDate.now(clock));
        userEntity.setUsername("john-doe");
        userEntity.setPhone(12345678);
        userEntity.setCreatedOn(LocalDateTime.now(clock));
        userEntity.setProfile(role);
        userEntity.setCompany(company);

        savedEntity = userRepository.save(userEntity);

        Users existingEntity = userRepository.findById(savedEntity.getId()).get();
        assertThat(existingEntity).isEqualTo(savedEntity);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void givenEmailAddress_whenFindByEmailIgnoreCase_thenSuccess() {

    }
}