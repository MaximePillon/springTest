package springproject.project;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springproject.project.model.User;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@DataJpaTest
@AutoConfigureMockMvc(secure = false)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

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
    public void userTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "this-is-token");
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/user").headers(headers)).andExpect(status().isNotFound());

    }

    @Test
    public void userGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/user?id=1").header("token","this-is-token"));
    }
}
