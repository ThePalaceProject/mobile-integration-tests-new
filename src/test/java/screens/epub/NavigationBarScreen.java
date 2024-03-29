package screens.epub;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class NavigationBarScreen extends Screen {

    private final IButton btnBack = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"readerToolbar\")]/android.widget.ImageButton")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[1]"))), "Back button");

    private final IButton btnTOC = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Button[contains(@resource-id,\"readerMenuTOC\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name=\"viewBookmarksAndTocButton\"]"))), "TOC button");
    private final IButton btnAddBookmark = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("readerMenuAddBookmark")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name=\"Add Bookmark\"]"))), "Add Bookmark button");
    private final IButton btnDeleteBookmark = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Button[contains(@content-desc,\"Delete the bookmark\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name=\"Remove Bookmark\"]"))), "Delete bookmark button");
    private final IButton btnFontSettings = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Button[contains(@resource-id,\"readerMenuSettings\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name=\"Reader settings\"]"))), "Font settings");
    private final IButton btnSearch = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("readerMenuSearch")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name=\"search.button\"]"))), "Search button");

    public NavigationBarScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"readerToolbar\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar"))), "Navigation bar screen");
    }

    public void tapTOCBookmarksButton() {
        btnTOC.click();
    }

    public void returnToPreviousScreen() {
        btnBack.click();
    }

    public void tapAddBookmarkButton() {
        btnAddBookmark.click();
    }

    public boolean isBookmarkDisplayed() {
        return btnDeleteBookmark.state().waitForDisplayed();
    }

    public void tapDeleteBookmarkButton() {
        btnDeleteBookmark.click();
    }

    public void tapFontSettingsButton() {
        btnFontSettings.click();
    }

    public void tapSearchIcon() {
        btnSearch.click();
    }
}
