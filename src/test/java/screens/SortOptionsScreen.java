package screens;

import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import enums.localization.sortoptions.SortByKeys;
import enums.localization.sortoptions.AvailabilityKeys;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class SortOptionsScreen extends Screen {

    private final IButton btnSortBy = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.Button")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Sort By:\"]/following-sibling::XCUIElementTypeButton"))), "Sort by button");
    private final IButton btnAvailability = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//*[contains(@resource-id,\"feedHeaderFacets\")]/android.widget.Button[1]")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[2]"))), "Availability button");
    private final IButton btnCollection = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[3]"))), "Collection button");

    private static final String SORT_SELECTION_ANDROID = "//*[contains(@resource-id,\"select_dialog_listview\")]//*[@text=\"%1$s\"]";
    private static final String SORT_SELECTION_IOS = "//XCUIElementTypeButton[@name=\"%1$s\"]";
    private static final String OPTIONS_IN_TABS_LOCATOR_IOS = "//XCUIElementTypeWindow/XCUIElementTypeOther[2]//XCUIElementTypeScrollView//XCUIElementTypeButton";

    private final BtnGetVariantsOfSorting btnVariantOfSorting = (button ->
            getElementFactory().getButton(LocatorUtils.getLocator(
                    new AndroidLocator(By.xpath(String.format("//android.widget.ListView/android.widget.CheckedTextView[@text=\"%s\"]", button))),
                    new IosLocator(By.xpath(String.format("//XCUIElementTypeOther//XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"%s\"]", button)))),
                    String.format("%s type of button", button)));

    public SortOptionsScreen(){
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//*[contains(@resource-id,\"feedHeaderFacets\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeCollectionView/preceding-sibling::XCUIElementTypeOther"))), "Sort options screen");
    }

    public void openSortBy() {
        btnSortBy.click();
    }

    public String getTypeVariantsOfBtn(String type) {
        IButton btnTypeOfSorting = btnVariantOfSorting.createBtn(type);
        return btnTypeOfSorting.getText();
    }

    public void changeSortByTo(SortByKeys key) {
        setSortSelection(key.getDefaultLocalizedValue());
    }

    public void openAvailability() {
        btnAvailability.click();
    }

    public void changeAvailabilityTo(AvailabilityKeys key) {
        setSortSelection(key.getDefaultLocalizedValue());
    }

    public void openCollection() {
        btnCollection.click();
    }

    public List<String> getOptionsInTabs() {
        List<String> options = new ArrayList<>();
        getElementFactory().findElements(By.xpath(OPTIONS_IN_TABS_LOCATOR_IOS), ElementType.BUTTON).forEach(option -> options.add(option.getText()));
        return options;
    }

    private void setSortSelection(String value) {
        getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(SORT_SELECTION_ANDROID, value))),
                new IosLocator(By.xpath(String.format(SORT_SELECTION_IOS, value)))), "Sorting value " + value).click();
    }

    @FunctionalInterface
    interface BtnGetVariantsOfSorting {
        IButton createBtn(String button);
    }
}
