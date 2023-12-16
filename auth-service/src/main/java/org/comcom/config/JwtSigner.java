package org.comcom.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtSigner {

    private final JwtProperties jwtProperties;
    private Instant iat;
    private Instant expDate;
    private Instant refreshDate;
    private Instant offlineDate;

    public String createAccessToken(String user, String profile) {
        initDate();
        return signToken(user, profile, expDate);
    }

    public String createRefreshToken(String user, String profile) {
        initDate();
        return signToken(user, profile, refreshDate);
    }

    public String createOfflineToken(String user, String profile) {
        initDate();
        return signToken(user, profile, offlineDate);
    }

    public String signToken(String user, String profile, Instant expirationDate) {
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(jwtProperties.signatureKey());
            return JWT.create()
                    .withJWTId(UUID.randomUUID().toString())
                    .withIssuedAt(iat)
                    .withExpiresAt(expirationDate)
                    .withIssuer(jwtProperties.issuer())
                    .withAudience(jwtProperties.audience())
                    .withSubject(user)
                    .withClaim("profile", profile)
                    .sign(algorithmHS);
        } catch (JWTCreationException ex) {
            //Invalid Signing configuration / Couldn't convert Claims.
            System.out.println("JWT Could not be created. \n " + ex.getMessage());
            throw ex;
        }
    }

    private void initDate() {
        iat = Instant.now();
        expDate = Instant.ofEpochMilli(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3)); // Will expires in 3 Days
        refreshDate = Instant.ofEpochMilli(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)); // Will expires in 30 Days
        offlineDate = Instant.ofEpochMilli(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(3)); // Will expires in 3 Hours
    }
}
