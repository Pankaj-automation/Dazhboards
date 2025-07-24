package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class Extentreportmanager {

    //	public static void main(String[] args) {
    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            new File("test-output/").mkdirs();
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/dazhboardsExtentReport.html");
            spark.config().setReportName("Dazhboards Test Report");
            spark.config().setDocumentTitle("Dazhboards Automation Suite");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Quality Engineer", "Pankaj");
        }
        return extent;
    }


}