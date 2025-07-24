package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Extentreportmanager {

    //	public static void main(String[] args) {
    private static ExtentReports extent;
    private static String reportPath;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportPath = "test-output/ExtentReport-" + timestamp + ".html";
            new File("test-output").mkdirs();
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Dazhboards Test Report");
            spark.config().setDocumentTitle("Dazhboards Automation Suite");
            spark.config().setTimelineEnabled(true);

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Quality Engineer", "Pankaj");
        }
        return extent;
    }


}