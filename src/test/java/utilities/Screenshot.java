package utilities;

import com.aventstack.extentreports.MediaEntityBuilder;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot extends StartupCode {
    /* public static ExtentReports extent;
     public static ExtentTest test;

     static {
         ExtentSparkReporter spark = new ExtentSparkReporter("./reports/ExtentReport.html");
         extent = new ExtentReports();
         extent.attachReporter(spark);
     }
 */
    public static String takeScreenshot(WebDriver driver, String testName) throws IOException {
        String folderPath = System.getProperty("user.dir") + File.separator + "screenshots";
        new File(folderPath).mkdirs();
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH.mm.ss").format(new Date());
        String filePath = folderPath + File.separator + testName + "_" + timestamp + ".png";

        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("✨ Screenshot saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    public static void tearDown1(ITestResult result) throws IOException {
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
}
