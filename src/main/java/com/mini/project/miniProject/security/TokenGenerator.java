package com.mini.project.miniProject.security;


import com.mini.project.miniProject.features.auth.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenGenerator {
    private final  JwtEncoder jwtAccessTokenEncoder;
    private final JwtEncoder jwtRefreshTokenEncoder;
    public TokenGenerator(
            JwtEncoder jwtAccessTokenEncoder,
            @Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder
    ) {
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
        this.jwtAccessTokenEncoder = jwtAccessTokenEncoder;
    }

    private String createAccessToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Instant now = Instant.now();
        //  we can also create scope for the token from the userDetails object here !
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.HOURS))
                .subject(userDetails.getUsername()) // email
                .issuer("istad.co.mobilebanking") //
                .id(userDetails.getUser().getId())
//                .notBefore(now)
//                .claims("scope", "read write")
                .build();
        return jwtAccessTokenEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    // expire after 7 days
    private String createRefreshToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(userDetails.getUsername())
                .issuer("istad.co.mobilebanking")
                .build();
        return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    // token rotation !
    public AuthResponse generateTokens(Authentication authentication ) {
        if(!(authentication.getPrincipal() instanceof CustomUserDetails  customUserDetails)){
            throw new BadCredentialsException("Provided Token is not valid");
        }
        String refreshToken ;
        if( authentication.getCredentials() instanceof Jwt jwt){
            Instant now = Instant.now();
            Instant expireAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expireAt);
            long daysUtilsExpired = duration.toDays();
            // Duration.between(Instant.now(), jwt.getExpiresAt()).toDays() < 7
            if(daysUtilsExpired < 7){
                refreshToken = createRefreshToken(authentication);
            }else {
                refreshToken = jwt.getTokenValue();
            }
        }else {
            refreshToken = createRefreshToken(authentication);
        }
        return AuthResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(createAccessToken(authentication))
                .userId(customUserDetails.getUser().getId())
                .build();

    }
}
