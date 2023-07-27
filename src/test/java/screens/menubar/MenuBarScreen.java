package screens.menubar;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class MenuBarScreen extends Screen {

    private static final String BOTTOM_MENU_ELEMENT_LOC_IOS = "//XCUIElementTypeButton[@name=\"%1$s\"]";
    private static final String MENU_BAR_LOCATOR_IOS = String.format(BOTTOM_MENU_ELEMENT_LOC_IOS, MenuBar.CATALOG.getItemName())
            + "/parent::XCUIElementTypeTabBar";
    private static final String MENU_BAR_LOCATOR_ANDROID = "bottomNavigator";

    private static final CreatingTypeOfButton typeOfButton = (button ->
            AqualityServices.getElementFactory().getButton(LocatorUtils.getLocator(
                    new AndroidLocator(By.xpath(String.format("//android.view.ViewGroup//android.widget.FrameLayout//android.widget.TextView[@text=\"%s\"]", button))),
                    new IosLocator(By.xpath(String.format("//XCUIElementTypeTabBar/XCUIElementTypeButton[@name=\"%s\"]", button)))), String.format("%s", button)));

    public MenuBarScreen(){
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("bottomNavigator")),
                new IosLocator(By.xpath(String.format(MENU_BAR_LOCATOR_IOS, MenuBar.CATALOG.getItemName())
                        + "/parent::XCUIElementTypeTabBar"))), "Menu bar screen");
    }

    public void openBottomMenuTab(MenuBar menuItem) {
        getButton(menuItem).click();
    }

    public boolean isMenuBarDisplayed() {
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.id(MENU_BAR_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(MENU_BAR_LOCATOR_IOS))), "Menu bar").state().waitForDisplayed();
    }

    public String getTypeOfTab(String tabName) {
        return typeOfButton.createBtn(tabName).getText();
    }

    private IButton getButton(MenuBar menuItem) {
        String itemName = menuItem.getItemName();

        if (AqualityServices.getApplication().getPlatformName() == PlatformName.ANDROID) {
            return getElementFactory().getButton(By.id(itemName), itemName);
        } else {
            return getElementFactory().getButton(By.xpath(String.format(BOTTOM_MENU_ELEMENT_LOC_IOS, itemName)), itemName);
        }
    }

    @FunctionalInterface
    interface CreatingTypeOfButton {
        IButton createBtn(String button);
    }
}
