package screens.epub;

import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class SearchEpubScreen extends Screen {

    private final ITextBox txbSearchField = getElementFactory().getTextBox(LocatorUtils.getLocator(
            new AndroidLocator(By.id("search_src_text")),
            new IosLocator(By.xpath("//XCUIElementTypeTextField"))), "Search text box");

    public SearchEpubScreen () {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("search_src_text")),
                new IosLocator(By.xpath("//XCUIElementTypeTextField"))), "Search epub screen");
    }

    public boolean isSearchScreenOpened() {
        return txbSearchField.state().waitForDisplayed();
    }

    public void enterText(String text) {
        txbSearchField.sendKeys(text);
    }

    public String getTextFromSearchTxb() {
        return txbSearchField.getText();
    }
}
