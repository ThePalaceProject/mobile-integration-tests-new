package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class TestModeScreen extends Screen {
    private final IButton btnHiddenLibraries = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Switch[contains(@text,\"Enable Hidden Libraries\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeSwitch[@name=\"Enable Hidden Libraries\"]"))), "Enable hidden libraries button");

    public TestModeScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Debug options\"]\"")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[name=\"Testing\"]"))), "Test mode screen");
    }

    public void enableHiddenLibraries() {
        ActionProcessorUtils.doForAndroid(SwipeElementUtils::swipeDown);
        btnHiddenLibraries.click();
    }
}
