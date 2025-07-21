package utilities;

import net.datafaker.Faker;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;

public class Reuseable {
    public WebDriver driver;
    public Logger logger;
    public Faker faker = new Faker();

    public WebDriver Reuseable1() {

        // logger = LogManager.getLogger(this.getClass());
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless=new");
        // Enable notification of the browser
        HashMap allow = new HashMap<>();
        allow.put("profile.default_content_setting_values.notifications", 1); // 1 = allow
        allow.put("profile.default_content_setting_values.geolocation", 1); // 1 = allow
        options.setExperimentalOption("prefs", allow);
        // Apply preferences to ChromeOptions

        driver = new ChromeDriver(options);
        driver.get("https://app2.dazhboards.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;

    }

    // Dynamic test data
    public String FirstName() {
        return faker.name().firstName();
    }

    public String LastName() {
        return faker.name().lastName();
    }

    public String Email() {
        return FirstName().toLowerCase() + "@yopmail.com";
    }

    public String Password() {
        return FirstName().toLowerCase() + "A51%%";
    }

    public String ZipCode() {
        return faker.address().zipCodeByState("CA");
    }

    public String PhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    public String Website() {
        return "www." + FirstName().toLowerCase() + ".com";
    }
}


