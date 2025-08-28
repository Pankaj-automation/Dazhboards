package Login.Test_Cases;

import Login.Pages.Login_Page;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.AllureEnvWriter;
import utilities.Logs;
import utilities.StartupCode;

import java.io.IOException;

import static utilities.Screenshot.tearDown1;

@Epic("Login")
@Feature("Required Field Alerts")
public class Login_Required_Field_Alerts_Test extends StartupCode {
    Login_Page login;

    @BeforeClass
    @Step("Launch browser")
    public void setup() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        login = new Login_Page(driver);
        Logs.info(test, "Started Login_Required_Field_Alerts_Test");
    }

    @Test(priority = 1, description = "Password Blank and invalid email inputs")
    @Severity(SeverityLevel.CRITICAL)
    @Story("1_Validate Email format")
    @Description("Validates browser-side alerts for Password blank and invalid email inputs")
    public void emailFieldValidation() throws InterruptedException {
        WebElement signInBtn = driver.findElement(By.xpath("//button[normalize-space()='Sign in']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signInBtn);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String blankMsg = (String) js.executeScript("return arguments[0].validationMessage;", login.getEmailFieldElement());
        Assert.assertTrue(blankMsg.toLowerCase().contains("fill"), "Expected required field alert");
        Logs.pass(driver, test, "Blank email field validation message: " + blankMsg);

        login.enterEmail("thred");  // Entered the invalid email to verify the alert
        String invalidMsg = (String) js.executeScript("return arguments[0].validationMessage;", login.getEmailFieldElement());
        Assert.assertTrue(invalidMsg.toLowerCase().contains("email"), "Expected email format warning");
        Logs.pass(driver, test, "Invalid email format message: " + invalidMsg);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("2_Failed login with incorrect password")
    @Description("Displays error message on incorrect password submission")
    public void passwordValidation() throws InterruptedException {
        login.enterEmail("pnkj@yopmail.com");
        login.enterPassword("wrongPassword");
        login.clickSignIn();
        String error = login.getErrorMessage();
        Assert.assertEquals(error.trim(), "Invalid credentials.");
        Logs.pass(driver, test, "Invalid password message validated: " + error);
    }

    @Test(priority = 3, description = "Empty password field validation")
    @Severity(SeverityLevel.MINOR)
    @Story("3_Submit with blank password")
    @Description("Validates required password field when left empty")
    public void emptyPasswordValidation() throws InterruptedException {

        login.clearFields();
        login.enterEmail("pnkj@yopmail.com");
        login.getPasswordFieldElement().clear();
        login.clickSignIn();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String pwdMsg = (String) js.executeScript("return arguments[0].validationMessage;", login.getPasswordFieldElement());
        Assert.assertTrue(pwdMsg.toLowerCase().contains("fill in this"), "Expected required password alert");
        Logs.pass(driver, test, "Blank password field validation message: " + pwdMsg);
    }

    @Test(priority = 4, description = "Both fields empty")
    @Severity(SeverityLevel.CRITICAL)
    @Story("4_Submit without any credentials")
    @Description("Ensure alerts appear when email and password are empty")
    public void bothFieldsEmpty() throws InterruptedException {
        login.clearFields();
        login.clickSignIn();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String msg = (String) js.executeScript("return arguments[0].validationMessage;", login.getEmailFieldElement());
        Assert.assertTrue(msg.toLowerCase().contains("fill in this"));
        Logs.pass(driver, test, "Required field message when both fields are blank: " + msg);
    }

    @AfterMethod
    public void takescreenshot(ITestResult result) throws IOException {
        tearDown1(result);

    }

    @AfterClass
    @Step("Closing browser and report finalization")
    public void teardown() {
        quitDriver();
        finalizeReport();
    }
}