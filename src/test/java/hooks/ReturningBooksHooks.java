package hooks;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.keysforcontext.ScenarioContextKey;
import framework.configuration.Credentials;
import framework.utilities.ScenarioContext;
import framework.utilities.returningbooksutil.APIUtil;
import io.cucumber.java.After;

import java.util.Map;

public class ReturningBooksHooks {

    private final ScenarioContext context;

    @Inject
    public ReturningBooksHooks(ScenarioContext scenarioContext) {
        context = scenarioContext;
    }

    @After(value = "@returnBooks", order = 0)
    public void returnBooks() {
        Credentials credentials = new Credentials();
        AqualityServices.getLogger().info("Test finished - returning books");
        Map<String, String> map = context.get(ScenarioContextKey.LIST_OF_CREDENTIALS_KEY);

        if (map == null || map.isEmpty()) {
            AqualityServices.getLogger().info("No stored credentials for returning books");
            return;
        }
        for (Map.Entry<String, String> m : map.entrySet()) {
            try {
                credentials.setBarcode(m.getKey());
                credentials.setPin(m.getValue());
                APIUtil.returnBooks(credentials);
                APIUtil.enterBooksAfterReturningBooks(credentials);
            } catch (Exception e) {
                AqualityServices.getLogger().error(String.format("Failed to return books for barcode '%s': %s", m.getKey(), e.getMessage()));
            }
        }
    }
}
