package edu.hubu.assist.service;

import edu.hubu.auto.model.*;
import edu.hubu.auto.request.query.*;
import edu.hubu.base.Model;
import edu.hubu.base.web.QueryRequest;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthService extends edu.hubu.security.AuthService {

    @Override
    public void restrictQuery(User current, String operation, QueryRequest query) {
        super.restrictQuery(current, operation, query);
        if (current.getCollege() != null) {
            if (query instanceof ClazzQuery) {
                ((ClazzQuery) query).setCollege(current.getCollege());
            } else if (query instanceof CollegeQuery) {
                ((CollegeQuery) query).setId(List.of(current.getCollege()));
            } else if (query instanceof CourseQuery) {
                ((CourseQuery) query).setCollege(current.getCollege());
            } else if (query instanceof JobQuery) {
                ((JobQuery) query).setCollege(current.getCollege());
            } else if (query instanceof ScoreQuery) {
                ((ScoreQuery) query).setCollege(current.getCollege());
            } else if (query instanceof StudentQuery) {
                ((StudentQuery) query).setCollege(current.getCollege());
            } else if (query instanceof TeacherQuery) {
                ((TeacherQuery) query).setCollege(current.getCollege());
            } else if (query instanceof UserQuery) {
                ((UserQuery) query).setCollege(current.getCollege());
            }
        }
    }

    @Override
    public void restrictModel(User current, String operation, Model model) {
        super.restrictModel(current, operation, model);
        if (current.getCollege() != null) {
            if (model instanceof Clazz) {
                ((Clazz) model).setCollege(current.getCollege());
            } else if (model instanceof Course) {
                ((Course) model).setCollege(current.getCollege());
            } else if (model instanceof Job) {
                ((Job) model).setCollege(current.getCollege());
            } else if (model instanceof Score) {
                ((Score) model).setCollege(current.getCollege());
            } else if (model instanceof Student) {
                ((Student) model).setCollege(current.getCollege());
            } else if (model instanceof Teacher) {
                ((Teacher) model).setCollege(current.getCollege());
            } else if (model instanceof User) {
                ((User) model).setCollege(current.getCollege());
            }
        }
    }

    @Override
    public void restrictUpdate(User current, String operation, QueryRequest query, Model model) {
        super.restrictUpdate(current, operation, query, model);
        restrictModel(current, operation, model);
        restrictQuery(current, operation, query);
    }
}
