package edu.hubu.advance.controller;

import edu.hubu.base.dao.RedisDao;
import edu.hubu.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.UUID;

@Controller
@RequestMapping("/api/captcha")
@Api(tags = "验证码")
public class CaptchaController {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private CaptchaService captchaService;

    @ApiOperation(value = "生成验证码")
    @GetMapping()
    public void handle(@ApiIgnore HttpServletResponse response) throws java.io.IOException {
        String captcha = captchaService.createText();
        BufferedImage image = captchaService.createImage(captcha);
        String uuid = UUID.randomUUID().toString();
        redisDao.set("captcha:" + uuid, captcha, 60);
        response.setHeader("id", uuid);
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache");
        var out = response.getOutputStream();
        ImageIO.write(image, "png", out);
    }
}
