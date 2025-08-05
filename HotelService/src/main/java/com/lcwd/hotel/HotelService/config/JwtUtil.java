

package com.lcwd.hotel.HotelService.config;

import java.security.Key;
import javax.crypto.SecretKey;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    
    // Same secret as in your gateway and user service
    public static final String SECRET = "mySecretKey123912738aopsgjnspkmndfsopkvajoirjg94gf2opfng2moknm";

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSignKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new RuntimeException("JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new RuntimeException("JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new RuntimeException("JWT claims string is empty", e);
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractClaimFromToken(String token, String claimName) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(claimName, String.class);
    }
}