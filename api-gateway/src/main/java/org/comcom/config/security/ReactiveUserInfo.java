package org.comcom.config.security;


import lombok.RequiredArgsConstructor;
import org.comcom.config.model.Users;
import org.comcom.config.repository.UserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactiveUserInfo implements ReactiveUserDetailsService{


    private final UserRepository userDb;

    @Override
    public Mono<UserDetails> findByUsername(String email) throws RuntimeException{
        Optional<Users> u = userDb.findByEmail(email);
        u.orElseThrow(() -> new UsernameNotFoundException("Incorrect Credentials"));

        return Mono.just(new UsersDetail(u.get()));
    }
}
