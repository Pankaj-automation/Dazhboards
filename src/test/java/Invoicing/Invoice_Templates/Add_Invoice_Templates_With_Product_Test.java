package Invoicing.Invoice_Templates;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.AllureEnvWriter;
import utilities.Logs;
import utilities.Reuseable;
import utilities.StartupCode;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static utilities.Screenshot.tearDown1;

@Epic("Invoice Templates")
@Feature("Add Invoice Templates With Product Test")
public class Add_Invoice_Templates_With_Product_Test extends StartupCode {
    WebDriverWait wait;

    @BeforeClass
    @Step("Step 1: Launch browser")
    public void setup() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Logs.info(test, "🚀 Started Add Invoice Templates With Product Test");
    }

    @Test(priority = 1, description = "Step 1: Login with user")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 1: Login with valid user credentials")
    @Description("Login with user and navigate to the Invoice Templates")
    public void Login() throws InterruptedException {
        Logs.info(test, "Entering email & password");

        driver.findElement(By.xpath("//input[@placeholder='Email address']")).sendKeys("pnkj@yopmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Admine51%%!");
        Thread.sleep(2000);
        Logs.info(test, "Clicking Sign In button");

        WebElement signInButton = driver.findElement(By.xpath("//button[normalize-space()='Sign in']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signInButton);
        signInButton.click();
        Logs.pass(driver, test, "✅ User logged in successfully");

    }

    @Test(priority = 2, description = "Step 2: Navigate to Invoice Template Page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 2: Navigate to Invoice Template Page")
    @Description("After login, navigate to the Invoice Templates Page")
    public void Navigate_to_Invoice_Template() throws InterruptedException {
        Thread.sleep(3000);
        Logs.info(test, "Scrolling to Invoicing menu");

        WebElement scrolltoIT = driver.findElement(By.xpath("//span[contains(text(),'Invoicing')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", scrolltoIT);
        scrolltoIT.click();
        Logs.info(test, "Clicking Invoice Templates link");

        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[normalize-space()='Invoice Templates']")).click();
        Logs.pass(driver, test, "✅ Navigated to Invoice Templates Page");

    }

    @Test(priority = 3, description = "Step 3: Fill basic invoice template details")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 3: Enter Invoice Template details")
    @Description("Verify that the admin is able to add an invoice template")
    public void Add_Invoice_Template() throws InterruptedException {
        Thread.sleep(2000);
        Logs.info(test, "Clicking Add Invoice Template button");

        driver.findElement(By.xpath("//a[normalize-space()='Add Invoice Template']")).click();
        try {
            ((JavascriptExecutor) driver).executeScript("let iframe = document.getElementById('produktly-checklist-beacon-iframe505');" + "if(iframe) iframe.style.display='none';");
            Logs.info(test, "iframe hidden successfully");
        } catch (Exception e) {
            Logs.warn(driver, test, "Could not hide iframe: " + e.getMessage());
        }
        Logs.info(test, "Selecting template and filling details");

        driver.findElement(By.xpath("//input[@value='right']")).click();
        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='showAddress']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='showEmail']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='showPhone']")).click();
        Thread.sleep(1000);
        WebElement scrolltoIT2 = driver.findElement(By.xpath("//label[normalize-space()='Yes']//input[@name='isGst']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(scrolltoIT2).perform();
        driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys(firstName);
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//input[contains(@type,'text')])[4]")).sendKeys(lastName);
        Thread.sleep(1000);
        WebElement scrolltoIT = driver.findElement(By.xpath("//h6[normalize-space()='Footer Notes']"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(scrolltoIT).perform();
        driver.findElement(By.xpath("(//input[@type='number'])[1]")).sendKeys(TwoDigitNumber);
        Thread.sleep(1000);
        Logs.pass(driver, test, "✅ Invoice Template Basic Details Saved");

        WebElement scrolltoIT23 = driver.findElement(By.xpath("//button[normalize-space()='Save & Next']"));
        Actions actions23 = new Actions(driver);
        actions23.moveToElement(scrolltoIT23).perform();
        scrolltoIT23.click();

    }

    @Test(priority = 4, description = "Step 4: Selected Product scenario")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 4: Attempt to save with selecting a product")
    @Description("Check the response when admin clicks save with selecting a product")
    public void With_selected_Product() throws InterruptedException {
        Thread.sleep(3000);
        Logs.info(test, "Selecting 'Uncategorized' product group");

        WebElement clickyes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Uncategorized']")));
        clickyes.click();
        Thread.sleep(2000);
        Logs.info(test, "Searching and selecting product");

        driver.findElement(By.xpath("(//input[@placeholder='Search by Product Name'])[1]")).sendKeys("day");
        Thread.sleep(3000);
       // driver.findElement(By.xpath("(//input[contains(@type,'checkbox')])[4]")).click();
        List<WebElement> checkboxes = driver.findElements(By.xpath("//table//input[@type='checkbox']"));

        if (checkboxes.size() > 0) {
            Logs.info(test, "Found " + checkboxes.size() + " checkbox(es) in the product list.");
            int selectedCount = 0;

            for (WebElement checkbox : checkboxes) {
                if (!checkbox.isSelected()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
                    checkbox.click();
                    selectedCount++;
                    Logs.info(test, "Checkbox selected. Total selected: " + selectedCount);
                }
                if (selectedCount == 2) {
                    break; // Stop after selecting 2 checkboxes
                }
            }
        } else {
            Logs.warn(driver, test, "No checkboxes found in the product list.");
        }
        WebElement scrolltoIT213 = driver.findElement(By.xpath("//button[normalize-space()='Save & Next']"));
        Actions actions233 = new Actions(driver);
        actions233.moveToElement(scrolltoIT213).perform();
        scrolltoIT213.click();
        Logs.pass(driver, test, "Product selected and saved successfully");

    }

    @Test(priority = 5, description = "Step 5: Fill Bank Details")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 5: Add Bank Details")
    @Description("Verify that admin can fill bank details correctly")
    public void Fill_bank_details() throws InterruptedException {
        Thread.sleep(1000);
        Logs.info(test, "Filling bank details fields");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[1]"))).sendKeys("ICICI");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[2]"))).sendKeys("Mohali");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[3]"))).sendKeys("Pnkj");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[4]"))).sendKeys("123456");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[5]"))).sendKeys("4111111111111111");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[6]"))).sendKeys("AA12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[7]"))).sendKeys("SMCOGB2LXXX");
        Logs.pass(driver, test, "✅ Bank Details Saved Successfully");
        WebElement scrolltoIT32 = driver.findElement(By.xpath("(//button[normalize-space()='Save & Next'])[1]"));
        Actions actions32 = new Actions(driver);
        actions32.moveToElement(scrolltoIT32).perform();
        scrolltoIT32.click();
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