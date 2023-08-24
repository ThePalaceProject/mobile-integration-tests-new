package screens.audiobook;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import constants.appattributes.IosAttributes;
import framework.utilities.DateUtils;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.time.Duration;

public class AudioPlayerScreen extends Screen {

    private final IButton btnPlay = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[@content-desc=\"Play\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@label=\"Play\"]"))), "Play button");
    private final IButton btnBack = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageButton[@content-desc=\"Back\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[1]"))), "Back button");
    private final IButton btnPause = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[@content-desc=\"Pause\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@label=\"Pause\"]"))), "Pause button");
    private final ILabel lblLeftTime = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_time")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"progress_leftLabel\"]"))), "Left time label");

    private static final String AUDIOBOOK_NAME_LOCATOR_ANDROID = "//android.widget.TextView[@text=\"%s\"]";
    private static final String AUDIOBOOK_NAME_LOCATOR_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]";

    public AudioPlayerScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.ImageView[@content-desc=\"Play\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeButton[@label=\"Play\"]"))), "Audio player screen");
    }

    public boolean isPlayerOpened(String bookName) {
        btnPlay.state().waitForDisplayed();

        return getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(AUDIOBOOK_NAME_LOCATOR_ANDROID, bookName))),
                new IosLocator(By.xpath(String.format(AUDIOBOOK_NAME_LOCATOR_IOS,bookName)))), "Book name").state().waitForDisplayed();
    }

    public void returnToPreviousScreen() {
        btnBack.click();
    }

    public void tapPlayBtn() {
        btnPlay.click();
    }

    public boolean isPauseButtonPresent() {
        return btnPause.state().waitForDisplayed();
    }

    public void tapPauseBtn() {
        btnPause.click();
    }

    public boolean isPlayButtonPresent() {
        return btnPlay.state().waitForDisplayed();
    }

    public Duration getLeftTime() {
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.ANDROID) {
            return DateUtils.getDuration(lblLeftTime.getText());
        } else {
            return DateUtils.getDuration(lblLeftTime.getAttribute(IosAttributes.VALUE));
        }
    }
}
