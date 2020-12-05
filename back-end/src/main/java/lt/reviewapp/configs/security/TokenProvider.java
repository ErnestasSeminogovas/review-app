package lt.reviewapp.configs.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TokenProvider {
    @Value("${token.access.salt}")
    private String accessTokenSalt;

    @Value("${token.access.duration}")
    private long accessTokenDuration;

    @Value("${token.refresh.salt}")
    private String refreshTokenSalt;

    @Value("${token.refresh.duration}")
    private long refreshTokenDuration;

    public String generateAccessToken(String username) {
        return generateToken(username, accessTokenDuration, accessTokenSalt);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, refreshTokenDuration, refreshTokenSalt);
    }

    private String generateToken(String username, long tokenDuration, String tokenSalt) {
        Date issuedAt = new Date();
        Date expiryDate = new Date(issuedAt.getTime() + tokenDuration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSalt)
                .compact();
    }

    public Optional<String> parseAccessUsername(String token) {
        return Optional.ofNullable(parseUsername(accessTokenSalt, token));
    }

    public Optional<String> parseRefreshUsername(String token) {
        return Optional.ofNullable(parseUsername(refreshTokenSalt, token));
    }

    private String parseUsername(String tokenSalt, String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenSalt).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return null;
        }
    }

}
