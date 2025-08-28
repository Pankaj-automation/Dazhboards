package SignUp.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Logs;

import java.time.Duration;

import static utilities.StartupCode.test;


public class CompanyDetailsPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    private By companyNameInput = By.xpath("//input[@spellcheck='false']");
    private By productCategoryInput = By.xpath("//input[@id='productCat_input']");
    private By businessNumberInput = By.xpath("//div[4]/input");
    private By addressInput = By.id("address");
    private By websiteInput = By.xpath("//input[@placeholder=\"What’s your website address? \"]");
    private By contactPhoneInput = By.xpath("//input[@placeholder='Enter phone number']");
    private By perPaxCheckbox = By.name("showPax3");
    private By smsOption = By.xpath("//div[9]/div/label");
    private By contactFirstName = By.xpath("//div[7]//div[2]//div[1]/input[1]");
    private By contactPosition = By.xpath("//div[7]//div[2]//div[2]/input[1]");
    private By contactEmail = By.xpath("//div[7]//div[2]//div[4]/input[1]");
    private By countryDropdown = By.xpath("//div[contains(@role,'button')]");
    private By countrySearch = By.xpath("//input[@placeholder='search']");
    private By countryPhone = By.xpath("//input[@value='+91']");
    private By addContactPersonBtn = By.xpath("//button[normalize-space()='Add Contact Person']");
    private By deleteContactPersonIcon = By.xpath("//div[contains(@title,'Delete')]");
    private By uploadLogoInput = By.xpath("//input[@type='file']");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By ScrolltoPax = By.xpath("//label[normalize-space()='Per Pax']");
    private By Scrolltoemail = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[7]/div[1]/div[2]/div[5]/div[1]/input[1]");

    public CompanyDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void fillCompanyName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(companyNameInput)).sendKeys(name);
    }

    public void selectProductCategory(String category) throws InterruptedException {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(productCategoryInput));
        dropdown.click();
        Thread.sleep(500);
        dropdown.sendKeys(category);
        dropdown.sendKeys(Keys.ARROW_DOWN);
        dropdown.sendKeys(Keys.ENTER);
        Logs.info(test, "Product Category Selected");
    }

    public void enterBusinessNumber(String number) {
        wait.until(ExpectedConditions.elementToBeClickable(businessNumberInput)).sendKeys(number);
        Logs.info(test, "Business Number Entered");

    }

    public void selectAddress(String address) throws InterruptedException {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(addressInput));
        input.sendKeys(address);
        Thread.sleep(2000);
        input.sendKeys(Keys.ARROW_DOWN);
        input.sendKeys(Keys.ENTER);
        Logs.info(test, "Address Selected");

    }

    public void enterWebsite(String website) {
        wait.until(ExpectedConditions.elementToBeClickable(websiteInput)).sendKeys(website);
        Logs.pass(driver, test, "Website Entered");

    }

    public void enterPhone(String phone) {
        wait.until(ExpectedConditions.elementToBeClickable(contactPhoneInput)).sendKeys(phone);
        Logs.info(test, "Phone Number Entered");

    }

    public void selectPerPaxCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(perPaxCheckbox)).click();
        Logs.info(test, "Per Pax Checkbox Marked");

    }

    public void selectSmsOption() {
        WebElement sms = wait.until(ExpectedConditions.elementToBeClickable(smsOption));
        js.executeScript("arguments[0].click();", sms);
        Logs.pass(driver, test, "SMS option Selected");
    }

    public void fillContactDetails(String name, String position, String email, String phone) throws InterruptedException {

        driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[7]/div[1]/div[2]/div[1]/input[1]")).sendKeys(name);
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[7]/div[1]/div[2]/div[2]/input[1]")).sendKeys("Quality Engineer");
        Logs.info(test, "✔ Contact person info filled");
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
        Logs.info(test, "Dropdown Option selected");
        driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[2]/form[1]/div[1]/div[7]/div[1]/div[2]/div[4]/input[1]")).sendKeys(email);
        Thread.sleep(1000);
        WebElement dropdownTrigger2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'gap-y-4 gap-x-5 grid grid-cols-2 mb-6')]//div//div[contains(@role,'button')]")));
        dropdownTrigger2.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='search']")).sendKeys("india");
        dropdownTrigger2.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        dropdownTrigger2.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@value='+91']")).sendKeys(phone);
        Logs.info(test, "✔ Country and phone number entered");


    }


    public void addContactPerson() {
        driver.findElement(addContactPersonBtn).click();
        Logs.info(test, "✔ New Contact Person Added");

    }

    public void deleteContactPerson() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteContactPersonIcon)).click();
        Logs.info(test, "✔ New Contact Person Deleted");

    }

    public void uploadLogo(String logoPath) {
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(uploadLogoInput));
        fileInput.sendKeys(logoPath);
        Logs.pass(driver, test, "✔ Company Logo Uploaded");

    }

    public void clickSaveButton() throws InterruptedException {
        Thread.sleep(3000);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        js.executeScript("arguments[0].click();", btn);
    }

    public void scrollToContactEmailInput() throws InterruptedException {
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(Scrolltoemail));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", ele);
        Thread.sleep(1000); // Optional delay if needed for animation completion
    }

    public void scrollToPerPaxCheckbox() throws InterruptedException {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(ScrolltoPax));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        Thread.sleep(1000);
    }

    public void selectdateformat() throws InterruptedException {

        WebElement dropdownTrigger3 = wait.until(ExpectedConditions.elementToBeClickable(By.id("headlessui-listbox-button-:r5:")));
        try {
            dropdownTrigger3.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownTrigger3);
        }
        Thread.sleep(1000);
        Actions actions = new Actions(driver);


        actions.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        Logs.info(test, "✔ Date format selected");

    }
}
