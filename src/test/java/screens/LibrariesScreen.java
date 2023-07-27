package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

public class LibrariesScreen extends Screen {

    private final IButton btnAddLibrary = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("accountsMenuActionAccountAdd")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Add Library\"]"))), "Add library button");

    private static final String LIBRARY_NAME_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/parent::XCUIElementTypeCell";
    private static final String LIBRARY_NAME_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]";


    public LibrariesScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Libraries\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Libraries\"]"))), "Libraries screen");
    }

    public boolean isLibraryPresent(String libraryName) {
        return getLibraryName(libraryName).state().waitForDisplayed();
    }

    private IButton getLibraryName(String libraryName) {
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LIBRARY_NAME_LOC_ANDROID, libraryName))),
                new IosLocator(By.xpath(String.format(LIBRARY_NAME_LOC_IOS, libraryName)))), libraryName);
    }

    public void addLibrary() {
        btnAddLibrary.click();
    }

    public void openLibrary(String libraryName) {
        ActionProcessorUtils.doForAndroid(() -> getLibraryButton(libraryName).click());
        ActionProcessorUtils.doForIos(() -> {
            Point point = getLibraryButton(libraryName).getElement().getCenter();
            TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
            action.tap(PointOption.point(point)).perform();
        });
    }

    private IButton getLibraryButton(String libraryName) {
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LIBRARY_NAME_LOC_ANDROID, libraryName))),
                new IosLocator(By.xpath(String.format(LIBRARY_NAME_LOC_IOS, libraryName)))), libraryName);
    }
}
