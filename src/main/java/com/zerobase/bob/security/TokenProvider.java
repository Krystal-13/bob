package com.zerobase.bob.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final UserDetailServiceComponent userDetailsServiceComponent;
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;

    @Value("{spring.jwt.secret}")
    private String secretKey;

    public String generateToken(String userEmail) {

        Claims claims = Jwts.claims().setSubject(userEmail);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

    private Claims parseClaims(String token) {

        return Jwts.parser().setSigningKey(this.secretKey)
                .parseClaimsJws(token).getBody();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.parseClaims(token);

        UserDetails userDetails =
                this.userDetailsServiceComponent.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) return false;

        Claims claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }
}
