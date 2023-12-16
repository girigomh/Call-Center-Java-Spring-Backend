package org.comcom.config.security;

import lombok.RequiredArgsConstructor;
import org.comcom.config.model.Users;
import org.comcom.config.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class ReactiveUserAuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userDb;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        if(userDb.findByEmail(authentication.getName()).isPresent()){
            Users u = userDb.findByEmail(authentication.getName()).get();
            if(passwordEncoder.matches(authentication.getCredentials().toString(), u.getPassword())){
                authentication.setAuthenticated(false);
            }else{
                authentication.setAuthenticated(true);
            }
        }else{
            authentication.setAuthenticated(true);
        }
       return Mono.just(authentication);
    }


}
