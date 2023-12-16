package org.comcom.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

/**
 *
 * @author Jack
 * @version 1.0.0
 */

@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtSecurityFilterGateway jwtFilter;

    private final ReactiveUserAuthenticationManager reactiveUserAuthenticationManager;

    private final CorsConfig corsConfig;

    @Bean
    public SecurityWebFilterChain springWebFilterChainConfigure(ServerHttpSecurity http)  {
        return http
                .csrf().disable()
                .cors().configurationSource(corsConfig.corsUrlFilter())
                .and()
                .httpBasic().disable()
                .logout().disable()
                .formLogin().disable()
                .authenticationManager(reactiveUserAuthenticationManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange()
                .pathMatchers("/comcom/oauth/**", "/comcom/api/v3/api-docs/**", "/comcom/api/swagger-ui/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }


}