package mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        this.driver.get("http://localhost:4200/login");
    }

    public boolean isLoaded() throws InterruptedException {
        Thread.sleep(5000);

        return this.wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("app-login")))
                .isDisplayed();
    }

    public void login(String user, String password) throws InterruptedException {
        this.driver.findElement(By.id("email")).clear();
        this.driver.findElement(By.id("email")).sendKeys(user);
        Thread.sleep(5000);

        this.driver.findElement(By.id("password")).clear();
        this.driver.findElement(By.id("password")).sendKeys(password);
        Thread.sleep(5000);

        this.driver.findElement(By.cssSelector("#login-button")).click();
        Thread.sleep(5000);
    }
}
