package screens;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class BooksScreen extends Screen {

    private final ILabel lblMyBooks = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"mainToolbar\")]/android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"My Books\"]"))), "My Books label");

    public BooksScreen(){
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"My Books\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText[@name=\"My Books\"]"))), "My Books screen");
    }

    public boolean isScreenOpened() {
        return lblMyBooks.state().waitForDisplayed();
    }
}
