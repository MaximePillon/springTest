package springproject.project.repository;

import springproject.project.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("imageRepository")
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByTitle(String title);

    Image findByUserIdAndFilename(int id, String filename);

    Image findById(int id);

    ArrayList<Image> findAllByIsPublic(boolean isPublic);

    ArrayList<Image> findAllByIsPublicAndDescriptionContaining(boolean isPublic, String filter);
}
