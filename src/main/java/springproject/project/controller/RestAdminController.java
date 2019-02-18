package springproject.project.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springproject.project.model.User;
import springproject.project.service.ImageService;
import springproject.project.service.UserService;
import springproject.project.validator.TokenConstraint;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@org.springframework.web.bind.annotation.RestController
@Validated
public class RestAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @RequestMapping("/rest/user")
    public Object userInfo(@TokenConstraint @RequestHeader("token") String token,
                           @RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0) {
            return userService.findAll();
        }
        return userService.findById(id);
    }

    @RequestMapping("/rest/image")
    public Object imageInfo(@TokenConstraint @RequestHeader("token") String token,
                            @RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0) {
            return imageService.findAll();
        }
        return imageService.findById(id);
    }

    public ResponseEntity<String> key(HttpServletRequest request) {
        if (request.getHeader("authorization") != "toto") {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
