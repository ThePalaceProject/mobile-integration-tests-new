package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class MainToolBarScreen extends Screen {

    private final IButton btnSearch = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("search_button")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[2]"))), "Search button");
    private final ILabel lblCategoryName = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//*[contains(@resource-id,\"mainToolbar\")]/android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText"))), "Category name");
    private final IButton btnChooseAnotherLibrary = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//*[contains(@resource-id,\"mainToolbar\")]/android.widget.ImageView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name=\"librarySwitchButton\"]"))), "Change library account");

    public MainToolBarScreen(){
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//*[contains(@resource-id,\"mainToolbar\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar"))), "Main tool bar screen");
    }

    public void openSearchModal(){
        btnSearch.click();
    }

    public String getCategoryName() {
        return lblCategoryName.getText();
    }

    public void chooseAnotherLibrary() {
        btnChooseAnotherLibrary.click();
    }
}
