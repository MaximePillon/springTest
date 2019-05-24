package springproject.project.repository;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import springproject.project.model.User;
import springproject.project.repository.UserRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

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
    }

    @Test
    public void findAnUser() throws Exception {
        User found = userRepository.findByEmail("testing@email.com");
        if (found == null) {
            throw new Exception("cannot find the user");
        }
    }

    @Test
    public void findActives() throws Exception {
        List<User> founds = userRepository.findByActive(1);
        if (founds.isEmpty()) {
            throw new Exception("cannot find active users");
        }
    }

}
