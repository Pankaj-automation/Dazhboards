/*
package SignUp.Test_Cases;

import Login.Pages.Login_Page;
import SignUp.Pages.CompanyDetailsPage;
import SignUp.Pages.OTPPage;
import SignUp.Pages.PlansPage;
import SignUp.Pages.SignUpPage;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.Logs;
import utilities.StartupCode;

import java.io.IOException;

import static utilities.Screenshot.tearDown1;

@Epic("Sign Up Flow")
@Feature("Complete SignUp Workflow For Paid Plan")
@Listeners({AllureTestNg.class})
public class SignUp_PaidPlan_Test extends StartupCode {
    pages.HomePage homePage;
    SignUpPage signUpPage;
    OTPPage otpPage;
    CompanyDetailsPage companyDetailsPage;
    PlansPage plansPage;
    Login_Page login;

    @BeforeClass
    @Step("Launch browser and initialize page objects")
    public void setup() throws InterruptedException {
        driver = setup1();
        homePage = new pages.HomePage(driver);
        signUpPage = new SignUpPage(driver);
        otpPage = new OTPPage(driver);
        companyDetailsPage = new CompanyDetailsPage(driver);
        plansPage = new PlansPage(driver);
        login = new Login_Page(driver);

    }

    @Test(priority = 1, description = "Click Sign Up link on homepage")
    @Story("1_Navigate to SignUp Page")
    @Severity(SeverityLevel.NORMAL)
    public void clickSignUpLink() {

        homePage.clickSignUpLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("/signup"), "Not redirected to SignUp page");
        Logs.pass(driver, test, "Sign Up button Clicked");

    }

    @Test(priority = 2, description = "Fill the Sign Up form")
    @Story("2_Fill SignUp Form")
    @Severity(SeverityLevel.CRITICAL)
    public void fillSignUpForm() {
        signUpPage.fillSignUpForm(email, firstName, lastName, password);
        Logs.info(test, email + "   " + password);
        Logs.pass(driver, test, "");

    }

    @Test(priority = 3, description = "Check T&C checkbox and validate links")
    @Story("3_Verify T&C and Privacy")
    @Severity(SeverityLevel.MINOR)
    public void checkTermsCheckboxAndLinks() {
        signUpPage.acceptTermsAndVerifyLinks();
        Logs.pass(driver, test, "Sign Up data Filled");

    }

    @Test(priority = 4, description = "Submit signup form")
    @Story("4_Click SignUp button")
    @Severity(SeverityLevel.CRITICAL)
    public void submitSignUp() throws InterruptedException {
        signUpPage.clickSignUpButton();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("otp"), "Did not navigate to OTP page");
    }

    @Test(priority = 5, description = "Enter OTP from Yopmail")
    @Story("5_OTP Verification")
    @Severity(SeverityLevel.BLOCKER)
    public void enterOtpFromEmail() throws InterruptedException, IOException {
        OTPPage.fetchOtpFromYopmail();
    }


    @Test(priority = 6, description = "Fill company and contact details")
    @Story("6_Fill Profile Details")
    @Severity(SeverityLevel.NORMAL)
    public void fillCompanyDetails() throws InterruptedException {
        Thread.sleep(5000);
        companyDetailsPage.fillCompanyName(lastName);
        Thread.sleep(1000);
        Thread.sleep(1000);
        companyDetailsPage.selectProductCategory("Adventure");
        Thread.sleep(1000);
        Thread.sleep(1000);
        companyDetailsPage.enterBusinessNumber(phoneNumber);
        Thread.sleep(1000);
        Thread.sleep(1000);
        companyDetailsPage.selectAddress("Mohali");
        Thread.sleep(1000);
        companyDetailsPage.enterWebsite(website);
        Thread.sleep(1000);
        companyDetailsPage.enterPhone(phoneNumber);
        Thread.sleep(1000);
        companyDetailsPage.scrollToPerPaxCheckbox();
        Thread.sleep(1000);
        Thread.sleep(1000);
        companyDetailsPage.selectdateformat();
        Thread.sleep(1000);
        companyDetailsPage.selectPerPaxCheckbox();
        Thread.sleep(1000);
        companyDetailsPage.selectSmsOption();
        Thread.sleep(1000);
        companyDetailsPage.scrollToContactEmailInput();
        Thread.sleep(1000);
        companyDetailsPage.fillContactDetails(firstName, "Quality Engineer", email, phoneNumber);
        Thread.sleep(1000);
        companyDetailsPage.addContactPerson();
        Thread.sleep(1000);
        companyDetailsPage.deleteContactPerson();
        Thread.sleep(1000);
        companyDetailsPage.uploadLogo("/home/jc-raj/Music/Automation/Dazhboards/Test data/pexels-18393328-6470943.jpg");
        Thread.sleep(1000);

        companyDetailsPage.clickSaveButton();
    }

    @Test(priority = 7, description = "Purchase a plan")
    @Story("7_Select Plan")
    @Severity(SeverityLevel.CRITICAL)
    public void purchasePlan() throws InterruptedException {
        plansPage.clickonmonths();

        plansPage.ScrolltoGetStarted();
        plansPage.clickGetStarted();
        plansPage.Buyingaplan();

        Thread.sleep(10000);

    }

    @AfterMethod
    public void takescreenshot(ITestResult result) throws IOException {
        tearDown1(result);

    }

    @AfterClass
    @Step("Close browser")
    public void tearDown() {
        quitDriver();
        finalizeReport();
    }
}


*/
