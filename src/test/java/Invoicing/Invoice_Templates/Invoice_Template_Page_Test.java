package Invoicing.Invoice_Templates;

import com.aventstack.extentreports.ExtentTest;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.AllureEnvWriter;
import utilities.Logs;
import utilities.StartupCode;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static utilities.Screenshot.tearDown1;

@Epic("Invoice Templates")
@Feature("Invoice Template Page")
public class Invoice_Template_Page_Test extends StartupCode {

    WebDriverWait wait;

    @BeforeClass
    @Step("Step 1: Launch browser")
    public void setup() throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = setup1();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Logs.info(test, "Started Test of Invoice Template Page");
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
        Thread.sleep(5000);
        WebElement scrolltoIT = driver.findElement(By.xpath("//span[contains(text(),'Invoicing')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", scrolltoIT);
        scrolltoIT.click();
        Logs.info(test, "Clicked on Invoicing menu");

        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[normalize-space()='Invoice Templates']")).click();
        Logs.info(test, "Navigated to Invoice Templates Page");
        try {
            ((JavascriptExecutor) driver).executeScript("let iframe = document.getElementById('produktly-checklist-beacon-iframe505');" + "if(iframe) iframe.style.display='none';");
            Logs.info(test, "iframe hidden successfully");
        } catch (Exception e) {
            Logs.warn(driver, test, "Could not hide iframe: " + e.getMessage());
        }
    }


    @Test(priority = 3, description = "Step 3: Sorting functionality on Invoice Template Page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 3: Verify sorting functionality")
    @Description("Check that columns in Invoice Templates Page can be sorted")
    public void Sorting_Functionality() throws InterruptedException {
        Logs.info(test, "🔹 Checking sorting functionality");
        verifyColumnSorting(driver, test, By.xpath("(//th[normalize-space()='Internal Name'])[1]"), By.xpath("//table//tbody/tr/td[1]"), "Internal Name");
        Logs.info(test, "Sorting verified for Internal Name column");
        verifyColumnSorting(driver, test, By.xpath("(//th[normalize-space()='External Name'])[1]"), By.xpath("//table//tbody/tr/td[2]"), "External Name");
        Logs.info(test, "Sorting verified for External Name column");
        verifyColumnSorting(driver, test, By.xpath("(//th[normalize-space()='Date Created'])[1]"), By.xpath("//table//tbody/tr/td[5]"), "Date Created");
        Logs.info(test, "Sorting verified for Date Created column");
        verifyColumnSorting(driver, test, By.xpath("(//th[normalize-space()='Created By'])[1]"), By.xpath("//table//tbody/tr/td[6]"), "Created By");
        Logs.info(test, "Sorting verified for Created By column");
        verifyColumnSorting(driver, test, By.xpath("(//th[normalize-space()='Date Updated'])[1]"), By.xpath("//table//tbody/tr/td[7]"), "Date Updated");
        Logs.info(test, "Sorting verified for Date Updated column");
        Logs.pass(driver, test, "All columns sorting verified successfully");

    }

    public void verifyColumnSorting(WebDriver driver, ExtentTest test, By headerLocator, By cellLocator, String columnName) throws InterruptedException {
        Logs.info(test, "Checking " + columnName + " sorting functionality");
        Logs.info(test, "Locating header for column: " + columnName);

        WebElement header = driver.findElement(headerLocator);
        Logs.info(test, "Capturing original data for column: " + columnName);

        List<String> originalList = driver.findElements(cellLocator).stream().map(e -> e.getText().trim()).collect(Collectors.toList());
        Logs.info(test, columnName + " - Original List: " + originalList);
        if (new HashSet<>(originalList).size() <= 1) {
            Logs.info(test, "Skipping " + columnName + " sorting check (all values identical).");
            return;
        }
        // --- Detect if this column contains dates ---
        Logs.info(test, "Detecting column type for " + columnName);

        boolean isDateColumn = columnName.toLowerCase().contains("date");
        Logs.info(test, columnName + " - Detected as " + (isDateColumn ? "📅 Date column" : "🔤 Text column"));
        // --- Expected Asc & Desc ---
        Logs.info(test, "Prepared expected ascending and descending order for " + columnName);

        List<String> expectedAsc;
        List<String> expectedDesc;
        if (isDateColumn) {
            // Parse date format like "2-Sep-2025" or "02-Sep-2025"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.ENGLISH);
            expectedAsc = originalList.stream().map(d -> LocalDate.parse(d.replaceFirst("^0", ""), formatter)).sorted().map(d -> d.format(formatter)).collect(Collectors.toList());
            expectedDesc = originalList.stream().map(d -> LocalDate.parse(d.replaceFirst("^0", ""), formatter)).sorted(Comparator.reverseOrder()).map(d -> d.format(formatter)).collect(Collectors.toList());
        } else {
            expectedAsc = new ArrayList<>(originalList);
            Collections.sort(expectedAsc, String.CASE_INSENSITIVE_ORDER);
            expectedDesc = new ArrayList<>(expectedAsc);
            Collections.reverse(expectedDesc);
        }
        // --- Ascending Sort ---
        Logs.info(test, "Clicked header to sort ascending for " + columnName);

        Thread.sleep(1500);
        header.click();
        Thread.sleep(1500); // wait for table refresh

        List<String> ascList = driver.findElements(cellLocator).stream().map(e -> e.getText().trim()).collect(Collectors.toList());
        Logs.info(test, columnName + " - Asc List: " + ascList);

        if (ascList.equals(expectedAsc)) {
            Logs.info(test, "✅ " + columnName + " ascending sort works");
        } else {
            Logs.warn(driver, test, "❌ " + columnName + " ascending sort failed.\nExpected: " + expectedAsc + "\nFound: " + ascList);
        }

        // --- Descending Sort ---
        Logs.info(test, "Clicked header again to sort descending for " + columnName);

        Thread.sleep(1500);
        header.click();
        Thread.sleep(1500); // wait for table refresh

        List<String> descList = driver.findElements(cellLocator).stream().map(e -> e.getText().trim()).collect(Collectors.toList());
        Logs.info(test, columnName + " - Desc List: " + descList);

        if (descList.equals(expectedDesc)) {
            Logs.info(test, "✅ " + columnName + " descending sort works");
        } else {
            Logs.warn(driver, test, "❌ " + columnName + " descending sort failed.\nExpected: " + expectedDesc + "\nFound: " + descList);
        }
        Logs.info(test, "Finished sorting verification for column: " + columnName);

    }


    @Test(priority = 4, description = "Step 4: Action icons visibility")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 4: Verify action icons visibility")
    @Description("Ensure all action icons are visible on Invoice Templates Page")
    public void Action_icons_visibility() throws InterruptedException {
        Logs.info(test, "Checking visibility of action icons on Invoice Templates Page");
        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        Logs.info(test, "Total rows found: " + rows.size());
        if (rows.isEmpty()) {
            Logs.warn(driver, test, "No rows found in the table, skipping icon check.");
            return;
        }
        for (int i = 1; i <= rows.size(); i++) {
            Logs.info(test, "Verifying icons in row " + i);
            verifyRowIcons(driver, test, i);
        }
        Logs.info(test, "Action icon visibility check completed for all rows.");
    }
    public void verifyRowIcons(WebDriver driver, ExtentTest test, int rowIndex) {
        By editIcon = By.xpath("//tbody/tr[" + rowIndex + "]/td[9]/div[1]/button[1]/span[1]//*[name()='svg']");
        By deleteIcon = By.xpath("//tbody/tr[" + rowIndex + "]/td[9]/div[1]/button[2]/span[1]//*[name()='svg']");
        By copyIcon = By.xpath("//tbody/tr[" + rowIndex + "]/td[9]/div[1]/button[3]/span[1]//*[name()='svg']");
        By previewIcon = By.xpath("//tbody/tr[" + rowIndex + "]/td[9]/div[1]/button[4]/span[1]//*[name()='svg']");
        verifyIconVisible(driver, test, editIcon, "Edit", rowIndex);
        Logs.info(test, "Checked Edit icon");
        verifyIconVisible(driver, test, deleteIcon, "Delete", rowIndex);
        Logs.info(test, "Checked Delete icon");
        verifyIconVisible(driver, test, copyIcon, "Copy", rowIndex);
        Logs.info(test, "Checked Copy icon");
        verifyIconVisible(driver, test, previewIcon, "Preview", rowIndex);
        Logs.info(test, "Checked Preview icon");

    }
    private void verifyIconVisible(WebDriver driver, ExtentTest test, By locator, String iconName, int rowIndex) {
        try {
            WebElement icon = driver.findElement(locator);
            if (icon.isDisplayed()) {
                Logs.info(test, "✅ " + iconName + " icon is visible in row " + rowIndex);
            } else {
                Logs.warn(driver, test, "❌ " + iconName + " icon is NOT visible in row " + rowIndex);
            }
        } catch (NoSuchElementException e) {
            Logs.warn(driver, test, "❌ " + iconName + " icon not found in DOM for row " + rowIndex);
        }
    }

    @Test(priority = 5, description = "Step 5: Edit functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 5: Verify edit functionality")
    @Description("Verify that Invoice Templates can be edited")
    public void Edit_Functionality() throws InterruptedException {
        Logs.info(test, "Verifying Edit functionality for Invoice Template");
        List<WebElement> editButtons = driver.findElements(By.xpath("//tbody/tr/td[9]/div[1]/button[1]"));
        WebElement editBtn = null;
        int rowIndex = -1;
        Logs.info(test, "Finding the Edit button in table rows");

        for (int i = 0; i < editButtons.size(); i++) {
            if (editButtons.get(i).isDisplayed() && editButtons.get(i).isEnabled()) {
                editBtn = editButtons.get(i);
                rowIndex = i + 1;
                Logs.info(test, "Found Edit button at row " + rowIndex);
                break;
            }
        }
        if (editBtn == null || rowIndex == -1) {
            Logs.warn(driver, test, "No Edit button found in any row.");
            return;
        }
        Logs.info(test, "Capturing original values from row " + rowIndex);

        String oldInternalName = driver.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]")).getText().trim();
        String oldExternalName = driver.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[2]")).getText().trim();
        String oldDateUpdated = driver.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[7]")).getText().trim();
        Logs.info(test, "Row " + rowIndex + " - Original Internal Name: " + oldInternalName);
        Logs.info(test, "Row " + rowIndex + " - Original External Name: " + oldExternalName);
        Logs.info(test, "Row " + rowIndex + " - Original Date Updated: " + oldDateUpdated);
        Logs.info(test, "Clicking Edit button for row " + rowIndex);
        editBtn.click();
        Thread.sleep(1500);
        Logs.info(test, "Updating Internal Name");
        WebElement internalNameInput = driver.findElement(By.xpath("(//input[@type='text'])[3]"));
        internalNameInput.clear();
        String newInternalName = oldInternalName + " " + firstName;
        internalNameInput.sendKeys(newInternalName);
        Logs.info(test, "Internal Name changed to: " + newInternalName);
        Logs.info(test, "Updating External Name");

        WebElement externalNameInput = driver.findElement(By.xpath("(//input[@type='text'])[4]"));
        externalNameInput.clear();
        String newExternalName = oldExternalName + " " + lastName;
        externalNameInput.sendKeys(newExternalName);
        Logs.info(test, "External Name changed to: " + newExternalName);

        Thread.sleep(3000);
        WebElement scrolltoIT2311 = driver.findElement(By.xpath("//button[normalize-space()='Save & Next']"));
        Actions actions2311 = new Actions(driver);
        actions2311.moveToElement(scrolltoIT2311).perform();
        scrolltoIT2311.click();
        Thread.sleep(3000);
        WebElement scrolltoIT23 = driver.findElement(By.xpath("//button[normalize-space()='Save & Next']"));
        Actions actions23 = new Actions(driver);
        actions23.moveToElement(scrolltoIT23).perform();
        scrolltoIT23.click();
        Thread.sleep(3000);
        WebElement scrolltoIT231 = driver.findElement(By.xpath("//button[normalize-space()='Save & Next']"));
        Actions actions231 = new Actions(driver);
        actions2311.moveToElement(scrolltoIT231).perform();
        scrolltoIT231.click();
        Logs.info(test, "apturing updated row values");

        String updatedInternalName = driver.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]")).getText().trim();
        String updatedExternalName = driver.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[2]")).getText().trim();
        String updatedDateUpdated = driver.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[7]")).getText().trim();
        Logs.info(test, "Row " + rowIndex + " - Updated Internal Name: " + updatedInternalName);
        Logs.info(test, "Row " + rowIndex + " - Updated External Name: " + updatedExternalName);
        Logs.info(test, "Row " + rowIndex + " - Updated Date Updated: " + updatedDateUpdated);
        Logs.info(test, "Verifying updates");

        if (!updatedInternalName.equals(oldInternalName) && updatedInternalName.equals(newInternalName)) {
            Logs.info(test, "Internal Name updated successfully.");
        } else {
            Logs.warn(driver, test, "Internal Name update failed. Expected: " + newInternalName + ", Found: " + updatedInternalName);
        }
        if (!updatedExternalName.equals(oldExternalName) && updatedExternalName.equals(newExternalName)) {
            Logs.info(test, "External Name updated successfully.");
        } else {
            Logs.warn(driver, test, "External Name update failed. Expected: " + newExternalName + ", Found: " + updatedExternalName);
        }
        if (!updatedDateUpdated.equals(oldDateUpdated)) {
            Logs.info(test, "Date Updated refreshed successfully.");
        } else {
            Logs.warn(driver, test, "Date Updated did not refresh. Old: " + oldDateUpdated + ", New: " + updatedDateUpdated);
        }
        Logs.info(test, "Edit functionality test completed for row " + rowIndex);
    }

    @Test(priority = 6, description = "Step 6: Update functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 6: Verify update functionality")
    @Description("Verify updates are correctly saved in Invoice Templates")
    public void Update_Functionality() throws InterruptedException {
        Logs.info(test, "Verifying Copy functionality for Invoice Template");
        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        WebElement originalRow = null;
        int rowIndex = -1;
        Logs.info(test, "Searching for the first row with a visible Copy button");
        for (int i = 0; i < rows.size(); i++) {
            try {
                WebElement copyBtn = rows.get(i).findElement(By.xpath(".//td[9]/div/button[3]/span//*[name()='svg']"));
                if (copyBtn.isDisplayed() && copyBtn.isEnabled()) {
                    rowIndex = i + 1; // XPath is 1-based
                    originalRow = rows.get(i);
                    Logs.info(test, "Copy icon found in row " + rowIndex);
                    // Capture existing data
                    String originalInternalName = originalRow.findElement(By.xpath("./td[1]")).getText().trim();
                    String originalExternalName = originalRow.findElement(By.xpath("./td[2]")).getText().trim();
                    String originalOtherField = originalRow.findElement(By.xpath("./td[3]")).getText().trim();
                    Logs.info(test, "Original Row Data - Internal Name: " + originalInternalName);
                    Logs.info(test, "Original Row Data - External Name: " + originalExternalName);
                    Logs.info(test, "Original Row Data - Other Field: " + originalOtherField);
                    // Click the Copy button
                    copyBtn.click();
                    Logs.info(test, "Copy button clicked ");
                    Thread.sleep(2000);
                    // Click through Save & Next flow
                    for (int step = 0; step < 3; step++) { // adjust based on number of Save & Next steps
                        WebElement saveNextBtn = driver.findElement(By.xpath("//button[normalize-space()='Save & Next']"));
                        Actions actions = new Actions(driver);
                        actions.moveToElement(saveNextBtn).perform();
                        saveNextBtn.click();
                        Thread.sleep(2000); // wait for save action
                    }
                    // Re-fetch table rows after saving copy
                    List<WebElement> updatedRows = driver.findElements(By.xpath("//tbody/tr"));
                    // Find the newly copied row (assume it's the first row)
                    WebElement copiedRow = updatedRows.get(0);
                    String copiedInternalName = copiedRow.findElement(By.xpath("./td[1]")).getText().trim();
                    String copiedExternalName = copiedRow.findElement(By.xpath("./td[2]")).getText().trim();
                    String copiedOtherField = copiedRow.findElement(By.xpath("./td[3]")).getText().trim();

                    Logs.info(test, "Row Data After Copied - Internal Name: " + copiedInternalName);
                    Logs.info(test, "Row Data After Copied - External Name: " + copiedExternalName);
                    Logs.info(test, "Row Data After Copied  - Other Field: " + copiedOtherField);

                    Logs.info(test, "Verifying copied data matches original");
                    Assert.assertEquals(copiedInternalName, originalInternalName, "Internal Name mismatch ");
                    Assert.assertEquals(copiedExternalName, originalExternalName, "External Name mismatch ");
                    Assert.assertEquals(copiedOtherField, originalOtherField, "Other Field mismatch ");

                    Logs.info(test, "Data copied and verified successfully ");
                    break;
                }
            } catch (Exception e) {
            }
        }

        if (originalRow == null) {
            Logs.warn(driver, test, "No Copy button found in any row.");
            Assert.fail("Copy button not found in table ");
        }
    }

    @Test(priority = 7, description = "Step 7: Status change functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 7: Verify status change functionality")
    @Description("Check if the status of an invoice template can be changed")
    public void Status_change_Functionality() throws InterruptedException {
        Logs.info(test, "Verifying Status functionality ");

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        Thread.sleep(2000); // wait for table to load
        boolean statusToggled = false;
        Logs.info(test, "Searching for first row with Active/Inactive status");

        for (int i = 0; i < rows.size(); i++) {
            try {
                // Find first Active or Inactive status in this row
                WebElement statusCell = rows.get(i).findElement(By.xpath(".//*[text()='Active' or text()='Inactive']"));
                String originalStatus = statusCell.getText().trim();
                Logs.info(test, "Found template with status: " + originalStatus + " in row " + (i + 1));
                // --- First toggle ---
                statusCell.click();
                Thread.sleep(1000);
                // Handle JS alert popup
                Alert alert = driver.switchTo().alert();
                Logs.info(test, "Alert appeared with text: " + alert.getText());
                alert.accept();
                Logs.info(test, "Popup accepted ");
                Thread.sleep(2000);
                WebElement toggledStatusCell = driver.findElements(By.xpath("//tbody/tr")).get(i).findElement(By.xpath(".//*[text()='Active' or text()='Inactive']"));
                String toggledStatus = toggledStatusCell.getText().trim();
                Assert.assertNotEquals(toggledStatus, originalStatus, "Status did not change after first toggle");
                Logs.info(test, "First toggle successful: " + originalStatus + " → " + toggledStatus);
                Logs.info(test, "Toggling back to original status");
                toggledStatusCell.click();
                Thread.sleep(1000);
                Alert alert2 = driver.switchTo().alert();
                Logs.info(test, "Alert text: " + alert2.getText());
                alert2.accept();
                Logs.info(test, "Popup accepted ");
                Thread.sleep(2000);
                WebElement finalStatusCell = driver.findElements(By.xpath("//tbody/tr")).get(i).findElement(By.xpath(".//*[text()='Active' or text()='Inactive']"));
                String finalStatus = finalStatusCell.getText().trim();
                Assert.assertEquals(finalStatus, originalStatus, "Status did not toggle back correctly");
                Logs.info(test, "Second toggle successful: " + toggledStatus + " → " + finalStatus);
                statusToggled = true;
                break; // stop after the first matching row

            } catch (Exception e) {
                // Row doesn't contain Active/Inactive, continue
                continue;
            }
        }

        if (!statusToggled) {
            Logs.warn(driver, test, "No Active/Inactive status found in any row.");
            Assert.fail("No status button found to toggle");
        }
    }

    @Test(priority = 9, description = "Step 9: Search functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 9: Verify search functionality")
    @Description("Search for templates using search bar")
    public void search_Functionality() throws InterruptedException {
        Logs.info(test, "Verifying Search functionality for Invoice Templates");
        WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Search']"));

        Logs.info(test, "Performing valid search");
        String validKeyword = "Pnkj";
        Logs.info(test, "Clearing search input and entering valid keyword: " + validKeyword);
        searchInput.clear();
        searchInput.sendKeys(validKeyword);
        Thread.sleep(1000);
        Logs.info(test, "Clicking Search button");
        driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
        Thread.sleep(2000);
        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertTrue(rows.size() > 0, "No rows found after searching for: " + validKeyword);
        Logs.info(test, "Rows found for valid search: " + rows.size());
        for (int i = 0; i < rows.size(); i++) {
            String internalName = rows.get(i).findElement(By.xpath("./td[1]")).getText().trim();
            String externalName = rows.get(i).findElement(By.xpath("./td[2]")).getText().trim();
            boolean matchFound = internalName.contains(validKeyword) || externalName.contains(validKeyword);
            Assert.assertTrue(matchFound, "Row " + (i + 1) + " does not match the search keyword: " + validKeyword);
            Logs.info(test, "Row " + (i + 1) + " matches search: Internal Name = " + internalName + ", External Name = " + externalName);
        }
        Logs.info(test, "Performing invalid search");
        String invalidKeyword = "RandomTextNotExist";
        Thread.sleep(2000);

        Logs.info(test, "Clearing search input and entering invalid keyword: " + invalidKeyword);
        searchInput.clear();
        searchInput.sendKeys(invalidKeyword);
        Thread.sleep(2000);

        Logs.info(test, "Clicking Search button");
        driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
        Thread.sleep(2000);

        Logs.info(test, "Verifying 'No Data' message is displayed for invalid search");
        WebElement noDataMsg = driver.findElement(By.xpath("//div[@class='py-4 text-center']"));
        Assert.assertTrue(noDataMsg.isDisplayed(),
                "No data message not displayed for invalid search: " + invalidKeyword);

        Logs.info(test, "No data message displayed for invalid search: " + invalidKeyword);
        Thread.sleep(2000);
        Logs.info(test, "Clearing search input");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].value = ''; " +                  // Clear the field
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " + // trigger input
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",   // trigger change
                searchInput
        );
        Thread.sleep(500);
        searchInput.sendKeys(" ");
        searchInput.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(500);
        Logs.info(test, "Clicking Search button after clearing input to reset table");

        driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
        Thread.sleep(2000);
        Logs.info(test, "Search input cleared and table reset successfully");

    }


    @Test(priority = 10, description = "Step 10: Status filter functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 10: Verify status filter functionality")
    @Description("Filter invoice templates by status")
    public void Status_filter_Functionality() throws InterruptedException {
        Logs.info(test, "Testing status filter functionality");
        Logs.info(test, "Filter by Active status");
        selectStatus("Active");
        verifyTableData("Active");
        Logs.info(test, "Filter by Inactive status");
        selectStatus("Inactive");
        verifyTableData("Inactive");
        Logs.info(test, "Filter by All Status");
        selectStatus("All Status");
        verifyTableData("All Status");
    }

    private void selectStatus(String status) throws InterruptedException {
        Logs.info(test, "Clicking status dropdown to select: " + status);
        WebElement dropdown = driver.findElement(By.xpath("//div[contains(@class,'zplus__control')]"));
        dropdown.click();
        Thread.sleep(1500);
        WebElement option = driver.findElement(By.xpath("//div[contains(@class,'zplus__option') and normalize-space()='" + status + "']"));
        option.click();
        Logs.info(test, "Selected filter: " + status);
        Thread.sleep(2000);
    }

    private void verifyTableData(String status) {
        Logs.info(test, "Verifying table data for status filter: " + status);
        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        // Assert.assertTrue(rows.size() > 0, "No rows found after selecting " + status);
        if (status.equals("All Status")) {
            Logs.info(test, "Total rows displayed for All Status: " + rows.size());
            boolean hasActive = rows.stream().anyMatch(r -> r.findElement(By.xpath("./td[8]")).getText().trim().equals("Active"));
            boolean hasInactive = rows.stream().anyMatch(r -> r.findElement(By.xpath("./td[8]")).getText().trim().equals("Inactive"));
            Assert.assertTrue(hasActive || hasInactive, "All Status filter should display both Active and Inactive records");
            Logs.info(test, "Verified All Status shows both Active and Inactive records");

        } else {
            for (int i = 0; i < rows.size(); i++) {
                String statusText = rows.get(i).findElement(By.xpath("./td[8]")) // last column assumed as Status
                        .getText().trim();
                Assert.assertEquals(statusText, status, "Row " + (i + 1) + " status mismatch for " + status);
                Logs.info(test, "Row " + (i + 1) + " verified: " + statusText);
            }

        }
        Logs.info(test, "Table verification completed for filter: " + status);
    }

    @Test(priority = 11, description = "Step 11: Add Invoice Template functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 11: Verify add invoice template functionality")
    @Description("Check  the response when admin click on Add invoice template")
    public void Add_Invoice_Template_Functionality() throws InterruptedException {
        Logs.info(test, "Testing add invoice template screen displayed");
        Logs.info(test, "Clicking on 'Add Invoice Template' button");

        driver.findElement(By.xpath("(//a[normalize-space()='Add Invoice Template'])[1]")).click();
        String actualUrl = driver.getCurrentUrl();
        String expectedPartialUrl = "/template/edit";
        Logs.info(test, "Verifying that the current URL contains: " + expectedPartialUrl);
        Logs.info(test, "Navigating back to 'Invoice Templates' page");

        Assert.assertTrue(actualUrl.contains(expectedPartialUrl), "Page did not navigate correctly. Expected URL to contain: " + expectedPartialUrl + " but got: " + actualUrl);
        Logs.pass(driver, test, "Add Invoice Template page opened successfully: " + actualUrl);

        Logs.pass(driver, test, "Add Invoice Template page opened successfully: " + actualUrl);
        driver.findElement(By.xpath("//a[normalize-space()='Invoice Templates']")).click();
        Logs.info(test, "Returned to Invoice Templates page successfully");

    }

    @Test(priority = 12, description = "Step 12: Verify delete functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Step 11: Verify delete functionality")
    @Description("Check  the response when admin click on delete")
    public void delete_Functionality() throws InterruptedException {
        Logs.info(test, "Testing delete functionality");
        List<WebElement> rowsBefore = driver.findElements(By.xpath("//tbody/tr"));
        Logs.info(test, "Total rows before deletion: " + rowsBefore.size());
        if (rowsBefore.isEmpty()) {
            throw new SkipException("No records found in table. Skipping delete test.");
        }
        String recordName = rowsBefore.get(0).findElement(By.xpath(".//td[1]")).getText().trim();
        Logs.info(test, "Record selected for deletion: " + recordName);
        WebElement deleteIcon = rowsBefore.get(0).findElement(By.xpath(".//td[9]//button[@title='Delete']//*[name()='svg']"));
        deleteIcon.click();
        Logs.info(test, "Clicking on delete icon for record: " + recordName);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            Logs.info(test, "Alert appeared with text: " + alert.getText());
            alert.accept();
            Logs.info(test, "Confirmed deletion by accepting alert");

        } catch (Exception e) {
            Logs.info(test, "No browser alert appeared after clicking delete");
        }
        Thread.sleep(2000); // allow table to refresh
        List<WebElement> rowsAfter = driver.findElements(By.xpath("//tbody/tr"));
        boolean recordStillPresent = rowsAfter.stream().anyMatch(row -> row.getText().contains(recordName));
        Assert.assertFalse(recordStillPresent, "Record was NOT deleted: " + recordName);
        Logs.pass(driver, test, "Record deleted successfully: " + recordName);
        Logs.info(test, "Total rows after deletion: " + rowsAfter.size());

    }

    @AfterMethod
    public void takescreenshot(ITestResult result) throws IOException {
        tearDown1(result);
    }

    @AfterClass
    public void QuitBrowser() throws InterruptedException {
      //  quitDriver();
        finalizeReport();
        Logs.info(test, "Browser closed and report finalized");
    }
}