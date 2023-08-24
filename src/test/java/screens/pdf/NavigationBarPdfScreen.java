package screens.pdf;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class NavigationBarPdfScreen extends Screen {

    private final IButton btnToc = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@resource-id,\"readerMenuTOC\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeOther[1]//XCUIElementTypeButton[2]"))), "TOC button");
    private final IButton btnSearch = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeOther[2]//XCUIElementTypeButton[2]"))), "Search button");
    private final IButton btnBack = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageButton[@content-desc=\"Back\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name=\"Back\"]"))), "Back button");
    private final IButton btnSettings = getElementFactory().getButton(By.xpath("//android.widget.TextView[@content-desc=\"Settings\"]"), "Settings button");

    public NavigationBarPdfScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"pdf_toolbar\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar"))), "Navigation bar pdf screen");
    }

    public void tapTocBookmarksBarButton() {
        btnToc.click();
    }

    public void tapSearchButton() {
        btnSearch.click();
    }

    public void tapBackButton() {
        btnBack.click();
    }

    public void tapSettingsButton() {
        btnSettings.click();
    }
}
