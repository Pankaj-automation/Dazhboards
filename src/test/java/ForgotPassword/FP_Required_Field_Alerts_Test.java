package ForgotPassword;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utilities.AllureEnvWriter;
import utilities.Screenshot;
import utilities.StartupCode;

import java.io.FileInputStream;
import java.io.IOException;

@Epic("Forgot Password Feature")
@Feature("Required Field Validation")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class FP_Required_Field_Alerts_Test extends StartupCode {

    @BeforeClass
    public void Start() throws InterruptedException {
        AllureEnvWriter.createEnvFile();

        driver = setup1();
        test = extent.createTest("Forgot_Password_Required_Field_Alerts_Test");
        test.info("*Started Forgot_Password_Required_Field_Alerts_Test*");
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Verify alerts on Forgot Password form")
    @Description("Validates email field required and format alerts on the Forgot Password page.")
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

    }

    @AfterMethod
    public void Takescreenshot(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = Screenshot.takeScreenshot(driver, result.getName());
            test.fail("Test Failed: " + result.getThrowable()).addScreenCaptureFromPath(screenshotPath);
            Allure.addAttachment("Screenshot on Failure", "image/png", new FileInputStream(screenshotPath), ".png");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        }
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

