package mk.ukim.finki.dick.prezemiakcijabackend.selenium;

import mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages.HomePage;
import mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages.LoginPage;
import mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages.RegisterPage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserApiTests {

    private WebDriver driver;

    private WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\drivers\\chromedriver.exe");

        return new ChromeDriver();
    }

    @BeforeTest
    public void setup() {
        this.driver = this.getDriver();
    }

    @Test
    public void loginSuccessful() throws InterruptedException {
        this.driver = this.getDriver();
        LoginPage loginPage = new LoginPage(this.driver);

        loginPage.open();
        assertTrue(loginPage.isLoaded());

        loginPage.login("random-user@gmail.com", "testtest");
        String successMessage = loginPage.getFeedback();

        assertEquals(successMessage, "Successful sign-in!");
        this.driver.quit();
    }

    @Test
    public void loginNotSuccessfulUserNotFound() throws InterruptedException {
        this.driver = this.getDriver();
        LoginPage loginPage = new LoginPage(this.driver);

        loginPage.open();
        assertTrue(loginPage.isLoaded());

        loginPage.login("invaliduser@gmail.com", "Test123!");
        String errorMessage = loginPage.getFeedback();

        assertEquals(errorMessage, "The user with the provided email (invaliduser@gmail.com) was not found.");
        this.driver.quit();
    }

    @Test
    public void loginNotSuccessfulInvalidCredentials() throws InterruptedException {
        this.driver = this.getDriver();
        LoginPage loginPage = new LoginPage(this.driver);

        loginPage.open();
        assertTrue(loginPage.isLoaded());

        loginPage.login("random-user@gmail.com", "Test123!3");
        String errorMessage = loginPage.getFeedback();

        assertEquals(errorMessage, "The provided 'Email' and 'Password' fields are invalid.");
        this.driver.quit();
    }

    @Test
    public void logoutSuccessful() throws InterruptedException {
        this.driver = this.getDriver();
        LoginPage loginPage = new LoginPage(this.driver);

        loginPage.open();
        assertTrue(loginPage.isLoaded());

        loginPage.login("random-user@gmail.com", "testtest");
        String successMessage = loginPage.getFeedback();

        assertEquals(successMessage, "Successful sign-in!");

        this.driver.findElement(By.cssSelector("#app-header > nb-card-body > nb-actions > nb-action:nth-child(4)")).click();
        successMessage = loginPage.getFeedback();

        assertEquals(successMessage, "Successful sign-out!");
        this.driver.quit();
    }

    @Test
    public void registerSuccessful() throws InterruptedException {
        this.driver = this.getDriver();
        RegisterPage registerPage = new RegisterPage(this.driver);

        registerPage.open();
        assertTrue(registerPage.isLoaded());

        registerPage.register("user@gmail.com", "Test123!", "Test123!", "Filip", "Spirovski");
        String successMessage = registerPage.getFeedback();

        assertTrue(new HomePage(this.driver).isLoaded());
        assertEquals(successMessage, "Successful registration!");
        this.driver.quit();
    }

    @Test
    public void registerNotSuccessfulUserAlreadyExists() throws InterruptedException {
        this.driver = this.getDriver();
        RegisterPage registerPage = new RegisterPage(this.driver);

        registerPage.open();
        assertTrue(registerPage.isLoaded());

        registerPage.register("user@gmail.com", "Test123!", "Test123!", "Filip", "Spirovski");

        registerPage.open();
        assertTrue(registerPage.isLoaded());

        registerPage.register("user@gmail.com", "Test123!", "Test123!", "Filip", "Spirovski");
        String errorMessage = registerPage.getFeedback();

        assertEquals(errorMessage, "A user with the provided email (user@gmail.com) already exists.");
        this.driver.quit();
    }

    @AfterTest
    public void teardown() {
        this.driver.quit();
    }
}
