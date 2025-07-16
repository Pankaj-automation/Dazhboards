package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils; // This one is important!

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

public class Screenshot {

	  public static String takeScreenshot(WebDriver driver, String testName) throws IOException {
		  String folderPath = System.getProperty("user.dir") + File.separator + "screenshots";
	        new File(folderPath).mkdirs();
	        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH.mm.ss").format(new Date());
	        String filePath  = folderPath + File.separator + testName + "_" + timestamp + ".png";

	        try {
	            // 3) Capture and copy
	            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            FileUtils.copyFile(srcFile, new File(filePath));
	            System.out.println("✨ Screenshot saved to: " + filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        // 4) Return it so ExtentReports can embed it
	        return filePath;
	    }
	  public static ExtentReports extent;
	    public static ExtentTest test;

	    static {
	        ExtentSparkReporter spark = new ExtentSparkReporter("./reports/ExtentReport.html");
	        extent = new ExtentReports();
	        extent.attachReporter(spark);
	    }
	
	    
	}