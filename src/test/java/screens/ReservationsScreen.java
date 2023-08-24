package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.IElementFactory;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import enums.BookType;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ReservationsScreen extends Screen {

    private final ILabel lblReservations = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"mainToolbar\")]/android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText"))), "Reservations label");
    private final ILabel lblNoBooks = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("feedEmptyText")),
            new IosLocator(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))), "No books label");
    private final IButton btnSort = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.Button")),
            new IosLocator(By.xpath(""))), "Sorting button");

    private static final String ACTION_BUTTON_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]/following-sibling::android.widget.LinearLayout//*[@text=\"%s\"]";
    private static final String BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID = ACTION_BUTTON_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID + "/ancestor::android.view.ViewGroup/android.widget.TextView[1]";
    private static final String BOOK_LOCATOR_ANDROID = "//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout";
    private static final String BOOK_TITLE_ANDROID = BOOK_LOCATOR_ANDROID + "/android.view.ViewGroup/android.widget.TextView[1]";
    private static final String BOOK_AUTHOR_ANDROID = BOOK_LOCATOR_ANDROID + "/android.view.ViewGroup/android.widget.TextView[2]";

    private static final String ACTION_BUTTON_BY_BOOK_NAME_AND_BUTTON_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeButton[contains(@name,\"%s\")]";
    private static final String BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS = ACTION_BUTTON_BY_BOOK_NAME_AND_BUTTON_LOC_IOS + "/ancestor::XCUIElementTypeOther[2]/XCUIElementTypeStaticText[1]";
    private static final String BOOK_AUTHOR_IOS = "//XCUIElementTypeCell/XCUIElementTypeOther/XCUIElementTypeStaticText[2]";
    private static final String BOOK_TITLE_IOS = "//XCUIElementTypeCell/XCUIElementTypeOther/XCUIElementTypeStaticText[1]";

    public ReservationsScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Holds\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Reservations\"]"))), "Reservations screen");
    }

    public boolean isScreenOpened() {
        return lblReservations.state().waitForDisplayed();
    }

    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().isDisplayed();
    }

    public boolean isBookDisplayed(BookType bookType, String bookName, ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        return getBookNameLabelFromListOfBooks(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID, bookName, actionButtonString))),
                new IosLocator(By.xpath(String.format(BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS, bookName, actionButtonString))))).state().waitForDisplayed();
    }

    public void openBook(BookType bookType, String bookName, ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        ILabel lblBookName = getBookNameLabelFromListOfBooks(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID, bookName, actionButtonString))),
                new IosLocator(By.xpath(String.format(BOOK_NAME_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS, bookName, actionButtonString)))));
        lblBookName.click();
    }

    public boolean isHoldsScreenOpened() {
        return lblReservations.state().waitForDisplayed();
    }

    public List<String> getListOfAuthors() {
        List<String> bookAuthors = new ArrayList<>();
        List<IElement> listOfBooks = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_AUTHOR_ANDROID)),
                new IosLocator(By.xpath(BOOK_AUTHOR_IOS))), ElementType.LABEL, ElementsCount.ANY, ElementState.EXISTS_IN_ANY_STATE);
        listOfBooks.forEach(book -> bookAuthors.add(book.getText()));
        return bookAuthors;
    }

    public List<String> getListOfTitles() {
        List<String> bookNames = new ArrayList<>();
        List<IElement> listOfBooks = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_TITLE_ANDROID)),
                new IosLocator(By.xpath(BOOK_TITLE_IOS))), ElementType.LABEL, ElementsCount.ANY, ElementState.EXISTS_IN_ANY_STATE);
        listOfBooks.forEach(book -> bookNames.add(book.getText()));
        return bookNames;
    }

    public String getTextFromHoldsHeader() {
        return lblReservations.getText();
    }

    public String getTextFromInformationLbl() {
        return lblNoBooks.getText();
    }

    public String getNameOfSorting() {
        return btnSort.getText();
    }

    public void sortBy() {
        btnSort.click();
    }

    private ILabel getBookNameLabelFromListOfBooks(By bookNameLoc) {
        IElementFactory elementFactory = AqualityServices.getElementFactory();
        ILabel lblBookName = elementFactory.getLabel(bookNameLoc, "Book Name label");
        lblBookName.state().waitForDisplayed();
        return lblBookName;
    }
}
