package screens.epub;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class NavigationBarScreen extends Screen {

    private final IButton btnTOC = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@resource-id,\"readerMenuTOC\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[2]"))), "TOC button");

    public NavigationBarScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"readerToolbar\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar"))), "Navigation bar screen");
    }

    public void tapTOCBookmarksButton() {
        btnTOC.click();
    }
}
