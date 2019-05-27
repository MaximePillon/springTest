package springproject.project.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import springproject.project.service.Logger;

import java.io.IOException;

@Aspect
@Component
public class ThrowerAccessAspect {

    @AfterThrowing(pointcut = "within(springproject.project.service.ImageService)", throwing = "ex")
    public void logThrownErrorByImageService(IOException ex) {
        Logger logger = Logger.getInstance();
        logger.log("Exception thrown by ImageService class\nException reason: " + ex.toString());
    }
}
