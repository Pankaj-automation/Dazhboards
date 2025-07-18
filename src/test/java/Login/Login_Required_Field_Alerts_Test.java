package Login;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.StartupCode;

import java.time.Duration;

public class Login_Required_Field_Alerts_Test extends StartupCode {

    @BeforeTest
    public void Start() throws InterruptedException {
        driver = setup1();
        test = extent.createTest("Login_Required_Field_Alerts_Test");
        test.info("*Started Login_Required_Field_Alerts_Test*");
    }

    @Test(priority = 1)
    public void Email_field_alert() throws InterruptedException {

        logger.info("Test started: Email_field_alert");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement companyInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Sign in']")));
        companyInput.click();
        logger.info("Clicked on Sign in button without entering email");
        test.info("Clicked on Sign in button without entering email");
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Email address']")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", emailInput);

        logger.info("Email field Validation Message: Expected Result: Please fill in this field.  Actual Result: " + validationMessage);
        test.pass("Email field Validation Message: Expected Result: Please fill in this field.  Actual Result: " + validationMessage);

        emailInput.sendKeys("thred");
        logger.info("Entered invalid email: thred");
        test.info("Entered invalid email: thred");

        String validationMessage1 = (String) js.executeScript("return arguments[0].validationMessage;", emailInput);
        logger.info("If user try to enter invalid email format: " + validationMessage1);
        test.pass("If user try to enter invalid email format: " + validationMessage1);
    }

    @Test(priority = 2)
    public void Password_fields_alert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Email address']")));
        emailField.clear();
        logger.info("Cleared email field");

        emailField.sendKeys("pnkj@yopmail.com");

        WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        passwordField.sendKeys("Admine51");
        logger.info("Entered email and incorrect password");
        test.info("Entered email and incorrect password");

        WebElement signInBtn = driver.findElement(By.xpath("//button[normalize-space()='Sign in']"));
        signInBtn.click();
        logger.info("Clicked on Sign in with wrong password");
        test.info("Clicked on Sign in with wrong password");

        WebElement invalidPasswordMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='text-white text-sm text-center']")));
        String actualText = invalidPasswordMsg.getText();
        String expectedText = "Invalid credentials.";
        Assert.assertEquals(actualText, expectedText, "Password mismatch validation failed!");
        logger.info("Assertion passed: Invalid credentials message displayed");
        test.pass("Assertion passed: Invalid credentials message displayed");

        logger.info("If password is not matched: " + actualText);
        test.pass("If password is not matched: " + actualText);
    }


    @AfterClass
    public void QuitBrowser() {
        logger.info("Loaded data in Extent report");
        test.info("Loaded data in Extent report");

        extent.flush();

        if (driver != null) {
            logger.info("Closing the browser");
            driver.quit();
        } else {
            logger.warn("Driver was null, browser not closed");
        }

        logger.info("Browser quit process completed");
        test.pass("Browser quit process completed");
    }
}