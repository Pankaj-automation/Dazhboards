package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Extentreportmanager {

    //	public static void main(String[] args) {
    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            String reportPath = "test-output/extent-report/index.html";  // ✅ Correct path
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

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