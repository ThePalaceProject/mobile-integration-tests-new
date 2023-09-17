package runners;

import aquality.appium.mobile.application.AqualityServices;
import framework.utilities.feedXMLUtil.GettingBookUtil;
import framework.utilities.feedXMLUtil.XMLUtil;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
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
                "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm"
        },
        tags = "@allure"
)

public class TestRunner {

        @BeforeClass
        public static void setup() {
                AqualityServices.getLogger().info("Start getting books");
                XMLUtil xmlUtil = new XMLUtil();
                GettingBookUtil.setXmlUtil(xmlUtil);
                GettingBookUtil.printDistributorsInfo();
                AqualityServices.getLogger().info("end getting books");
        }
}
