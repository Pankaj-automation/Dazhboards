package SignUp.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUpPage {

    WebDriver driver;
    WebDriverWait wait;

    private By emailField = By.xpath("//input[@placeholder='Email Address']");
    private By firstNameField = By.xpath("//input[@placeholder='First Name']");
    private By lastNameField = By.xpath("//input[@placeholder='Last Name']");
    private By passwordField = By.xpath("//input[@placeholder='Password']");
    private By checkbox = By.name("check");
    private By termsLink = By.linkText("Terms of Use");
    private By privacyLink = By.linkText("Privacy Policy");
    private By signUpButton = By.xpath("//button[normalize-space()='Sign Up']");
    private By otpInput = By.xpath("//input[@placeholder='Please enter verification code']");
    private By otpNextBtn = By.xpath("//button[normalize-space()='Next']");

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillSignUpForm(String email, String firstName, String lastName, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(passwordField).sendKeys(password);
    }

    public void acceptTermsAndVerifyLinks() {
        WebElement checkboxElem = wait.until(ExpectedConditions.elementToBeClickable(checkbox));
        checkboxElem.click();
        if (!checkboxElem.isSelected()) {
            throw new AssertionError("Checkbox is not selected after clicking!");
        }

        String mainWindow = driver.getWindowHandle();

        // Terms of Use
        WebElement termsElem = wait.until(ExpectedConditions.elementToBeClickable(termsLink));
        termsElem.click();
        switchToNewTab(mainWindow);
        if (!driver.getCurrentUrl().toLowerCase().contains("terms")) {
            throw new AssertionError("Terms of Use page did not open!");
        }
        driver.close();
        driver.switchTo().window(mainWindow);

        // Privacy Policy
        WebElement privacyElem = wait.until(ExpectedConditions.presenceOfElementLocated(privacyLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", privacyElem);

        switchToNewTab(mainWindow);
        if (!driver.getCurrentUrl().toLowerCase().contains("privacy")) {
            throw new AssertionError("Privacy Policy page did not open!");
        }
        driver.close();
        driver.switchTo().window(mainWindow);
    }

    public void clickSignUpButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpButton)).click();
    }

    public void enterOTP(String otp) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpInput)).sendKeys(otp);
        driver.findElement(otpNextBtn).click();
    }

    private void switchToNewTab(String originalWindow) {
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }
}
