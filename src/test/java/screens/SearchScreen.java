package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.KeyboardUtils;
import framework.utilities.LocatorUtils;
import io.appium.java_client.android.nativekey.AndroidKey;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class SearchScreen extends Screen {

    private final ITextBox txbSearch = getElementFactory().getTextBox(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.AutoCompleteTextView[contains(@resource-id,\"search_src_text\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeSearchField"))), "Search field");
    private final IButton btnClearSearch = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[contains(@resource-id,\"search_close_btn\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"clear.button.text\"]"))), "Clear search field button");
    private final IButton btnBackIos = getElementFactory().getButton(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[1]"), "Back button ios");
    private final IButton btnDeleteIos = getElementFactory().getButton(By.xpath("//XCUIElementTypeKey[@name=\"delete\"]"), "Delete button");
    private final IButton btnSearchIos = getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@name=\"Search\"]"), "Search button on iOS");
    private final IButton btnK = getElementFactory().getButton(By.xpath("//XCUIElementTypeKey[@name=\"k\"]"), "K button");

    public SearchScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//*[contains(@resource-id,\"search_src_text\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Search\"]"))), "Search screen");
    }

    public boolean isSearchScreenOpened(){
        return txbSearch.state().waitForDisplayed();
    }

    public void setSearchedText(String text) {
        txbSearch.sendKeys(text);
    }

    public void applySearch(){
        ActionProcessorUtils.doForAndroid(() -> {
            KeyboardUtils.pressKey(AndroidKey.ENTER);
            KeyboardUtils.hideKeyboard();
        });
        ActionProcessorUtils.doForIos(btnSearchIos::click);
    }

    public void clearSearchField(){
        btnClearSearch.click();
    }

    public boolean isSearchFieldEmpty() {
        return txbSearch.getText().contains("Search");
    }

    public String getTextFromSearchField() {
        return txbSearch.getText();
    }

    public void deleteSomeData() {
        ActionProcessorUtils.doForAndroid(() -> KeyboardUtils.pressKey(AndroidKey.DEL));
        ActionProcessorUtils.doForIos(btnDeleteIos::click);
    }

    public void inputCharacterK() {
        ActionProcessorUtils.doForAndroid(() -> KeyboardUtils.pressKey(AndroidKey.K));
        ActionProcessorUtils.doForIos(btnK::click);
    }

    public boolean isSearchButtonClickable() {
        return btnSearchIos.state().isClickable();
    }

    public boolean isSearchLineDisplayed() {
        return txbSearch.state().waitForDisplayed();
    }

    public void closeSearchScreen() {
        ActionProcessorUtils.doForAndroid(() -> {
            btnClearSearch.click();
            if (txbSearch.state().isDisplayed())
                btnClearSearch.click();
        });
        ActionProcessorUtils.doForIos(btnBackIos::click);
    }

    public String getTextFromBackButton() {
        return btnBackIos.getText();
    }
}
