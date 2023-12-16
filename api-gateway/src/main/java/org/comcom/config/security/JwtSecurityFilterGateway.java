package org.comcom.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

/**
 *
 * @author Anonymous
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class JwtSecurityFilterGateway implements WebFilter {

    private final JWTConstant config;

    private final ReactiveUserInfo reactiveUserInfo;

    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        boolean tokenExist = exchange.getRequest().getHeaders().containsKey("Authorization");

        if(tokenExist){
            final String authHeader = exchange.getRequest().getHeaders().get("Authorization").get(0);
            if (authHeader != null && authHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
                String authToken = authHeader.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");

                try {
                    Algorithm algorithm = Algorithm.HMAC256(config.getSignatureKey());

                    JWTVerifier verifier = JWT.require(algorithm)
                            .withIssuer(config.getIssuer()).withAudience(config.getAudience())
                            .build(); //Reusable verifier instance

                    DecodedJWT jwt = verifier.verify(authToken);
                    UserDetails userDetails = reactiveUserInfo.findByUsername(jwt.getSubject()).toFuture().get();
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

                    exchange.getRequest().mutate().header("x-auth-user", userDetails.getUsername()).build();
                    exchange.getRequest().mutate().header("x-authorities", userDetails.getAuthorities().toString()).build();
                    return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(usernamePasswordAuthenticationToken));

                } catch (JWTVerificationException  e) {
                    //don't trust the JWT!
                    System.err.println("JWT Verification Error: " + e.getMessage());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return Mono.empty();

                }catch(InterruptedException | ExecutionException e){
                    System.err.println("Future Error: " + e.getMessage());
                    System.err.println("### Interrupted Data ###");
                    return Mono.empty();
                }
            }
        }

        return chain.filter(exchange);
    }
}
