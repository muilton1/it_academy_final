package it.academy.user_service.controller.utils;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtTokenUtil {
    private static final String jwtSecret = "NDQ1ZjAzNjQtMzViZi00MDRjLTljZjQtNjNjYWIyZTU5ZDYw";
    private static final String jwtIssuer = "ITAcademy";

    public static String generateAccessToken(UserDetails user) {
        return generateAccessToken(user.getUsername());
    }

    public static String generateAccessToken(String name) {
        return Jwts.builder()
                .setSubject(name)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public static String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public static Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public static boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new SignatureException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("JWT claims string is empty");
        }
        return false;
    }
}
