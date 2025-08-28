package ForgotPassword.Test_Cases;

import ForgotPassword.Pages.ForgotPassword_Page;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.AllureEnvWriter;
import utilities.Logs;
import utilities.StartupCode;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import static utilities.Screenshot.tearDown1;

@Epic("Forgot Password")
@Feature("Forgot Password Flow")
//@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class ForgotPassword_Test extends StartupCode {
    ForgotPassword_Page forgot;

    @BeforeClass
    public void Start() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        forgot = new ForgotPassword_Page(driver);
        Logs.info(test, "*Started Forgot_Password*");
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("1_Initiate forgot password with email")
    public void Forgot_Password_Flow() throws InterruptedException {
        Logs.info(test, "Started Forgot Password Flow");
        WebElement forgotPasswordLink = driver.findElement(By.xpath("//a[normalize-space()='Forgot Password?']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", forgotPasswordLink);
        Logs.info(test, "Clicked on 'Forgot Password?'");
        forgot.enterEmail("pnkj@yopmail.com");
        Logs.info(test, "Entered email for password recovery");
        forgot.clickSendRecoveryEmailButton();
        Logs.info(test, "Clicked on 'Send Recovery Email'");
        String message = forgot.getPopupMessage();
        System.out.println("Popup Message: " + message);
        Logs.info(test, "Popup Message: " + message);
        Logs.pass(driver, test, "Clicked OK on popup");
        forgot.clickOkOnPopup();

    }

    @Test(priority = 2)
    @Severity(SeverityLevel.NORMAL)
    @Story("2_Access Yopmail and click reset link")
    public void Clicked_reset_button() {
        logger.info("Started: Clicking Reset Password button in Yopmail");
        Logs.info(test, "Started: Clicking Reset Password button in Yopmail");
        ((JavascriptExecutor) driver).executeScript("window.open('https://yopmail.com/en/', '_blank');");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='login']")));
        loginInput.sendKeys("pnkj");
        Logs.info(test, "Entered Yopmail inbox name");
        WebElement checkInbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='material-icons-outlined f36']")));
        checkInbox.click();
        Logs.info(test, "Clicked on inbox refresh icon");
        driver.switchTo().frame("ifmail");
        WebElement resetLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@align='left']//a[1]")));
        resetLink.click();
        Logs.pass(driver, test, "Clicked on Reset Password link in email");
    }


    @Test(priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    @Story("3_Enter and submit new password")
    public void Change_Password() {
        Logs.info(test, "Started Change Password process");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        forgot.enterNewPassword("Admine");
        Logs.info(test, "Entered new password");
        forgot.enterConfirmPassword("Admwine");
        Logs.info(test, "Entered confirm password");
        forgot.clickSubmit();
        Logs.pass(driver, test, "Clicked Save to change password");
    }

    @Test(priority = 4)
    @Severity(SeverityLevel.NORMAL)
    @Story("4_Validate incorrect password alerts")
    public void validatePasswordAlerts() {
        Logs.info(test, "Started validation for password alerts");
        String alert1 = forgot.getMinLengthAlert();
        Logs.pass(driver, test, "Min Length Alert Text: " + alert1);
        String alert2 = forgot.getConfirmMismatchAlert();
        Logs.pass(driver, test, "Confirm Password Mismatch Alert: " + alert2);
    }

    @AfterMethod
    public void takescreenshot(ITestResult result) throws IOException {
        tearDown1(result);
    }

    @AfterClass
    public void QuitBrowser() throws InterruptedException {
        quitDriver();
        finalizeReport();
    }
}
