package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class WelcomeScreen extends Screen {

    private final IButton btnFindYourLibrary = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Button")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Find Your Library\"]"))), "Find your library button");

    public WelcomeScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.Button[contains(@resource-id,\"selectionButton\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Find Your Library\"]"))), "Welcome screen");
    }

    public boolean isWelcomeScreenOpened() {
        return btnFindYourLibrary.state().waitForDisplayed();
    }

    public void tapFindYourLibraryBtn() {
        btnFindYourLibrary.click();
    }
}
