package org.comcom.service;

import org.comcom.dto.*;

public interface AuthService {

    boolean registerUser(UserRegistrationRequest user, Roles role);

    LoginResponse authenticate(LoginRequest loginRequest);

    void requestPasswordReset(PasswordResetRequest resetRequest);

    LoginResponse resetPassword(PasswordReset passwordReset);
}
