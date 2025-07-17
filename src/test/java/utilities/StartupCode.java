package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class StartupCode {

    protected WebDriver driver;            // 🔁 changed to protected
    protected Reuseable reuse;             // optional
    protected Logger logger;               // 🔁 changed to protected
    protected ExtentReports extent;        // 🔁 changed to protected
    protected ExtentTest test;             // 🔁 changed to protected
    protected Faker fakedata;              // optional

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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
}