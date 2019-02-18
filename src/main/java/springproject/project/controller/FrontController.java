package springproject.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springproject.project.model.Image;
import springproject.project.service.ImageService;

import java.util.ArrayList;

@Controller
public class FrontController {

    @Autowired
    private ImageService imageService;

    @GetMapping(value= {"/", "/home"})

    public ModelAndView homePage() {
        ModelAndView _new = new ModelAndView();

        ArrayList<Image> images = imageService.getPublics();

        _new.addObject("images", images);
        _new.addObject("filter", "");
        _new.setViewName("home");
        return _new;
    }

    @GetMapping(value = "search")
    public ModelAndView search(@RequestParam(value = "filter") String key) {
        ModelAndView _new = new ModelAndView();

        ArrayList<Image> images = imageService.getPublicsWithKey(key);

        _new.addObject("images", images);
        _new.addObject("filter", key);
        _new.setViewName("home");
        return _new;
    }
}
