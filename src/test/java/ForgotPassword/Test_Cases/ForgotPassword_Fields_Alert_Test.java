package ForgotPassword.Test_Cases;

import ForgotPassword.Pages.ForgotPassword_Page;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utilities.AllureEnvWriter;
import utilities.Logs;
import utilities.StartupCode;

import java.io.IOException;

import static utilities.Screenshot.tearDown1;

@Epic("Forgot Password Feature")
@Feature("Required Field Validation")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class ForgotPassword_Fields_Alert_Test extends StartupCode {
    ForgotPassword_Page forgot;

    @BeforeClass
    public void Start() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        forgot = new ForgotPassword_Page(driver);
        Logs.pass(driver, test, "*Started Forgot_Password_Required_Field_Alerts_Test*");
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("1_Verify alerts on Forgot Password form")
    @Description("Validates email field required and format alerts on the Forgot Password page.")
    public void Forgot_Password_Email_field_alerts() throws InterruptedException {
        forgot.openForgotPasswordPage();
        forgot.clickSendRecoveryEmailButton();
        String validationMessage = forgot.getEmailFieldValidationMessage();
        Logs.info(test, "Email field Validation Message: Expected: Please fill in this field. Actual: " + validationMessage);
        SoftAssert softAssert = new SoftAssert();
        Assert.assertEquals(validationMessage, "Please fill in this field.");
        softAssert.assertAll();
        forgot.enterEmail("loop");
        String invalidEmailValidationMessage = forgot.getEmailFormatValidationMessage();
        Logs.pass(driver, test, "If user tries to enter invalid email format: " + invalidEmailValidationMessage);
    }

    @AfterMethod
    public void takescreenshot(ITestResult result) throws IOException {
        tearDown1(result);
    }

    @AfterClass
    public void QuitBrowser() {
        finalizeReport();
        quitDriver();
    }
}
