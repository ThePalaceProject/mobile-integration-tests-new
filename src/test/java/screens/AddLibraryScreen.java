package screens;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.Screen;
import com.google.common.collect.Ordering;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.KeyboardUtils;
import framework.utilities.LocatorUtils;
import framework.utilities.PlatformUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class AddLibraryScreen extends Screen {

    private final ILabel lblAddLibrary = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup/android.widget.TextView[@text=\"Add Library\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText"))), "Add library label");
    private final IButton btnSearch = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("accountMenuActionSearch")),
            new IosLocator(By.xpath(""))), "Search button");
    private final ITextBox txbSearchField = getElementFactory().getTextBox(LocatorUtils.getLocator(
            new AndroidLocator(By.id("search_src_text")),
            new IosLocator(By.xpath("//XCUIElementTypeSearchField"))), "Search field");
    private final IButton btnClearSearchField = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("search_close_btn")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"clear.button.text\"]"))), "Clear search field button");

    private static final String LIBRARY_BUTTON_LOCATOR_ANDROID = "//android.widget.TextView[contains(@text, \"%s\")]";
    private static final String LIBRARY_BUTTON_LOCATOR_IOS = "//XCUIElementTypeStaticText[contains(@name, \"%s\")]";


    public AddLibraryScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("accountRegistryTitle")),
                new IosLocator(By.xpath("//XCUIElementTypeSheet[@name=\"Add Your Library\"]"))), "Add Library screen");
    }

    public boolean isAddLibraryScreenOpened() {
        return lblAddLibrary.state().waitForDisplayed();
    }

    public void addLibraryViaSearch(String libraryName) {
        ActionProcessorUtils.doForAndroid(() -> {
            btnSearch.click();
            KeyboardUtils.hideKeyboard();
            txbSearchField.clearAndType(libraryName);
            state().waitForDisplayed();
            getLibraryButton(libraryName).click();
        });
        ActionProcessorUtils.doForIos(() -> {
            txbSearchField.click();
            txbSearchField.clearAndType(libraryName);
            getLibraryButton(libraryName).click();
        });
    }

    public void typeLibraryName(String libraryName) {
        ActionProcessorUtils.doForAndroid(() -> {
            btnSearch.click();
            KeyboardUtils.hideKeyboard();
            txbSearchField.clearAndType(libraryName);
            state().waitForDisplayed();
        });
        ActionProcessorUtils.doForIos(() -> {
            txbSearchField.click();
            txbSearchField.clearAndType(libraryName);
        });
    }

    public void clearSearchField(){
        btnClearSearchField.click();
    }

    public boolean isSearchFieldEmpty() {
        if(PlatformUtils.getPlatformName() == PlatformName.ANDROID) {
            return txbSearchField.getText().equals("Search accounts…");
        } else {
            return txbSearchField.getText().isEmpty();
        }
    }

    public boolean isLibraryDisplayed(String libraryName) {
        return getLibraryButton(libraryName).state().waitForDisplayed();
    }

    public boolean isLibraryContainsWord(String word) {
        List<String> libraries = getLibrariesNames();
        return libraries.stream().allMatch(library -> library.contains(word));
    }

    public boolean isSearchResultEmpty() {
        return getLibrariesNames().isEmpty();
    }

    public boolean isSortingOfLibrariesCorrect() {
        List<String > libraries = getLibrariesNames();
        libraries.remove(0);
        return Ordering.natural().isOrdered(libraries);
    }

    private IButton getLibraryButton(String libraryName) {
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_ANDROID, libraryName))),
                new IosLocator(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_IOS, libraryName)))), libraryName);
    }

    private List<String> getLibrariesNames() {
        List<IElement> libraries = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(LIBRARY_BUTTON_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(LIBRARY_BUTTON_LOCATOR_IOS))), ElementType.LABEL);

        List<String> names = new ArrayList<>();
        libraries.forEach(library -> names.add(library.getText().toLowerCase()));
        return names;
    }
}
