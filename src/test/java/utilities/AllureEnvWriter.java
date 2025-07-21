package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvWriter {
    public static void createEnvFile() {
        try {
            Properties props = new Properties();
            props.setProperty("Browser", "Chrome");
            props.setProperty("Browser.Version", "124");
            props.setProperty("Environment", "Staging");
            props.setProperty("OS", System.getProperty("os.name"));
            props.setProperty("Java.Version", System.getProperty("java.version"));

            File allureResultsDir = new File("allure-results");
            if (!allureResultsDir.exists()) {
                allureResultsDir.mkdirs(); // create allure-results if it doesn't exist
            }

            FileWriter writer = new FileWriter("allure-results/environment.properties");
            props.store(writer, "Allure Environment Properties");
            writer.close();

            System.out.println("✔️ Allure environment.properties file created.");

        } catch (IOException e) {
            System.err.println("❌ Failed to create environment.properties: " + e.getMessage());
        }
    }
}

