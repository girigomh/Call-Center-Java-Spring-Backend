package org.comcom;

import lombok.RequiredArgsConstructor;
import org.comcom.model.*;
import org.comcom.repository.CompanyCategoryRepository;
import org.comcom.repository.CompanyRepository;
import org.comcom.repository.RoleRepository;
import org.comcom.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
@EnableAsync
@ConfigurationPropertiesScan("org.comcom.config")
@RequiredArgsConstructor
public class AuthServiceModule implements ApplicationRunner {

    private final RoleRepository roleDB;

    private final CompanyRepository companyDB;

    private final CompanyCategoryRepository companyCategoryDB;

    private final UserRepository userDB;

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceModule.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        if(roleDB.findAll().size() == 0){
            roleDB.save(new Role("anonymous", "profile:read", "Anonymous Account"));
            roleDB.save(new Role("customer", "profile:read,profile:update,profile:remove", "Customer Account"));
            roleDB.save(new Role("interpreter", "profile:read,profile:update,profile:remove", "Interpreter Account"));
            roleDB.save(new Role("captioner", "profile:read,profile:update,profile:remove", "Captioner Account"));
            roleDB.save(new Role("company_employee", "profile:read,profile:update,profile:remove", "Company Employee Account"));
            roleDB.save(new Role("company_leader", "profile:read,profile:update,profile:remove", "Company Leader Account"));
            roleDB.save(new Role("switching_center_employee", "profile:read,profile:update,profile:remove", "Switching Center Employee Account"));
            roleDB.save(new Role("switching_center_leader", "profile:read,profile:update,profile:remove", "Switching Center Leader Account"));
            roleDB.save(new Role("communication_assistance", "profile:read,profile:update,profile:remove", "Communication Assistance Account"));
            roleDB.save(new Role("admin", "account:all", "Administrator"));
        }

        if(companyCategoryDB.findAll().size() == 0){
            companyCategoryDB.save(new Company_Category("Tech", "Technology Company"));
        }

        if(companyDB.findAll().size() == 0){
            companyDB.save(new Company("Google", "contact@google.com", "https://google.com", "https://video-link-company.com", "Google Company", LocalTime.now(), "Software", "https://video-link-company.com/employee", "https://video-link-company.com/leader", companyCategoryDB.findAll().get(0)));
        }

        if(userDB.findAll().size() == 0){

            userDB.save(createCustomerData());
            userDB.save(createAdminData());
            userDB.save(createCompanyLeaderData());

        }
    }

    public Users createCustomerData(){
        Users customerAccount = new Users();
        customerAccount.setFirstName("Customer");
        customerAccount.setLastName("Daniel");
        customerAccount.setGender(Gender.MALE);
        customerAccount.setEmail("customer@gmail.com");
        customerAccount.setDob(LocalDate.now());
        customerAccount.setUsername("Customer Daniel");
        customerAccount.setPassword(passwordEncoder.encode("pass"));
        customerAccount.setPhone(123456789);
        customerAccount.setHouseNo("01");
        customerAccount.setStreet("ST-123");
        customerAccount.setLocation("Hallein");
        customerAccount.setCity("Salzburg");
        customerAccount.setCountry("Austria");
        customerAccount.setZip("1234-AU");
        customerAccount.setProfilePhoto("Photo");
        customerAccount.setTitlePhoto("TPhoto");
        customerAccount.setVideoCallUrl("https://video.com");
        customerAccount.setProfile(roleDB.findByName("customer"));
        return customerAccount;
    }

    public Users createAdminData(){
        Users adminAccount = new Users();
        adminAccount.setFirstName("Admin");
        adminAccount.setLastName("Daniel");
        adminAccount.setGender(Gender.MALE);
        adminAccount.setEmail("admin@comcom.at");
        adminAccount.setDob(LocalDate.now());
        adminAccount.setUsername("Admin Daniel");
        adminAccount.setPassword(passwordEncoder.encode("pass"));
        adminAccount.setPhone(12345678);
        adminAccount.setHouseNo("01");
        adminAccount.setStreet("ST-123");
        adminAccount.setLocation("Hallein");
        adminAccount.setCity("Salzburg");
        adminAccount.setCountry("Austria");
        adminAccount.setZip("1234-AU");
        adminAccount.setProfilePhoto("Photo");
        adminAccount.setTitlePhoto("TPhoto");
        adminAccount.setVideoCallUrl("https://video.com");
        adminAccount.setProfile(roleDB.findByName("admin"));
        return adminAccount;
    }

    public Users createCompanyLeaderData(){
        Users companyLeaderAccount = new Users();
        companyLeaderAccount.setFirstName("Larry");
        companyLeaderAccount.setLastName("Page");
        companyLeaderAccount.setGender(Gender.MALE);
        companyLeaderAccount.setEmail("admin@google.com");
        companyLeaderAccount.setDob(LocalDate.now());
        companyLeaderAccount.setUsername("Larry Page");
        companyLeaderAccount.setPassword(passwordEncoder.encode("pass"));
        companyLeaderAccount.setPhone(1234567);
        companyLeaderAccount.setHouseNo("01");
        companyLeaderAccount.setStreet("ST-123");
        companyLeaderAccount.setLocation("Hallein");
        companyLeaderAccount.setCity("Salzburg");
        companyLeaderAccount.setCountry("Austria");
        companyLeaderAccount.setZip("1234-AU");
        companyLeaderAccount.setProfilePhoto("Photo");
        companyLeaderAccount.setTitlePhoto("TPhoto");
        companyLeaderAccount.setVideoCallUrl("https://video.com");
        companyLeaderAccount.setProfile(roleDB.findByName("company_leader"));
        return companyLeaderAccount;
    }
}