package mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InitiativeEditPage extends BasePage {

    public InitiativeEditPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() throws InterruptedException {
        Thread.sleep(5000);

        return this.wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("app-initiative-edit")))
                .isDisplayed();
    }

    public void updateInitiativeTitle(String title) throws InterruptedException {
        this.driver.findElement(By.id("title")).clear();
        this.driver.findElement(By.id("title")).sendKeys(title);
        this.driver.findElement(By.id("save-changes-button")).click();
        Thread.sleep(5000);
    }
}
