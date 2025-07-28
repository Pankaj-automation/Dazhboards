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

@Epic("Login")
@Feature("Validation Messages")
public class Login_Required_Field_Alerts_Test extends StartupCode {
    pages.Login_Page loginPage;

    @BeforeClass
    public void setUp() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        loginPage = new pages.Login_Page(driver);
        test.info("Started LoginValidationTest");
    }

    @Test(priority = 1, description = "Blank and Invalid Email")
    @Severity(SeverityLevel.CRITICAL)
    public void testEmailFieldAlerts() {
        loginPage.clickSignIn();
        String msg1 = loginPage.getEmailFieldValidationMessage();
        test.info("Blank email validation: " + msg1);

        loginPage.enterEmail("thred");  // Invalid email
        String msg2 = loginPage.getEmailFieldValidationMessage();
        test.info("Invalid email validation: " + msg2);
    }

    @Test(priority = 2, description = "Wrong Password")
    @Severity(SeverityLevel.NORMAL)
    public void testWrongPassword() {
        loginPage.clearEmailField();
        loginPage.enterEmail("pnkj@yopmail.com");
        loginPage.enterPassword("wrongpass");
        loginPage.clickSignIn();

        String errorText = loginPage.getInvalidPasswordText();
        Assert.assertEquals(errorText, "Invalid credentials.", "Wrong password alert mismatch");
        test.info("Wrong password validation: " + errorText);
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
    public void tearDown() {
        quitDriver();
        finalizeReport();
    }
}
