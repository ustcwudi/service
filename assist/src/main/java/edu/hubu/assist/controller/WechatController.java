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
    public User getUser(String[] links, JSONObject json, JSONObject form) {
        var user = super.getUser(links, json, form);
        return user;
    }
}
