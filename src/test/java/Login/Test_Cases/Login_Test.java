package Login.Test_Cases;

import Login.Pages.Login_Page;
import io.qameta.allure.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.AllureEnvWriter;
import utilities.Config;
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
        driver = setup1();  // Assuming this is a method in StartupCode to initialize the driver
        login = new Login_Page(driver);
        Logs.info(test, "Started Login_Test");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][]{
                {"invalid@yopmail.com", "wrongpass", false},  // invalid email and password
                {"pnkj@yopmail.com", "wrongpass", false},     // valid email and invalid password
                {"", "", false},                              // Empty fields
                {Config.get("email"), Config.get("password"), true}  // valid email and valid password
        };
    }

    @Test(dataProvider = "loginData")  // Add @Test annotation here
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login with various credentials")
    @Description("Test login with different email/password combinations")
    public void loginTests(String email, String password, boolean isSuccessExpected) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        login.enterEmail(email);
        login.enterPassword(password);
        login.clickSignIn();
        if (isSuccessExpected) {
            // Wait until the URL contains either "dashboard" or "pos"
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("dashboard"),
                    ExpectedConditions.urlContains("pos")
            ));

            // Assert that URL contains either "dashboard" or "pos"
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(
                    currentUrl.contains("dashboard") || currentUrl.contains("pos"),
                    "Login failed with valid credentials"
            );

            Logs.pass(driver, test, "Login successful with: " + email + " " + password);
        } else {
            try {
                wait.until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("dashboard"),
                        ExpectedConditions.urlContains("pos")
                ));
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