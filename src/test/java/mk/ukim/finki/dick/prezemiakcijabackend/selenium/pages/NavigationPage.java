package mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigationPage extends BasePage {

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() throws InterruptedException {
        Thread.sleep(5000);

        return this.wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("app-header")))
                .isDisplayed();
    }

    public void browseInitiatives() {
        WebElement browseInitiatives = this.driver.findElement(By.cssSelector("nb-action[title='Browse initiatives']"));
        browseInitiatives.click();
    }

    public void startNewInitiative() {
        WebElement newInitiative = this.driver.findElement(By.cssSelector("nb-action[title='Start an initiative']"));
        newInitiative.click();
    }
}
