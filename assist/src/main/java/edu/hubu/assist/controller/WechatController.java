package edu.hubu.assist.controller;

import edu.hubu.auto.model.*;
import edu.hubu.auto.request.query.*;
import edu.hubu.base.dao.MongoDao;
import io.swagger.annotations.Api;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wechat")
@Api(tags = "微信")
public class WechatController extends edu.hubu.advance.controller.WechatController {

    @Autowired
    private MongoDao<Teacher, TeacherQuery> teacherMongoDao;

    @Autowired
    private MongoDao<Student, StudentQuery> studentMongoDao;

    @Override
    public User newUser(JSONObject json, JSONObject form) {
        var user = super.newUser(json, form);
        var account = form.getString("account");
        var name = form.getString("name");
        var type = form.getInteger("type");
        if (name != null && !name.isEmpty() && account != null && !account.isEmpty() && type >= 0 && type <= 1) {
            user.setAccount(account);
            user.setName(form.getString("name"));
            if (type == 0) {
                var teacher = teacherMongoDao.findOne(new Query()
                        .addCriteria(Criteria.where("name").is(name).andOperator(Criteria.where("code").is(account))));
                if (teacher != null) {
                    user.setRole("000000000000000000000001");
                    user.setCollege(teacher.getCollege());
                    user.setDepartment(teacher.getDepartment());
                } else
                    return null;
            } else if (type == 1) {
                var student = studentMongoDao.findOne(new Query()
                        .addCriteria(Criteria.where("name").is(name).andOperator(Criteria.where("code").is(account))));
                if (student != null) {
                    user.setRole("000000000000000000000002");
                    user.setCollege(student.getCollege());
                    user.setDepartment(student.getDepartment());
                } else
                    return null;
            } else {
                return null;
            }
            return user;
        } else {
            return null;
        }
    }
}
