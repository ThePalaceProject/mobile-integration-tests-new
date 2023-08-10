package hooks;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.keysForContext.ScenarioContextKey;
import framework.configuration.Credentials;
import framework.utilities.ScenarioContext;
import framework.utilities.returningBooksUtil.APIUtil;
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

        System.out.println(map.size());

        if (map.size() == 0) {
            throw new RuntimeException("There are not barcodes for returning books");
        }
        for (Map.Entry<String, String> m : map.entrySet()) {
            credentials.setBarcode(m.getKey());
            credentials.setPin(m.getValue());
            APIUtil.returnBooks(credentials);
            APIUtil.enterBooksAfterReturningBooks(credentials);
        }
    }
}
