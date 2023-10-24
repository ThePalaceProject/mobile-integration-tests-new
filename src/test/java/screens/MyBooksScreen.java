package screens;

import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import enums.BookType;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class MyBooksScreen extends Screen {

    private final ILabel lblMyBooks = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"mainToolbar\")]/android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"My Books\"]"))), "My Books label");
    private final ILabel lblNoBooks = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("feedEmptyMessage")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name,'Visit the Catalog')]"))), "No books present label");
    private final ILabel mainBooksElementCollection = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"feedContentRefresh\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView"))), "Elements collection container");

    private static final String BOOK_LOC_ANDROID = "//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout";
    private static final String BOOK_NAME_LOC_ANDROID = BOOK_LOC_ANDROID + "/android.view.ViewGroup/android.widget.TextView[1]";
    private static final String AUTHORS_NAME_LOC_ANDROID = BOOK_LOC_ANDROID + "/android.view.ViewGroup/android.widget.TextView[2]";
    private static final String BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]/following-sibling::android.widget.LinearLayout//*[@text=\"%s\"]";
    private static final String BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID = BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID + "/ancestor::android.view.ViewGroup/android.widget.TextView[1]";

    private static final String BOOK_LOC_IOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeButton";
    private static final String BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeButton[contains(@name,\"%s\")]";
    private static final String BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_LOC_IOS = BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS + "/ancestor::XCUIElementTypeButton/XCUIElementTypeStaticText[1]";
    private static final String BOOK_NAME_LOC_IOS = BOOK_LOC_IOS + "/XCUIElementTypeOther/XCUIElementTypeStaticText[1]";
    private static final String AUTHORS_NAME_LOC_IOS = BOOK_LOC_IOS + "/XCUIElementTypeOther/XCUIElementTypeStaticText[2]";

    public MyBooksScreen(){
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"My Books\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText[@name=\"My Books\"]"))), "My Books screen");
    }

    public boolean isScreenOpened() {
        return lblMyBooks.state().waitForDisplayed();
    }

    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().waitForDisplayed();
    }

    public boolean isBookDisplayed(BookType bookType, String bookName, ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        ILabel lblBookName = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID, bookName, actionButtonString))),
                new IosLocator(By.xpath(String.format(BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_LOC_IOS, bookName, actionButtonString)))), bookName);
        return lblBookName.state().waitForDisplayed();
    }

    public int getCountOfBooks() {
        return getListOfBooks().size();
    }

    public void refreshList() {
        SwipeElementUtils.swipeElementDown(mainBooksElementCollection);
    }

    public void openBook(BookType bookType, String bookName, ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        ILabel lblBookName = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID, bookName, actionButtonString))),
                new IosLocator(By.xpath(String.format(BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_LOC_IOS, bookName, actionButtonString)))), bookName);
        lblBookName.click();
    }

    public boolean areBooksDisplayed(List<String> listOfBooks) {
        boolean isDisplayed = true;
        List<String> listOfBooksNames = getNamesOfBooks();
        for (String book: listOfBooksNames) {
            if(!listOfBooks.contains(book)) {
                isDisplayed = false;
                break;
            }
        }
        return isDisplayed;
    }

    public List<String> getListOfAuthors() {
        List<IElement> listOfBooks = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(AUTHORS_NAME_LOC_ANDROID)),
                new IosLocator(By.xpath(AUTHORS_NAME_LOC_IOS))), ElementType.LABEL, ElementsCount.ANY, ElementState.EXISTS_IN_ANY_STATE);
        List<String> authorsNames = new ArrayList<>();
        listOfBooks.forEach(book -> authorsNames.add(book.getText()));
        return authorsNames;
    }

    public List<String> getListOfTitles() {
        return getNamesOfBooks();
    }

    private List<String> getNamesOfBooks() {
        List<String> bookNames = new ArrayList<>();
        List<IElement> listOfBooks = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_NAME_LOC_ANDROID)),
                new IosLocator(By.xpath(BOOK_NAME_LOC_IOS))), ElementType.LABEL, ElementsCount.ANY, ElementState.EXISTS_IN_ANY_STATE);
        listOfBooks.forEach(book -> bookNames.add(book.getText()));
        return bookNames;
    }

    private List<IElement> getListOfBooks() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_LOC_ANDROID)),
                new IosLocator(By.xpath(BOOK_LOC_IOS))), ElementType.LABEL, ElementsCount.ANY, ElementState.EXISTS_IN_ANY_STATE);
    }
}
