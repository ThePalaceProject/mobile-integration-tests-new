package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.Screen;
import enums.localization.account.AccountScreenSignInStatus;
import enums.timeouts.BooksTimeouts;
import framework.configuration.Credentials;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.time.Duration;

public class AccountScreen extends Screen {

    private final ITextBox txbCard = getElementFactory().getTextBox(LocatorUtils.getLocator(
            new AndroidLocator(By.id("authBasicUserField")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField"))), "Library card text box");
    private final ITextBox txbPassword = getElementFactory().getTextBox(LocatorUtils.getLocator(
            new AndroidLocator(By.id("authBasicPassField")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeSecureTextField"))), "Password text box");
    private final IButton btnSignIn = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath(String.format(SIGN_IN_BTN_LOCATOR_ANDROID, AccountScreenSignInStatus.SIGN_IN.getDefaultLocalizedValue()))),
            new IosLocator(By.xpath(String.format(SIGN_IN_BTN_LOCATOR_IOS, AccountScreenSignInStatus.SIGN_IN.getDefaultLocalizedValue())))), "Sign in button");
    private final IButton btnSignOut = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath(String.format(SIGN_IN_BTN_LOCATOR_ANDROID, AccountScreenSignInStatus.SIGN_OUT.getDefaultLocalizedValue()))),
            new IosLocator(By.xpath(String.format(SIGN_IN_BTN_LOCATOR_IOS, AccountScreenSignInStatus.SIGN_OUT.getDefaultLocalizedValue())))),"Sign out button");
    private final ILabel lblLoading = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("accountLoginProgressBar")),
            new IosLocator(By.xpath(""))), "Sign in loading status bar");
    private final IButton btnSignInError = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("accountLoginButtonErrorDetails")),
            new IosLocator(By.xpath(""))), "Error info");
    private final IButton btnSignInAction = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id(BTN_SIGN_IN_ID_ANDROID)),
            new IosLocator(By.xpath(""))), "Log ... action");

    private static final String BTN_SIGN_IN_ID_ANDROID = "authBasicLogin";
    private static final String SIGN_IN_BTN_LOCATOR_ANDROID = "//*[contains(@resource-id,\"" + BTN_SIGN_IN_ID_ANDROID + "\") and @text=\"%1$s\"]";

    private static final String SIGN_IN_BTN_LOCATOR_IOS = "//XCUIElementTypeStaticText[@name=\"%1$s\"]\n";

    public AccountScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("auth")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar[@name=\"Account\"]"))), "Account screen");
    }

    public void enterCredentials (Credentials credentials) {
        ActionProcessorUtils.doForAndroid(() -> {
            txbCard.clearAndType(credentials.getBarcode());
            txbPassword.clearAndTypeSecret(credentials.getPin());
        });

        ActionProcessorUtils.doForIos(() -> {
            AqualityServices.getConditionalWait().waitFor(() -> btnSignIn.state().isDisplayed() || btnSignOut.state().isDisplayed());
            if(!btnSignOut.state().isDisplayed()) {
                txbCard.click();
                txbCard.clearAndType(credentials.getBarcode());
                txbPassword.clearAndTypeSecret(credentials.getPin());
            }
        });
    }

    public void tapSignIn() {
        btnSignIn.click();
    }

    public boolean isSignInSuccessful() {
        if(AqualityServices.getApplication().getPlatformName() == PlatformName.ANDROID) {
            lblLoading.state().waitForDisplayed();
            lblLoading.state().waitForNotDisplayed();
            AqualityServices.getConditionalWait().waitFor(() ->
                    btnSignOut.state().isDisplayed() || btnSignInError.state().isDisplayed(), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
            return getLoginButtonText().equals(AccountScreenSignInStatus.SIGN_OUT.getDefaultLocalizedValue());
        } else {
            return btnSignOut.state().isDisplayed();
        }
    }

    private String getLoginButtonText() {
        return btnSignInAction.state().waitForDisplayed() ? btnSignInAction.getText() : "";
    }
}
