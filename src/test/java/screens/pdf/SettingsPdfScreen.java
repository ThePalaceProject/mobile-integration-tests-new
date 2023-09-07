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
    private final IButton btnHorizontalScrolling = getElementFactory().getButton(By.xpath("//android.widget.RadioButton[@resource-id=\"scrollHorizontal\"]"), "Horizontal scrolling");
    private final IButton btnWrappedScrolling = getElementFactory().getButton(By.xpath("//android.widget.RadioButton[@resource-id=\"scrollWrapped\"]"), "Wrapped scrolling");
    private final IButton btnLastPage = getElementFactory().getButton(By.xpath("//android.widget.Button[@resource-id=\"lastPage\"]"), "Last page button");
    private final IButton btnNoSpreads = getElementFactory().getButton(By.xpath("//android.widget.RadioButton[@resource-id=\"spreadNone\"]"), "No spreads");
    private final IButton btnOddSpreads = getElementFactory().getButton(By.xpath("//android.widget.RadioButton[@resource-id=\"spreadOdd\"]"), "Odd spreads");
    private final IButton btnEvenSpreads = getElementFactory().getButton(By.xpath("//android.widget.RadioButton[@resource-id=\"spreadEven\"]"), "Even spreads");

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

    public void tapGoToLastPage() {
        btnLastPage.click();
    }

    public void tapGoToFirstPage() {
        btnFirstPage.click();
    }

    public void tapVerticalScrolling() {
        btnVerticalScrolling.click();
    }

    public boolean isNoSpreadsAvailable() {
        return btnNoSpreads.state().isEnabled();
    }

    public boolean isOddSpreadsAvailable() {
        return btnOddSpreads.state().isEnabled();
    }

    public boolean isEvenSpreadsAvailable() {
        return btnEvenSpreads.state().isEnabled();
    }

    public void tapHorizontalScrolling() {
        btnHorizontalScrolling.click();
    }

    public boolean isHorizontalScrollingChosen() {
        return btnHorizontalScrolling.getAttribute(Attributes.CHECKED).equals(Boolean.TRUE.toString());
    }

    public void tapWrappedScrolling() {
        btnWrappedScrolling.click();
    }

    public boolean isWrappedScrollingChosen() {
        return btnWrappedScrolling.getAttribute(Attributes.CHECKED).equals(Boolean.TRUE.toString());
    }
}
