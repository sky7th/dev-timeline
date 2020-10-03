package com.sky7th.devtimeline.chat.config.security;

import com.sky7th.devtimeline.chat.config.security.exception.InvalidTokenRequestException;
import com.sky7th.devtimeline.core.domain.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenValidator {

    private static final Logger logger = LoggerFactory.getLogger(TokenValidator.class);
    public static final String ROLE = "role";
    public static final String NAME = "name";
    public static final String IMAGE_URL = "imageUrl";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public UserContext getUserContextFromToken(String token) {
        Claims claims = validateToken(token);
        Long userId = Long.parseLong(claims.getId());
        UserRole role = UserRole.valueOf(claims.get(ROLE, String.class));
        String name = claims.get(TokenValidator.NAME, String.class);
        String imageUrl = claims.get(TokenValidator.IMAGE_URL, String.class);

        return new UserContext(userId, role, name, imageUrl);
    }

    public Claims validateToken(String authToken) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();

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