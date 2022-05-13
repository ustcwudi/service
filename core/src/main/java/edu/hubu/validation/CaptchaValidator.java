package edu.hubu.validation;

import edu.hubu.base.dao.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class CaptchaValidator implements ConstraintValidator<CaptchaValid, String> {

    @Autowired
    private RedisDao redisDao;

    @Override
    public void initialize(CaptchaValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            var pair = value.split(":");
            if (pair.length == 2) {
                return redisDao.get("captcha:" + pair[0]).equals(pair[1]);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
