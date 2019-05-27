package springproject.project.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminAccessAspect {

    @Before("execution(* springproject.project.validator.TokenValidator.isValid(..))")
    public void logRequestedAccessToAdminData(JoinPoint joinPoint) throws Throwable {
        Logger logger = Logger.getInstance();
        logger.log("A request has requested access to admin data");
    }
}
