package my.music.note.back.validator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import my.music.note.back.config.TokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {

    private final JWTVerifier jwtVerifier;


    @Autowired
    private TokenValidator(TokenConfig tokenConfig) {
        Algorithm algorithm = Algorithm.HMAC512(tokenConfig.getSecret());
        this.jwtVerifier = JWT.require(algorithm).build();
    }

    public DecodedJWT verify(String token) {
        return jwtVerifier.verify(token);
    }

}
