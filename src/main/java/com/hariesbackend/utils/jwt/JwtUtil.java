package com.hariesbackend.utils.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final byte[] SECRET_KEY = "harieshariesharieshaireshariesha".getBytes();
    private static final long ACCESS_TOKEN_EXPIRY = 1 * 60 * 1000; // 60 minutes
    private static final long REFRESH_TOKEN_EXPIRY = 30 * 24 * 10 * 60 * 1000; // 30 days
    private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    public static String generateAccessToken(String username) {

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
                .signWith(algorithm, SECRET_KEY)
                .compact();
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
        System.out.println("body access" + claimsJws.getBody());
        return token;
    }

    public static String generateRefreshToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY))
                .signWith(algorithm, SECRET_KEY)
                .compact();
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
        System.out.println("body access" + claimsJws.getBody());
        return token;
    }

    public static Claims verifyToken(String token) throws RuntimeException, Exception {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY).build()
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired", e);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }

    }

    // 토큰에서 만료 날짜를 가져오는 메서드
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // 토큰에서 특정 클레임을 가져오는 메서드
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // 토큰에서 모든 클레임을 가져오는 메서드
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰이 만료되었는지 확인하는 메서드
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // 토큰을 검증하는 메서드
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
