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

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlaybackSpeedScreen extends Screen {

    private final IButton btnCancel = AqualityServices.getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Cancel\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeWindow/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther[2]//XCUIElementTypeButton"))), "Cancel button");
    private final ILabel lblPlaybackSpeed = getElementFactory().getLabel(By.xpath("//XCUIElementTypeWindow/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]//XCUIElementTypeScrollView//XCUIElementTypeStaticText"), "Playback speed label");

    private static final String PLAYBACK_SPEED_ANDROID = "//*[@text=\"%sx\"]";

    private static final String PLAYBACK_SPEED_IOS = "//XCUIElementTypeScrollView//XCUIElementTypeAny/XCUIElementTypeButton[contains(@name,\"%s\")]";

    public PlaybackSpeedScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//androidx.recyclerview.widget.RecyclerView[contains(@resource-id,\"list\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Playback Speed\"]"))), "Playback speed screen");
    }

    private static final Map<String, String> speedNameIos = Stream.of(
            new String[]{"2.0", "Two times normal speed. Fastest."},
            new String[]{"0.75", "Three quarters of normal speed. Slower."},
            new String[]{"1.25", "One and one quarter faster than normal speed."},
            new String[]{"1.5", "One and a half times faster than normal speed."}
    ).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public void selectPlaybackSpeed(String playbackSpeed) {
        ActionProcessorUtils.doForIos(() -> {
            System.out.println("Speed: " + playbackSpeed);

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
