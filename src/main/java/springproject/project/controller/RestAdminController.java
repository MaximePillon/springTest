package springproject.project.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springproject.project.model.Image;
import springproject.project.model.User;
import springproject.project.service.ImageService;
import springproject.project.service.UserService;
import springproject.project.validator.TokenConstraint;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@org.springframework.web.bind.annotation.RestController
@Validated
public class RestAdminController {

    private UserService userService;

    private ImageService imageService;

    @Autowired
    public RestAdminController(UserService userService,
                               ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @RequestMapping("/rest/user")
    public Object userInfo(@TokenConstraint @RequestHeader("token") String token,
                           @RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0) {
            return userService.findAll();
        }
        return userService.findById(id);
    }


    @RequestMapping("/rest/userEmail")
    public Object userByEmail(@TokenConstraint @RequestHeader("token") String token,
                              @RequestParam(value = "email", defaultValue = "") String email) {
        if (email.isEmpty()) {
            return null;
        }
        return userService.findByPartialEmail(email);
    }

    @RequestMapping("/rest/image")
    public Object imageInfo(@TokenConstraint @RequestHeader("token") String token,
                            @RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0) {
            return imageService.findAll();
        }
        return imageService.findById(id);
    }

    @RequestMapping("/rest/imageUser")
    public Object imagesByUser(@TokenConstraint @RequestHeader("token") String token,
                               @RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0) {
            return null;
        }
        return imageService.findByUserId(id);
    }

    @RequestMapping("/rest/imageDesc")
    public Object imagesByDescription(@TokenConstraint @RequestHeader("token") String token,
                                      @RequestParam(value = "description", defaultValue = "") String desc) {
        if (desc.isEmpty()) {
            return null;
        }
        return imageService.findByDescription(desc);
    }

    @RequestMapping("/rest/imageRemove")
    public Object imageRemoveById(@TokenConstraint @RequestHeader("token") String token,
                                  @RequestParam(value = "id", defaultValue = "0") int id) {
        imageService.removeImageById(id);
        return null;
    }

    @RequestMapping("/rest/imageAdd")
    @ResponseStatus(value = HttpStatus.OK)
    public Object addImage(@TokenConstraint @RequestHeader("token") String token,
                           @RequestBody Map<String, String> payload) throws IOException {

        byte[] decodedBytes = Base64.getDecoder().decode(payload.get("file"));

        User user = userService.findById(Integer.parseInt(payload.get("userId")));

        Image image = new Image();
        image.setTitle(payload.get("title"));
        image.setDescription(payload.get("description"));
        image.setUser(user);

        imageService.createImageFromRest(decodedBytes, image, payload.get("filename"));
        return null;
    }

    @RequestMapping(value = "/rest/bundle", produces = "application/json")
    public Object getUserBundle(@RequestParam(value = "name", defaultValue = "") String name) {
        System.out.println("A request is received with: " + name);
        if (name.isEmpty())
            return null;

        return userService.findEmailWithPackage(name);
    }

    @RequestMapping(
            value = "/rest/imageId",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> imageById(@TokenConstraint @RequestHeader("token") String token,
                                            @RequestParam(value = "id", defaultValue = "0") int id,
                                            HttpServletResponse response) throws IOException {
        String path = imageService.getPathById(id);

        File serverFile = new File(imageService.getPathById(id));

        byte[] bytes = Files.readAllBytes(serverFile.toPath());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }


    public ResponseEntity<String> key(HttpServletRequest request) {
        if (request.getHeader("authorization") != "toto") {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
