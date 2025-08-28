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
            props.setProperty("Test URL", "https://app2.dazhboards.com/");
            props.setProperty("Quality Engineer", "Pankaj");
            props.setProperty("OS", System.getProperty("os.name"));
            props.setProperty("Java.Version", System.getProperty("java.version"));

            File allureResultsDir = new File("allure-results");
            if (!allureResultsDir.exists()) {
                boolean created = allureResultsDir.mkdirs();
                if (!created) {
                    System.err.println("❌ Failed to create allure-results directory.");
                    return;
                }
            }

            File envFile = new File(allureResultsDir, "environment.properties");
            try (FileWriter writer = new FileWriter(envFile)) {
                props.store(writer, "Allure Environment Properties");
                System.out.println("✔️ Allure environment.properties file created at " + envFile.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("❌ Failed to create environment.properties: " + e.getMessage());
        }
    }
}