package screens;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class PrivacyPolicyScreen extends Screen {
    private final ILabel lblPrivacyPolicy = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Privacy Policy\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeOther[@name=\"Privacy Policy\"]"))), "Privacy Policy label");

    public PrivacyPolicyScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Privacy Policy\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar[@name=\"Privacy Policy\"]"))), "Privacy Policy screen");
    }

    public boolean isPrivacyPolicyScreenOpened() {
        return lblPrivacyPolicy.state().waitForDisplayed();
    }
}
