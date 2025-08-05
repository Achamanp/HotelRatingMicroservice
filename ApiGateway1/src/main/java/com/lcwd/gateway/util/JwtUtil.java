package com.lcwd.gateway.util;

import java.security.Key;

import javax.crypto.SecretKey;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {


    public static final String SECRET = "mySecretKey123912738aopsgjnspkmndfsopkvajoirjg94gf2opfng2moknm";


    public void validateToken(String token) {
		try {
	    	 Jwts.parser()
	    			.verifyWith((SecretKey)getSignKey())
	    			.build().parseSignedClaims(token);
	    	 
	    	}catch (MalformedJwtException e) {
	            log.error("Invalid JWT token: {}", e.getMessage());
	        } catch (ExpiredJwtException e) {
	            log.error("JWT token is expired: {}", e.getMessage());
	        } catch (UnsupportedJwtException e) {
	            log.error("JWT token is unsupported: {}", e.getMessage());
	        } catch (IllegalArgumentException e) {
	            log.error("JWT claims string is empty: {}", e.getMessage());
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
}