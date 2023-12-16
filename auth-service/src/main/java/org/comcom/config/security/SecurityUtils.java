package org.comcom.config.security;

import org.comcom.exception.UnauthorizedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static DefaultUserDetails getCurrentUser() {
        if (getAuthentication() instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("Unauthorized", "Unauthorized");
        }
        return (DefaultUserDetails) getAuthentication().getPrincipal();
    }
}
