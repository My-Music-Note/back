package my.music.note.back.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import my.music.note.back.config.TokenConfig;
import my.music.note.back.data.dto.jwt.response.TokenCreateResponse;
import my.music.note.back.validator.TokenValidator;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenConfig tokenConfig;
    private final TokenValidator tokenValidator;

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    private static final String AUTHORITY = "authority";

    public TokenCreateResponse createToken(Long userId, boolean isAdmin) {

        Date issuedAt = new Date(System.currentTimeMillis());

        String authority;

        if (isAdmin) {
            authority = ROLE_ADMIN;
        } else {
            authority = ROLE_USER;
        }

        String token = JWT.create()
                .withIssuer(tokenConfig.getIssuer())
                .withSubject(String.valueOf(userId))
                .withIssuedAt(issuedAt)
                .withExpiresAt(new Date(issuedAt.getTime() + tokenConfig.getAccessExpiration()))
                .withClaim(AUTHORITY, authority)
                .sign(Algorithm.HMAC512(tokenConfig.getSecret()));

        return new TokenCreateResponse(token);
    }

    private DecodedJWT isValidToken(String token) {
        return tokenValidator.verify(token);
    }

    public Long getUserId(String token) {
        DecodedJWT decodedJWT = isValidToken(token);
        return Long.valueOf(decodedJWT.getSubject());
    }

}
