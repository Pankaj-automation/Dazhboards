package Login.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login_Page {
    WebDriver driver;
    By emailInput = By.xpath("//input[@placeholder='Email address']");
    By passwordInput = By.xpath("//input[@placeholder='Password']");
    By signInBtn = By.xpath("//button[normalize-space()='Sign in']");
    By errorMessage = By.xpath("//span[@class='text-white text-sm text-center']");

    public Login_Page(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickSignIn() {
        driver.findElement(signInBtn).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public WebElement getEmailFieldElement() {
        return driver.findElement(emailInput);
    }

    public WebElement getPasswordFieldElement() {
        return driver.findElement(passwordInput);
    }

    public void clearFields() {
        getEmailFieldElement().clear();
        getPasswordFieldElement().clear();
    }
}
