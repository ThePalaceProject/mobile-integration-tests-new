package screens.pdf;

import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

import java.util.List;

public class SearchPdfScreen extends Screen {

    private final ITextBox txbSearchLine = getElementFactory().getTextBox(By.xpath("//XCUIElementTypeWindow/XCUIElementTypeOther[2]//XCUIElementTypeTextField"), "Search line");

    private static final String NUMBER_OF_FOUND_TEXT_LOCATOR = "//XCUIElementTypeCell/XCUIElementTypeStaticText[2]";
    private static final String FOUND_TEXT_NUMBER_LOCATOR = "//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"%d\"]";

    public SearchPdfScreen() {
        super(By.xpath("//XCUIElementTypeSearchField"), "Search pdf screen");
    }

    public void enterText(String text) {
        txbSearchLine.sendKeys(text);
    }

    public String getTextFromSearchTxb() {
        return txbSearchLine.getText();
    }

    public int openRandomFoundText() {
        int pageNumber = (int) (Math.random() * (getNumbersOfFoundTexts().size()) + 1);
        ILabel foundText = getElementFactory().getLabel(By.xpath(String.format(FOUND_TEXT_NUMBER_LOCATOR, pageNumber)), "Found text");
        foundText.click();
        return pageNumber;
    }

    private List<ILabel> getNumbersOfFoundTexts() {
        return getElementFactory().findElements(By.xpath(NUMBER_OF_FOUND_TEXT_LOCATOR), ElementType.LABEL);
    }

    public void deleteText() {
        txbSearchLine.clear();
    }

    public boolean isSearchFieldEmpty() {
        return txbSearchLine.getText().isEmpty();
    }
}
