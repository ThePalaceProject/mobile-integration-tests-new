package screens;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class SoftwareLicensesScreen extends Screen {
    private final String LINK_IOS = "//XCUIElementTypeLink[contains(@name, \"%s\")]";
    private final String LINK_ANDROID = "//android.widget.TextView[contains(@text, \"%s\")]";

    private final ILabel lblLicense = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Palace License\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Palace License\"]"))), "Palace License label");

    public SoftwareLicensesScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Software Licenses\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar[@name=\"Software Licenses\"]"))), "Software licenses screen");
    }

    public boolean isSoftwareLicensesScreenOpened() {
        return lblLicense.state().waitForDisplayed();
    }

    public void scrollThePage(String link) {
        while (!isLinkDisplayed(link)) {
            SwipeElementUtils.swipeDown();
        }
    }

    public boolean isLinkAvailable(String link) {
        ILabel lblLink = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LINK_ANDROID, link))),
                new IosLocator(By.xpath(String.format(LINK_IOS, link)))), "Link");
        return lblLink.state().isClickable();
    }

    private boolean isLinkDisplayed(String link) {
        ILabel lblLink = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LINK_ANDROID, link))),
                new IosLocator(By.xpath(String.format(LINK_IOS, link)))), "Link");
        return lblLink.state().isDisplayed();
    }
}
