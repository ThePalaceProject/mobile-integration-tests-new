package screens;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import enums.BookType;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.LocatorUtils;
import framework.utilities.PlatformUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class CatalogBooksScreen extends Screen {

    private final ILabel lblNameOfFirstBook = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.TextView[1]")),
            new IosLocator(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[1]"))), "Book name of the first book");
    private final ILabel lblNoResults = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("feedEmptyMessage")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"No Results\")]"))), "No results label");

    private static final String BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]/following-sibling::android.widget.LinearLayout/android.widget.Button[@text=\"%s\"]";
    private static final String BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID = BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID + "/ancestor::android.view.ViewGroup/android.widget.TextView[1]";
    private static final String BOOK_NAME_LOCATOR_ANDROID = "//android.view.ViewGroup[contains(@resource-id, \"bookCellIdle\")]/android.widget.TextView[contains(@resource-id, \"bookCellIdleTitle\")]";

    private static final String BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeButton[contains(@name,\"%s\")]";
    private static final String BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_IOS = BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS + "/ancestor::XCUIElementTypeOther/XCUIElementTypeStaticText[1]";
    private static final String BOOK_NAME_LOCATOR_IOS = "//XCUIElementTypeCell/XCUIElementTypeOther/XCUIElementTypeStaticText[1]";

    public CatalogBooksScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("feedWithGroups")),
                new IosLocator(By.xpath("//XCUIElementTypeCollectionView"))), "Catalog books screen");
    }

    public boolean isBookDisplayed(BookType bookType, String bookName, ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        String bookNameLocator = bookName;

        if(PlatformUtils.getPlatformName() == PlatformName.IOS && BookType.AUDIOBOOK == bookType) {
            bookNameLocator = bookNameLocator + ". Audiobook.";
        }

        ILabel lblBookName = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID, bookName, actionButtonString))),
                new IosLocator(By.xpath(String.format(BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_IOS, bookNameLocator, actionButtonString)))),
                "Book name label");

        return lblBookName.state().waitForDisplayed();
    }

    public boolean isBooksContainWord(String word){
        List<String> books = getBooksName();
        return books.stream().allMatch(library -> library.contains(word));
    }

    public List<String> getListOfBooks(){
        return getBooksName();
    }

    private List<String> getBooksName() {
        List<ILabel> lblBooks = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_NAME_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(BOOK_NAME_LOCATOR_IOS))), ElementType.LABEL);

        List<String> booksName = new ArrayList<>();
        lblBooks.forEach(book->booksName.add(book.getText().toLowerCase()));
        return booksName;
    }

    public String getNameOfFirstBook() {
        return lblNameOfFirstBook.getText();
    }

    public boolean isNoResults() {
        return lblNoResults.state().waitForDisplayed();
    }
}
