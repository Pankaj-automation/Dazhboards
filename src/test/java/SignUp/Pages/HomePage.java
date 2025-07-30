package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Logs;

import static utilities.StartupCode.test;

public class HomePage {
    WebDriver driver;
    private By signUpLink = By.xpath("//a[@href='/signup']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSignUpLink() {
        driver.findElement(signUpLink).click();
        Logs.info(test, "✔Navigated Sign Up");

    }
}
