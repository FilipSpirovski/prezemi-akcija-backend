package mk.ukim.finki.dick.prezemiakcijabackend.selenium;

import mk.ukim.finki.dick.prezemiakcijabackend.H2JpaConfig;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.Initiative;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Category;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.EventType;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.InitiativeNotFound;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.InitiativeRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages.*;
import mk.ukim.finki.dick.prezemiakcijabackend.service.InitiativeService;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2JpaConfig.class}, loader = AnnotationConfigContextLoader.class)
public class InitiativeApiTests {

    private WebDriver driver;

    @Resource
    private InitiativeRepository initiativeRepository;

    private NavigationPage navigationPage;

    private HomePage homePage;

    private InitiativesPage initiativesPage;

    private InitiativeDetailsPage initiativeDetailsPage;

    private InitiativeEditPage initiativeEditPage;

    private InitiativeCreatePage initiativeCreatePage;

    private WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\drivers\\chromedriver.exe");

        return new ChromeDriver();
    }

    @BeforeTest
    private void setup() throws InterruptedException {
        //this.initData();

        this.driver = this.getDriver();
        this.navigationPage = new NavigationPage(this.driver);
        this.homePage = new HomePage(this.driver);
        this.initiativesPage = new InitiativesPage(this.driver);
        this.initiativeDetailsPage = new InitiativeDetailsPage(this.driver);
        this.initiativeEditPage = new InitiativeEditPage(this.driver);
        this.initiativeCreatePage = new InitiativeCreatePage(this.driver);

        LoginPage loginPage = new LoginPage(this.driver);

        loginPage.open();
        assertTrue(loginPage.isLoaded());

        loginPage.login("filip.spirovski@gmail.com", "testtest");
        assertEquals("Successful sign-in!", loginPage.getFeedback());
        assertTrue(this.homePage.isLoaded());
    }

    private void initData() {
        String initiatorEmail = "filip.spirovski@gmail.com";
        Category category = Category.HEALTH;
        String title = "Processed foods are killing you one bite at a time!";
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia arcu est, " +
                "eget dictum mi sagittis sed. Praesent metus sem, commodo vitae vestibulum id, dapibus non erat. " +
                "Etiam ultricies ante in nibh consequat, quis faucibus lorem lobortis. Etiam ornare rhoncus mauris " +
                "ullamcorper commodo. Aenean volutpat eu nisl id dapibus. Suspendisse sit amet tellus porttitor, " +
                "commodo augue sed, pharetra libero. Quisque tristique elementum justo a faucibus. Morbi laoreet, " +
                "ligula sed aliquam cursus, sem nisl venenatis ligula, in posuere velit nisl a metus. Donec laoreet " +
                "ullamcorper pulvinar. Vivamus facilisis nunc ac auctor bibendum. Curabitur dignissim, diam et " +
                "facilisis condimentum, nibh massa ullamcorper nisi, in mattis tellus ex id diam. Aenean diam orci, " +
                "condimentum vitae pharetra non, vehicula vel mi. Sed porta lacus a sapien malesuada, sit amet auctor" +
                " nulla gravida. Sed tincidunt urna ac velit rhoncus, iaculis pharetra velit imperdiet.";
        LocalDateTime scheduledFor = LocalDateTime.now();
        EventType eventType = EventType.IN_GROUP;
        String location = "Kantina";
        List<String> participants = new ArrayList<>();

        Initiative initiative1 = new Initiative(1L, initiatorEmail, category, title,
                description, scheduledFor, eventType, location, participants);
        Initiative initiative2 = new Initiative(2L, initiatorEmail, category, title,
                description, scheduledFor, eventType, location, participants);

        this.initiativeRepository.save(initiative1);
        this.initiativeRepository.save(initiative2);
    }

    @Test
    public void getAllInitiativesTest() throws InterruptedException {
        this.navigationPage.browseInitiatives();
        assertTrue(this.initiativesPage.isLoaded());
    }

    @Test
    public void getInitiativeDetailsTest() throws InterruptedException {
        this.navigationPage.browseInitiatives();
        assertTrue(this.initiativesPage.isLoaded());

        Long id = 1L;
//        Initiative initiative = this.initiativeRepository.findById(id)
//                .orElseThrow(() -> new InitiativeNotFound(id));

        this.initiativesPage.getSingleInitiative(id);
        assertTrue(this.initiativeDetailsPage.isLoaded());

        String title = "Processed foods are killing you one bite at a time!";
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia arcu est, " +
                "eget dictum mi sagittis sed. Praesent metus sem, commodo vitae vestibulum id, dapibus non erat. " +
                "Etiam ultricies ante in nibh consequat, quis faucibus lorem lobortis. Etiam ornare rhoncus mauris " +
                "ullamcorper commodo. Aenean volutpat eu nisl id dapibus. Suspendisse sit amet tellus porttitor, " +
                "commodo augue sed, pharetra libero. Quisque tristique elementum justo a faucibus. Morbi laoreet, " +
                "ligula sed aliquam cursus, sem nisl venenatis ligula, in posuere velit nisl a metus. Donec laoreet " +
                "ullamcorper pulvinar. Vivamus facilisis nunc ac auctor bibendum. Curabitur dignissim, diam et " +
                "facilisis condimentum, nibh massa ullamcorper nisi, in mattis tellus ex id diam. Aenean diam orci, " +
                "condimentum vitae pharetra non, vehicula vel mi. Sed porta lacus a sapien malesuada, sit amet auctor" +
                " nulla gravida. Sed tincidunt urna ac velit rhoncus, iaculis pharetra velit imperdiet.";
        String initiatorEmail = "filip.spirovski@gmail.com";

        assertEquals(this.initiativeDetailsPage.getTitle(), String.format("Title: \" %s \"", title));
        assertEquals(this.initiativeDetailsPage.getDescription(), String.format("Description: \" %s \"", description));
        assertEquals(this.initiativeDetailsPage.getInitiatorEmail(), String.format("Published by %s", initiatorEmail));
    }

    @Test
    public void addNewInitiativeTest() throws InterruptedException {
        this.navigationPage.startNewInitiative();
        assertTrue(this.initiativeCreatePage.isLoaded());

        Long id = 2L;
        Initiative initiative = this.initiativeRepository.findById(id)
                .orElseThrow(() -> new InitiativeNotFound(id));
        this.initiativeRepository.delete(initiative);

        this.initiativeCreatePage.createNewInitiative(initiative);
        assertTrue(this.initiativesPage.isLoaded());

        this.initiativesPage.getSingleInitiative(id + 1);
        assertTrue(this.initiativeDetailsPage.isLoaded());

        assertEquals(this.initiativeDetailsPage.getTitle(), String.format("Title: \" %s \"", initiative.getTitle()));
        assertEquals(this.initiativeDetailsPage.getDescription(), String.format("Description: \" %s \"", initiative.getDescription()));
        assertEquals(this.initiativeDetailsPage.getInitiatorEmail(), String.format("Published by %s", initiative.getInitiatorEmail()));
    }

    @Test
    public void updateExistingInitiativeTest() throws InterruptedException {
        this.navigationPage.browseInitiatives();
        assertTrue(this.initiativesPage.isLoaded());

        Long id = 1L;
//        Initiative initiative = this.initiativeRepository.findById(id)
//                .orElseThrow(() -> new InitiativeNotFound(id));

        this.initiativesPage.getSingleInitiative(id);
        assertTrue(this.initiativeDetailsPage.isLoaded());

        this.initiativeDetailsPage.editInitiative();
        assertTrue(this.initiativeEditPage.isLoaded());

        String newTitle = "This is a new title!";
        this.initiativeEditPage.updateInitiativeTitle(newTitle);
        assertTrue(this.initiativesPage.isLoaded());
        assertEquals("Initiative successfully updated!", this.initiativesPage.getFeedback());

        this.initiativesPage.getSingleInitiative(id);
        assertTrue(this.initiativeDetailsPage.isLoaded());

        assertEquals(this.initiativeDetailsPage.getTitle(), String.format("Title: \" %s \"", newTitle));
    }

    @Test
    public void addAndRemoveParticipantToExistingInitiativeTest() throws InterruptedException {
        this.navigationPage.browseInitiatives();
        assertTrue(this.initiativesPage.isLoaded());

        Long id = 1L;
//        Initiative initiative = this.initiativeRepository.findById(id)
//                .orElseThrow(() -> new InitiativeNotFound(id));

        this.initiativesPage.getSingleInitiative(id);
        assertTrue(this.initiativeDetailsPage.isLoaded());

        this.initiativeDetailsPage.openParticipantsList();
        int count = this.initiativeDetailsPage.getNumberOfParticipants();

        this.initiativeDetailsPage.changeUserParticipation();
        assertEquals("Action successfully performed!", this.initiativeDetailsPage.getFeedback());
        assertEquals(count + 1, this.initiativeDetailsPage.getNumberOfParticipants());

        this.initiativeDetailsPage.changeUserParticipation();
        assertEquals("Action successfully performed!", this.initiativeDetailsPage.getFeedback());
        assertEquals(count, this.initiativeDetailsPage.getNumberOfParticipants());
    }

    @Test
    public void deleteExistingInitiativeTest() throws InterruptedException {
        this.navigationPage.browseInitiatives();
        assertTrue(this.initiativesPage.isLoaded());

        Long id = 1L;
//        Initiative initiative = this.initiativeRepository.findById(id)
//                .orElseThrow(() -> new InitiativeNotFound(id));

        this.initiativesPage.getSingleInitiative(id);
        assertTrue(this.initiativeDetailsPage.isLoaded());

        this.initiativeDetailsPage.deleteInitiative();
        assertEquals("Initiative successfully deleted!", this.initiativeDetailsPage.getFeedback());
        assertTrue(this.initiativesPage.isLoaded());
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}
