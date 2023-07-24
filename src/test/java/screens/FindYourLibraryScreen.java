package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import com.google.common.collect.Ordering;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class FindYourLibraryScreen extends Screen {
    private final IButton btnAddLib = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout//android.widget.TextView[@text=\"Add Library\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"Add Library\"]"))), "Add library button");
    private final CreatingLibraryLocator libraryLocator = (index ->
            getElementFactory().getLabel(LocatorUtils.getLocator(
                    new AndroidLocator(By.xpath(String.format("//android.widget.LinearLayout//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[%d]/android.widget.LinearLayout/android.widget.TextView[1]", index))),
                    new IosLocator(By.xpath(String.format("//XCUIElementTypeSheet//XCUIElementTypeScrollView[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[%d]/XCUIElementTypeButton", index)))), "Library"));
    private final IButton btnCancel = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Cancel\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeSheet//XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"Cancel\"]"))), "Close button");

    private static final String LIBRARY_NAME_IOS = "//XCUIElementTypeSheet//XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"%s\"]";
    private static final String LIBRARY_NAME_ANDROID = "//android.widget.LinearLayout/android.widget.TextView[@text=\"%s\"]";

    public FindYourLibraryScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.LinearLayout//android.widget.TextView[@text=\"Add Library\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeSheet[@name=\"Find Your Library\"]"))), "Find your library screen in Catalog");
    }

    public void tapAddLibrary() {
        btnAddLib.click();
    }

    public void tapToLibrary(String libName) {
        getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LIBRARY_NAME_ANDROID, libName))),
                new IosLocator(By.xpath(String.format(LIBRARY_NAME_IOS, libName)))), "Library button").click();
    }

    public boolean isSortingAlphabetical(int amountOfLibraries) {
        List<String > libraries = getListOfLibraries(amountOfLibraries);
        return Ordering.natural().isOrdered(libraries);
    }

    public void tapCancelBtn() {
        btnCancel.click();
    }

    private List<String > getListOfLibraries(int listSize) {
        List<String > libraries = new ArrayList<>();
        int index = 1;
        int end = 0;
        while (end <= listSize) {
            ILabel lblLibrary = libraryLocator.createLbl(index);
            libraries.add(lblLibrary.getText());
            index+=2;
            end++;
        }
        return libraries;
    }

    @FunctionalInterface
    interface CreatingLibraryLocator {
        ILabel createLbl(int index);
    }
}
