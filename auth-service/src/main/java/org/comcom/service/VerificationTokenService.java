package org.comcom.service;

import com.auth0.jwt.interfaces.Verification;
import org.comcom.model.Users;
import org.comcom.model.VerificationToken;
import org.comcom.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public void createVerificationToken(Users user, String token){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        verificationTokenRepository.save(verificationToken);
    }

    public Optional<VerificationToken> getByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
