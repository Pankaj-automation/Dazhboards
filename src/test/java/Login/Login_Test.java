package Login;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.StartupCode;

import java.io.IOException;
import java.time.Duration;

public class Login_Test extends StartupCode {

    @BeforeTest
    public void Start() throws InterruptedException {
        driver = setup1();
    }

    @Test(priority = 1)
    public void Email_field_alert() throws InterruptedException {
        test = extent.createTest("Login_Test");
        test.info("*Started Login_Test*");
        logger.info("Test started: Email_field_alert");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement companyInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Sign in']")));
        companyInput.click();
        logger.info("Clicked on Sign in button without entering email");
        test.info("Clicked on Sign in button without entering email");

        Thread.sleep(1000);
        WebElement Emailvalidation = driver.findElement(By.xpath("//input[@placeholder='Email address']"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", Emailvalidation);
        Thread.sleep(1000);
        logger.info("Email field Validation Message: Expected Result: Please fill in this field.  Actual Result: " + validationMessage);
        test.pass("Email field Validation Message: Expected Result: Please fill in this field.  Actual Result: " + validationMessage);

        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='Email address']")).sendKeys("thred");
        logger.info("Entered invalid email: thred");
        test.info("Entered invalid email: thred");

        WebElement roleSelect1 = driver.findElement(By.xpath("//input[@placeholder='Email address']"));
        Thread.sleep(1000);
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        String validationMessage1 = (String) js1.executeScript("return arguments[0].validationMessage;", roleSelect1);
        Thread.sleep(1000);

        logger.info("If user try to enter invalid email format: " + validationMessage1);
        test.pass("If user try to enter invalid email format: " + validationMessage1);
        String message = "If user try to enter invalid email format: " + validationMessage1;
    }

    @Test(priority = 2)
    public void Password_Page_fields_alert() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='Email address']")).clear();
        logger.info("Cleared email field");

        driver.findElement(By.xpath("//input[@placeholder='Email address']")).sendKeys("pnkj@yopmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Admine51");
        logger.info("Entered email and incorrect password");
        test.info("Entered email and incorrect password");

        driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();
        logger.info("Clicked on Sign in with wrong password");
        test.info("Clicked on Sign in with wrong password");

        Thread.sleep(1000);
        WebElement invalidpassword = driver.findElement(By.xpath("//span[@class='text-white text-sm text-center']"));
        Thread.sleep(1000);
        String actualText = invalidpassword.getText();
        String expectedText = "Invalid credentials.";
        Assert.assertEquals(actualText, expectedText, "❌ Password mismatch validation failed!");
        logger.info("Assertion passed: Invalid credentials message displayed");
        test.pass("Assertion passed: Invalid credentials message displayed");

        Thread.sleep(1000);
        logger.info("If password is not matched: " + invalidpassword.getText());
        test.pass("If password is not matched: " + invalidpassword.getText());
    }

    @Test(priority = 3)
    public void login() throws IOException, InterruptedException {
        logger.info("Starting login process...");
        System.out.println("Starting login process...");
        test.info("Starting login process...");

        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='Email address']")).clear();
        logger.info("Cleared email field");
        test.info("Cleared email field");

        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='Email address']")).sendKeys("pnkj@yopmail.com");
        logger.info("Entered valid email");
        test.info("Entered valid email");

        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='Password']")).clear();
        logger.info("Cleared password field");
        test.info("Cleared password field");

        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Admine51%%!");
        logger.info("Entered valid password");
        test.info("Entered valid password");

        driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();
        logger.info("Credentials entered and login button clicked.");
        System.out.println("Credentials entered and login button clicked.");
        test.info("Clicked on Sign in");

        Thread.sleep(5000);
        String currentURL = driver.getCurrentUrl();
        logger.info("Current URL after login: " + currentURL);
        System.out.println("Current URL after login: " + currentURL);
        test.info("Current URL after login: " + currentURL);

        Thread.sleep(1000);
        String expectedTextInURL = "pos"; // adjust this to match your actual post-login URL
        Assert.assertTrue(currentURL.contains(expectedTextInURL), "URL does not contain expected text after login");
        logger.info("URL validation passed.");
        test.pass("URL validation passed.");
        System.out.println("URL validation passed.");
    }

    @AfterTest
    public void QuitBrowser() throws IOException, InterruptedException {
        Thread.sleep(1000);
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