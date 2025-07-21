package ForgotPassword;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.StartupCode;

import java.time.Duration;
import java.util.ArrayList;

public class Forgot_Password extends StartupCode {
    @BeforeClass
    public void Start() throws InterruptedException {
        driver = setup1();
        test = extent.createTest("Forgot_Password");
        test.info("*Started Forgot_Password*");
    }

    @Test(priority = 1)
    public void Forgot_Password_Flow() throws InterruptedException {
        logger.info("Started Forgot Password Flow");
        test.info("Started Forgot Password Flow");
        System.out.println("Started Forgot Password Flow");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[normalize-space()='Forgot Password?']")).click();
        logger.info("Clicked on 'Forgot Password?'");
        test.info("Clicked on 'Forgot Password?'");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Email*']")));
        emailInput.sendKeys("pnkj@yopmail.com");
        logger.info("Entered email for password recovery");
        test.info("Entered email for password recovery");

        WebElement sendButton = driver.findElement(By.xpath("//button[normalize-space()='Send Recovery Email']"));
        sendButton.click();
        logger.info("Clicked on 'Send Recovery Email'");
        test.info("Clicked on 'Send Recovery Email'");

        WebElement popupText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='swal-text']")));
        String message = popupText.getText();
        System.out.println("Popup Message: " + message);
        logger.info("Popup Message: " + message);
        test.pass("Popup appeared with message: " + message);

        WebElement okButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okButton);


        logger.info("Clicked OK on popup");
        test.info("Clicked OK on popup");
    }

    @Test(priority = 2)
    public void Clicked_reset_button() {
        logger.info("Started: Clicking Reset Password button in Yopmail");
        test.info("Started: Clicking Reset Password button in Yopmail");
        System.out.println("Navigating to Yopmail inbox...");

        ((JavascriptExecutor) driver).executeScript("window.open('https://yopmail.com/en/', '_blank');");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement loginInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='login']")));
        loginInput.sendKeys("pnkj");
        logger.info("Entered Yopmail inbox name");
        test.info("Entered Yopmail inbox name");

        WebElement checkInbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='material-icons-outlined f36']")));
        checkInbox.click();
        logger.info("Clicked on inbox refresh icon");
        test.info("Clicked on inbox refresh icon");

        driver.switchTo().frame("ifmail");

        WebElement resetLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@align='left']//a[1]")));
        resetLink.click();
        logger.info("Clicked on Reset Password link in email");
        test.pass("Clicked on Reset Password link in email");
        System.out.println("Reset Password link clicked.");
    }

    @Test(priority = 3)
    public void Change_Password() {
        logger.info("Started Change Password process");
        test.info("Started Change Password process");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement newPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Create new password']")));
        newPassword.sendKeys("Admine");
        logger.info("Entered new password");
        test.info("Entered new password");

        WebElement confirmPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Confirm new password']")));
        confirmPassword.sendKeys("Admwine");
        logger.info("Entered confirm password");
        test.info("Entered confirm password");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        submitButton.click();
        logger.info("Clicked submit to change password");
        test.pass("Clicked submit to change password");
        System.out.println("Clicked submit to change password");


    }

    @Test(priority = 4)
    public void validatePasswordAlerts() {
        logger.info("Started validation for password alerts");
        test.info("Started validation for password alerts");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait and fetch alert for minimum password length
        WebElement alert1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Min Length must be 8 characters long')]")));
        String alert1Text = alert1.getText();
        logger.info("Min Length Alert Text: " + alert1Text);
        test.pass("Min Length Alert Text: " + alert1Text);
        System.out.println("Alert 1 (Min Length): " + alert1Text);

        // Wait and fetch alert for mismatched confirm password
        WebElement alert2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Confirm Password is not matched with New Password')]")));
        String alert2Text = alert2.getText();
        logger.info("Confirm Password Mismatch Alert: " + alert2Text);
        test.pass("Confirm Password Mismatch Alert: " + alert2Text);
        System.out.println("Alert 2 (Password Mismatch): " + alert2Text);

        logger.info("Completed validation for password alerts");
        test.info("Completed validation for password alerts");
    }


    @AfterClass
    public void QuitBrowser() throws InterruptedException {
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



