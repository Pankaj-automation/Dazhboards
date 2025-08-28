/*
package SignUp;

import com.aventstack.extentreports.MediaEntityBuilder;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.AllureEnvWriter;
import utilities.Screenshot;
import utilities.StartupCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Epic("Sign Up")
@Feature("Complete Flow of Signup")
public class SignUp_Tests extends StartupCode {

    @BeforeClass
    @Step("Setup and Launch Browser")
    public void Start() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        test.info("*Started Login_Test*");
    }

    @Test(priority = 1)
    @Story("Navigate to signup")
    @Description("User clicks on the SignUp link on homepage")
    @Severity(SeverityLevel.NORMAL)
    public void Click_on_SignUp_Link() throws IOException, InterruptedException {
        test.info("*Started SignUp_Test*");
        logger.info("*SignUp_Test Initialized*");
        logger.info("*****Opening Browser*****");
        logger.info("*****Started SignUp*****");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        try {
            ((JavascriptExecutor) driver).executeScript("let iframe = document.getElementById('produktly-checklist-beacon-iframe505');" + "if(iframe) iframe.style.display='none';");
            logger.info("iframe hidden successfully");
            test.info("iframe hidden successfully");
        } catch (Exception e) {
            logger.warn("Could not hide iframe: " + e.getMessage());
            test.warning("Could not hide iframe: " + e.getMessage());
        }
        WebElement signUpLink = driver.findElement(By.xpath("//a[@href='/signup']"));
        signUpLink.click();
        String currentUrl = driver.getCurrentUrl();
        logger.info("Current URL: " + currentUrl);
        System.out.println("Current URL: " + currentUrl);
        test.pass("After clicked on sign up, current URL is: " + currentUrl);
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertTrue(currentUrl.contains("/signup"), "Sign Up page was not opened!");

    }

    @Test(priority = 2)
    @Story("Fill signup form")
    @Description("User fills all required fields in signup form")
    @Severity(SeverityLevel.CRITICAL)
    public void Filed_signup_form() throws IOException, InterruptedException {
        logger.info("*****Filling_signup_form*****");
        test.info("Filling_signup_form");

        WebElement Email = driver.findElement(By.xpath("//input[@placeholder='Email Address']"));
        Email.sendKeys(email);
        System.out.println(email);
        logger.info(email);
        test.pass(email);
        driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys(firstName);
        driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(lastName);
        WebElement Password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        Password.sendKeys(password);
        System.out.println(password);
        logger.info(password);
        test.pass(password);
        logger.info("*****Filed_signup_form*****");
        test.pass("Filed_signup_form");
        Thread.sleep(1000);

    }

    @Test(priority = 3)
    @Story("Verify terms and privacy checkbox")
    @Description("Check the terms and conditions checkbox and verify privacy policy links")
    @Severity(SeverityLevel.MINOR)
    public void Verify_checkbox_and_links() throws IOException, InterruptedException {
        logger.info("*****Verifying_checkbox_and_links*****");
        test.info("Verifying_checkbox_and_links");
        WebElement checkbox = driver.findElement(By.xpath("//input[@name='check']"));
        checkbox.click();
        Assert.assertTrue(checkbox.isSelected(), "Checkbox is not selected after clicking!");
        logger.info("Checkbox clicked and selected successfully");
        test.pass("Checkbox clicked and selected successfully");

        String mainWindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[normalize-space()='Terms of Use']")).click();
        WebElement termsLink = driver.findElement(By.xpath("//a[normalize-space()='Terms of Use']"));
        Assert.assertTrue(termsLink.isDisplayed() && termsLink.isEnabled(), "'Terms of Use' link is not clickable or visible!");
        logger.info("'Terms of Use' link is visible and enabled");
        test.pass("'Terms of Use' link is visible and enabled");
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.toLowerCase().contains("terms"), "Terms of Use page did not open!");
        logger.info("Navigated to Terms of Use page: " + currentUrl);
        test.pass("Navigated to Terms of Use page: " + currentUrl);
        driver.close();
        driver.switchTo().window(mainWindow);
        String mainWindow1 = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[contains(@class,'')][normalize-space()='Privacy Policy']")).click();
        WebElement privacyLink = driver.findElement(By.xpath("//a[contains(@class,'')][normalize-space()='Privacy Policy']"));

        Assert.assertTrue(privacyLink.isDisplayed() && privacyLink.isEnabled(), "'Privacy Policy' link is not clickable or visible!");
        logger.info("'Privacy Policy' link is visible and enabled");
        test.pass("'Privacy Policy' link is visible and enabled");
        for (String windowHandle1 : driver.getWindowHandles()) {
            if (!windowHandle1.equals(mainWindow1)) {
                driver.switchTo().window(windowHandle1);
                break;
            }
        }

        String currentUrl1 = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl1.toLowerCase().contains("privacy"), "Privacy Policy page did not open!");
        logger.info("Navigated to Privacy Policy page: " + currentUrl1);
        test.pass("Navigated to Privacy Policy page: " + currentUrl1);
        driver.close();
        driver.switchTo().window(mainWindow1);


    }

    @Test(priority = 4)
    @Story("Submit signup")
    @Description("Click on sign up button to submit the form")
    @Severity(SeverityLevel.CRITICAL)
    public void Click_on_Sign_button() throws IOException, InterruptedException {
        logger.info("*****Clicking on Signup button*****");
        test.info("Clicking on Signup button");

        driver.findElement(By.xpath("//button[normalize-space()='Sign Up']")).click();
        Thread.sleep(3000);
        String currentUrl2 = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl2.contains("otppage") || currentUrl2.contains("otppage"), "Sign Up failed or did not redirect to expected page. Current URL: " + currentUrl2);
        logger.info("Sign Up button clicked, redirected to: " + currentUrl2);
        test.pass("Sign Up successful, redirected to: " + currentUrl2);

    }

    @Test(priority = 5)
    @Story("Verify OTP flow")
    @Description("Enter and verify OTP received via email or SMS")
    @Severity(SeverityLevel.BLOCKER)
    public void OTP_Verification() throws IOException, InterruptedException {
        logger.info("*****Starting OTP Verification*****");
        test.info("OTP Verification started");
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
            logger.info("Found email subject: " + subject);

            if (subject.toLowerCase().contains("otp email")) {
                emailItem.click();
                otpEmailFound = true;
                break;
            }

        }
        driver.switchTo().defaultContent();
        if (!otpEmailFound) {
            test.fail("❌ OTP email not found in inbox");
            logger.error("❌ OTP email not found in inbox");
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
        logger.info("Extracted OTP: " + otpCode);
        test.info("Extracted OTP: " + otpCode);

        driver.switchTo().defaultContent();
        driver.switchTo().window(tabs.get(0));

        driver.findElement(By.xpath("//input[@placeholder='Please enter verification code']")).sendKeys(otpCode);
        driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();

        test.pass("OTP submitted successfully.");
        logger.info("OTP submitted successfully.");
    }

    @Test(priority = 6)
    @Story("Complete profile")
    @Description("User fills in company information after signup")
    @Severity(SeverityLevel.NORMAL)
    public void Fill_company_details() throws IOException, InterruptedException {
        logger.info("*****Started Fill_company_details*****");
        test.info("Started Fill_company_details");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(5000);

        logger.info("Entering company name");
        test.info("Entering company name");
        WebElement companyInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@spellcheck='false']")));
        companyInput.sendKeys(lastName);
        Thread.sleep(1000);
        System.out.println("Company name entered successfully");
        test.pass("Company name entered successfully");

        logger.info("Selecting product category");
        test.info("Selecting product category");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='productCat_input']"))).click();
        Thread.sleep(1000);

        WebElement updatedDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='productCat_input']")));
        updatedDropdown.sendKeys("Adventure");
        Thread.sleep(1000);
        updatedDropdown.sendKeys(Keys.ARROW_DOWN);
        updatedDropdown.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        System.out.println("Product category selected");
        test.pass("Product category selected");

        logger.info("Entering Business Number");
        test.info("Entering Business Number");
        WebElement Business_Number = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[4]/input[1]")));
        Thread.sleep(1000);
        Business_Number.sendKeys(phoneNumber);
        Thread.sleep(1000);
        System.out.println("Business number entered");
        test.pass("Business number entered");

        logger.info("Entering location address");
        test.info("Entering location address");
        WebElement locationInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address']")));
        Thread.sleep(1000);
        locationInput.sendKeys("Mohali");
        Thread.sleep(1000);
        wait.until(ExpectedConditions.attributeContains(locationInput, "value", "Mohali"));
        Thread.sleep(1000);
        locationInput.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        locationInput.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        System.out.println("✔ Location selected");
        test.pass("Location selected");

        logger.info("Entering website address");
        test.info("Entering website address");
        WebElement websiteaddress = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='What’s your website address? ']")));
        Thread.sleep(1000);
        websiteaddress.sendKeys(website);
        System.out.println("✔ Website entered");
        test.pass("Website entered");

        logger.info("Entering Contact number");
        test.info("Entering Contact number");
        WebElement phonenumber = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='col-span-6']//input[@placeholder='Enter phone number']")));
        Thread.sleep(1000);
        phonenumber.sendKeys(phoneNumber);
        Thread.sleep(1000);
        System.out.println("✔ Phone number entered");
        test.pass("Phone number entered");

        logger.info("Scrolling ");
        test.info("Scrolling ");
        WebElement element = driver.findElement(By.xpath("//label[normalize-space()='Per Pax']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        Thread.sleep(1000);

        WebElement dropdownTrigger3 = wait.until(ExpectedConditions.elementToBeClickable(By.id("headlessui-listbox-button-:r5:")));
        try {
            dropdownTrigger3.click();
        } catch (ElementClickInterceptedException e) {
            //  System.out.println("Click intercepted — using JS click instead. Reason: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownTrigger3);
        }
        Thread.sleep(1000);
        Actions actions = new Actions(driver);
        logger.info("Navigating dropdown using ARROW_DOWN");
        test.info("Navigating dropdown using ARROW_DOWN");

        actions.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        System.out.println("✔ Dropdown selection performed");
        test.pass("Dropdown selection successful");


        logger.info("Clicking Per/Pax checkbox");
        test.info("Clicking Per/Pax checkbox");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[contains(@name,'showPax3')]")).click();
        logger.info("Clicking SMS radio/checkbox");
        test.info("Clicking SMS radio/checkbox");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[5]/div[9]/div[1]/label[1]")).click();
        Thread.sleep(1000);
        logger.info("Scrolling ");
        test.info("Scrolling ");
        WebElement ele = driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[7]/div[1]/div[2]/div[5]/div[1]/input[1]"));
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", ele);
        Thread.sleep(1000);
        logger.info("Entering contact person details");
        test.info("Entering contact person details");
        driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[7]/div[1]/div[2]/div[1]/input[1]")).sendKeys(firstName);
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[7]/div[1]/div[2]/div[2]/input[1]")).sendKeys("Quality Engineer");
        System.out.println("✔ Contact person info filled");
        test.pass("Contact person info filled");
        logger.info("Selecting department from dropdown");
        test.info("Selecting department from dropdown");
        WebElement dropdownTrigger5 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='headlessui-listbox-button-:rn:']")));
        dropdownTrigger5.click();
        Thread.sleep(1000);
        Actions actions1 = new Actions(driver);
        actions1.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(1000);
        actions1.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(1000);
        actions1.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);

        logger.info("Entering email address");
        test.info("Entering email address");
        driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[7]/div[1]/div[2]/div[4]/input[1]")).sendKeys(email);
        Thread.sleep(1000);

        logger.info("Selecting country from flag dropdown");
        test.info("Selecting country from flag dropdown");
        WebElement dropdownTrigger2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'gap-y-4 gap-x-5 grid grid-cols-2 mb-6')]//div//div[contains(@role,'button')]")));
        dropdownTrigger2.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='search']")).sendKeys("india");
        dropdownTrigger2.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        dropdownTrigger2.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@value='+91']")).sendKeys(phoneNumber);
        System.out.println("✔ Country and phone number entered");
        test.pass("Country and phone number entered");

        logger.info("Clicking Add Contact Person");
        test.info("Clicking Add Contact Person");
        driver.findElement(By.xpath("//button[normalize-space()='Add Contact Person']")).click();
        Thread.sleep(1000);
        System.out.println("✔ Contact person added");
        test.pass("Contact person added");

        logger.info("Deleting contact person entry");
        test.info("Deleting contact person entry");
        driver.findElement(By.xpath("//div[contains(@title,'Delete')]")).click();
        Thread.sleep(1000);
        System.out.println("✔ Contact person deleted");
        test.pass("Contact person deleted");

        logger.info("Uploading logo");
        test.info("Uploading logo");
        WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
        uploadInput.sendKeys("/home/jc-raj/Music/Automation/Dazhboards/test-output/pexels-18393328-6470943.jpg");
        System.out.println("✔ Logo uploaded");
        test.pass("Logo uploaded successfully");

        logger.info("Closing overlay popup");
        test.info("Closing overlay popup");

        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@id,'produktly-checklist-beacon-iframe')]")));

            driver.switchTo().frame(iframe);
            logger.info("Switching to overlay iframe");
            test.info("Switching to overlay iframe");

            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Close checklist beacon']//*[name()='svg']")));
            closeBtn.click();

            logger.info("Overlay popup closed");
            test.pass("Overlay popup closed");
            driver.switchTo().defaultContent();

        } catch (TimeoutException e) {
            logger.info("Overlay iframe not found, continuing without closing");
            test.info("Overlay iframe not found, continuing test");
        } catch (Exception e) {
            logger.warn("Unexpected error while handling overlay: " + e.getMessage());
            test.warning("Unexpected overlay issue: " + e.getMessage());
            driver.switchTo().defaultContent();
        }

        System.out.println("✔ Overlay Popup closed");
        logger.info("Overlay popup closed successfully");
        test.pass("Overlay popup closed successfully");


        Thread.sleep(5000);

        driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();

        Thread.sleep(10000);

        WebElement saveBtn = driver.findElement(By.xpath("//button[normalize-space()='Save']"));
        if (!saveBtn.isEnabled()) {
            logger.warn("⚠️ Save button is present but not enabled");
        }
        WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement saveBtn4 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[normalize-space()='Save']")));

        wait.until(driver1 -> {
            WebElement btn = driver1.findElement(By.xpath("//button[normalize-space()='Save']"));
            String disabledAttr = btn.getAttribute("disabled");
            return (disabledAttr == null || disabledAttr.equalsIgnoreCase("false"));
        });
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn4);


    }

    @Test(priority = 7)
    @Story("Select a plan")
    @Description("User purchases a plan after signup")
    @Severity(SeverityLevel.CRITICAL)
    public void Purchase_Plan() throws IOException, InterruptedException {
        logger.info("*****Starting Purchase_Plan*****");
        test.info("Purchase_Plan started");
        System.out.println("Starting Purchase_Plan");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(3000);
        WebElement ele = driver.findElement(By.xpath("//div[13]//div[1]//div[3]//button[1]"));
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", ele);

        Thread.sleep(5000);
        logger.info("Waiting for 'Get Started' button");
        test.info("Waiting for 'Get Started' button");

        WebElement clickongetstarted = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[13]//div[1]//div[3]//button[1]")));
        clickongetstarted.click();
        logger.info("'Get Started' button clicked");
        test.pass("'Get Started' button clicked");
        System.out.println("'Get Started' button clicked");


        String currentURL = driver.getCurrentUrl();
        System.out.println("URL after purchase: " + currentURL);
        logger.info("URL after plan purchase: " + currentURL);
        test.pass("URL after plan purchase: " + currentURL);

        String expectedSubstring = "plans";
        Assert.assertTrue(currentURL.contains(expectedSubstring), "Current URL does not contain expected text: " + expectedSubstring);
        logger.info("URL assertion passed");
        test.pass("URL contains expected substring: " + expectedSubstring);
        System.out.println("Assertion passed: URL contains '" + expectedSubstring + "'");
        logger.info("*****Plan Purchased*****");
        test.info("Plan Purchased");
    }


    @AfterMethod
    public void tearDown1(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = Screenshot.takeScreenshot(driver, result.getName());
            String relativePath = ".." + File.separator + "screenshots" + File.separator + new File(screenshotPath).getName();
            test.fail("Test Failed: " + result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());
            Allure.addAttachment("Screenshot on Failure", "image/png", new FileInputStream(screenshotPath), ".png");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        }
    }

    @AfterClass
    public void QuitBrowser() throws IOException, InterruptedException {
        quitDriver();
        finalizeReport();
    }

}*/
