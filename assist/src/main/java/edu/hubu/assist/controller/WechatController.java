package edu.hubu.assist.controller;

import edu.hubu.auto.model.User;
import io.swagger.annotations.Api;

import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wechat")
@Api(tags = "微信")
public class WechatController extends edu.hubu.advance.controller.WechatController {
    @Override
    public User newUser(JSONObject json, JSONObject form) {
        var user = super.newUser(json, form);
        user.setAccount(form.getString("account"));
        user.setName(form.getString("name"));
        user.setRole((form.getIntValue("type") == 0 ? "000000000000000000000001" : "000000000000000000000002"));
        return user;
    }
}
