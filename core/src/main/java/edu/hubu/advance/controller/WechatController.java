package edu.hubu.advance.controller;

import edu.hubu.auto.model.User;
import edu.hubu.auto.request.query.UserQuery;
import edu.hubu.base.Result;
import edu.hubu.base.dao.MongoDao;
import edu.hubu.config.WechatConfiguration;
import edu.hubu.security.Token;
import edu.hubu.security.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/wechat")
@Api(tags = "微信")
@Slf4j
public class WechatController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WechatConfiguration wechatConfiguration;

    @Autowired
    private MongoDao<User, UserQuery> userMongoDao;

    public User newUser(JSONObject json, JSONObject form) {
        var user = new User();
        user.setOpenIdentify(json.getString("openid"));
        user.setUnionIdentify(json.getString("unionid"));
        return user;
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public Result login(@ApiIgnore HttpServletResponse response, @RequestBody JSONObject form,
            @RequestHeader(value = "link", required = false) String link) throws Exception {
        var url = new URL(
                "https://api.weixin.qq.com/sns/jscode2session?appid=" + wechatConfiguration.getId()
                        + "&secret=" + wechatConfiguration.getSecret()
                        + "&js_code=" + form.getString("code") + "&grant_type=authorization_code");
        var connection = url.openConnection();
        connection.connect();
        var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String lines = "";
        String line = reader.readLine();
        while (line != null) {
            lines += line;
            line = reader.readLine();
        }
        log.debug(lines);
        var json = JSONObject.parseObject(lines);
        if (json.getIntValue("errcode") == 0) {
            String[] links = link == null ? new String[] {} : link.split(",");
            var user = userMongoDao
                    .findOne(new Query().addCriteria(Criteria.where("openIdentify").is(json.getString("openid"))),
                            links);
            if (user == null) {
                // 未注册微信用户，开始注册
                user = newUser(json, form);
                if (user != null) {
                    userMongoDao.add(user);
                }
            }
            if (user != null) {
                var token = new Token();
                token.setUid(user.getId());
                token.setRid(user.getRole());
                token.setAccount(user.getAccount());
                var maxAge = 8 * 3600;
                var cookie = new Cookie("jwt", tokenService.encode(token, maxAge));
                cookie.setMaxAge(maxAge);
                cookie.setPath("/");
                response.addCookie(cookie);
                return Result.ok(user);
            } else {
                return Result.fail("验证失败");
            }
        } else {
            return Result.fail(json.getString("errmsg"));
        }
    }
}
