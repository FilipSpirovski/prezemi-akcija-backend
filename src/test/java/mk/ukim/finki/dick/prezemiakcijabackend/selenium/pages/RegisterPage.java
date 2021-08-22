package mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        this.driver.get("http://localhost:4200/register");
        PageFactory.initElements(this.driver, RegisterPage.class);
    }

    public boolean isLoaded() throws InterruptedException {
        Thread.sleep(5000);
        return this.wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("app-register")))
                .isDisplayed();
    }

    public void register(String email, String password, String confirmPassword, String name, String surname) throws InterruptedException {
        this.driver.findElement(By.id("email")).clear();
        this.driver.findElement(By.id("email")).sendKeys(email);

        this.driver.findElement(By.id("password")).clear();
        this.driver.findElement(By.id("password")).sendKeys(password);

        this.driver.findElement(By.id("name")).clear();
        this.driver.findElement(By.id("name")).sendKeys(name);

        this.driver.findElement(By.id("surname")).clear();
        this.driver.findElement(By.id("surname")).sendKeys(surname);

        this.driver.findElement(By.id("register-button")).click();
        Thread.sleep(5000);
    }
}
