package org.comcom.config.security;

import lombok.RequiredArgsConstructor;
import org.comcom.exception.IncorrectCredentialsException;
import org.comcom.model.Users;
import org.comcom.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(IncorrectCredentialsException::new);

        //if (!user.getVerified()) throw new AccountNotVerifiedException();

        return new DefaultUserDetails(user);
    }
}
