package screens;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class AboutAppScreen extends Screen {

    private final ILabel lblAboutPalace = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Image[@text=\"The Palace Project\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeImage[@name=\"The Palace Project\"]"))), "About palace label");

    public AboutAppScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"About Palace\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar[@name=\"About Palace\"]"))), "About Palace screen");
    }

    public boolean isOpened() {
        return lblAboutPalace.state().waitForDisplayed();
    }
}