package edu.hubu.security;

import edu.hubu.auto.model.User;
import edu.hubu.base.Model;
import edu.hubu.base.web.QueryRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    public void restrictQuery(User current, String operation, QueryRequest query) {
    }

    public void restrictModel(User current, String operation, Model model) {
    }

    public void restrictUpdate(User current, String operation, QueryRequest query, Model model) {
    }
}
