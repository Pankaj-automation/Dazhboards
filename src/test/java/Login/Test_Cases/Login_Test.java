package Login.Test_Cases;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.AllureEnvWriter;
import utilities.Screenshot;
import utilities.StartupCode;

import java.io.FileInputStream;
import java.io.IOException;

@Epic("Login Module")
@Feature("Login Functionality")
public class Login_Test extends StartupCode {
    pages.Login_Page loginPage;

    @BeforeClass
    public void setUp() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        loginPage = new pages.Login_Page(driver);
        logger.info("🚀 Browser launched and maximized");
    }

    @Test(priority = 1, description = "qqqLogin with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can successfully log in with valid credentials")
    @Story("Positive login test")
    public void login() throws IOException {
        logger.info("Enter email");
        loginPage.enterEmail("pnkj@yopmail.com");
        logger.info("Entered valid email");
        loginPage.enterPassword("Admine51%%!");
        logger.info("Entered valid password");
        loginPage.clickSignIn();
        logger.info("Clicked on Sign in");
        String currentUrl = loginPage.getCurrentUrl();
        logger.info("Current URL after login: " + currentUrl);

        if (currentUrl.contains("pos")) {

            logger.info("URL validation passed.");
        } else {
            logger.info("URL validation failed. Expected URL to contain 'pos'");
            Assert.fail("URL does not contain 'pos'");
        }
    }


    @AfterMethod
    public void tearDown1(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = Screenshot.takeScreenshot(driver, result.getName());
            test.fail("Test Failed: " + result.getThrowable()).addScreenCaptureFromPath(screenshotPath);
            Allure.addAttachment("Screenshot on Failure", "image/png", new FileInputStream(screenshotPath), ".png");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        }
    }

    @AfterClass
    public void Close() {
        quitDriver();
        finalizeReport();
    }
}
