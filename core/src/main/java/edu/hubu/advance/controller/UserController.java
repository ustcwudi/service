package edu.hubu.advance.controller;

import edu.hubu.auto.model.User;
import edu.hubu.auto.request.query.UserQuery;
import edu.hubu.base.Result;
import edu.hubu.base.dao.MongoDao;
import edu.hubu.security.TokenService;
import edu.hubu.validation.CaptchaValid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户")
public class UserController extends edu.hubu.auto.controller.UserController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MongoDao<User, UserQuery> userMongoDao;

    @Data
    static class LoginForm {
        @NotEmpty(message = "账号不能为空")
        private String account;
        @NotEmpty(message = "密码不能为空")
        private String password;
        private Boolean remember;
        @CaptchaValid
        private String captcha;
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public Result login(@ApiIgnore HttpServletResponse response, @Valid @RequestBody LoginForm form,
            @RequestHeader(value = "link", required = false) String link) {
        Query query = new Query().addCriteria(Criteria.where("account").is(form.getAccount()))
                .addCriteria(Criteria.where("password").is(form.getPassword()));
        String[] links = link == null ? new String[] {} : link.split(",");
        var user = userMongoDao.findOne(query, links);
        if (user != null) {
            var maxAge = 8 * 3600;
            if (form.getRemember() != null && form.getRemember()) {
                maxAge = 7 * 24 * 3600;
            }
            var cookie = new Cookie("jwt", tokenService.encode(user, maxAge));
            cookie.setMaxAge(maxAge);
            cookie.setPath("/");
            response.addCookie(cookie);
            return Result.ok(user);
        } else {
            return Result.fail("账号或密码错误");
        }
    }

    @GetMapping("info")
    @ApiOperation("个人信息")
    public Result info(@ApiIgnore User current,
            @RequestHeader(value = "link", required = false) String link) {
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(userMongoDao.findOne(current.getId(), links));
    }
}
