package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import com.google.common.collect.Ordering;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import java.util.ArrayList;
import java.util.List;

public class LibrariesScreen extends Screen {

    private final IButton btnAddLibrary = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("accountsMenuActionAccountAdd")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Add Library\"]"))), "Add library button");
    private final IButton btnDeleteLibrary = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Button[@text=\"Remove\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@label=\"Delete\"]"))), "Delete library button");

    private static final String LIBRARY_NAME_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/parent::XCUIElementTypeCell";
    private static final String LIBRARY_LOC_IOS = "//XCUIElementTypeTable/XCUIElementTypeCell";
    private static final String LIBRARY_NAME_ON_LIBRARY_SETTINGS_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]";
    private static final String LIBRARY_CELL_BY_LIBRARY_NAME_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/parent::XCUIElementTypeCell";

    private static final String LIBRARY_NAME_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]";
    private static final String LIBRARY_NAME_ON_LIBRARY_SETTINGS_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]";
    public static final String POPUP_MENU_BUTTON_BY_LIBRARY_NAME_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]/parent::android.widget.LinearLayout/following-sibling::android.widget.ImageButton";


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

    public boolean isAddLibraryBtnDisplayed() {
        return btnAddLibrary.state().isDisplayed();
    }

    public boolean isLibrariesAreSorted() {
        boolean areSorted = ActionProcessorUtils.doForIos(() -> {
            List<String> libraries = getLibrariesNames();
            libraries.remove(0);
            return Ordering.natural().isOrdered(libraries);
        });

        if(!areSorted) {
            areSorted = ActionProcessorUtils.doForAndroid(() -> Ordering.natural().isOrdered(getLibrariesNames()));
        }

        return areSorted;
    }

    public boolean isLibrarySettingsOpened(String libraryName) {
        return getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LIBRARY_NAME_ON_LIBRARY_SETTINGS_LOC_ANDROID, libraryName))),
                new IosLocator(By.xpath(String.format(LIBRARY_NAME_ON_LIBRARY_SETTINGS_LOC_IOS, libraryName)))), "Library name on library settings screen").state().isDisplayed();
    }

    public void deleteLibrary(String libraryName) {
        ActionProcessorUtils.doForIos(() -> {
            IButton libraryToSwitchLeft = getElementFactory().getButton(By.xpath(String.format(LIBRARY_CELL_BY_LIBRARY_NAME_LOC_IOS, libraryName)), "library cell");
            SwipeElementUtils.swipeElementLeft(libraryToSwitchLeft);
            btnDeleteLibrary.click();
        });

        ActionProcessorUtils.doForAndroid(() -> {
            getElementFactory().getButton(By.xpath(String.format(POPUP_MENU_BUTTON_BY_LIBRARY_NAME_LOC_ANDROID, libraryName)), "Popup menu button").click();
            btnDeleteLibrary.click();
        });
    }

    private IButton getLibraryButton(String libraryName) {
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LIBRARY_NAME_LOC_ANDROID, libraryName))),
                new IosLocator(By.xpath(String.format(LIBRARY_NAME_LOC_IOS, libraryName)))), libraryName);
    }

    private List<String> getLibrariesNames() {
        List<ILabel> libraries = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(LIBRARY_NAME_LOC_ANDROID)),
                new IosLocator(By.xpath(LIBRARY_LOC_IOS))), ElementType.LABEL);
        List<String> names = new ArrayList<>();
        libraries.forEach(library -> names.add(library.getText().toLowerCase()));
        return names;
    }
}
