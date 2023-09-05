package screens.epub;

import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class FontAndBackgroundSettingsEpubScreen extends Screen {

    public FontAndBackgroundSettingsEpubScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.FrameLayout[contains(@resource-id,\"custom\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Sans font\"]"))), "Font and Background settings screen");
    }
}
