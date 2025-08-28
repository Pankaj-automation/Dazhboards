/*
package Invoicing.Invoice_Templates;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.AllureEnvWriter;
import utilities.Logs;
import utilities.StartupCode;

@Epic("Login")
@Feature("Add_Invoice_Templates_Test")
public class Add_Invoice_Templates_Test extends StartupCode {

    @BeforeClass
    @Step("Launch browser")
    public void setup() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        Logs.info(test, "Started Add_Invoice_Templates_Test");

    }

    @Test(priority = 1, description = "Login with user")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login with user")
    @Description("Login with user and go to the Invoice Templates")
    public void Login() throws InterruptedException {
        driver.findElement(By.xpath("//input[@placeholder='Email address']")).sendKeys("pnkj@yopmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Admine51%%!");
        Thread.sleep(2000);
        WebElement signInButton = driver.findElement(By.xpath("//button[normalize-space()='Sign in']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signInButton);
        signInButton.click();

        Thread.sleep(3000);
        WebElement scrolltoIT = driver.findElement(By.xpath("//span[contains(text(),'Invoicing')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", scrolltoIT);
        scrolltoIT.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[normalize-space()='Invoice Templates']")).click();


    }

    @Test(priority = 2, description = "Password Blank and invalid email inputs")
    @Severity(SeverityLevel.CRITICAL)
    @Story("2_Add Invoice Template")
    @Description("Verify that the admin should able to add invoice template")
    public void Add_Invoice_Template() throws InterruptedException {
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[normalize-space()='Add Invoice Template']")).click();


        try {
            ((JavascriptExecutor) driver).executeScript("let iframe = document.getElementById('produktly-checklist-beacon-iframe505');" + "if(iframe) iframe.style.display='none';");
            Logs.info(test, "iframe hidden successfully");
        } catch (Exception e) {
            Logs.warn(driver, test, "Could not hide iframe: " + e.getMessage());
        }

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

        driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys("Pnkj");
        Thread.sleep(1000);

        driver.findElement(By.xpath("(//input[contains(@type,'text')])[4]")).sendKeys("Pnkj");
        Thread.sleep(1000);
        WebElement scrolltoIT = driver.findElement(By.xpath("//h6[normalize-space()='Footer Notes']"));

        Actions actions1 = new Actions(driver);
        actions1.moveToElement(scrolltoIT).perform();
        driver.findElement(By.xpath("(//input[@type='number'])[1]")).sendKeys("12");

        WebElement scrolltoIT23 = driver.findElement(By.xpath("//button[normalize-space()='Save & Next']"));

        Actions actions23 = new Actions(driver);
        actions23.moveToElement(scrolltoIT23).perform();
        scrolltoIT23.click();
        
    }


}
*/
