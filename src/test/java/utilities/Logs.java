package utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Logs {
    private static Logger Logger() {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        return LogManager.getLogger(className);
    }

    private static void attachScreenshot(WebDriver driver, ExtentTest test, String label, String message) {
        try {
            String screenshotPath = Screenshot.takeScreenshot(driver, label);
            String fileName = new File(screenshotPath).getName();
            String relativePath = ".." + File.separator + "screenshots" + File.separator + fileName;

            test.info(MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());
            File screenshotFile = new File(screenshotPath);

            Allure.step(message, () -> {
                Allure.addAttachment("Screenshot", "image/png", new FileInputStream(screenshotFile), ".png");
            });
        } catch (IOException e) {
            Logs.info(test, "⚠️ Failed to capture screenshot: " + e.getMessage());
        }
    }

    public static void pass(WebDriver driver, ExtentTest test, String message) {
        Logger().info("✅ " + message);
        test.pass("✅ " + message);
        attachScreenshot(driver, test, "pass", "✅ " + message);
    }

    public static void fail(WebDriver driver, ExtentTest test, String message) {
        Logger().error("❌ " + message);
        test.fail("❌ " + message);
        attachScreenshot(driver, test, "fail", "❌ " + message);
    }

    public static void info(ExtentTest test, String message) {
        Logger().info("ℹ️ " + message);
        test.info("ℹ️ " + message);
    }

    public static void warn(WebDriver driver, ExtentTest test, String message) {
        Logger().warn("⚠️ " + message);
        test.warning("⚠️ " + message);
        attachScreenshot(driver, test, "warn", "⚠️ " + message);
    }
}
