package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ILink;
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
    private final IButton btnApproveSignOut = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Sign out\"]"))),"Sign in approve");
    private final IButton btnLogInError = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("accountLoginButtonErrorDetails")),
            new IosLocator(By.xpath(""))), "Error info");
    private final ILabel lblForgotPassword = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("resetPasswordLabel")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"Forgot your password\")]"))), "Forgot your password label");
    private final ILink lnkLicenseAgreement = getElementFactory().getLink(LocatorUtils.getLocator(
            new AndroidLocator(By.id("accountEULA")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[contains(@name, \"License Agreement\")]"))), "License agreement link");
    private final IButton btnContentLicenses = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Content Licenses\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[7]/XCUIElementTypeStaticText"))), "Content licenses button");
    private final ILabel lblLibrariesAndPalaces = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@text, \"Libraries are palaces\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"Libraries are palaces\")]"))), "Libraries and palaces label");

    private static final String BTN_SIGN_IN_ID_ANDROID = "authBasicLogin";
    private static final String SIGN_IN_BTN_LOCATOR_ANDROID = "//*[contains(@resource-id,\"" + BTN_SIGN_IN_ID_ANDROID + "\") and @text=\"%1$s\"]";
    private static final String LIBRARY_NAME_LOCATOR_ANDROID = "accountCellTitle";

    private static final String SIGN_IN_BTN_LOCATOR_IOS = "//XCUIElementTypeStaticText[@name=\"%1$s\"]\n";
    private static final String LIBRARY_NAME_LOCATOR_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]";

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

    public boolean isLogoutRequired() {
        return btnSignOut.state().isDisplayed();
    }

    public String getTextFromCardTxb() {
        return txbCard.getText();
    }

    public String getTextFromPinTxb() {
        return txbPassword.getText();
    }

    public void tapSignOut() {
        btnSignOut.click();
    }

    public void tapApproveSignOut() {
        btnApproveSignOut.click();
    }

    public String getTextFromSignInButton() {
        return btnSignIn.getText();
    }

    public boolean isLogOutErrorDisplayed() {
        return btnLogInError.state().waitForDisplayed();
    }

    public boolean isLibraryOpened(String libraryName) {
        ILabel library = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.id(LIBRARY_NAME_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(String.format(LIBRARY_NAME_LOCATOR_IOS, libraryName)))), "Library label");
        return library.state().waitForDisplayed();
    }

    public boolean isLibraryCardFieldDisplayed() {
        return txbCard.state().waitForDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return txbPassword.state().waitForDisplayed();
    }

    public boolean isSignInBtnDisplayed() {
        return btnSignIn.state().waitForDisplayed();
    }

    public boolean isForgotPasswordMessageDisplayed() {
        return lblForgotPassword.state().waitForDisplayed();
    }

    public boolean isLicenseAgreementLinkDisplayed() {
        return lnkLicenseAgreement.state().waitForDisplayed();
    }

    public void openContentLicenses() {
        btnContentLicenses.click();
    }

    public boolean isContentLicOpened() {
        return lblLibrariesAndPalaces.state().waitForDisplayed();
    }
}
