package chungbazi.chungbazi_be.domain.auth.jwt;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String accessTokenGenerate(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String refreshTokenGenerate(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .setAllowedClockSkewSeconds(60)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new BadRequestHandler(ErrorStatus.MALFORMED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new BadRequestHandler(ErrorStatus.UNSUPPORTED_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new BadRequestHandler(ErrorStatus.EXPIRED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new BadRequestHandler(ErrorStatus.EMPTY_CLAIMS);
        } catch (JwtException e) {
            throw new BadRequestHandler(ErrorStatus.INVALID_TOKEN);
        }
    }

    public String extractSubject(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new BadRequestHandler(ErrorStatus.EXPIRED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new BadRequestHandler(ErrorStatus.MALFORMED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new BadRequestHandler(ErrorStatus.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new BadRequestHandler(ErrorStatus.EMPTY_CLAIMS);
        } catch (JwtException e) {
            throw new BadRequestHandler(ErrorStatus.INVALID_TOKEN);
        }
    }

    public Long getUserIdFromToken(String token) {
        return Long.parseLong(extractSubject(token)); // subject를 userId로 변환
    }

}