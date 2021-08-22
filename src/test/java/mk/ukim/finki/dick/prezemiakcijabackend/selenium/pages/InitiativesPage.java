package mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InitiativesPage extends BasePage {

    public InitiativesPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() throws InterruptedException {
        Thread.sleep(5000);

        return this.wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("app-initiatives")))
                .isDisplayed();
    }

    public void getSingleInitiative(Long id) {
        this.driver.findElement(By.id(id.toString())).click();
    }
}
