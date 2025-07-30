package Login.Test_Cases;

import Login.Pages.Login_Page;
import io.qameta.allure.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.AllureEnvWriter;
import utilities.Logs;
import utilities.StartupCode;

import java.io.IOException;
import java.time.Duration;

import static utilities.Screenshot.tearDown1;

@Epic("Login Module")
@Feature("Login Scenarios")
public class Login_Test extends StartupCode {
    Login_Page login;

    @BeforeClass
    public void setup() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        login = new Login_Page(driver);
        Logs.info(test, "Started Login_Test");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][]{

                {"invalid@yopmail.com", "wrongpass", false},  // invalid email
                {"pnkj@yopmail.com", "wrongpass", false},     // invalid password
                {"", "", false},                              // Emppty fields
                {"pnkj@yopmail.com", "Admine51%%!", true},    // valid credentials
        };
    }

    @Test(dataProvider = "loginData", description = "login scenarios")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login with various credentials")
    @Description("Test login with different email/password combinations")
    public void loginTests(String email, String password, boolean isSuccessExpected) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        login.enterEmail(email);
        login.enterPassword(password);
        login.clickSignIn();

        if (isSuccessExpected) {
            wait.until(ExpectedConditions.urlContains("pos"));
            Assert.assertTrue(driver.getCurrentUrl().contains("pos"), "Login failed with valid credentials");
            Logs.pass(driver, test, "Login successful with: " + email + " " + password);
        } else {
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                shortWait.until(ExpectedConditions.urlContains("pos"));
                Assert.fail("❌ Login succeeded unexpectedly for invalid credentials: " + email);
            } catch (Exception e) {
                Logs.fail(driver, test, "Login correctly failed for: " + email + " " + password);
            }
        }
    }

    @AfterMethod
    public void takescreenshot(ITestResult result) throws IOException {
        tearDown1(result);

    }

    @AfterClass
    public void teardown() {
        finalizeReport();
        quitDriver();
    }
}
