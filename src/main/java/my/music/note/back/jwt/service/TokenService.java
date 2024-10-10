package my.music.note.back.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import my.music.note.back.config.TokenConfig;
import my.music.note.back.jwt.validator.TokenValidator;
import my.music.note.back.user.dto.request.DeleteAccountRequest;
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

    public String createToken(String providerId, boolean isAdmin) {

        Date issuedAt = new Date(System.currentTimeMillis());

        String authority;

        if (isAdmin) {
            authority = ROLE_ADMIN;
        } else {
            authority = ROLE_USER;
        }

        return JWT.create()
                .withIssuer(tokenConfig.getIssuer())
                .withSubject(providerId)
                .withIssuedAt(issuedAt)
                .withExpiresAt(new Date(issuedAt.getTime() + tokenConfig.getAccessExpiration()))
                .withClaim(AUTHORITY, authority)
                .sign(Algorithm.HMAC512(tokenConfig.getSecret()));
    }

    private DecodedJWT isValidToken(String token) {
        return tokenValidator.verify(token);
    }

    public String getProviderId(String token) {
        DecodedJWT decodedJWT = isValidToken(token);
        return decodedJWT.getSubject();
    }

}
