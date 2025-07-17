package Login;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.StartupCode;

import java.io.IOException;

public class Login_Test extends StartupCode {

    @BeforeTest
    public void Start() throws InterruptedException {
        driver = setup1();
    }

    @Test(priority = 1)
    public void login() throws IOException, InterruptedException {
        test = extent.createTest("Login_Test");
        test.info("*Started Login_Test*");
        logger.info("Starting login process...");
        System.out.println("Starting login process...");

        driver.findElement(By.xpath("//input[@placeholder='Email address']")).sendKeys("pnkj@yopmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Admine51%%!");
        driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();
        logger.info("Credentials entered and login button clicked.");
        test.pass("Credentials entered and login button clicked.");
        System.out.println("Credentials entered and login button clicked.");

        Thread.sleep(3000);

        String currentURL = driver.getCurrentUrl();
        logger.info("Current URL after login: " + currentURL);
        System.out.println("Current URL after login: " + currentURL);
        test.info("Current URL after login: " + currentURL);

        // ✅ Assertion
        String expectedTextInURL = "pos"; // adjust this to match your actual post-login URL
        Assert.assertTrue(currentURL.contains(expectedTextInURL), "URL does not contain expected text after login");
        logger.info("URL validation passed.");
        test.pass("URL validation passed.");
        System.out.println("URL validation passed.");
    }

    @AfterTest
    public void QuitBrowser() throws IOException, InterruptedException {
        Thread.sleep(3000);
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