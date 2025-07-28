/*
package Login;

import com.aventstack.extentreports.MediaEntityBuilder;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.AllureEnvWriter;
import utilities.Screenshot;
import utilities.StartupCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

@Epic("Login Module")
@Feature("Login Functionality")
public class Login_Test extends StartupCode {

    @BeforeClass
    @Step("Setup and Launch Browser")
    public void Start() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        test.info("*Started Log2222in_Test*");
    }

    @Test(priority = 1, description = "wwwLogin with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can successfully log in with valid credentials")
    @Story("Positive login test")
    public void login() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.info("wwwStarting login process...");
        test.info("Starting login process...");

        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Email address']")));
        emailInput.sendKeys("pnkj@yopmail.com");
        logger.info("Entered valid email");
        test.info("Entered valid email");

        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Password']")));
        passwordInput.sendKeys("Admine51%%!");
        logger.info("Entered valid password");
        test.info("Entered valid password");

        WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Sign in']")));
        signInBtn.click();
        logger.info("Credentials entered and login button clicked.");
        test.info("Clicked on Sign in");

        wait.until(ExpectedConditions.urlContains("pos"));
        String currentURL = driver.getCurrentUrl();
        logger.info("Current URL after login: " + currentURL);
        test.info("Current URL after login: " + currentURL);

        Assert.assertTrue(currentURL.contains("pos"), "URL does not contain expected text after login");
        logger.info("URL validation passed.");
        test.pass("URL validation passed.");
    }

    @AfterMethod
    public void tearDown1(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = Screenshot.takeScreenshot(driver, result.getName());
            String relativePath = ".." + File.separator + "screenshots" + File.separator + new File(screenshotPath).getName();
            test.fail("Test Failed: " + result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());
            Allure.addAttachment("Screenshot on Failure", "image/png", new FileInputStream(screenshotPath), ".png");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        }
    }


    @AfterClass
    public void QuitBrowser() {

        quitDriver();
        finalizeReport();
    }
}*/
