package ForgotPassword.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPassword_Page {
    WebDriver driver;
    WebDriverWait wait;
    // Locators
    private By forgotPasswordLink = By.xpath("//a[normalize-space()='Forgot Password?']");
    private By emailInput = By.xpath("//input[@placeholder='Email*']");
    private By sendRecoveryEmailBtn = By.xpath("//button[normalize-space()='Send Recovery Email']");
    private By popupMessage = By.xpath("//div[@class='swal-text']");
    private By popupOkBtn = By.xpath("//button[normalize-space()='OK']");
    private By newPassword = By.cssSelector("input[placeholder='Create new password']");
    private By confirmPassword = By.cssSelector("input[placeholder='Confirm new password']");
    private By submitBtn = By.xpath("//button[@type='submit']");
    private By minLengthAlert = By.xpath("//div[contains(text(),'Min Length must be 8 characters long')]");
    private By confirmMismatchAlert = By.xpath("//div[contains(text(),'Confirm Password is not matched with New Password')]");
    public ForgotPassword_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Forgot Password flow actions
    public void openForgotPasswordPage() {
        driver.findElement(forgotPasswordLink).click();
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void clickSendRecoveryEmailButton() {
        driver.findElement(sendRecoveryEmailBtn).click();
    }

    public String getPopupMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(popupMessage)).getText();
    }

    public void clickOkOnPopup() {
        WebElement ok = wait.until(ExpectedConditions.elementToBeClickable(popupOkBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ok);
    }

    // Password Reset
    public void enterNewPassword(String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(newPassword)).sendKeys(pass);
    }

    public void enterConfirmPassword(String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPassword)).sendKeys(pass);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
    }

    // Alerts
    public String getMinLengthAlert() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(minLengthAlert)).getText();
    }

    public String getConfirmMismatchAlert() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmMismatchAlert)).getText();
    }

    // Validation Messages (for required and format)
    public String getEmailFieldValidationMessage() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].validationMessage;", input);
    }

    public String getEmailFormatValidationMessage() {
        WebElement input = driver.findElement(emailInput);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].validationMessage;", input);
    }
}
