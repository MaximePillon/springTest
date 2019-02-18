package springproject.project.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;
import springproject.project.model.Image;
import springproject.project.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service("imageService")
public class ImageService {

    private static String IMAGE_PATH = "user-images/";

    private ImageRepository imageRepository;
    private ResourceLoader resourceLoader;

    @Autowired
    public ImageService(ImageRepository imageRepository,
                        ResourceLoader resourceLoader) {
        this.imageRepository = imageRepository;
        this.resourceLoader = resourceLoader;
    }

    public Image getByUserAndFilename(int id, String name) {
            return imageRepository.findByUserIdAndFilename(id, name);
    }

    public Image getById(int id) {
        return imageRepository.findById(id);
    }

    public String getPathById(int id) {
        Image image = getById(id);
        return IMAGE_PATH + image.getUser().getId() + "/" + image.getFilename();
    }

    public ArrayList<Image> getPublics() {
        return imageRepository.findAllByIsPublic(true);
    }

    public ArrayList<Image> getPublicsWithKey(String key) {
        return imageRepository.findAllByIsPublicAndDescriptionContaining(true, key);
    }

    public void createImage(MultipartFile file, Image image) throws IOException {
        Logger logger = Logger.getInstance();

        if (!file.isEmpty()) {
            File destFile = new File(IMAGE_PATH + image.getUser().getId() + "/" + file.getOriginalFilename());
            destFile.getParentFile().mkdirs();
            destFile.createNewFile();

            Path destPath = destFile.toPath();
            Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
            image.setFilename(file.getOriginalFilename());
            imageRepository.save(image);
            logger.logCreate("image for user " + image.getUser().getUsername() + " of name " + image.getTitle());
        }
    }

    public void delete(Image image) throws IOException {
        Logger logger = Logger.getInstance();

        Files.delete(Paths.get(IMAGE_PATH + image.getUser().getId() + "/" + image.getFilename()));
        logger.logDelete("image for user " + image.getUser().getUsername() + " of name " + image.getTitle());
        imageRepository.delete(image);
        imageRepository.flush();

    }

    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    public Image findById(int id) {
        return imageRepository.findById(id);
    }

    public void updateImage(Image actual, Image newDetails)
    {
        actual.setTitle(newDetails.getTitle());
        actual.setIsPublic(newDetails.isIsPublic());
        actual.setDescription(newDetails.getDescription());

        imageRepository.saveAndFlush(actual);
    }

    public String getImagePath() {
        return IMAGE_PATH;
    }

    public String analysis() {
        String output = "Begining of the Image analysis:\n";

        output += "- " + imageRepository.count() + " images are actually uploaded\n";
        output += "- " + imageRepository.findAllByIsPublic(true).size() + " of them are public";

        return output;
    }
}
