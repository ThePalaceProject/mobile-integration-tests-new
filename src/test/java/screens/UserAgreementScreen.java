package screens;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class UserAgreementScreen extends Screen {
    private static final String LINK_IOS = "//XCUIElementTypeLink[contains(@name, \"%s\")]";
    private final String LINK_ANDROID = "//android.widget.TextView[contains(@text, \"%s\")]";
    private final ILabel lblAgreement = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@text, \"License Agreement\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"License Agreement\")]"))), "User Agreement label");

    public UserAgreementScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("android.widget.TextView[@text=\"User Agreement\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar[@name=\"User Agreement\"]"))), "User Agreement screen");
    }

    public boolean isUserAgreementScreenOpened() {
        return lblAgreement.state().waitForDisplayed();
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
