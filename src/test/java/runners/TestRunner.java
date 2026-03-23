package runners;

import aquality.appium.mobile.application.AqualityServices;
import framework.utilities.feedxmlutil.GettingBookUtil;
import framework.utilities.feedxmlutil.XMLUtil;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {
                "hooks",
                "stepdefinitions"
        },
        plugin = {
                "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm",
                "aquality.tracking.integrations.cucumber5jvm.AqualityTrackingCucumber5Jvm"
        }
)

public class TestRunner {

        private TestRunner() {}

        @BeforeClass
        public static void setup() {
                boolean skipFeedBootstrap = Boolean.parseBoolean(System.getProperty("skipFeedBootstrap", "false"));
                if (skipFeedBootstrap) {
                        AqualityServices.getLogger().info("Skipping XML feed bootstrap due to -DskipFeedBootstrap=true");
                        return;
                }

                int bootstrapTimeoutSeconds = Integer.parseInt(System.getProperty("feedBootstrapTimeoutSeconds", "180"));
                AqualityServices.getLogger().info(String.format("Start getting books (timeout: %d sec)", bootstrapTimeoutSeconds));

                CompletableFuture<Void> bootstrapFuture = CompletableFuture.runAsync(() -> {
                        XMLUtil xmlUtil = new XMLUtil();
                        GettingBookUtil.setXmlUtil(xmlUtil);
                        GettingBookUtil.printDistributorsInfo();
                });

                try {
                        bootstrapFuture.get(bootstrapTimeoutSeconds, TimeUnit.SECONDS);
                        AqualityServices.getLogger().info("End getting books");
                } catch (Exception e) {
                        bootstrapFuture.cancel(true);
                        if (isIosRun()) {
                                AqualityServices.getLogger().error("Feed bootstrap did not complete in time. Continuing with iOS fallback book lookup. " + e.getMessage());
                                return;
                        }
                        throw new RuntimeException("Feed bootstrap failed for non-iOS run", e);
                }
        }

        private static boolean isIosRun() {
                return "ios".equalsIgnoreCase(System.getProperty("platformName", "ios"));
        }
}
