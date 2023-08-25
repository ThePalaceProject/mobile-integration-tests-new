package screens.audiobook;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import enums.localization.catalog.TimerKeys;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class SleepTimerScreen extends Screen {

    private final IButton btnCancel = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Cancel\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeWindow/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther[2]//XCUIElementTypeButton"))), "Cancel button");

    private static final String SLEEP_TIMER_LOC_ANDROID = "//*[contains(@resource-id, \"player_sleep_item_view_name\") and @text=\"%s\"]";
    private static final String SLEEP_TIMER_LOC_IOS = "//XCUIElementTypeButton[@name=\"%s\"]";

    public SleepTimerScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[contains(@resource-id,\"player_sleep_item_view_name\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Sleep Timer\"]"))), "Sleep timer screen");
    }

    public void setTimer(TimerKeys timerSetting) {
        ActionProcessorUtils.doForIos(() -> {
            String buttonName = timerSetting.getDefaultLocalizedValue();
            getElementFactory().getButton(By.xpath(String.format(SLEEP_TIMER_LOC_IOS, buttonName)), buttonName).click();
        });

        ActionProcessorUtils.doForAndroid(() -> {
            String timerSettingName = timerSetting.getDefaultLocalizedValue().replace("Chapter", "chapter");
            getElementFactory().getButton(By.xpath(String.format(SLEEP_TIMER_LOC_ANDROID, timerSettingName)), timerSettingName).click();
        });
    }

    public void closeSleepTimer() {
        btnCancel.click();
    }

    public String getTextFromCancelBtn() {
        return btnCancel.getText();
    }
}
