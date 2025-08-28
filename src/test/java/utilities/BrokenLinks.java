package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static utilities.StartupCode.driver;
import static utilities.StartupCode.test;

public class BrokenLinks {


    public void Brokenlink() {
        // This method will verify all links on the current page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Logs.info(test, "ℹ️ Total links: " + links.size());

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            if (url == null || url.isEmpty()) {
                continue;
            }

            boolean isBroken = false;
            int responseCode = 0;

            try {
                // HTTP connection check
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
                responseCode = connection.getResponseCode();

                if (responseCode >= 400) {
                    isBroken = true;
                }

            } catch (Exception e) {
                Logs.info(test, "Exception for URL: " + url + " — " + e.getMessage());
                isBroken = true;
            }

            // In-browser verification for broken links
            if (isBroken) {
                String originalUrl = driver.getCurrentUrl();
                try {
                    driver.get(url);
                    Thread.sleep(3000);

                    String title = driver.getTitle();
                    String source = driver.getPageSource();

                    if (title.contains("404") || source.contains("404") || title.contains("Not Found")) {
                        Logs.fail(driver, test, "Broken link: " + url + " — Page shows 404 (Status Code: " + responseCode + ")");
                    } else {
                        Logs.pass(driver, test, "Valid link in browser: " + url + " — Page loaded successfully");
                    }

                    // Navigate back to the original page
                    //  driver.navigate().back();

                } catch (Exception e) {
                    Logs.info(test, "Exception while checking in browser: " + url + " — " + e.getMessage());
                    try {
                        // Attempt to navigate back in case of an error
                        //  driver.navigate().back();
                    } catch (Exception ignored) {
                    }
                }
            } else {
                Logs.info(test, "Valid link (HTTP): " + url + " — Status Code: " + responseCode);
            }
        }
    }

    public void Weblink(WebElement scope) {
        List<WebElement> links = scope.findElements(By.tagName("a"));
        Logs.info(test, "ℹ️ Total profile page links: " + links.size());

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            if (url == null || url.isEmpty() || url.startsWith("javascript")) {
                continue;
            }

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                int responseCode = connection.getResponseCode();

                if (responseCode >= 400) {
                    Logs.fail(driver, test, "❌ Broken link: " + url + " — HTTP Status: " + responseCode);
                } else {
                    Logs.pass(driver, test, "✅ Valid link: " + url + " — HTTP Status: " + responseCode);
                }

            } catch (Exception e) {
                Logs.fail(driver, test, "❌ Exception checking URL: " + url + " — " + e.getMessage());
            }
        }
    }

    public void BrokenImage() {

        List<WebElement> images = driver.findElements(By.tagName("img"));
        Logs.info(test, "Total images: " + images.size());

        for (WebElement img : images) {
            String imageURL = img.getAttribute("src");

            if (imageURL == null || imageURL.isEmpty()) {
                Logs.info(test, "Image src is empty or null");
                continue;
            }

            try {
                HttpURLConnection connection = (HttpURLConnection) (new URL(imageURL).openConnection());
                connection.setRequestMethod("HEAD");
                connection.connect();
                int responseCode = connection.getResponseCode();

                if (responseCode >= 400) {
                    Logs.fail(driver, test, "🔴 Broken Image: " + imageURL + " | Response Code: " + responseCode);
                } else {
                    Logs.info(test, "🟢 Valid Image: " + imageURL + " | Response Code: " + responseCode);
                }
            } catch (Exception e) {
                Logs.info(test, "⚠️ Exception for: " + imageURL + " -> " + e.getMessage());
            }
        }
    }
}
