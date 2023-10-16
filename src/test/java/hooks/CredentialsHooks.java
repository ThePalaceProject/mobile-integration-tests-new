package hooks;

import framework.configuration.ConfigurationStorage;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class CredentialsHooks {
    @After(order = 2)
    public void unlockCredentials(Scenario scenario) {
        ConfigurationStorage.unlockCredentials();
    }
}
