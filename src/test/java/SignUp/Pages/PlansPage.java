package SignUp.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.Logs;

import java.time.Duration;

import static utilities.StartupCode.test;

public class PlansPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    private By getStartedBtn = By.xpath("(//button[contains(text(),'Get Started')])[1]");
    private By months = By.xpath("//a[normalize-space()='Quarterly']");


    public PlansPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void clickonmonths() throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(months));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            wait.until(ExpectedConditions.elementToBeClickable(months)).click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Dropdown option 'Quarterly' not found or visible. XPath might be incorrect or element not loaded.");
        }
    }

    public void clickGetStarted() throws InterruptedException {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(getStartedBtn));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(getStartedBtn)).click();
        Logs.info(test, "Get Started Button CLicked");
    }

    public boolean verifyRedirection(String expectedPartialURL) {
        return driver.getCurrentUrl().contains(expectedPartialURL);
    }

    public void ScrolltoGetStarted() throws InterruptedException {

        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(getStartedBtn));
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", ele);

    }

    public void Buyingaplan() throws InterruptedException {
        WebElement clickonpaynow = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='flex gap-2 items-center']//button[1]")));
        clickonpaynow.click();
        Logs.info(test, "'Pay Now' button clicked");
        WebElement clickonstripe = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[@class='text-[#2b2b2b] text-lg font-semibold mb-1']")));
        clickonstripe.click();
        Logs.info(test, "'Stripe' payment option selected");
        WebElement entercardnumber = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='cardNumber']")));
        entercardnumber.sendKeys("4242424242424242");
        Logs.info(test, "Card number entered");
        WebElement entercardexpiry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='cardExpiry']")));
        entercardexpiry.sendKeys("0229");
        Logs.info(test, "Card expiry entered");
        WebElement entercvv = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='cardCvc']")));
        entercvv.sendKeys("123");
        Logs.info(test, "Card CVV entered");
        WebElement Entername = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='billingName']")));
        Entername.sendKeys("Pankaj Sharma");
        Logs.pass(driver, test, "Billing name entered");
        WebElement element = driver.findElement(By.xpath("//div[@class='SubmitButton-IconContainer']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        Thread.sleep(1000);
        Logs.info(test, "Clicking submit button");
        WebElement clicksubmit = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='SubmitButton-IconContainer']")));
        clicksubmit.click();
        String currentURL = driver.getCurrentUrl();
        Logs.info(test, "URL after plan purchase: " + currentURL);
        String expectedSubstring = "plans";
        Assert.assertTrue(currentURL.contains(expectedSubstring), "Current URL does not contain expected text: " + expectedSubstring);
        Logs.info(test, "URL assertion passed");
        test.pass("URL contains expected substring: " + expectedSubstring);
        System.out.println("Assertion passed: URL contains '" + expectedSubstring + "'");
        Logs.info(test, "*****Plan Purchased*****");
    }

}