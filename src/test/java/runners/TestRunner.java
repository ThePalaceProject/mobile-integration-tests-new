package runners;

import aquality.appium.mobile.application.AqualityServices;
import framework.utilities.feedxmlutil.GettingBookUtil;
import framework.utilities.feedxmlutil.XMLUtil;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.qameta.allure.Allure; //Import Allure
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/features"},
        glue = {
                "hooks",
                "stepdefinitions"
        },
        plugin = {
                "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm",
                "aquality.tracking.integrations.cucumber5jvm.AqualityTrackingCucumber5Jvm"
        },
        tags = "@test"
)

public class TestRunner {

        private TestRunner() {}

        @BeforeClass
        public static void setup() {
                // Fetch the BrowserStack app link from the system property (from the Maven command line)
                String bsAppLink = System.getProperty("bs_app_link", "bs://c18a7bd4186aa3384596153ab08de6e3f157d350"); // Default URL if not set

                // Log the BrowserStack app URL to Allure
                Allure.addAttachment("BrowserStack App URL", bsAppLink); // Attach to Allure report

                // Existing setup logic
                AqualityServices.getLogger().info("Start getting books");
                XMLUtil xmlUtil = new XMLUtil();
                GettingBookUtil.setXmlUtil(xmlUtil);
                GettingBookUtil.printDistributorsInfo();
                AqualityServices.getLogger().info("end getting books");
    }
}
        }
}
