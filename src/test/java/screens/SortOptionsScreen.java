package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import constants.localization.sortoptions.SortByKeys;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class SortOptionsScreen extends Screen {

    private final IButton btnSortBy = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.Button")),
            new IosLocator(By.xpath("(//XCUIElementTypeCollectionView/preceding-sibling::XCUIElementTypeOther//XCUIElementTypeButton)[1]"))), "Sort by button");

    private static final String SORT_SELECTION_ANDROID = "//*[contains(@resource-id,\"select_dialog_listview\")]";
    private static final String SORT_SELECTION_IOS = "//XCUIElementTypeButton[@name=\"%1$s\"]";

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

    private void setSortSelection(String value) {
        getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(SORT_SELECTION_ANDROID)),
                new IosLocator(By.xpath(SORT_SELECTION_IOS))), "Sorting value " + value).click();
    }

    @FunctionalInterface
    interface BtnGetVariantsOfSorting {
        IButton createBtn(String button);
    }
}
