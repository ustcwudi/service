package edu.hubu.assist.aspect;

import edu.hubu.auto.model.User;
import edu.hubu.auto.request.query.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CollegeCheck {

    @Pointcut("execution(public * edu.hubu..*Controller.*(edu.hubu.auto.model.User, ..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        var arguments = joinPoint.getArgs();
        User current = null;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof User) {
                current = (User) arguments[i];
                if (current != null && current.getCollege() == null)
                    break;
            }
            if (arguments[i] instanceof StudentQuery) {
                var query = (StudentQuery) arguments[i];
                query.setCollege(current.getCollege());
                arguments[i] = query;
                break;
            } else if (arguments[i] instanceof JobQuery) {
                var query = (JobQuery) arguments[i];
                query.setCollege(current.getCollege());
                arguments[i] = query;
                break;
            } else if (arguments[i] instanceof CourseQuery) {
                var query = (CourseQuery) arguments[i];
                query.setCollege(current.getCollege());
                arguments[i] = query;
                break;
            } else if (arguments[i] instanceof TeacherQuery) {
                var query = (TeacherQuery) arguments[i];
                query.setCollege(current.getCollege());
                arguments[i] = query;
                break;
            }
        }
        return joinPoint.proceed(arguments);
    }
}
