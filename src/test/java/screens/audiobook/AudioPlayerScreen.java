package screens.audiobook;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.selenium.core.elements.ElementState;
import constants.appattributes.IosAttributes;
import enums.localization.catalog.TimerKeys;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.DateUtils;
import framework.utilities.LocatorUtils;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AudioPlayerScreen extends Screen {

    private final PlaybackSpeedScreen playbackSpeedScreen;
    private final SleepTimerScreen sleepTimerScreen;

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
    private final IButton btnToc = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_menu_toc")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar//XCUIElementTypeButton[@label=\"Table of contents\"]"))), "TOC button");
    private final ILabel lblChapterName = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_spine_element")),
            new IosLocator(By.xpath("(//XCUIElementTypeStaticText[@name=\"progress_rightLabel\"])[1]"))), "Chapter name label");
    private final IButton btnPlaybackSpeed = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_menu_playback_rate_image")),
            new IosLocator(By.xpath("//XCUIElementTypeToolbar//XCUIElementTypeButton"))), "Playback speed button");
    private final ILabel lblRightTime = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_time_maximum")),
            new IosLocator(By.xpath("(//XCUIElementTypeStaticText[@name=\"progress_rightLabel\"])[2]"))), "Right time label");
    private final IButton btnSkipAhead = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_jump_forwards")),
            new IosLocator(By.name("skip_forward"))), "Skip ahead button");
    private final IButton btnSkipBehind = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_jump_backwards")),
            new IosLocator(By.name("skip_back"))), "Skip behind button");
    private final IButton btnSleepTimer = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_menu_sleep_image")),
            new IosLocator(By.xpath("//XCUIElementTypeToolbar//XCUIElementTypeButton[3]"))), "Sleep timer button");
    private final IButton btnSlider = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.SeekBar")),
            new IosLocator(By.xpath("//XCUIElementTypeOther[@name=\"progress_grip\"]"))), "Slider");
    private final ILabel lblPlaybackProgress = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.SeekBar")),
            new IosLocator(By.xpath("//XCUIElementTypeOther[@name = \"progress_background\"]"))), "Playback progress");
    private final ILabel lblLineRemaining = getElementFactory().getLabel(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"hours\")]"), "Line remaining");
    private final IButton btnPlaySpeed = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@resource-id, \"playback_rate_text\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[contains(@name, \"speed\")]"))), "Button play speed");
    private final IButton btnBookmark = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_menu_add_bookmark")),
            new IosLocator(By.name("Add Bookmark"))), "Bookmark icon");
    private final ILabel lblBookmarkAdded = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.name("Bookmark added"))), "Bookmark added message");

    private static final String AUDIOBOOK_NAME_LOCATOR_ANDROID = "//android.widget.TextView[@text=\"%s\"]";
    private static final String SLEEP_TIMER_LOC_ANDROID = "//*[contains(@resource-id, \"player_menu_sleep\") and @content-desc=\"Set Your Sleep Timer. The Sleep Timer Is Currently Set To Sleep At %s\"]";
    private static final String PLAYBACK_SPEED_LOC_ANDROID = "//*[contains(@resource-id, \"player_menu_playback_rate_text\") and @text=\"%sx\"]";

    private static final String AUDIOBOOK_NAME_LOCATOR_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]";
    private static final String TIME_IN_HOURS_LEFT_LOCATOR_IOS = "//XCUIElementTypeToolbar//XCUIElementTypeButton[@name=\"%d hour and %d minutes until playback pauses\"]";
    private static final String TIME_IN_MINUTES_LEFT_LOCATOR_IOS = "//XCUIElementTypeToolbar//XCUIElementTypeButton[@name=\"%d minutes and %d seconds until playback pauses\"]";
    private static final String TIME_IN_SECONDS_LEFT_LOCATOR_IOS = "//XCUIElementTypeToolbar//XCUIElementTypeButton[@name=\"%d seconds until playback pauses\"]";
    private static final String PLAYBACK_SPEED_LOC_IOS = "//XCUIElementTypeToolbar//XCUIElementTypeButton[contains(@name, \"%s\")]";

    public AudioPlayerScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.ImageView[@content-desc=\"Play\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeButton[@label=\"Play\"]"))), "Audio player screen");
        playbackSpeedScreen = new PlaybackSpeedScreen();
        sleepTimerScreen = new SleepTimerScreen();
    }

    private static final Map<String, String> speedNameIos = Stream.of(
            new String[]{"2.0", "Two times normal speed. Fastest."},
            new String[]{"0.75", "Three quarters of normal speed. Slower."},
            new String[]{"1.25", "One and one quarter faster than normal speed."},
            new String[]{"1.5", "One and a half times faster than normal speed."}
    ).collect(Collectors.toMap(data -> data[0], data -> data[1]));

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
        Duration leftTime = ActionProcessorUtils.doForAndroid(() -> DateUtils.getDuration(lblLeftTime.getText()));

        if(leftTime == null) {
            leftTime = ActionProcessorUtils.doForIos(() -> DateUtils.getDuration(lblLeftTime.getAttribute(IosAttributes.VALUE)));
        }

        return leftTime;
    }

    public boolean isAudiobookNamePresent(String audiobookName) {
        return getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(AUDIOBOOK_NAME_LOCATOR_ANDROID, audiobookName))),
                new IosLocator(By.xpath(String.format(AUDIOBOOK_NAME_LOCATOR_IOS, audiobookName)))), "audiobook name").state().waitForDisplayed();
    }

    public void openToc() {
        btnToc.click();
    }

    public String getChapterName() {
        String chapterName = ActionProcessorUtils.doForIos(() -> lblChapterName.getAttribute(IosAttributes.VALUE));

        if(chapterName == null) {
            chapterName = ActionProcessorUtils.doForAndroid(lblChapterName::getText);
        }

        return chapterName;
    }

    public void openPlaybackSpeed() {
        btnPlaybackSpeed.click();
    }

    public PlaybackSpeedScreen getPlaybackSpeedAudiobookScreen() {
        return playbackSpeedScreen;
    }

    public Duration getRightTime() {
        Duration rightTime = ActionProcessorUtils.doForIos(() -> DateUtils.getDuration(lblRightTime.getAttribute(IosAttributes.VALUE)));

        if(rightTime == null) {
            rightTime = ActionProcessorUtils.doForAndroid(() -> DateUtils.getDuration(lblRightTime.getText()));
        }

        return rightTime;
    }

    public void skipAhead() {
        btnSkipAhead.click();
    }

    public void skipBehind() {
        btnSkipBehind.click();
    }

    public void openSleepTimer() {
        btnSleepTimer.click();
    }

    public SleepTimerScreen getSleepTimerAudiobookScreen() {
        return sleepTimerScreen;
    }

    public void stretchPlaySliderForward() {
        int startX = AqualityServices.getApplication().getDriver().findElement(btnSlider.getLocator()).getLocation().getX();
        int startY = AqualityServices.getApplication().getDriver().findElement(btnSlider.getLocator()).getLocation().getY();
        int endX = AqualityServices.getApplication().getDriver().findElement(lblPlaybackProgress.getLocator()).getSize().width / 2;
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.press(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, startY)).release().perform();
    }

    public void stretchPlaySliderToTheEnd() {
        int startX = AqualityServices.getApplication().getDriver().findElement(btnSlider.getLocator()).getLocation().getX();
        int startY = AqualityServices.getApplication().getDriver().findElement(btnSlider.getLocator()).getLocation().getY();
        double endX = AqualityServices.getApplication().getDriver().findElement(lblPlaybackProgress.getLocator()).getSize().width * 0.9;
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.press(PointOption.point(startX, startY)).moveTo(PointOption.point((int) endX, startY)).release().perform();
    }

    public boolean isLineRemainingDisplayed() {
        return lblLineRemaining.state().isDisplayed();
    }

    public String getPlaySpeedValue() {
        return btnPlaySpeed.getText();
    }

    public boolean isTimerSetTo(TimerKeys timerSetting) {
        String timerSettingName = timerSetting.getDefaultLocalizedValue();
        timerSettingName = timerSettingName.replace("file", "File");
        return getElementFactory().getButton(By.xpath(String.format(SLEEP_TIMER_LOC_ANDROID, timerSettingName)), timerSettingName, ElementState.EXISTS_IN_ANY_STATE).state().waitForDisplayed();
    }

    public boolean isTimerEqualTo(Duration chapterLength) {
        int seconds = (int) chapterLength.getSeconds() % 60;
        int minutes = (int) (chapterLength.toMinutes() >= 60 ? chapterLength.toMinutes() % 60 : chapterLength.toMinutes());
        return getElementFactory().getButton(By.xpath(String.format(TIME_IN_HOURS_LEFT_LOCATOR_IOS,
                (int) chapterLength.toHours(), minutes)), "Timer").state().isDisplayed() ||
                getElementFactory().getButton(By.xpath(String.format(TIME_IN_MINUTES_LEFT_LOCATOR_IOS, minutes, seconds)), "Timer").state().isDisplayed() ||
                getElementFactory().getButton(By.xpath(String.format(TIME_IN_SECONDS_LEFT_LOCATOR_IOS, seconds)), "Timer").state().isDisplayed();
    }

    public void stretchPlaySliderBack() {
        double startX = AqualityServices.getApplication().getDriver().findElement(btnSlider.getLocator()).getLocation().getX();
        double startY = AqualityServices.getApplication().getDriver().findElement(btnSlider.getLocator()).getLocation().getY();
        double endX = AqualityServices.getApplication().getDriver().findElement(lblPlaybackProgress.getLocator()).getLocation().getX();
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.longPress(PointOption.point((int) startX, (int) startY)).moveTo(PointOption.point((int) endX, (int) startY)).release().perform();
    }

    public void tapOnPlayBarForward() {
        double xPositionFurtherFromCenter = lblPlaybackProgress.getElement().getCenter().x * 1.25;
        double yPosition = lblPlaybackProgress.getElement().getCenter().y;
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.tap(PointOption.point((int) xPositionFurtherFromCenter, (int) yPosition)).perform();
    }

    public void tapOnPlayBarBackward() {
        double xPositionCloserThanCenter = lblPlaybackProgress.getElement().getCenter().x * 0.2;
        double yPosition = lblPlaybackProgress.getElement().getCenter().y;
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.tap(PointOption.point((int) xPositionCloserThanCenter, (int) yPosition)).perform();
    }

    public boolean isPlaybackSpeedPresent(String playbackSpeed) {
        boolean isPresent = ActionProcessorUtils.doForIos(() -> {
            String speedOptionName = speedNameIos.get(playbackSpeed);
            return getElementFactory().getButton(By.xpath(String.format(PLAYBACK_SPEED_LOC_IOS, speedOptionName)), speedOptionName).state().waitForDisplayed();
        });

        if(!isPresent) {
            isPresent = ActionProcessorUtils.doForAndroid(() ->
                    getElementFactory().getButton(By.xpath(String.format(PLAYBACK_SPEED_LOC_ANDROID, playbackSpeed)), "Playback speed").state().waitForDisplayed());
        }

        return isPresent;
    }

    public void tapBookmarkIcon() {
        btnBookmark.click();
    }

    public boolean isBookmarkAddedMessageDisplayed() {
        return lblBookmarkAdded.state().waitForDisplayed();
    }
}
