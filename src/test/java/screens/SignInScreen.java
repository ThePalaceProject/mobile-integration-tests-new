package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILink;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.KeyboardUtils;
import framework.utilities.LocatorUtils;
import io.appium.java_client.android.nativekey.AndroidKey;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class SignInScreen extends Screen {
    private final ITextBox txbLibraryCard = getElementFactory().getTextBox(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout[contains(@resource-id, \"authBasicUser\")]//android.widget.EditText")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField"))), "Library card tex box");
    private final ITextBox txbPassword = getElementFactory().getTextBox(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.EditText[@text=\"Password\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeSecureTextField[@value=\"Password\"]"))), "Password text box");
    private final IButton btnSignIn = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Button[@text=\"Sign in\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Sign in\"]"))), "Sign in button");
    private final ILink linkLicAgreement = getElementFactory().getLink(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@text, \"User License Agreement\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[contains(@name, \"User License Agreement\")]"))), "License Agreement link");
    private final IButton btnDeleteIos = getElementFactory().getButton(By.xpath("//XCUIElementTypeKey[@name=\"delete\"]"), "Delete button");

    public SignInScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[contains(@text, \"sign in\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText[@name=\"Sign in\"]"))), "Sign in screen");
    }

    public boolean isLibraryCardDisplayed() {
        return txbLibraryCard.state().waitForDisplayed();
    }

    public boolean isPasswordDisplayed() {
        return txbPassword.state().waitForDisplayed();
    }

    public boolean isSignInBtnDisplayed() {
        return btnSignIn.state().waitForDisplayed();
    }

    public boolean isLinkToTheLicenseAgreementDisplayed() {
        return linkLicAgreement.state().waitForDisplayed();
    }

    public String getTextFromLibraryCard() {
        return txbLibraryCard.getText();
    }

    public String getTextFromPassword() {
        return txbPassword.getText();
    }

    public void enterPassword(String password) {
        txbPassword.clearAndType(password);
    }

    public void enterLibraryCard(String libCard) {
        txbLibraryCard.clearAndType(libCard);
    }

    public void tapSignInBtn() {
        btnSignIn.click();
    }

    public void deleteSomeDataInLibCard() {
        ActionProcessorUtils.doForAndroid(() -> KeyboardUtils.pressKey(AndroidKey.DEL));
        ActionProcessorUtils.doForIos(btnDeleteIos::click);
    }

    public void setTextInLibCard(String text) {
        txbLibraryCard.sendKeys(text);
    }

    public void tapSignIn() {
        btnSignIn.click();
    }
}
