package screens.pdf;

import aquality.appium.mobile.elements.Attributes;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class SettingsPdfScreen extends Screen {

    private final IButton btnFirstPage = getElementFactory().getButton(By.xpath("//android.widget.Button[@resource-id=\"firstPage\"]"), "First page button");
    private final IButton btnVerticalScrolling = getElementFactory().getButton(By.xpath("//android.widget.RadioButton[@resource-id=\"scrollVertical\"]"), "Vertical scrolling");

    public SettingsPdfScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.View[@resource-id=\"secondaryToolbar\"]")),
                new IosLocator(By.xpath(""))), "Settings screen");
    }

    public boolean isSettingsScreenOpened() {
        return btnFirstPage.state().waitForDisplayed();
    }

    public boolean isVerticalScrollingChosen() {
        return btnVerticalScrolling.getAttribute(Attributes.CHECKED).equals(Boolean.TRUE.toString());
    }
}
