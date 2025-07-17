package ForgotPassword;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.StartupCode;

public class FP_Required_Field_Alerts_Test extends StartupCode {

    @BeforeTest
    public void Start() throws InterruptedException {
        driver = setup1();
        test = extent.createTest("Forgot_Password_Required_Field_Alerts_Test");
        test.info("*Started Forgot_Password_Required_Field_Alerts_Test*");
    }

    @Test(priority = 1)
    public void Forgot_Password_Email_field_alerts() throws InterruptedException {
        driver.findElement(By.xpath("//a[normalize-space()='Forgot Password?']")).click();
        driver.findElement(By.xpath("//button[normalize-space()='Send Recovery Email']")).click();

        WebElement roleSelect = driver.findElement(By.xpath("//input[@placeholder='Email*']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", roleSelect);

        logger.info("Email field Validation Message: Expected Result: Please fill in this field.  " + "Actual Result: " + validationMessage);
        test.pass("Email field Validation Message: Expected Result: Please fill in this field.  " + "Actual Result: " + validationMessage);

        SoftAssert softAssert = new SoftAssert();
        Assert.assertEquals(validationMessage, "Please fill in this field.");
        softAssert.assertAll();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='Email*']")).sendKeys("loop");
        WebElement roleSelect1 = driver.findElement(By.xpath("//input[@placeholder='Email*']"));

        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        String validationMessage1 = (String) js1.executeScript("return arguments[0].validationMessage;", roleSelect1);
        Thread.sleep(1000);

        logger.info("If user try to enter invalid email format: " + validationMessage1);
        test.pass("If user try to enter invalid email format: " + validationMessage1);
        String message = "If user try to enter invalid email format: " + validationMessage1;
        logger.info(message);
        test.pass(message);

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

