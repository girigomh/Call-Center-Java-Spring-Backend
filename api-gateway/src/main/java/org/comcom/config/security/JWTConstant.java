package org.comcom.config.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Anonymous
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "config.jwt")
@Configuration
@Getter
@Setter
public class JWTConstant {

    private String issuer;
    private String audience;
    private String signatureKey;
}
