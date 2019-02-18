package springproject.project.Task;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springproject.project.service.ImageService;
import springproject.project.service.Logger;
import springproject.project.service.UserService;

@Component
public class LoggingJob implements Job {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        Logger logger = Logger.getInstance();

        logger.log(userService.analysis());
        logger.log(imageService.analysis());
    }
}


