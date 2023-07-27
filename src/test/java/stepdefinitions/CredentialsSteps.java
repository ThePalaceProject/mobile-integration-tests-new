package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import constants.keysForContext.ScenarioContextKey;
import enums.keysforcontext.ContextLibrariesKeys;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.configuration.Configuration;
import framework.configuration.Credentials;
import framework.utilities.ScenarioContext;
import framework.utilities.returningBooksUtil.APIUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import screens.*;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CredentialsSteps {

    private final MenuBarScreen menuBarScreen;
    private final SettingsScreen settingsScreen;
    private final LibrariesScreen librariesScreen;
    private final AccountScreen accountScreen;
    private final AlertScreen alertScreen;
    private final CatalogScreen catalogScreen;
    private final ScenarioContext context;

    @Inject
    public CredentialsSteps(ScenarioContext context) {
        menuBarScreen = new MenuBarScreen();
        settingsScreen = new SettingsScreen();
        librariesScreen = new LibrariesScreen();
        accountScreen = new AccountScreen();
        alertScreen = new AlertScreen();
        catalogScreen = new CatalogScreen();
        this.context = context;
    }

    @When("Enter credentials for {string} library")
    public void enterCredentials(String libraryName) {
        saveLibraryForLogOut(libraryName);
        openAccounts();
        librariesScreen.openLibrary(libraryName);
        Credentials credentials = Configuration.getCredentials(libraryName);
        storeCredentials(credentials);
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && alertScreen.state().waitForDisplayed()) {
            AqualityServices.getApplication().getDriver().switchTo().alert().dismiss();
            AqualityServices.getLogger().info("Alert appears and dismiss alert");
        }
        accountScreen.enterCredentials(credentials);
        accountScreen.tapSignIn();
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && alertScreen.state().waitForDisplayed()) {
            alertScreen.waitAndPerformAlertActionIfDisplayed(ActionButtonsForBooksAndAlertsKeys.ALLOW);
        }
        boolean isLoginPerformedSuccessfully = AqualityServices.getConditionalWait().waitFor(() -> accountScreen.isSignInSuccessful() || catalogScreen.state().isDisplayed());
        if (!isLoginPerformedSuccessfully) {
            throw new RuntimeException("Sign in is not completed");
        }
    }

    @Then("Login is performed successfully")
    public void checkLoginIsPerformedSuccessfully() {
        Assert.assertTrue("Sign in is not completed",  accountScreen.isSignInSuccessful() || catalogScreen.state().isDisplayed());
    }

    private void openAccounts() {
        menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
        menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
        settingsScreen.openLibraries();
    }

    private void saveLibraryForLogOut(String libraryName) {
        saveLibraryInContext(ContextLibrariesKeys.LOG_OUT.getKey(), libraryName);
    }

    private void saveLibraryInContext(String key, String libraryName) {
        List<String> listOfLibraries = context.containsKey(key)
                ? context.get(key)
                : new ArrayList<>();

        listOfLibraries.add(libraryName);
        context.add(key, listOfLibraries);
    }

    private void storeCredentials(Credentials credentials) {
        String barcode = credentials.getBarcode();
        String pin = credentials.getPin();
        APIUtil.enterBookAfterOpeningAccount(credentials);
        if (context.get(ScenarioContextKey.LIST_OF_CREDENTIALS_KEY) == null) {
            Map<String, String> hashMap = new HashMap<>();
            context.add(ScenarioContextKey.LIST_OF_CREDENTIALS_KEY, hashMap);
        }
        Map<String, String> map = context.get(ScenarioContextKey.LIST_OF_CREDENTIALS_KEY);
        map.put(barcode, pin);
        context.add(ScenarioContextKey.LIST_OF_CREDENTIALS_KEY, map);
    }
}
