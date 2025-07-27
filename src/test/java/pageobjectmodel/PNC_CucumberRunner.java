package pageobjectmodel;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.zeroturnaround.zip.ZipUtil;
import pnc.qel.cucumber.AbstractCucumberTestNGTest;
import pnc.qel.reporting.framework.RuntimeInsights;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
@Test
@CucumberOptions(
        monochrome = false,
        features = {"src/test/resources/feature.KTLO/"},
        tags = "@NT",
        glue = {"pnc.qel.cucumber.stepdefinitions", "stepdefinitionfiles", "com.deque.attest.cucumber"},
        //Modifying the plugin line will disable reporting capabilities.
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "pretty","html:target/cucumber-html-reports/report.html", "json:target/cucumber-html-reports/cucumber.json"}
        )
public class PNC_CucumberRunner extends AbstractCucumberTestNGTest {
        @AfterSuite
        public void teardown() {
                File file = new File("Spark_Report/JenkinsReport");
                String reportFile = file.getAbsolutePath();
                String reportFileZip = file.getAbsolutePath() + " for KTLO Alert_Management " + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".zip/";
                ZipUtil.pack(new File(reportFile), new File(reportFileZip));
                RuntimeInsights.attachFileToEmail(reportFileZip);
        }
}
 
 