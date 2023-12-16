package org.comcom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config.jwt")
public record JwtProperties(String issuer, String audience, String signatureKey) {
}
