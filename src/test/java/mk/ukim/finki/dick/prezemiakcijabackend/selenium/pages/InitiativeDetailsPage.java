package mk.ukim.finki.dick.prezemiakcijabackend.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InitiativeDetailsPage extends BasePage {

    public InitiativeDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() throws InterruptedException {
        Thread.sleep(5000);

        return this.wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("app-initiative-detail")))
                .isDisplayed();
    }

    public String getTitle() {
        return this.driver.findElement(By.cssSelector("#app-initiative-detail > nb-card-body > nb-tabset > nb-tab.pt-5.content-active > p:nth-child(2)")).getText();
    }

    public String getDescription() {
        return this.driver.findElement(By.cssSelector("#app-initiative-detail > nb-card-body > nb-tabset > nb-tab.pt-5.content-active > p:nth-child(3)")).getText();
    }

    public String getInitiatorEmail() {
        return this.driver.findElement(By.cssSelector("#app-initiative-detail > nb-card-footer > p")).getText();
    }

    public void openParticipantsList() {
        this.driver.findElement(By.cssSelector("#app-initiative-detail > nb-card-body > nb-tabset > ul > li:nth-child(2) > a")).click();
    }

    public int getNumberOfParticipants() {
        return this.driver.findElements(By.cssSelector("#initiative-participants > nb-list > nb-list-item")).size();
    }

    public void changeUserParticipation() throws InterruptedException {
        this.driver.findElement(By.id("change-participation-button")).click();
        Thread.sleep(5000);
    }

    public void editInitiative() {
        this.driver.findElement(By.id("edit-initiative-button")).click();
    }

    public void deleteInitiative() throws InterruptedException {
        this.driver.findElement(By.id("delete-initiative-button")).click();
        Thread.sleep(5000);
    }
}
