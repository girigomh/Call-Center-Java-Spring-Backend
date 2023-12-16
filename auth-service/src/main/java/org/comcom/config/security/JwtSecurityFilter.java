package org.comcom.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.comcom.config.JwtProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain fc) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            String authToken = authHeader.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
            try {
                Algorithm algorithm = Algorithm.HMAC256(jwtProperties.signatureKey());
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer(jwtProperties.issuer())
                        .withAudience(jwtProperties.audience())
                        .build();
                DecodedJWT jwt = verifier.verify(authToken);
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getSubject());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (JWTVerificationException e) {
                //don't trust the JWT!
                System.err.println("JWT Verification Error: " + e.getMessage());
            }
        }
        fc.doFilter(request, response);
    }
}
