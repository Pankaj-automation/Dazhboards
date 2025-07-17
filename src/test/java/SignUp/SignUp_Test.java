package SignUp;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.Extentreportmanager;
import utilities.Reuseable;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Test {
    WebDriver driver;
    Reuseable reuse;
    Logger logger;
    ExtentReports extent;
    ExtentTest test;
    Faker fakedata;
    Reuseable data = new Reuseable();
    String firstName = data.FirstName();
    String lastName = data.LastName();
    String email = data.Email();
    String password = data.Password();
    String zipCode = data.ZipCode();
    String phoneNumber = data.PhoneNumber();
    String website = data.Website();

    @BeforeClass
    public void setup() throws InterruptedException {
        reuse = new Reuseable(); // Create instance of Reuseable
        driver = reuse.Reuseable1(); // Initialize driver
        Thread.sleep(1000);
        driver.manage().window().maximize();
        logger = LogManager.getLogger(this.getClass());
        extent = Extentreportmanager.getExtentReports();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


    }

    @Test(priority = 1)
    public void Click_on_SignUp_Link() throws IOException, InterruptedException {
        test = extent.createTest("SignUp_Test");
        test.info("*Started SignUp_Test*");
        logger.info("*SignUp_Test Initialized*");
        logger.info("*****Opening Browser*****");
        logger.info("*****Started SignUp*****");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[@href='/signup']")).click();
        String currentUrl = driver.getCurrentUrl();
        logger.info(currentUrl);
        System.out.println(currentUrl);
        test.pass("After clicked on sign up the URL of current Page " + currentUrl);
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(currentUrl.contains("/signup"), "Sign Up page was not opened!");
    }

    @Test(priority = 2)
    public void Filed_signup_form() throws IOException, InterruptedException {
        logger.info("*****Filling_signup_form*****");
        test.info("Filling_signup_form");

        driver.findElement(By.xpath("//input[@placeholder='Email Address']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys(firstName);
        driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(lastName);
        WebElement Password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        Password.sendKeys(password);
        System.out.println(password);
        logger.info("*****Filed_signup_form*****");
        test.pass("Filed_signup_form");
        Thread.sleep(1000);

    }

    @Test(priority = 3)
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
    public void OTP_Verification() throws IOException, InterruptedException {
        logger.info("*****Starting OTP Verification*****");
        test.info("OTP Verification started");
        ((JavascriptExecutor) driver).executeScript("window.open('https://yopmail.com/en/', '_blank');");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        driver.findElement(By.xpath("//input[@id='login']")).sendKeys(email);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//i[@class='material-icons-outlined f36']")).click();
        Thread.sleep(3000);
        // driver.findElement(By.xpath("//button[@id='refresh']")).click(); // Click refresh
        // Thread.sleep(3000);

        driver.switchTo().frame("ifmail");

        String emailBody = driver.findElement(By.tagName("body")).getText();
        System.out.println("Email Body: " + emailBody);
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
    public void Fill_company_details() throws IOException, InterruptedException {
        logger.info("*****Started Fill_company_details*****");
        test.info("Started Fill_company_details");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(3000);

        logger.info("Entering company name");
        test.info("Entering company name");
        WebElement companyInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@spellcheck='false']")));
        companyInput.sendKeys(lastName);
        Thread.sleep(1000);
        System.out.println("✔ Company name entered successfully");
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
        System.out.println("✔ Product category selected");
        test.pass("Product category selected");

        logger.info("Entering Business Number");
        test.info("Entering Business Number");
        WebElement Business_Number = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[4]/input[1]")));
        Thread.sleep(1000);
        Business_Number.sendKeys(phoneNumber);
        Thread.sleep(1000);
        System.out.println("✔ Business number entered");
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

        WebElement dropdownTrigger3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='headlessui-listbox-button-:r5:']")));
        dropdownTrigger3.click();
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

        logger.info("Closing  overlay popup");
        test.info("Closing  overlay popup");
        WebElement iframe = driver.findElement(By.xpath("//*[contains(@id,'produktly-checklist-beacon-iframe')]"));
        driver.switchTo().frame(iframe);
        WebElement closeBtn = driver.findElement(By.xpath("//button[@aria-label='Close checklist beacon']//*[name()='svg']"));
        closeBtn.click();
        driver.switchTo().defaultContent();
        System.out.println("✔ Overlay Popup closed");
        test.pass(" Overlay Popup closed");

    }

    @Test(priority = 7)
    public void Purchase_Plan() throws IOException, InterruptedException {
        logger.info("*****Starting Purchase_Plan*****");
        test.info("Purchase_Plan started");
        System.out.println("Starting Purchase_Plan");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Thread.sleep(3000);
        logger.info("Waiting for 'Get Started' button");
        test.info("Waiting for 'Get Started' button");

        WebElement clickongetstarted = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body//div//div[@component='layout']//div//div//div//div//div//div[1]//div[1]//div[3]//button[1]")));
        clickongetstarted.click();
        logger.info("'Get Started' button clicked");
        test.pass("'Get Started' button clicked");
        System.out.println("'Get Started' button clicked");

        WebElement clickonpaynow = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='flex gap-2 items-center']//button[1]")));
        clickonpaynow.click();
        logger.info("'Pay Now' button clicked");
        test.pass("'Pay Now' button clicked");
        System.out.println("'Pay Now' button clicked");

        WebElement clickonstripe = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[@class='text-[#2b2b2b] text-lg font-semibold mb-1']")));
        clickonstripe.click();
        logger.info("'Stripe' payment option selected");
        test.pass("'Stripe' payment option selected");
        System.out.println("'Stripe' payment option selected");

        WebElement entercardnumber = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='cardNumber']")));
        entercardnumber.sendKeys("4242424242424242");
        logger.info("Card number entered");
        test.info("Card number entered");
        System.out.println("Card number entered");

        WebElement entercardexpiry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='cardExpiry']")));
        entercardexpiry.sendKeys("0229");
        logger.info("Card expiry entered");
        test.info("Card expiry entered");
        System.out.println("Card expiry entered");

        WebElement entercvv = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='cardCvc']")));
        entercvv.sendKeys("123");
        logger.info("Card CVV entered");
        test.info("Card CVV entered");
        System.out.println("Card CVV entered");

        WebElement Entername = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='billingName']")));
        Entername.sendKeys("Pankaj Sharma");
        logger.info("Billing name entered");
        test.info("Billing name entered");
        System.out.println("Billing name entered");

        logger.info("Scrolling to submit button");
        test.info("Scrolling to submit button");
        System.out.println("Scrolling to submit button");

        WebElement element = driver.findElement(By.xpath("//div[@class='SubmitButton-IconContainer']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        Thread.sleep(1000);

        logger.info("Clicking submit button");
        test.info("Clicking submit button");
        WebElement clicksubmit = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='SubmitButton-IconContainer']")));
        clicksubmit.click();

        String currentURL = driver.getCurrentUrl();
        System.out.println("URL after purchase: " + currentURL);
        logger.info("URL after plan purchase: " + currentURL);
        test.pass("URL after plan purchase: " + currentURL);

        String expectedSubstring = "purchase_plan=true";
        Assert.assertTrue(currentURL.contains(expectedSubstring), "Current URL does not contain expected text: " + expectedSubstring);
        logger.info("URL assertion passed");
        test.pass("URL contains expected substring: " + expectedSubstring);
        System.out.println("Assertion passed: URL contains '" + expectedSubstring + "'");
        logger.info("*****Plan Purchased*****");
        test.info("Plan Purchased");
    }

    @AfterTest
    public void QuitBrowser() throws IOException, InterruptedException {
        Thread.sleep(3000);
        logger.info("Loaded data in Extent report");
        test.info("Loaded data in Extent report");
        System.out.println("Loaded data in Extent report...");
        extent.flush();
        if (driver != null) {
            logger.info("Closing the browser");
            test.info("Closing the browser");
            System.out.println("Closing the browser...");
            driver.quit();
        } else {
            logger.warn("Driver was null, browser not closed");
            test.warning("Driver was null, browser not closed");
            System.out.println("Driver is null — nothing to quit.");
        }

        logger.info("Browser quit process completed");
        test.pass("Browser quit process completed");
        System.out.println("Browser quit process completed.");
    }


}







