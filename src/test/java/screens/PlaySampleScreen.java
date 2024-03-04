package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class PlaySampleScreen extends Screen {

    private final ILabel lblTimeDuration = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@resource-id, \"player_remaining_time\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"left\")]"))), "Time duration");
    private final IButton btnRewinding = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[contains(@resource-is, \"player_jump_forwards\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[contains(@name, \"30 Seconds\")]"))), "30 Seconds rewind");
    private final IButton btnPlay = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[@content-desc=\"Play\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Play\"]"))), "Play button");
    private final IButton btnPause = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[@content-desc=\"Pause\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Pause\"]"))), "Pause button");
    private final IButton btbBack = getElementFactory().getButton(By.xpath("//android.view.ViewGroup[contains(@content-desc=\"toolbar\")]/android.widget.ImageButton"), "Back button");

    private static final String BOOK_NAME_LOCATOR_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]";

    public static final String BOOK_NAME_LOCATOR_ANDROID = "//android.widget.TextView[@text=\"%s\"]";

    public PlaySampleScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("")),
                new IosLocator(By.xpath(""))), "Play sample screen");
    }

    public boolean isBookTitleDisplayed(String bookName) {
        ILabel lblBookName = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_NAME_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(String.format(BOOK_NAME_LOCATOR_IOS, bookName)))), "Book name");
        return lblBookName.state().waitForDisplayed();
    }

    public boolean isTimeDurationDisplayed() {
        return lblTimeDuration.state().waitForDisplayed();
    }

    public boolean isRewindingDisplayed() {
        return btnRewinding.state().waitForDisplayed();
    }

    public boolean isPlayButtonDisplayed() {
        return btnPlay.state().waitForDisplayed();
    }

    public boolean isPauseButtonDisplayed() {
        return btnPause.state().waitForDisplayed();
    }

    public void clickPlayButton() {
        btnPlay.click();
    }

    public void clickPauseButton() {
        btnPause.click();
    }

    public void clickBackButton() {
        btbBack.click();
    }
}
