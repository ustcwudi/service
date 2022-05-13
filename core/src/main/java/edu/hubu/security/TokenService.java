package edu.hubu.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.hubu.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenService {

    @Autowired
    private ServiceConfiguration serviceConfiguration;

    public String encode(Token token) {
        return JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 3600))
                .withClaim("uid", token.getUid())
                .withClaim("rid", token.getRid())
                .withClaim("account", token.getAccount())
                .sign(Algorithm.HMAC512(serviceConfiguration.getSecret()));
    }

    public Token decode(String jwt) {
        var decodedJWT = JWT.require(Algorithm.HMAC512(serviceConfiguration.getSecret())).build().verify(jwt);
        var claims = decodedJWT.getClaims();
        Token token = new Token();
        token.setUid(claims.get("uid").asString());
        token.setRid(claims.get("rid").asString());
        token.setAccount(claims.get("account").asString());
        return token;
    }
}
