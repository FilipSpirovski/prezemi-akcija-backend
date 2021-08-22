package mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.Initiative;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class InitiativeCreatePage extends BasePage {

    public InitiativeCreatePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() throws InterruptedException {
        Thread.sleep(5000);

        return this.wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("app-initiative-create")))
                .isDisplayed();
    }

    public void createNewInitiative(Initiative initiative) throws InterruptedException {
        Select category = new Select(this.driver.findElement(By.id("categoryName")));
        category.selectByValue(initiative.getCategory().toString());

        this.driver.findElement(By.id("title")).clear();
        this.driver.findElement(By.id("title")).sendKeys(initiative.getTitle());

        this.driver.findElement(By.id("description")).clear();
        this.driver.findElement(By.id("description")).sendKeys(initiative.getDescription());

        this.driver.findElement(By.id("scheduledFor")).clear();
        this.driver.findElement(By.id("scheduledFor")).sendKeys("2020-01-15T18:00");

        Select eventType = new Select(this.driver.findElement(By.id("eventTypeName")));
        eventType.selectByValue(initiative.getEventType().toString());

        this.driver.findElement(By.id("location")).clear();
        this.driver.findElement(By.id("location")).sendKeys(initiative.getLocation());

        this.driver.findElement(By.id("save-initiative-button")).click();
        Thread.sleep(5000);
    }
}
