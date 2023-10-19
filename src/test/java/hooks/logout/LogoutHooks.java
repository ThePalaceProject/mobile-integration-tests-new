package hooks.logout;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import enums.keysforcontext.ContextLibrariesKeys;
import enums.localization.account.AccountScreenSignInStatus;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import enums.timeouts.AuthorizationTimeouts;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.ScenarioContext;
import io.cucumber.java.After;
import screens.AccountScreen;
import screens.AlertScreen;
import screens.LibrariesScreen;
import screens.SettingsScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class LogoutHooks {

    private final MenuBarScreen menuBarScreen;
    private final SettingsScreen settingsScreen;
    private final LibrariesScreen librariesScreen;
    private final AccountScreen accountScreen;
    private final AlertScreen alertScreen;
    private final ScenarioContext context;

    @Inject
    public LogoutHooks(ScenarioContext context) {
        this.context = context;
        menuBarScreen = new MenuBarScreen();
        settingsScreen = new SettingsScreen();
        librariesScreen = new LibrariesScreen();
        accountScreen = new AccountScreen();
        alertScreen = new AlertScreen();
    }
    @After(value = "@logout", order = 3)
    public void logout() {
        AqualityServices.getLogger().info("Test finished - logging out");
        restartApp();
        List<String> listOfLibraries = context.get(ContextLibrariesKeys.LOG_OUT.getKey());
        if (listOfLibraries.isEmpty()) {
            throw new RuntimeException("There are not libraries for logout");
        }
        for (String library : listOfLibraries) {
            menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
            menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
            settingsScreen.openLibraries();
            librariesScreen.openLibrary(library);
            if (accountScreen.isLogoutRequired()) {
                final String cardTextBeforeLogout = accountScreen.getTextFromCardTxb();
                final String pinTextBeforeLogout = accountScreen.getTextFromPinTxb();
                accountScreen.tapSignOut();

                ActionProcessorUtils.doForAndroid(() -> {
                    do {
                        if(accountScreen.isLogOutErrorDisplayed()) {
                            accountScreen.tapSignOut();
                        }
                    } while (!accountScreen.getTextFromSignInButton().equals(AccountScreenSignInStatus.SIGN_IN.getDefaultLocalizedValue())
                            && accountScreen.getTextFromCardTxb().equals(cardTextBeforeLogout)
                            && accountScreen.getTextFromPinTxb().equals(pinTextBeforeLogout));
                });

                ActionProcessorUtils.doForIos(() -> {
                    accountScreen.tapApproveSignOut();
                    if(alertScreen.state().waitForDisplayed()){
                        alertScreen.waitAndPerformAlertActionIfDisplayed(ActionButtonsForBooksAndAlertsKeys.SIGN_OUT);
                    }
                    AqualityServices.getConditionalWait().waitFor(() ->
                                    accountScreen.getTextFromSignInButton().equals(AccountScreenSignInStatus.SIGN_IN.getDefaultLocalizedValue())
                                            && !accountScreen.getTextFromCardTxb().equals(cardTextBeforeLogout)
                                            && !accountScreen.getTextFromPinTxb().equals(pinTextBeforeLogout),
                            Duration.ofMillis(AuthorizationTimeouts.USER_LOGGED_OUT.getTimeoutMillis()),
                            Duration.ofMillis(AuthorizationTimeouts.USER_LOGGED_OUT.getPollingMillis()),
                            Collections.singletonList(NoSuchElementException.class));
                });
            }
        }
    }

    protected void restartApp() {
        AqualityServices.getApplication().getDriver().closeApp();
        AqualityServices.getApplication().getDriver().launchApp();
    }
}
