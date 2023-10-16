package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.time.Duration;

public class SettingsScreen extends Screen {

    private final IButton btnLibraries = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@text, \"Libraries\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Libraries\"]"))), "Libraries button");
    private final ILabel lblSettings = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar"))), "Settings label");
    private final IButton btnAboutPalace = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"About Palace\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"About App\"]"))), "About App button");
    private final IButton btnPrivacyPolicy = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Privacy Policy\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Privacy Policy\"]"))), "Privacy Policy button");
    private final IButton btnUserAgreement = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"User Agreement\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"User Agreement\"]"))), "User Agreement button");
    private final IButton btnSoftwareLicenses = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Software Licenses\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Software Licenses\"]"))), "Software Licenses screen");
    private final IButton btnTestMode = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Debug options\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Testing\"]"))), "Test mode button");
    private final IButton btnCommitAndroid = getElementFactory().getButton(By.xpath("//android.widget.TextView[@text=\"Commit\"]"), "Commit button Android");
    private final IButton btnPalaceVersionIos = getElementFactory().getButton(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"Palace version\")]"), "Palace version button");

    private static final String LIBRARY_LOCATOR_ANDROID = "//android.widget.LinearLayout//android.widget.TextView[@text=\"%s\"]";
    private static final String LIBRARY_LOCATOR_IOS = "//XCUIElementTypeTable//XCUIElementTypeStaticText[@name=\"%s\"]";
    private static final int TAP_COUNT_FOR_TEST_MODE = 8;
    private static final int DURATION_FOR_LONG_PRESS_FOR_OPENING_TEST_MODE = 9;

    public SettingsScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[contains(@text, \"App info\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar[@name=\"Settings\"]"))), "Settings screen");
    }

    public void openLibraries(){
        btnLibraries.click();
    }

    public boolean isScreenOpened() {
        return lblSettings.state().waitForDisplayed();
    }

    public void openAboutApp() {
        btnAboutPalace.click();
    }

    public void openPrivacyPolicy() {
        btnPrivacyPolicy.click();
    }

    public void openUserAgreement() {
        btnUserAgreement.click();
    }

    public void openSoftwareLic() {
        btnSoftwareLicenses.click();
    }

    public void openLibrary(String libraryName) {
        getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LIBRARY_LOCATOR_ANDROID, libraryName))),
                new IosLocator(By.xpath(String.format(LIBRARY_LOCATOR_IOS, libraryName)))), "Library " + libraryName).click();
    }

    public void openTestMode() {
        ActionProcessorUtils.doForAndroid(() -> {
            TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
            action.tap(TapOptions.tapOptions().withTapsCount(TAP_COUNT_FOR_TEST_MODE)
                            .withElement(ElementOption.element(btnCommitAndroid.getElement())))
                            .perform();
            btnTestMode.click();
        });

        ActionProcessorUtils.doForIos(() -> {
            TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
            action.longPress(LongPressOptions.longPressOptions()
                            .withElement(ElementOption.element(btnPalaceVersionIos.getElement()))
                            .withDuration(Duration.ofSeconds(DURATION_FOR_LONG_PRESS_FOR_OPENING_TEST_MODE)))
                    .release()
                    .perform();
            btnTestMode.click();
        });
    }

    public String getTextFromSettingsHeader() {
        return lblSettings.getText();
    }

    public String getTextFromLibrariesBtn() {
        return btnLibraries.getText();
    }
}
