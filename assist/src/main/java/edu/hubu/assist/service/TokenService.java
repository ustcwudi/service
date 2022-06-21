package edu.hubu.assist.service;

import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.interfaces.DecodedJWT;

import edu.hubu.auto.model.User;
import org.springframework.stereotype.Component;

@Component
public class TokenService extends edu.hubu.security.TokenService {

    @Override
    protected Builder encode(Builder builder, User user) {
        return super.encode(builder, user).withClaim("college", user.getCollege());
    }

    @Override
    protected void decode(DecodedJWT jwt, User user) {
        super.decode(jwt, user);
        user.setCollege(jwt.getClaim("college").asString());
    }
}
