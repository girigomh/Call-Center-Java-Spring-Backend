package org.comcom.service.implementation;

import org.comcom.config.JwtProperties;
import org.comcom.config.JwtSigner;
import org.comcom.config.PasswordResetProperties;
import org.comcom.config.security.DefaultUserDetails;
import org.comcom.config.security.SecurityUtils;
import org.comcom.dto.LoginRequest;
import org.comcom.dto.LoginResponse;
import org.comcom.dto.RoleDto;
import org.comcom.dto.UserDto;
import org.comcom.model.Role;
import org.comcom.model.Users;
import org.comcom.notification.NotificationService;
import org.comcom.repository.UserRepository;
import org.comcom.service.UserService;
import org.comcom.utils.FakeClock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(MockitoExtension.class)
class DefaultAuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authenticationMock;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private NotificationService notificationService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtProperties jwtProperties;
    @Mock
    private PasswordResetProperties passwordResetProperties;
    //@InjectMocks
    //private DefaultAccountService accountService;
    @InjectMocks
    private JwtSigner jwtSigner;
    @Spy
    private final Clock clock = new FakeClock();
    private static MockedStatic<SecurityUtils> securityUtilsMock;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
    }

    @AfterEach
    void cleanup() {
        securityUtilsMock.close();
    }


    @Test
    void authenticate_shouldAuthenticateUser_givenValidCredentials() {
        RoleDto roleDto = RoleDto.builder()
            .name("ADMIN")
            .scopes("account:all")
            .description("Admin")
            .build();
        UserDto userDto = UserDto.builder()
            .email("user@domain.com")
            .profile(roleDto)
            .build();
        LoginRequest loginRequest = new LoginRequest("user@domain.com", "1234");
        Users userEntity = Users.builder()
            .email("user@domain.com")
            .password("encoded-password")
            .profile(new Role("ADMIN", "account:all", "Admin"))
            .build();
        UserDetails userDetails = new DefaultUserDetails(userEntity);
        LoginResponse expected = new LoginResponse("xyz", "zyx", UserDto.builder().email("user@domain.com").build());

        when(authenticationManager.authenticate(any())).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(userDetails);
        when(jwtSigner.createAccessToken(any(), any())).thenReturn("xyz");
        when(jwtSigner.createRefreshToken(any(), any())).thenReturn("zyx");
        when(userService.getUserByEmail(any())).thenReturn(userDto);

        //LoginResponse actual = accountService.authenticate(loginRequest);
        //assertThat(actual).isEqualTo(expected);
    }

    @Test
    void authenticate_shouldThrowWrongCredentialsException_givenInvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest("user@domain.com", "1234");

        doThrow(new BadCredentialsException("Incorrect e-mail or password")).when(authenticationManager).authenticate(any());

       // assertEquals("Wrong credentials", exception.getMessage());
    }
}