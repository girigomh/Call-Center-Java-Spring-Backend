package org.comcom.service.implementation;

import lombok.AllArgsConstructor;
import org.comcom.config.JwtSigner;
import org.comcom.config.PasswordResetProperties;
import org.comcom.config.security.DefaultUserDetails;
import org.comcom.dto.*;
import org.comcom.exception.BadInputException;
import org.comcom.exception.IncorrectCredentialsException;
import org.comcom.exception.VerifyNotException;
import org.comcom.exception.WrongResetKeyException;
import org.comcom.model.Company;
import org.comcom.model.Gender;
import org.comcom.model.OnlineStatus;
import org.comcom.model.Users;
import org.comcom.notification.NotificationService;
import org.comcom.repository.CompanyRepository;
import org.comcom.repository.RoleRepository;
import org.comcom.repository.UserRepository;
import org.comcom.service.AuthService;
import org.comcom.service.UserService;
import org.comcom.service.VerificationTokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private UserService userService;
    private UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtSigner jwtSigner;
    private PasswordResetProperties passwordResetProperties;
    private Clock clock;
    private NotificationService notificationService;
    private VerificationTokenService verificationTokenService;
    private JavaMailSender javaMailSender;



    @Override
    @Transactional
    public boolean registerUser(UserRegistrationRequest user, Roles role) {
        Users userEntity = new Users();
        Company companyEntity;
        switch (role) {
            case CUSTOMER -> {
                this.validateCommonAttributes(user);
                userEntity.setCompany(null);
            }
            case INTERPRETER, CAPTIONER, COMPANY_EMPLOYEE, COMPANY_LEADER, SWITCHING_CENTER_EMPLOYEE, SWITCHING_CENTER_LEADER, COMMUNICATION_ASSISTANCE -> {
                this.validateCommonAttributes(user);
                companyEntity = this.validateCompany(user);
                userEntity.setCompany(companyEntity);
            }
            case ADMIN -> {
                userEntity.setVerified(true);
                userEntity.setSmsVerified(true);
                userEntity.setStatus(OnlineStatus.ONLINE);
            }
            default -> throw new BadInputException("Invalid Profile", "Invalid Profile");
        }

        BeanUtils.copyProperties(user, userEntity);
        BeanUtils.copyProperties(user.getAddressDto(), userEntity);
        String password = passwordEncoder.encode(user.getPassword());
        userEntity.setPassword(password);
        userEntity.setProfile(roleRepository.findByName(role.name()));

        switch (user.getGender()) {
            case "M" -> userEntity.setGender(Gender.MALE);
            case "F" -> userEntity.setGender(Gender.FEMALE);
            case "O" -> userEntity.setGender(Gender.OTHER);
        }

        userEntity.setStatus(OnlineStatus.AWAY);

        userEntity.setCreatedOn(LocalDateTime.now());
        userEntity.setUpdatedOn(LocalDateTime.now());
        userEntity.setVerified(false);

        userRepository.save(userEntity);

        String token = generateVerificationCode();
        System.out.println(token);
        verificationTokenService.createVerificationToken(userEntity, token);

        sendVerificationEmail(userEntity, token);

        return true;
    }

    public  String generateVerificationCode() {
        // Define the possible digits
        String digits = "0123456789";

        // Create a random object
        Random random = new Random();

        // Generate a random 6-digit code
        StringBuilder verificationCode = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            verificationCode.append(digits.charAt(random.nextInt(digits.length())));
        }

        return verificationCode.toString();
    }


    protected void validateCommonAttributes(UserRegistrationRequest userDto) {
        // Validate that the user's first name is not empty
        if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
            throw new BadInputException("First name cannot be empty", "This field is required");
        }
        // Validate that the user's last name is not empty
        if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
            throw new BadInputException("Last name cannot be empty", "This field is required");
        }

        // Validate that the user's email address is not empty
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new BadInputException("Email address cannot be empty", "This field is required");
        }

        // Validate that the user's password is not empty
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new BadInputException("Password cannot be empty", "This field is required");
        }
    }


    private Company validateCompany(UserRegistrationRequest userDto) {
        if (userDto.getCompanyDto() != null) {
            return companyRepository.findAll().stream().filter(company -> company.getId() == userDto.getCompanyDto().getId()).findFirst().get();
        } else {
            throw new BadInputException("Company ID & Name required", "This field is required");
        }
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.pwd()));
            Users user = userRepository.findByEmailIgnoreCase(loginRequest.email()).get();
            System.out.println("LoginRequest: " + loginRequest);
            if(user.getVerified()) return signToken(authentication);
            else throw new VerifyNotException();
        } catch (AuthenticationException exception) {
            throw new IncorrectCredentialsException();
        }
    }

    @Override
    public void requestPasswordReset(PasswordResetRequest resetRequest) {
        userRepository.findByEmailIgnoreCase(resetRequest.email())
            .ifPresent(userEntity -> {
                if (userEntity.getVerified()) {
                    int expirationTimeInHours = passwordResetProperties.expirationTimeInHours();
                    int resetKey = getPasswordResetKey();
                    userEntity.setResetKey(resetKey);
                    userEntity.setResetDate(clock.instant());
                    userRepository.save(userEntity);
                    notificationService.sendPasswordResetRequestEmail(
                        userEntity.getEmail(),
                        userEntity.getFirstName(),
                        userEntity.getResetKey(),
                        expirationTimeInHours
                    );
                }
            });
    }

    @Override
    public LoginResponse resetPassword(PasswordReset passwordReset) {
        Users userEntity = userRepository.findByResetKey(passwordReset.resetKey())
            .orElseThrow(() -> new WrongResetKeyException(passwordReset.resetKey()));

        int expirationTimeInHours = passwordResetProperties.expirationTimeInHours();
        if (userEntity.getResetDate().plus(expirationTimeInHours, ChronoUnit.HOURS).isAfter(clock.instant())) {
            userEntity.setPassword(passwordEncoder.encode(passwordReset.newPassword()));
            userEntity.setResetKey(null);
            userEntity.setResetDate(null);
            userRepository.save(userEntity);
            String accessToken = jwtSigner.createAccessToken(userEntity.getEmail(), userEntity.getProfile().getName());
            String refreshToken = jwtSigner.createRefreshToken(userEntity.getEmail(), userEntity.getProfile().getName());
            notificationService.sendPasswordResetConfirmationEmail(userEntity.getEmail(), userEntity.getFirstName());
            return new LoginResponse(accessToken, refreshToken, userEntity.toDto());
        } else {
            throw new WrongResetKeyException(passwordReset.resetKey());
        }
    }

    private LoginResponse signToken(Authentication authentication) {
        DefaultUserDetails authUser = (DefaultUserDetails) authentication.getPrincipal();
        String accessToken = jwtSigner.createAccessToken(authUser.getUsername(), authUser.getProfile().getName());
        String refreshToken = jwtSigner.createRefreshToken(authUser.getUsername(), authUser.getProfile().getName());
        UserDto user = userService.getUserByEmail(authUser.getUsername());
        updateStatus(user.getEmail());
        user.setStatus(OnlineStatus.ONLINE);

        return new LoginResponse(accessToken, refreshToken, user);
    }

    private void updateStatus(String email){
        Users u = userRepository.findByEmailIgnoreCase(email).get();
        u.setStatus(OnlineStatus.ONLINE);
        userRepository.save(u);
    }

    private int getPasswordResetKey() {
        int resetKey;
        do {
            resetKey = new Random().nextInt(100000, 999999);
        } while (userRepository.findByResetKey(resetKey).isPresent());
        return resetKey;
    }

    private void sendVerificationEmail(Users user, String token){
        String subject = "Email Verification";
        String message = "Email verification code: " + token + " for app.comcom.at";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("earl0425@outlook.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
        System.out.println("your email: " + user.getEmail());
        System.out.println("Email sent: " + message);
    }
}
