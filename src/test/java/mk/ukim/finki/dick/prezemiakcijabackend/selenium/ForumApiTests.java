package mk.ukim.finki.dick.prezemiakcijabackend.selenium;

import mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ForumApiTests {

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
    public void getForumForInitiativeSuccessful() throws InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver);

        loginPage.open();

        loginPage.login("filip.spirovski@gmail.com", "testtest");

        long initiativeId = 1L;
        this.driver.findElement(By.cssSelector("#app-header > nb-card-body > nb-actions > nb-action:nth-child(3)")).click();
        Thread.sleep(3000);

        this.driver.findElement(By.id(Long.toString(initiativeId))).click();
        Thread.sleep(3000);

        this.driver.findElements(By.cssSelector(".tab-link")).get(2).click();
        Thread.sleep(3000);

        assertTrue(this.driver.findElement(By.id("app-comments")).isDisplayed());
    }

    @Test
    public void getCommentsForForumSuccessful() throws InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver);

        loginPage.open();

        loginPage.login("filip.spirovski@gmail.com", "testtest");

        long initiativeId = 1L;
        this.driver.findElement(By.cssSelector("#app-header > nb-card-body > nb-actions > nb-action:nth-child(3)")).click();
        Thread.sleep(3000);

        this.driver.findElement(By.id(Long.toString(initiativeId))).click();
        Thread.sleep(3000);

        this.driver.findElements(By.cssSelector(".tab-link")).get(2).click();
        Thread.sleep(3000);

        List<WebElement> comments = this.driver.findElements(By.cssSelector(".comment"));

        this.driver.findElement(By.id("commentForm")).sendKeys("New comment");
        this.driver.findElement(By.id("submit-comment-icon")).click();
        Thread.sleep(3000);

        List<WebElement> commentsAfterAddingNew = this.driver.findElements(By.cssSelector(".comment"));

        assertEquals(commentsAfterAddingNew.size(), comments.size() + 1);
    }

    @Test
    public void addNewCommentSuccessful() throws InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver);

        loginPage.open();

        loginPage.login("filip.spirovski@gmail.com", "testtest");

        long initiativeId = 1L;
        this.driver.findElement(By.cssSelector("#app-header > nb-card-body > nb-actions > nb-action:nth-child(3)")).click();
        Thread.sleep(3000);

        this.driver.findElement(By.id(Long.toString(initiativeId))).click();
        Thread.sleep(3000);

        this.driver.findElements(By.cssSelector(".tab-link")).get(2).click();
        Thread.sleep(3000);

        List<WebElement> comments = this.driver.findElements(By.cssSelector(".comment"));

        this.driver.findElement(By.id("commentForm")).sendKeys("New comment");
        this.driver.findElement(By.id("submit-comment-icon")).click();
        Thread.sleep(3000);

        List<WebElement> commentsAfterAddingNew = this.driver.findElements(By.cssSelector(".comment"));
        String successMessage = loginPage.getFeedback();

        assertEquals(commentsAfterAddingNew.size(), comments.size() + 1);
        assertEquals(successMessage, "Comment successfully added!");
    }

    @Test
    public void deleteExistingCommentSuccessful() throws InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver);

        loginPage.open();

        loginPage.login("filip.spirovski@gmail.com", "testtest");

        long initiativeId = 1L;
        this.driver.findElement(By.cssSelector("#app-header > nb-card-body > nb-actions > nb-action:nth-child(3)")).click();
        Thread.sleep(3000);

        this.driver.findElement(By.id(Long.toString(initiativeId))).click();
        Thread.sleep(3000);

        this.driver.findElements(By.cssSelector(".tab-link")).get(2).click();
        Thread.sleep(3000);

        this.driver.findElement(By.id("commentForm")).sendKeys("Delete this comment");
        this.driver.findElement(By.id("submit-comment-icon")).click();
        Thread.sleep(3000);

        List<WebElement> deleteCommentButtons = this.driver.findElements(By.cssSelector(".delete-comment-button"));
        deleteCommentButtons.get(deleteCommentButtons.size() - 1).click();
        Thread.sleep(2000);

        List<WebElement> deleteCommentButtonsAfterDeletingComment = this.driver.findElements(By.cssSelector(".delete-comment-button"));

        assertEquals(deleteCommentButtonsAfterDeletingComment.size(), deleteCommentButtons.size() - 1);
    }

    @AfterTest
    public void teardown() {
        this.driver.quit();
    }
}
