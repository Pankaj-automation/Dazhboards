package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class StartupCode {


    protected static WebDriver driver;
    protected static ExtentTest test;
    protected Reuseable reuse;
    protected Logger logger;
    protected ExtentReports extent;
    protected Faker fakedata;

    Reuseable data = new Reuseable();
    protected String firstName = data.FirstName();
    protected String lastName = data.LastName();
    protected String email = data.Email();
    protected String password = data.Password();
    protected String zipCode = data.ZipCode();
    protected String phoneNumber = data.PhoneNumber();
    protected String website = data.Website();

    public WebDriver setup1() throws InterruptedException {
        reuse = new Reuseable();
        driver = reuse.Reuseable1();
        Thread.sleep(1000);
        driver.manage().window().maximize();

        logger = LogManager.getLogger(this.getClass());
        extent = Extentreportmanager.getExtentReports();

        String testClassName = this.getClass().getSimpleName();
        test = extent.createTest(testClassName);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver;
    }

    public void finalizeReport() {

        logger.info("✅ Extent report Loaded.");
        test.info("✅ Extent report Loaded.");
        extent.flush();
    }

    public void quitDriver() {
        if (driver != null) {
            logger.info("Closing the browser");
            test.info("Closing the browser");
            driver.quit();
        } else {
            logger.warn("Driver is null. Nothing to quit.");
        }
        test.pass("Browser quit process completed");
    }
}
