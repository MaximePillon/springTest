package springproject.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import springproject.project.model.Image;
import springproject.project.model.User;
import springproject.project.service.ImageEditor;
import springproject.project.service.ImageService;
import springproject.project.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class GalleryController {

    private ImageService imageService;

    private UserService userService;

    private ImageEditor imageEditor;

    @Autowired
    public GalleryController(ImageService imageService,
                             UserService userService,
                             ImageEditor imageEditor) {
        this.imageEditor = imageEditor;
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping(value = "/gallery")
    public ModelAndView getGallery() {
        ModelAndView _new = new ModelAndView();

        User user = userService.getLoggedUser();

        Image image = new Image();
        _new.addObject("image", image);
        _new.addObject("user", user);
        _new.addObject("imagePath", imageService.getImagePath());
        _new.setViewName("gallery/home");
        return _new;
    }

    @PostMapping(value = "/upload")
    public RedirectView addPhoto(@Valid Image image,
                                 @RequestParam("file") MultipartFile file) {
        User user = userService.getLoggedUser();

        if (user.imageCounter() >= user.getBundleObject().numberOfFile())
            return new RedirectView("gallery");

        image.setUser(user);

        try {
            imageService.createImage(file, image);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return new RedirectView("gallery");
    }

    @PostMapping(value = "/edit")
    public RedirectView editPhoto(Image image) {

        Image actual = imageService.getById(image.getId());
        imageService.updateImage(actual, image);

        return new RedirectView("gallery");
    }

    @GetMapping(value = "delete/{imgId}")
    public RedirectView deletePhoto(@PathVariable(value = "imgId") int id) {
        User user = userService.getLoggedUser();
        Image image = imageService.getById(id);

        userService.deleteImage(user, image);
        try {
            imageService.delete(image);
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return new RedirectView("/gallery");
    }

    @GetMapping(value = "download/{imgId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public FileSystemResource downloadPhoto(@PathVariable(value = "imgId") int id,
                                            HttpServletResponse response) {
        Image img = imageService.getById(id);
        File initialFile = new File(imageService.getImagePath() + img.getUser().getId() + "/" + img.getFilename());
        return new FileSystemResource(initialFile);
    }

    @GetMapping(value = "details/{imageId}")
    public ModelAndView detailsPhoto(@PathVariable(value = "imageId") int id) {
        ModelAndView _new = new ModelAndView();

        User user = userService.getLoggedUser();
        Image image = imageService.getById(id);

        _new.addObject("image", image);
        _new.addObject("user", user);
        _new.setViewName("gallery/details");
        return _new;
    }

    @GetMapping(value = "image/{userId}/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName,
                           @PathVariable(value = "userId") int id) throws IOException {
        File serverFile = new File(imageService.getImagePath() + id + '/' + imageName);

        return Files.readAllBytes(serverFile.toPath());
    }

    @GetMapping(value = "filter/{imgId}/{filterName}")
    public RedirectView applyFilter(@PathVariable(value = "imgId") int imgId,
                                    @PathVariable(value = "filterName") String filterName) {
        String path = imageService.getPathById(imgId);
        imageEditor.apply(path, filterName, 50);
        return new RedirectView("/details/" + imgId);
    }
}
