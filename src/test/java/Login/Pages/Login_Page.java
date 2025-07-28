package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login_Page {
    private WebDriver driver;
    private WebDriverWait wait;

    private By emailInput = By.xpath("//input[@placeholder='Email address']");
    private By passwordInput = By.xpath("//input[@placeholder='Password']");
    private By signInBtn = By.xpath("//button[normalize-space()='Sign in']");
    private By invalidPasswordMsg = By.xpath("//span[@class='text-white text-sm text-center']");

    public Login_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(emailInput)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(password);
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInBtn)).click();
    }

    public String getCurrentUrl() {
        wait.until(ExpectedConditions.urlContains("pos"));
        return driver.getCurrentUrl();
    }

    public String getEmailFieldValidationMessage() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].validationMessage;", element);
    }

    public String getInvalidPasswordText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(invalidPasswordMsg)).getText();
    }

    public void clearEmailField() {
        driver.findElement(emailInput).clear();
    }
}
