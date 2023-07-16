package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class SettingsScreen extends Screen {

    private final IButton btnLibraries = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@text, \"Libraries\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Libraries\"]"))), "Libraries button");
    private final ILabel lblSettings = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar"))), "Settings label");

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
}
