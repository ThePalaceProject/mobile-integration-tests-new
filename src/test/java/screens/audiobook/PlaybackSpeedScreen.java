package screens.audiobook;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class PlaybackSpeedScreen extends Screen {

    private final IButton btnCancel = AqualityServices.getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Cancel\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Cancel\"]"))), "Cancel button");
    private final ILabel lblPlaybackSpeed = getElementFactory().getLabel(By.xpath("//XCUIElementTypeStaticText[@name=\"Playback Speed\"]"), "Playback speed label");

    private static final String PLAYBACK_SPEED_ANDROID = "//*[@text=\"%sx\"]";

    private static final String PLAYBACK_SPEED_IOS = "//XCUIElementTypeButton[@name=\"audiobookPlayer.speed.%1$s\"] | //XCUIElementTypeButton[contains(@name,\"%1$s\")] | //XCUIElementTypeButton[contains(@label,\"%1$s\")]";

    public PlaybackSpeedScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//androidx.recyclerview.widget.RecyclerView[contains(@resource-id,\"list\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Playback Speed\"]"))), "Playback speed screen");
    }

    public void selectPlaybackSpeed(String playbackSpeed) {
        ActionProcessorUtils.doForIos(() -> {
            getElementFactory().getButton(By.xpath(String.format(PLAYBACK_SPEED_IOS, playbackSpeed)), "Playback speed " + playbackSpeed).click();
        });
        ActionProcessorUtils.doForAndroid(() -> {
            getElementFactory().getButton(By.xpath(String.format(PLAYBACK_SPEED_ANDROID, playbackSpeed)), "Playback speed").click();
        });
    }

    public void closePlaybackScreen() {
        btnCancel.click();
    }

    public String getTextFromPlaybackSpeedLbl() {
        return lblPlaybackSpeed.getText();
    }

    public String getTextFromCancelBtn() {
        return btnCancel.getText();
    }
}
