package Invoicing.Invoice_Templates;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import utilities.Logs;
import utilities.StartupCode;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static utilities.Screenshot.tearDown1;

@Epic("Invoice Templates")
@Feature("Validation on Invoice Template")
public class Validation_on_Invoice_Template_Test extends StartupCode {
    WebDriverWait wait;

    @BeforeClass
    @Step("Step 1: Launch browser")
    public void setup() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Logs.info(test, "Started Validation on Invoice Template Test");
    }

    @Test(priority = 1, description = "Step 1: Login with user")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 1: Login with valid user credentials")
    @Description("Login with user and navigate to the Invoice Templates")
    public void Login() throws InterruptedException {
        Logs.info(test, "Enter login credentials");
        driver.findElement(By.xpath("//input[@placeholder='Email address']")).sendKeys("pnkj@yopmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Admine51%%!");
        Thread.sleep(2000);
        WebElement signInButton = driver.findElement(By.xpath("//button[normalize-space()='Sign in']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signInButton);
        signInButton.click();
        Logs.info(test, "Clicked on Sign in Button");
        Logs.info(test, "User logged in successfully");

    }

    @Test(priority = 2, description = "Step 2: Navigate to Invoice Template Page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 2: Navigate to Invoice Template Page")
    @Description("After login, navigate to the Invoice Templates Page")
    public void Navigate_to_Invoice_Template() throws InterruptedException {
        Logs.info(test, "Navigating to Invoicing menu");
        Thread.sleep(3000);
        try {
            ((JavascriptExecutor) driver).executeScript("let iframe = document.getElementById('produktly-checklist-beacon-iframe505');" + "if(iframe) iframe.style.display='none';");
            Logs.info(test, "iframe hidden successfully");
        } catch (Exception e) {
            Logs.warn(driver, test, "Could not hide iframe: " + e.getMessage());
        }
        WebElement scrolltoIT = driver.findElement(By.xpath("//span[contains(text(),'Invoicing')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", scrolltoIT);
        scrolltoIT.click();
        Logs.info(test, "Clicked on Invoicing menu");

        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[normalize-space()='Invoice Templates']")).click();
        Logs.info(test, "Navigated to Invoice Templates Page");

    }

    @Test(priority = 3, description = "Step 3: Navigate to Add Invoice Template Page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 3: Navigate to Add Invoice Template Page")
    @Description("After login, navigate to the Add Invoice Templates Page and then clicked on back button")
    public void Template_creation_cancelled() throws InterruptedException {
        Logs.info(test, "Navigating to Invoicing menu");
        driver.findElement(By.xpath("//a[normalize-space()='Add Invoice Template']")).click();
        try {
            ((JavascriptExecutor) driver).executeScript("let iframe = document.getElementById('produktly-checklist-beacon-iframe505');" + "if(iframe) iframe.style.display='none';");
            Logs.info(test, "iframe hidden successfully");
        } catch (Exception e) {
            Logs.warn(driver, test, "Could not hide iframe: " + e.getMessage());
        }
        driver.findElement(By.xpath("//input[@value='right']")).click();
        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='showAddress']")).click();
        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='showEmail']")).click();
        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='showPhone']")).click();
        Logs.info(test, "✅ Basic toggle fields filled");
        WebElement scrolltoIT2 = driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='isGst']"));
        new Actions(driver).moveToElement(scrolltoIT2).perform();
        Logs.info(test, "Scrolled to GST field");
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys(firstName);
        driver.findElement(By.xpath("(//input[contains(@type,'text')])[4]")).sendKeys(lastName);
        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='isGst']")).click();
        Thread.sleep(2000);
        WebElement gstField3 = driver.findElement(By.xpath("(//input[@type='number'])[1]"));
        gstField3.sendKeys("13");
        Logs.info(test, "✅ Filled Internal, External, GST fields");
        WebElement backBtn = driver.findElement(By.xpath("//button[normalize-space()='Back']"));
        new Actions(driver).moveToElement(backBtn).perform();
        backBtn.click();
        Logs.info(test, "🔙 Clicked Back, navigated to Invoice Templates Page");
        Thread.sleep(1500);
        driver.findElement(By.xpath("//a[normalize-space()='Add Invoice Template']")).click();
        Logs.info(test, "🔄 Reopened Add Invoice Template Page");
        Thread.sleep(2000);
        String internalName = driver.findElement(By.xpath("(//input[@type='text'])[1]")).getAttribute("value");
        String externalName = driver.findElement(By.xpath("(//input[@type='text'])[2]")).getAttribute("value");
        String gstValue = driver.findElement(By.xpath("(//input[@type='number'])[1]")).getAttribute("value");
        Thread.sleep(2000);
        if (internalName.isEmpty() && externalName.isEmpty() && gstValue.isEmpty()) {
            Logs.info(test, "✅ Fresh start verified: All fields are empty after reopening Add Invoice Template.");
        } else {
            Logs.warn(driver, test,
                    "❌ Fields are not reset! Internal='" + internalName + "', External='" + externalName + "', GST='" + gstValue + "'");
        }
        Assert.assertTrue(internalName.isEmpty(), "❌ Internal Name should be empty on fresh start!");
        Assert.assertTrue(externalName.isEmpty(), "❌ External Name should be empty on fresh start!");
        Assert.assertTrue(gstValue.isEmpty(), "❌ GST field should be empty on fresh start!");
    }


    @Test(priority = 4, description = "Step 4: Fill basic invoice template details")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 4: Enter Invoice Template details")
    @Description("Verify that the admin is able to add an invoice template")
    public void Add_Invoice_Template() throws InterruptedException {
        Thread.sleep(2000);
        Logs.info(test, "Clicking on 'Add Invoice Template' link");

        try {
            ((JavascriptExecutor) driver).executeScript("let iframe = document.getElementById('produktly-checklist-beacon-iframe505');" + "if(iframe) iframe.style.display='none';");
            Logs.info(test, "iframe hidden successfully");
        } catch (Exception e) {
            Logs.warn(driver, test, "Could not hide iframe: " + e.getMessage());
        }


        driver.findElement(By.xpath("//input[@value='right']")).click();
        Logs.info(test, "Enabling 'Show Address', 'Show Email', 'Show Phone'");

        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='showAddress']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='showEmail']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='showPhone']")).click();
        Thread.sleep(1000);

        WebElement scrolltoIT2 = driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='isGst']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(scrolltoIT2).perform();
        Logs.info(test, "Scrolled to GST field");

        By saveBtnLocator = By.xpath("//button[normalize-space()='Save & Next']");
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
        Actions actions2 = new Actions(driver);
        actions2.moveToElement(saveBtn).perform();
        saveBtn.click();
        Logs.info(test, "Clicked Save & Next to proceed");

        Thread.sleep(1500);
        WebElement internalNameField = driver.findElement(By.xpath("(//input[@type='text'])[3]"));
        if (internalNameField.isDisplayed() && internalNameField.getAttribute("value").isEmpty()) {
            Logs.info(test, "Internal Name is empty, filling value "+firstName);
            internalNameField.sendKeys(firstName);
            WebElement saveBtn1 = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
            Actions actions21 = new Actions(driver);
            actions21.moveToElement(saveBtn1).perform();
            saveBtn1.click();
            Logs.info(test, "Clicked Save & Next after filling Internal Name");

            Thread.sleep(1500);
        }
        WebElement externalNameField = driver.findElement(By.xpath("(//input[contains(@type,'text')])[4]"));
        if (externalNameField.isDisplayed() && externalNameField.getAttribute("value").isEmpty()) {
            Logs.info(test, "External Name is empty, filling value "+lastName);
            externalNameField.sendKeys(lastName);
            WebElement saveBtn12 = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
            Actions actions212 = new Actions(driver);
            actions212.moveToElement(saveBtn12).perform();
            saveBtn12.click();
            Logs.info(test, "Clicked Save & Next after filling External Name");

            Thread.sleep(1500);
        }
        WebElement gstField3 = driver.findElement(By.xpath("(//input[@type='number'])[1]"));
        Thread.sleep(1500);
        Logs.info(test, "Filling GST field with 'Yes' option");

        driver.findElement(By.xpath("(//label[contains(text(),'Yes')])[4]")).click();
        gstField3.clear();
        gstField3.sendKeys("13");
        Thread.sleep(1500);
        String valueYes = gstField3.getAttribute("value");
        if (valueYes.equals("13")) {
            Logs.info(test, "✅ GST field accepts input when 'Yes' is selected. Value: " + valueYes);
        } else {
            Logs.info(test, "❌ GST field did not accept input with 'Yes'. Value: " + valueYes);
        }
        Thread.sleep(1500);

        driver.findElement(By.xpath("(//label[contains(text(),'No')])[5]")).click();
        gstField3.clear();
        gstField3.sendKeys("15");

        Thread.sleep(1500);

        String valueNo = gstField3.getAttribute("value");
        if (valueNo.equals("15")) {
            Logs.info(test, "✅ GST field accepts input when 'No' is selected. Value: " + valueNo);
        } else {
            Logs.warn(driver, test, "❌ GST field did not accept input with 'No'. Value: " + valueNo);
        }
        Thread.sleep(1500);

        Assert.assertEquals(valueYes, "13", "GST field failed for Yes option!");
        Assert.assertEquals(valueNo, "15", "GST field failed for No option!");

        WebElement gstField = driver.findElement(By.xpath("(//input[@type='number'])[1]"));
        gstField.clear();
        gstField.sendKeys("abc@@");
        String actualValue = gstField.getAttribute("value");
        if (actualValue.isEmpty()) {
            Logs.info(test, "✅ GST field correctly rejected alphabetic/special characters. Value after typing 'abc@@': '" + actualValue + "'");
        } else {
            Logs.warn(driver, test, "❌ GST field accepted invalid input! Value: '" + actualValue + "'");
        }

        WebElement gstField1 = driver.findElement(By.xpath("(//input[@type='number'])[1]"));
        if (gstField1.isDisplayed() && gstField1.getAttribute("value").isEmpty()) {
            gstField1.sendKeys(TwoDigitNumber);
            String numericValue = gstField1.getAttribute("value");

            if (numericValue.matches("\\d+")) {
                Logs.info(test, "✅ GST field only accepts numeric values. Current value: " + numericValue);
            } else {
                Logs.warn(driver, test, "❌ GST field accepted non-numeric input! Value: " + numericValue);
            }
            WebElement saveBtn13 = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
            Actions actions213 = new Actions(driver);
            actions213.moveToElement(saveBtn13).perform();
            saveBtn13.click();
            Thread.sleep(1500);

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(saveBtnLocator));
                Logs.info(test, "✅ Confirmed: page moved to next step.");
            } catch (Exception e) {
                Logs.warn(driver, test, "❌ Validation failed: still stuck on same page.");
            }
            Thread.sleep(1500);
        }
        driver.findElement(By.xpath("//button[normalize-space()='Back']")).click();
        Logs.info(test, "Navigated Back to verify persisted data");

        String expectedValue1 = firstName;
        String actualValue1 = driver.findElement(By.xpath("(//input[contains(@type,'text')])[3]")).getAttribute("value");
        Logs.info(test, "Internal Name: Expected=" + expectedValue1 + ", Actual=" + actualValue1);
        Assert.assertEquals(actualValue1, expectedValue1, "❌ Internal Name not saved! Expected: " + expectedValue1 + " but found: " + actualValue1);
        String expectedValue12 = lastName;
        String actualValue12 = driver.findElement(By.xpath("(//input[contains(@type,'text')])[4]")).getAttribute("value");
        Logs.info(test, "External Name: Expected=" + expectedValue12 + ", Actual=" + actualValue12);

        Assert.assertEquals(actualValue12, expectedValue12, "❌ External Name not saved! Expected: " + expectedValue12 + " but found: " + actualValue12);
        String expectedValue121 = TwoDigitNumber;
        String actualValue121 = driver.findElement(By.xpath("(//input[@type='number'])[1]")).getAttribute("value");
        Logs.info(test, "GST Value: Expected=" + expectedValue121 + ", Actual=" + actualValue121);

        Assert.assertEquals(actualValue121, expectedValue121, "❌ ❌ GST field not saved! Expected: " + expectedValue121 + " but found: " + actualValue121);

        Logs.info(test, "✅ Step 1 data persisted successfully after navigating away and back.");
        WebElement saveBtn13 = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
        Actions actions213 = new Actions(driver);
        actions213.moveToElement(saveBtn13).perform();
        saveBtn13.click();

        Logs.info(test, "Clicked Save & Next to continue after data persisted");
        Thread.sleep(1500);

    }


    @Test(priority = 5, description = "Step 5: Not Selected Product scenario")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 5: Attempt to save without selecting a product")
    @Description("Check the response when admin clicks save without selecting a product")
    public void Not_selected_Product() throws InterruptedException {
        Logs.info(test, "Clicking on 'Uncategorized' category");

        Thread.sleep(3000);
        WebElement clickyes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Uncategorized']")));
        clickyes.click();
        Thread.sleep(2000);
        Logs.info(test, "Searching for product 'day'");

        driver.findElement(By.xpath("(//input[@placeholder='Search by Product Name'])[1]")).sendKeys("day");
        Thread.sleep(3000);
        Logs.info(test, "Selecting checkboxes for products");

        //driver.findElement(By.xpath("(//input[contains(@type,'checkbox')])[4]")).click();
       // driver.findElement(By.xpath("(//input[contains(@type,'checkbox')])[5]")).click();
        List<WebElement> checkboxes = driver.findElements(By.xpath("//table//input[@type='checkbox']"));

        if (checkboxes.size() > 0) {
            Logs.info(test, "✅ Found " + checkboxes.size() + " checkbox(es) in the product list.");
            int selectedCount = 0;

            for (WebElement checkbox : checkboxes) {
                if (!checkbox.isSelected()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
                    checkbox.click();
                    selectedCount++;
                    Logs.info(test, "✅ Checkbox selected. Total selected: " + selectedCount);
                }
                if (selectedCount == 2) {
                    break; // Stop after selecting 2 checkboxes
                }
            }
        } else {
            Logs.warn(driver, test, "⚠ No checkboxes found in the product list.");
        }

        WebElement scrolltoIT213 = driver.findElement(By.xpath("//button[normalize-space()='Save & Next']"));
        Actions actions233 = new Actions(driver);
        actions233.moveToElement(scrolltoIT213).perform();
        scrolltoIT213.click();
        Logs.info(test, "Clicked Save & Next to go to Bank Details page");
    }

    @Test(priority = 6, description = "Step 6: Fill Bank Details")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 6: Add Bank Details")
    @Description("Verify that admin can fill bank details correctly")
    public void Fill_bank_details() throws InterruptedException {
        Thread.sleep(1000);


        By saveBtnLocator = By.xpath("(//button[normalize-space()='Save & Next'])[1]");
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
        Actions actions2 = new Actions(driver);
        actions2.moveToElement(saveBtn).perform();
        saveBtn.click();
        Thread.sleep(1500);
        WebElement BanknameField = driver.findElement(By.xpath("(//input[@type='text'])[1]"));
        if (BanknameField.isDisplayed() && BanknameField.getAttribute("value").isEmpty()) {
            String validationMessage = BanknameField.getAttribute("validationMessage");
            Logs.info(test, "Bank Name Validation Message: " + validationMessage);
            Logs.info(test, "Filling Bank Name with 'ICICI'");
            BanknameField.sendKeys("ICICI");
            By saveBtnLocator1 = By.xpath("//button[normalize-space()='Save & Next']");
            WebElement saveBtn1 = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
            Actions actions21 = new Actions(driver);
            actions21.moveToElement(saveBtn1).perform();
            saveBtn1.click();
            Thread.sleep(1500);
            Logs.info(test, "Clicked Save & Next after filling Bank Name");

        }
        WebElement BankaddressField = driver.findElement(By.xpath("(//input[@type='text'])[2]"));
        if (BankaddressField.isDisplayed() && BankaddressField.getAttribute("value").isEmpty()) {
            String validationMessage = BankaddressField.getAttribute("validationMessage");
            Logs.info(test, "Bank Address Validation Message: " + validationMessage);
            Logs.info(test, "Filling Bank Address with 'Mohali'");
            BankaddressField.sendKeys("Mohali");
            By saveBtnLocator1 = By.xpath("//button[normalize-space()='Save & Next']");
            WebElement saveBtn1 = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
            Actions actions21 = new Actions(driver);
            actions21.moveToElement(saveBtn1).perform();
            saveBtn1.click();
            Logs.info(test, "Clicked Save & Next after filling Bank Address");

            Thread.sleep(1500);
        }
        WebElement AccountNameField = driver.findElement(By.xpath("(//input[@type='text'])[3]"));
        if (AccountNameField.isDisplayed() && AccountNameField.getAttribute("value").isEmpty()) {
            String validationMessage = AccountNameField.getAttribute("validationMessage");
            Logs.info(test, "Account Name Validation Message: " + validationMessage);
            Logs.info(test, "Filling Account Name with 'Pnkj'");
            AccountNameField.sendKeys("Pnkj");
            By saveBtnLocator1 = By.xpath("//button[normalize-space()='Save & Next']");
            WebElement saveBtn1 = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
            Actions actions21 = new Actions(driver);
            actions21.moveToElement(saveBtn1).perform();
            saveBtn1.click();
            Logs.info(test, "Clicked Save & Next after filling Account Name");

            Thread.sleep(1500);
        }

        driver.findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys("we");
        Logs.info(test, "Entered invalid Branch BSB to trigger validation error");

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='text-red-600 text-xs mt-1'])[1]")));

        String alertText = errorMsg.getText();
        Logs.info(test, "Validation Message displayed: " + alertText);

        Assert.assertEquals(alertText, "It should be a 6-digit number (e.g., 123-456) or 6 digits without dashes (e.g., 123456).", "Validation message mismatch!");
        WebElement BranchBSBField = driver.findElement(By.xpath("(//input[@type='text'])[4]"));
        BranchBSBField.clear();
        if (BranchBSBField.isDisplayed() && BranchBSBField.getAttribute("value").isEmpty()) {
            String validationMessage = BranchBSBField.getAttribute("validationMessage");
            Logs.info(test, "Branch BSB Validation Message: " + validationMessage);
            Logs.info(test, "Filling Branch BSB with '123456'");
            BranchBSBField.sendKeys("123456");
            By saveBtnLocator1 = By.xpath("//button[normalize-space()='Save & Next']");
            WebElement saveBtn1 = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
            Actions actions21 = new Actions(driver);
            actions21.moveToElement(saveBtn1).perform();
            saveBtn1.click();
            Logs.info(test, "Clicked Save & Next after filling Branch BSB");

            Thread.sleep(1500);
        }
        WebElement AccountNumberField = driver.findElement(By.xpath("(//input[@type='text'])[5]"));
        if (AccountNumberField.isDisplayed() && AccountNumberField.getAttribute("value").isEmpty()) {
            String validationMessage = AccountNumberField.getAttribute("validationMessage");
            Logs.info(test, "Account Number Validation Message: " + validationMessage);
            Logs.info(test, "Filling Account Number with '4111111111111111'");
            AccountNumberField.sendKeys("4111111111111111");
            Thread.sleep(1500);
        }

        WebElement enterdata6 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[6]")));
        enterdata6.sendKeys("AA12345");
        Logs.info(test, "Filled additional field 6 with 'AA12345'");

        WebElement enterdata7 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[7]")));
        enterdata7.sendKeys("SMCOGB2LXXX");
        Logs.info(test, "Filled additional field 7 with 'SMCOGB2LXXX'");

        WebElement scrolltoIT32 = driver.findElement(By.xpath("(//button[normalize-space()='Save & Next'])[1]"));
        Actions actions32 = new Actions(driver);
        actions32.moveToElement(scrolltoIT32).perform();
        scrolltoIT32.click();
        Logs.info(test, "Clicked final Save & Next to complete Bank Details step");
        Thread.sleep(3000);

    }


    @AfterMethod
    public void takescreenshot(ITestResult result) throws IOException {
        tearDown1(result);
    }

    @AfterClass
    public void QuitBrowser() throws InterruptedException {
        quitDriver();
        finalizeReport();
        Logs.info(test, "ℹ Browser closed and report finalized");

    }
}