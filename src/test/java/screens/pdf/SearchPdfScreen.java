package screens.pdf;

import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.Screen;
import constants.appattributes.IosAttributes;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.KeyboardUtils;
import io.appium.java_client.android.nativekey.AndroidKey;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPdfScreen extends Screen {

    private final ITextBox txbSearchLine = getElementFactory().getTextBox(By.xpath("//XCUIElementTypeWindow/XCUIElementTypeOther[2]//XCUIElementTypeTextField"), "Search line");
    private final IButton btnDone = getElementFactory().getButton(By.xpath("//XCUIElementTypeWindow/XCUIElementTypeOther[2]//XCUIElementTypeButton"), "Apply search");
    private final IButton returnKey = getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@name=\"Return\"]"), "Return key");

    private static final String FOUND_TEXT_WITH_TEXT_LOCATOR_IOS = "//XCUIElementTypeStaticText[contains(@name, \"%s\")]";
    private static final String FOUND_TEXT_NUMBER_BY_FOUND_TEXT_LOCATOR_IOS = FOUND_TEXT_WITH_TEXT_LOCATOR_IOS + "/following-sibling::XCUIElementTypeStaticText";
    private static final String FOUND_TEXT_LOCATOR_IOS = "//XCUIElementTypeCell/XCUIElementTypeStaticText[1]";

    public SearchPdfScreen() {
        super(By.xpath("//XCUIElementTypeSearchField"), "Search pdf screen");
    }

    public void enterText(String text) {
        txbSearchLine.sendKeys(text);
    }

    public void applySearch() {
        ActionProcessorUtils.doForIos(returnKey::click);
        ActionProcessorUtils.doForAndroid(() -> KeyboardUtils.pressKey(AndroidKey.ENTER));
    }

    public String getTextFromSearchTxb() {
        return txbSearchLine.getText();
    }

    public String openFoundText(String text) {
        ILabel foundText = getElementFactory().getLabel(By.xpath(String.format(FOUND_TEXT_WITH_TEXT_LOCATOR_IOS,text)), text);
        ILabel foundTextNumber = getElementFactory().getLabel(By.xpath(String.format(FOUND_TEXT_NUMBER_BY_FOUND_TEXT_LOCATOR_IOS, text)), "Page number");
        String pageNumber = foundTextNumber.getText();
        foundText.click();
        return pageNumber;
    }

    public void deleteText() {
        txbSearchLine.clear();
    }

    public boolean isSearchFieldEmpty() {
        return txbSearchLine.getText().equals("Search");
    }

    public boolean isSearchPdfScreenOpened() {
        return txbSearchLine.state().waitForDisplayed();
    }

    public boolean isSearchResultEmpty() {
        return getFoundTexts().size() == 0;
    }

    public void closeSearchScreen() {
        btnDone.click();
    }

    public List<String> getListOfFoundTexts() {
        return getFoundTexts().stream().map(lblText -> lblText.getAttribute(IosAttributes.NAME)).collect(Collectors.toList());
    }

    private List<ILabel> getFoundTexts() {
        return getElementFactory().findElements(By.xpath(FOUND_TEXT_LOCATOR_IOS), ElementType.LABEL);
    }
}
