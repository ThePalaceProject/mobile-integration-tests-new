package screens;

import aquality.appium.mobile.elements.ElementType;
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
    private final IButton btnCancel = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Cancel\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeSheet//XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"Cancel\"]"))), "Close button");

    private static final String LIBRARY_NAME_IOS = "//XCUIElementTypeSheet//XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"%s\"]";
    private static final String CURRENT_LIBRARY_NAME_LOCATOR_ANDROID = "//android.widget.LinearLayout/android.widget.TextView[@text=\"%s\"]";
    private static final String LIBRARY_NAME_LOCATOR_ANDROID = "//androidx.recyclerview.widget.RecyclerView[contains(@resource-id,\"recyclerView\")]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[1]";

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
                new AndroidLocator(By.xpath(String.format(CURRENT_LIBRARY_NAME_LOCATOR_ANDROID, libName))),
                new IosLocator(By.xpath(String.format(LIBRARY_NAME_IOS, libName)))), "Library button").click();
    }

    public boolean isSortingAlphabetical(int libraryAmount) {
        List<String> libraries = getListOfLibraries(libraryAmount);
        return Ordering.natural().isOrdered(libraries);
    }

    public void tapCancelBtn() {
        btnCancel.click();
    }

    private List<String > getListOfLibraries(int amount) {
        List<ILabel> libraryLabels = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(LIBRARY_NAME_LOCATOR_ANDROID)),
                new IosLocator(By.xpath("asd"))), ElementType.LABEL);

        List<String> libraries = new ArrayList<>();
        libraryLabels.stream().limit(amount).forEach(label -> libraries.add(label.getText().toLowerCase()));
        return libraries;
    }
}
