package SignUp.Pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Logs;
import utilities.Screenshot;
import utilities.StartupCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPPage extends StartupCode {
    static WebDriver driver;

    public OTPPage(WebDriver driver) {
        this.driver = driver;
    }

    public static String fetchOtpFromYopmail() throws InterruptedException, IOException {
        ((JavascriptExecutor) driver).executeScript("window.open('https://yopmail.com/en/', '_blank');");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        driver.findElement(By.xpath("//input[@id='login']")).sendKeys(email);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//i[@class='material-icons-outlined f36']")).click(); // Check inbox
        Thread.sleep(5000); // Wait for inbox to load

        driver.switchTo().frame("ifinbox");

        List<WebElement> emailList = driver.findElements(By.xpath("//div[@class='m']"));

        boolean otpEmailFound = false;
        for (int i = 0; i < emailList.size(); i++) {
            WebElement emailItem = emailList.get(i);
            String subject = emailItem.getText().trim();

            // System.out.println("Email " + (i + 1) + " Subject: " + subject);
            Logs.info(test, "Found email subject: " + subject);

            if (subject.toLowerCase().contains("otp")) {
                emailItem.click();
                otpEmailFound = true;
                break;
            }

        }
        driver.switchTo().defaultContent();
        if (!otpEmailFound) {
            Logs.fail(driver, test, "❌ OTP email not found in inbox");
            String screenshotPath = Screenshot.takeScreenshot(driver, "OTP_Email_Not_Found");
            Allure.addAttachment("OTP Email Not Found", "image/png", new FileInputStream(screenshotPath), ".png");
            driver.quit();
            throw new RuntimeException("❌ OTP email not found. Browser closed.");
        }

        Thread.sleep(3000);

        driver.switchTo().frame("ifmail");

        String emailBody = driver.findElement(By.tagName("body")).getText();
        //   System.out.println("Email Body: " + emailBody);

        Pattern pattern = Pattern.compile("\\b(\\d{6})\\b"); // Match 6-digit code
        Matcher matcher = pattern.matcher(emailBody);
        String otpCode = "";
        if (matcher.find()) {
            otpCode = matcher.group();
        }

        System.out.println("OTP: " + otpCode);
        Logs.info(test, "Extracted OTP: " + otpCode);

        driver.switchTo().defaultContent();
        driver.switchTo().window(tabs.get(0));

        driver.findElement(By.xpath("//input[@placeholder='Please enter verification code']")).sendKeys(otpCode);
        driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();

        Logs.info(test, "OTP submitted successfully.");
        return emailBody;
    }
}