package springproject.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import springproject.project.model.User;
import springproject.project.service.UserService;

@Controller
public class BucketController {

    private UserService userService;

    @Autowired
    public BucketController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/bucket")
    public ModelAndView getBucket() {
        ModelAndView _new = new ModelAndView();

        _new.setViewName("bucket/home");
        return _new;
    }

    @GetMapping(value = "/bucket/{type}")
    public RedirectView applyBucket(@PathVariable(value = "type") String type) {
        User user = userService.getLoggedUser();

        user.setBundle(type);
        userService.validate(user);
        return new RedirectView("/home");
    }
}
