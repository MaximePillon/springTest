package springproject.project.browser;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=9000")
@SeleniumAnnotation(driver = ChromeDriver.class, baseUrl = "http://localhost:9000")
public class BrowserTesting {

    private String expectedTitle = "Epicture";
    private String loginTitle = "Login";
    private String frenchGreeting = "Bienvenu sur le project Epicture !";
    private String englishGreeting = "Welcome to Epicture project !";
    private String errorLogin = "Email or Password invalid, please verify";
    private String errorUsername = "Provide an username please";
    private String errorFirstname = "Provide your firstname please";
    private String errorLastname = "Provide your lastname please";
    private String errorEmail = "Please provide an email";

    @Autowired
    private WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver","/home/max/algebra/springProject/lib/chromedriver");
    }

    @Test
    public void mainPageTitleTest() {
        String actualTitle = driver.getTitle();

        assertNotNull(actualTitle);
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void mainPageLangTest() {
        //Getting the text of the main page
        WebElement element = driver.findElement(By.id("greeting"));
        String content = element.getText();

        // Asserting that the text is by default in english
        assertNotNull(content);
        assertEquals(englishGreeting, content);

        // get the element to switch language to french
        element = driver.findElement(By.id("fr"));
        element.click();
        element = driver.findElement(By.id("greeting"));
        content = element.getText();

        // Asserting that the language has actually been shifted to french
        assertNotNull(content);
        assertEquals(frenchGreeting, content);
    }

    @Test
    public void loginRedirectionTest() {
        //getting the gallery page which is a protected one
        WebElement element = driver.findElement(By.id("gallery"));
        element.click();

        String actualTitle = driver.getTitle();
        assertEquals(loginTitle, actualTitle);
    }

    @Test
    public void lockEmptyFieldLogin() {
        //getting the login page
        WebElement element = driver.findElement(By.id("profile"));
        element.click();

        //get the login button and click on it
        element = driver.findElement(By.id("login"));
        element.click();

        //check that an error has appeared
        element = driver.findElement(By.id("error"));
        String content = element.getText();
        assertEquals(errorLogin, content);

        // set a random email
        element = driver.findElement(By.id("email"));
        element.sendKeys("value", "this@email.com");

        // get the login button adn click on it
        element = driver.findElement(By.id("login"));
        element.click();

        //check that an error has appeared
        element = driver.findElement(By.id("error"));
        content = element.getText();
        assertEquals(errorLogin, content);
    }

    @Test
    public void checkUserNameError() {
        String content = registerErrorGet("errorUsername");
        assertEquals(errorUsername, content);

        Boolean result = registerFillAndErrorGet("username", "ThisIsAnUsername", "errorUsername");
        assertTrue(result);
    }

    @Test
    public void checkFirstnameError() {
        String content = registerErrorGet("errorFirstname");
        assertEquals(errorFirstname, content);

        Boolean result = registerFillAndErrorGet("firstname", "ThisIsAFirstname", "errorFirstname");
        assertTrue(result);
    }

    @Test
    public void checkLastnameError() {
        String content = registerErrorGet("errorLastname");
        assertEquals(errorLastname, content);

        Boolean result = registerFillAndErrorGet("lastname", "ThisIsALastname", "errorLastname");
        assertTrue(result);
    }

    @Test
    public void checkEmailError() {
        String content = registerErrorGet("errorEmail");
        assertEquals(errorEmail, content);

        Boolean result = registerFillAndErrorGet("email", "this@email.com", "errorEmail");
        assertTrue(result);
    }

    private Boolean registerFillAndErrorGet(String to, String what, String errorField) {
        WebElement element = driver.findElement(By.id(to));
        element.sendKeys("value", what);

        clickRegister();

        try {
            driver.findElement(By.id(errorField));
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

    private String registerErrorGet(String errorField) {
        getRegisterPage();
        clickRegister();

        WebElement element = driver.findElement(By.id(errorField));
        return element.getText();
    }


    private void getRegisterPage() {
        WebElement element = driver.findElement(By.id("profile"));
        element.click();
        element = driver.findElement(By.id("register"));
        element.click();
    }

    private void clickRegister()
    {
        WebElement element = driver.findElement(By.id("signup"));
        element.click();
    }
}
