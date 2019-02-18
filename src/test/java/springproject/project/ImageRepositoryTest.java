package springproject.project;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import springproject.project.model.Image;
import springproject.project.model.User;
import springproject.project.repository.ImageRepository;
import springproject.project.repository.UserRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ImageRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Before
    public void createUser() {
        User test = new User();
        test.setUsername("test");
        test.setEmail("testing@email.com");
        test.setCity("testParadize");
        test.setFirstName("mitchel");
        test.setLastName("morris");
        test.setPassword("12345678");

        entityManager.persist(test);
        entityManager.flush();

        Image image = new Image();
        image.setTitle("hey");
        image.setIsPublic(true);
        image.setUser(test);
    }

    @Test
    public void findImage() throws Exception {
        Image image = imageRepository.findByTitle("hey");
        if (image == null)
            throw new Exception("cannot find the image");
    }

    @Test
    public void findPublic() throws Exception {
        List<Image> founds = imageRepository.findAllByIsPublic(true);
        if (founds.isEmpty()) {
            throw new Exception("cannot find public images");
        }
    }
}
