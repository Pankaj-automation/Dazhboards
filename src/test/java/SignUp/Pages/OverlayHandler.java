package SignUp.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Logs;

import java.time.Duration;

import static utilities.StartupCode.test;

public class OverlayHandler {

    private final By overlayIframe = By.xpath("//*[contains(@id,'produktly-checklist-beacon-iframe')]");
    private final By closeButton = By.xpath("//button[@aria-label='Close checklist beacon']//*[name()='svg']");
    private WebDriver driver;
    private WebDriverWait wait;

    public OverlayHandler(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void closeOverlayIfPresent() {
        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(overlayIframe));
            driver.switchTo().frame(iframe);
            Logs.info(test, "Switching to overlay iframe");

            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(closeButton));
            closeBtn.click();
            Logs.pass(driver, test, "Overlay popup closed");


        } catch (TimeoutException e) {
            Logs.info(test, "Overlay iframe not found, continuing without closing");

        } catch (Exception e) {
            Logs.warn(null, test, "Unexpected error while handling overlay: " + e.getMessage());

        } finally {
            try {
                driver.switchTo().defaultContent();
            } catch (Exception ignored) {
            }
        }
    }
}
