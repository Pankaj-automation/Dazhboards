package ForgotPassword;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.StartupCode;

import java.util.ArrayList;

public class Forgot_Password extends StartupCode {
    @BeforeTest
    public void Start() throws InterruptedException {
        driver = setup1();
        test = extent.createTest("Forgot_Password_Required_Field_Alerts_Test");
        test.info("*Started Forgot_Password_Required_Field_Alerts_Test*");
    }

    @Test(priority = 1)
    public void Forgot_Password_Flow() throws InterruptedException {
        driver.findElement(By.xpath("//a[normalize-space()='Forgot Password?']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Email*']")).sendKeys("pnkj@yopmail.com");

        driver.findElement(By.xpath("//button[normalize-space()='Send Recovery Email']")).click();
        Thread.sleep(2000);
        //   Alert alert = driver.switchTo().alert();
        WebElement popuptext = driver.findElement(By.xpath("//div[@class='swal-text']"));
        System.out.println(popuptext.getText());
        driver.findElement(By.xpath(" //button[normalize-space()='OK']")).click();


        Thread.sleep(2000);

    }

    @Test(priority = 2)
    public void Clicked_reset_button() throws InterruptedException {
        logger.info("*****Started clicking on Reset Password button*****");
        test.info("Started clicking on Reset Password button");
        ((JavascriptExecutor) driver).executeScript("window.open('https://yopmail.com/en/', '_blank');");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        driver.findElement(By.xpath("//input[@id='login']")).sendKeys("pnkj");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//i[@class='material-icons-outlined f36']")).click();
        Thread.sleep(3000);
        // driver.findElement(By.xpath("//button[@id='refresh']")).click(); // Click refresh
        // Thread.sleep(3000);

        driver.switchTo().frame("ifmail");

        driver.findElement(By.xpath("//td[@align='left']//a[1]")).click();

        test.pass("Clicked on Reset Password button");
        logger.info("Clicked on Reset Password button");


    }

    @Test(priority = 3)
    public void Change_Password() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder='Create new password']")).sendKeys("Admine");
        driver.findElement(By.xpath("//input[@placeholder='Confirm new password']")).sendKeys("Admwine");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

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



