package Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.StartupCode;

import java.io.IOException;
import java.time.Duration;

public class Login extends StartupCode {

    @BeforeTest
    public void Start() throws InterruptedException {
        driver = setup1();
        test = extent.createTest("Login_Test");
        test.info("*Started Login_Test*");
    }


    @Test(priority = 1)
    public void login() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.info("Starting login process...");
        System.out.println("Starting login process...");
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
        System.out.println("Credentials entered and login button clicked.");
        test.info("Clicked on Sign in");

        wait.until(ExpectedConditions.urlContains("pos"));
        String currentURL = driver.getCurrentUrl();
        logger.info("Current URL after login: " + currentURL);
        System.out.println("Current URL after login: " + currentURL);
        test.info("Current URL after login: " + currentURL);

        Assert.assertTrue(currentURL.contains("pos"), "URL does not contain expected text after login");
        logger.info("URL validation passed.");
        test.pass("URL validation passed.");
        System.out.println("URL validation passed.");
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
