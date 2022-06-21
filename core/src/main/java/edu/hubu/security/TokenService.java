package edu.hubu.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import edu.hubu.auto.model.User;
import edu.hubu.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenService {

    @Autowired
    protected ServiceConfiguration serviceConfiguration;

    protected Builder encode(Builder builder, User user) {
        return builder
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .withClaim("account", user.getAccount());
    }

    protected void decode(DecodedJWT jwt, User user) {
        user.setId(jwt.getClaim("id").asString());
        user.setRole(jwt.getClaim("role").asString());
        user.setAccount(jwt.getClaim("account").asString());
    }

    public String encode(User user, int maxAge) {
        var builder = JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + 1000 * maxAge));
        return encode(builder, user).sign(Algorithm.HMAC512(serviceConfiguration.getSecret()));
    }

    public User decode(String jwt) {
        var decodedJWT = JWT.require(Algorithm.HMAC512(serviceConfiguration.getSecret())).build().verify(jwt);
        var user = new User();
        decode(decodedJWT, user);
        return user;
    }
}
