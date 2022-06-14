package edu.hubu.security;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import edu.hubu.base.Result;
import edu.hubu.base.dao.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Order(1)
@Aspect
@Component
@Slf4j
public class TokenCheck {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisDao redisDao;

    @Pointcut("execution(public * edu.hubu..*Controller.*(edu.hubu.security.Token, ..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        var arguments = joinPoint.getArgs();
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof Token) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
                redisDao.set("url", request.getRequestURI(), 10000);
                var cookies = request.getCookies();
                if (cookies == null)
                    return Result.fail("用户未登录");
                for (var cookie : cookies) {
                    if (cookie.getName().equals("jwt")) {
                        try {
                            var token = tokenService.decode(cookie.getValue());
                            arguments[i] = token;
                            log.info(token.getAccount() + ":" + token.getUid() + ":" + request.getMethod() + ":"
                                    + request.getRequestURI());
                            return joinPoint.proceed(arguments);
                        } catch (AlgorithmMismatchException e) {
                            log.error(e.getMessage());
                            return Result.fail("令牌验证失败");
                        } catch (SignatureVerificationException e) {
                            log.error(e.getMessage());
                            return Result.fail("令牌签名错误");
                        } catch (TokenExpiredException e) {
                            log.error(e.getMessage());
                            return Result.fail("令牌过期");
                        } catch (InvalidClaimException e) {
                            log.error(e.getMessage());
                            return Result.fail("令牌字段错误");
                        }
                    }
                }
                break;
            }
        }
        return Result.fail("用户未登录");
    }
}
