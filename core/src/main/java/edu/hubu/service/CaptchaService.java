package edu.hubu.service;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Properties;

@Service
public class CaptchaService {

    private final DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

    public CaptchaService() {
        Properties properties = new Properties();
        properties.put("kaptcha.background.impl", "edu.hubu.config.TranslucentBackground");
        properties.put("kaptcha.border", "no");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
    }

    public String createText() {
        return defaultKaptcha.createText();
    }

    public BufferedImage createImage(String text) {
        return defaultKaptcha.createImage(text);
    }
}
