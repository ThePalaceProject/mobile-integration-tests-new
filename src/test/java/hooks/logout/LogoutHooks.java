package hooks.logout;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import enums.keysforcontext.ContextLibrariesKeys;
import enums.localization.account.AccountScreenSignInStatus;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import enums.timeouts.AuthorizationTimeouts;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import screens.AccountScreen;
import screens.AlertScreen;
import screens.LibrariesScreen;
import screens.SettingsScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class LogoutHooks {
    private static final String IOS_LOGOUT_MODE_PROPERTY = "ios.logoutMode";
    private static final String IOS_LOGOUT_MODE_DEVICE_SWITCH = "device_switch";
    private static final String IOS_LOGOUT_MODE_TAGGED = "tagged";
    private static final String IOS_LOGOUT_MODE_EVERY_SCENARIO = "every_scenario";

    private static final List<String> FALLBACK_LIBRARIES_FOR_LOGOUT = Arrays.asList(
            "Lyrasis Reads",
            "A1QA Test Library",
            "Palace Bookshelf"
    );

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
    @After(order = 3)
    public void logout(Scenario scenario) {
        if (shouldSkipLogout(scenario)) {
            return;
        }

        AqualityServices.getLogger().info("Test finished - logging out");
        List<String> listOfLibraries = getLibrariesForLogout();
        if (listOfLibraries.isEmpty()) {
            AqualityServices.getLogger().info("No libraries available for logout");
            return;
        }
        restartAppSafely();
        for (String library : listOfLibraries) {
            logoutFromLibrarySafely(library);
        }
    }

    private List<String> getLibrariesForLogout() {
        Set<String> libraries = new LinkedHashSet<>();
        if (context.containsKey(ContextLibrariesKeys.LOG_OUT.getKey())) {
            List<String> contextLibraries = context.get(ContextLibrariesKeys.LOG_OUT.getKey());
            if (contextLibraries != null) {
                libraries.addAll(contextLibraries);
            }
        }
        if (libraries.isEmpty() && isIosPlatform()) {
            libraries.addAll(FALLBACK_LIBRARIES_FOR_LOGOUT);
        }
        return new ArrayList<>(libraries);
    }

    private void logoutFromLibrarySafely(String library) {
        try {
            menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
            menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
            settingsScreen.openLibraries();
            if (!librariesScreen.isLibraryPresent(library)) {
                AqualityServices.getLogger().info(String.format("Library '%s' is not present on libraries screen. Skipping logout.", library));
                return;
            }
            librariesScreen.openLibrary(library);
            if (!accountScreen.isLogoutRequired()) {
                AqualityServices.getLogger().info(String.format("Library '%s' is already signed out.", library));
                return;
            }

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
                                accountScreen.getTextFromSignInButton().equals(AccountScreenSignInStatus.SIGN_IN.getDefaultLocalizedValue()),
                        Duration.ofMillis(AuthorizationTimeouts.USER_LOGGED_OUT.getTimeoutMillis()),
                        Duration.ofMillis(AuthorizationTimeouts.USER_LOGGED_OUT.getPollingMillis()),
                        Collections.singletonList(NoSuchElementException.class));
            });
        } catch (Exception e) {
            AqualityServices.getLogger().error(String.format("Failed to perform logout for library '%s': %s", library, e.getMessage()));
        }
    }

    protected void restartAppSafely() {
        if (!AqualityServices.isApplicationStarted()) {
            AqualityServices.getLogger().info("Application is not started. Skipping app restart before logout.");
            return;
        }
        restartApp();
    }

    protected void restartApp() {
        AqualityServices.getApplication().getDriver().closeApp();
        AqualityServices.getApplication().getDriver().launchApp();
    }

    private boolean isIosPlatform() {
        return AqualityServices.getApplication().getPlatformName().equals(PlatformName.IOS);
    }

    private boolean shouldSkipLogout(Scenario scenario) {
        if (!isIosPlatform()) {
            if (!scenario.getSourceTagNames().contains("@logout")) {
                AqualityServices.getLogger().info("Skipping logout hook for Android scenario without @logout tag");
                return true;
            }
            return false;
        }

        String iosLogoutMode = System.getProperty(IOS_LOGOUT_MODE_PROPERTY, IOS_LOGOUT_MODE_DEVICE_SWITCH).trim().toLowerCase();
        switch (iosLogoutMode) {
            case IOS_LOGOUT_MODE_EVERY_SCENARIO:
                return false;
            case IOS_LOGOUT_MODE_TAGGED:
                if (!scenario.getSourceTagNames().contains("@logout")) {
                    AqualityServices.getLogger().info("Skipping iOS logout hook for scenario without @logout tag (ios.logoutMode=tagged)");
                    return true;
                }
                return false;
            case IOS_LOGOUT_MODE_DEVICE_SWITCH:
                AqualityServices.getLogger().info("Skipping iOS logout to keep session active on current device (ios.logoutMode=device_switch)");
                return true;
            default:
                AqualityServices.getLogger().warn(String.format(
                        "Unknown value for %s='%s'. Falling back to '%s'.",
                        IOS_LOGOUT_MODE_PROPERTY,
                        iosLogoutMode,
                        IOS_LOGOUT_MODE_DEVICE_SWITCH));
                AqualityServices.getLogger().info("Skipping iOS logout to keep session active on current device (ios.logoutMode=device_switch)");
                return true;
        }
    }
}
