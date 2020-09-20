package com.sky7th.devtimeline.api.security;

import com.sky7th.devtimeline.api.config.AppProperties;
import com.sky7th.devtimeline.api.security.exception.InvalidTokenRequestException;
import com.sky7th.devtimeline.api.user.CustomUserDetails;
import com.sky7th.devtimeline.core.domain.user.UserRole;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Instant;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String ROLE = "role";

    private AppProperties appProperties;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(CustomUserDetails customUserDetails) {
        return createToken(customUserDetails.getId(), customUserDetails.getUserRole());
    }

    public String createToken(Long id, UserRole userRole) {
        Claims claims = Jwts.claims().setId(Long.toString(id));
        claims.put(ROLE, userRole);
        Instant expiryDate = Instant.now().plusMillis(appProperties.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public UserContext getUserContextFromToken(String token) {
        Claims claims = validateToken(token);
        Long userId = Long.parseLong(claims.getId());
        UserRole role = UserRole.valueOf(claims.get(ROLE, String.class));

        return new UserContext(userId, role);
    }

    public Claims validateToken(String authToken) {
        try {
            return Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken).getBody();

        } catch (SignatureException ex) {
            logger.error("JWT 서명 확인 불가");
            throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");

        } catch (MalformedJwtException ex) {
            logger.error("잘못된 JWT 구성");
            throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");

        } catch (ExpiredJwtException ex) {
            logger.error("JWT 유효기간 초과");
            throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");

        } catch (UnsupportedJwtException ex) {
            logger.error("JWT 형식 불일치");
            throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
            throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
        }
    }

}
