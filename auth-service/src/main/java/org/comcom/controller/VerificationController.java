package org.comcom.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comcom.model.OnlineStatus;
import org.comcom.model.Users;
import org.comcom.model.VerificationToken;
import org.comcom.repository.UserRepository;
import org.comcom.service.AuthService;
import org.comcom.service.UserService;
import org.comcom.service.VerificationTokenService;
import org.comcom.service.implementation.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/v1/account/verify/email")
@AllArgsConstructor
@Slf4j(topic = "VerificationController")
public class VerificationController {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping()
    public ResponseEntity<String> verifyEmail(@RequestParam("source") String source, @RequestParam("data") String token){
        Optional<VerificationToken> verificationTokenOpt = verificationTokenService.getByToken(token);

        if(verificationTokenOpt.isPresent()) {
            VerificationToken verificationToken = verificationTokenOpt.get();

            if(verificationToken.getExpiryDate().isAfter(LocalDateTime.now())){
                Users user = verificationToken.getUser();
                user.setVerified(true);
                Users u = userRepository.findByEmailIgnoreCase(user.getEmail()).get();
                u.setVerified(true);
                userRepository.save(u);
                return ResponseEntity.ok("Email verified successfully.");
            } else {
                Users u = userRepository.findByEmailIgnoreCase(verificationToken.getUser().getEmail()).get();
                userRepository.deleteById(u.getId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired.");
            }
        }else {
            Users u = userRepository.findByEmailIgnoreCase(verificationTokenOpt.get().getUser().getEmail()).get();
            userRepository.deleteById(u.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token.");
        }
    }

}
