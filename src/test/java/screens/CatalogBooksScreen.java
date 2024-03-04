package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import constants.appattributes.IosAttributes;
import enums.BookType;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import enums.timeouts.BooksTimeouts;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import framework.utilities.PlatformUtils;
import models.AndroidLocator;
import models.CatalogBookModel;
import models.IosLocator;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CatalogBooksScreen extends Screen {

    private static final String AUDIOBOOK_LOCATOR_PART = ". Audiobook.";

    private final ILabel lblNameOfFirstBook = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.TextView[1]")),
            new IosLocator(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[1]"))), "Book name of the first book");
    private final ILabel lblNoResults = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("feedEmptyMessage")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"No Results\")]"))), "No results label");

    private static final String BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]/following-sibling::android.widget.LinearLayout/android.widget.Button[@text=\"%s\"]";
    private static final String BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID = BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID + "/ancestor::android.view.ViewGroup/android.widget.TextView[1]";
    private static final String BOOK_NAME_LOCATOR_ANDROID = "//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout/android.view.ViewGroup/android.widget.TextView[contains(@resource-id, \"bookCellIdleTitle\")]";
    private static final String AUTHOR_BY_BOOK_NAME_AND_BUTTON_LOCATOR_ANDROID = BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID + "/ancestor::android.view.ViewGroup/android.widget.TextView[contains(@resource-id, \"bookCellIdleAuthor\")]";
    private static final String PROGRESS_BAR_BY_BOOK_NAME_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]/following-sibling::android.widget.ProgressBar";
    private static final String BUTTON_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID = "//android.widget.TextView/following-sibling::android.widget.LinearLayout//*[@text=\"%s\"]";
    private static final String AUTHOR_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID = BUTTON_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID + "/ancestor::android.view.ViewGroup/android.widget.TextView[2]";
    private static final String BOOK_NAME_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID = BUTTON_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID + "/ancestor::android.view.ViewGroup/android.widget.TextView[1]";

    private static final String BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeButton[contains(@name,\"%s\")]";
    private static final String BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_IOS = BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS + "/ancestor::XCUIElementTypeOther/XCUIElementTypeStaticText[1]";
    private static final String BOOK_NAME_LOCATOR_IOS = "//XCUIElementTypeCell/XCUIElementTypeOther/XCUIElementTypeStaticText[1]";
    private static final String AUTHOR_BY_BOOK_NAME_AND_BUTTON_LOCATOR_IOS = BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS + "/ancestor::XCUIElementTypeOther[2]/XCUIElementTypeStaticText[2]";
    private static final String PROGRESS_BAR_BY_BOOK_NAME_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeProgressIndicator";
    private static final String BUTTON_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS = "//XCUIElementTypeStaticText/following-sibling::XCUIElementTypeOther/XCUIElementTypeButton[contains(@name,\"%s\")]";
    private static final String AUTHOR_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_IOS = BUTTON_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS + "/ancestor::XCUIElementTypeOther[2]/XCUIElementTypeStaticText[2]";
    private static final String BOOK_NAME_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_IOS = BUTTON_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS + "/ancestor::XCUIElementTypeOther[2]/XCUIElementTypeStaticText[1]";

    public CatalogBooksScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("feedWithGroups")),
                new IosLocator(By.xpath("//XCUIElementTypeCollectionView"))), "Catalog books screen");
    }

    public boolean isBookDisplayed(BookType bookType, String bookName, ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        String bookNameLocator = bookName;

        if(PlatformUtils.getPlatformName() == PlatformName.IOS && BookType.AUDIOBOOK == bookType) {
            bookNameLocator = bookNameLocator + AUDIOBOOK_LOCATOR_PART;
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

    public String getBookFromCatalogSection(){
        List<ILabel> bookLabels = getBooksLabels();
        Random random = new Random();
        int bookIndex = random.nextInt(bookLabels.size() - 3) + 4;
        ILabel bookName = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout/android.view.ViewGroup/android.widget.TextView[contains(@resource-id, \"bookCellIdleTitle\")]")),
                new IosLocator(By.xpath(String.format("//XCUIElementTypeCollectionView/XCUIElementTypeCell[%d]/XCUIElementTypeOther/XCUIElementTypeStaticText[1]", bookIndex)))), "Book name");
        return bookName.getText();
    }

    public String getNameOfFirstBook() {
        return lblNameOfFirstBook.getText();
    }

    public boolean isFirstBookInCatalogDisplayed() {
        return lblNameOfFirstBook.state().waitForDisplayed();
    }

    public boolean isNoResults() {
        return lblNoResults.state().waitForDisplayed();
    }

    public CatalogBookModel openBookAndGetBookInfo(BookType bookType, String bookName, ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        String bookNameForLocator = bookName;
        if (PlatformUtils.getPlatformName() == PlatformName.IOS && BookType.AUDIOBOOK == bookType) {
            bookNameForLocator = bookNameForLocator + AUDIOBOOK_LOCATOR_PART;
        }

        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        ILabel lblBookName = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID, bookNameForLocator, actionButtonString))),
                new IosLocator(By.xpath(String.format(BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_IOS, bookNameForLocator, actionButtonString)))), bookName);
        ILabel lblAuthor = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(AUTHOR_BY_BOOK_NAME_AND_BUTTON_LOCATOR_ANDROID, bookNameForLocator, actionButtonString))),
                new IosLocator(By.xpath(String.format(AUTHOR_BY_BOOK_NAME_AND_BUTTON_LOCATOR_IOS, bookNameForLocator, actionButtonString)))), "Author");
        String author;
        if (!lblAuthor.state().waitForDisplayed()) {
            author = null;
        } else {
            author = lblAuthor.getText();
        }

        CatalogBookModel bookInfo = new CatalogBookModel()
                .setTitle(bookName)
                .setAuthor(author);
        lblBookName.click();
        return bookInfo;
    }

    public void openBook(ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookName) {
        String actionButton = actionButtonKey.getDefaultLocalizedValue();
        ILabel lblBookName = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID, bookName, actionButton))),
                new IosLocator(By.xpath(String.format(BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_IOS, bookName, actionButton)))), "Book");
        lblBookName.click();
    }

    private List<String> getBooksName() {
        List<ILabel> lblBooks = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_NAME_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(BOOK_NAME_LOCATOR_IOS))), ElementType.LABEL);

        List<String> booksName = new ArrayList<>();
        lblBooks.forEach(book->booksName.add(book.getText()));
        return booksName;
    }

    private List<ILabel> getBooksLabels() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_NAME_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(BOOK_NAME_LOCATOR_IOS))), ElementType.LABEL);
    }

    public CatalogBookModel clickActionButtonAndGetBookInfo(BookType bookType, String bookName, ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        String bookNameForLocator = bookName;
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && BookType.AUDIOBOOK == bookType) {
            bookNameForLocator = bookNameForLocator + AUDIOBOOK_LOCATOR_PART;
        }

        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        IButton actionButton = getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID, bookNameForLocator, actionButtonString))),
                new IosLocator(By.xpath(String.format(BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS, bookNameForLocator, actionButtonString)))), "Action button to click");

        ILabel lblAuthor = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(AUTHOR_BY_BOOK_NAME_AND_BUTTON_LOCATOR_ANDROID, bookNameForLocator, actionButtonString))),
                new IosLocator(By.xpath(String.format(AUTHOR_BY_BOOK_NAME_AND_BUTTON_LOCATOR_IOS, bookNameForLocator, actionButtonString)))), "Author label");
        String author;
        if (!lblAuthor.state().isDisplayed()) {
            author = null;
        } else {
            author = lblAuthor.getText();
        }
        CatalogBookModel bookInfo = new CatalogBookModel()
                .setTitle(bookName)
                .setAuthor(author);
        actionButton.click();
        if (actionButtonKey == ActionButtonsForBooksAndAlertsKeys.GET || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.REMOVE
                || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.DELETE || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.RETURN
                || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.RESERVE) {
            String bookNameForConditionalWait = bookNameForLocator;
            AqualityServices.getConditionalWait().waitFor(() -> !isProgressBarDisplayed(bookNameForConditionalWait), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
        }
        return bookInfo;
    }

    public boolean isProgressBarDisplayed(String bookName) {
        return getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(PROGRESS_BAR_BY_BOOK_NAME_LOC_ANDROID, bookName))),
                new IosLocator(By.xpath(String.format(PROGRESS_BAR_BY_BOOK_NAME_LOC_IOS, bookName)))), "Progress bar").state().isDisplayed();
    }

    public void clickActionButton(ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookName) {
        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID, bookName, actionButtonString))),
                new IosLocator(By.xpath(String.format(BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS, bookName, actionButtonString)))), "Action button").click();
        if (actionButtonKey == ActionButtonsForBooksAndAlertsKeys.GET || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.REMOVE
                || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.DELETE || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.RETURN
                || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.RESERVE) {
            AqualityServices.getConditionalWait().waitFor(() -> !isProgressBarDisplayed(bookName), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
        }
    }

    public boolean isActionButtonDisplayed(String bookName, ActionButtonsForBooksAndAlertsKeys key) {
        return getActionButton(bookName, key).state().waitForDisplayed();
    }

    public CatalogBookModel clickActionButtonOnTheFirstBookAndGetBookInfo(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        String actionButtonString = actionButtonKey.getDefaultLocalizedValue();
        IButton actionButton = getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BUTTON_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID, actionButtonString))),
                new IosLocator(By.xpath(String.format(BUTTON_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS, actionButtonString)))), "Action button on the first book");
        ILabel lblAuthor = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(AUTHOR_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID, actionButtonString))),
                new IosLocator(By.xpath(String.format(AUTHOR_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_IOS, actionButtonString)))), "Author label");
        ILabel lblBookName = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_NAME_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_ANDROID, actionButtonString))),
                new IosLocator(By.xpath(String.format(BOOK_NAME_ON_THE_FIRST_BOOK_BY_BOOK_NAME_AND_BUTTON_LOC_IOS, actionButtonString)))), "Book name label");
        String author;
        if (!lblAuthor.state().isDisplayed()) {
            author = null;
        } else {
            if(AqualityServices.getApplication().getPlatformName()==PlatformName.IOS) {
                author = lblAuthor.getAttribute(IosAttributes.NAME);
            } else {
                author = lblAuthor.getText();
            }

        }

        CatalogBookModel bookInfo = new CatalogBookModel();

        ActionProcessorUtils.doForIos(() -> bookInfo.setTitle(lblBookName.getAttribute(IosAttributes.NAME)).setAuthor(author));

        ActionProcessorUtils.doForAndroid(() -> bookInfo.setTitle(lblBookName.getText()).setAuthor(author));

        actionButton.click();
        if (actionButtonKey == ActionButtonsForBooksAndAlertsKeys.GET || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.REMOVE
                || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.DELETE || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.RETURN
                || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.RESERVE) {
            AqualityServices.getConditionalWait().waitFor(() -> !isProgressBarDisplayed(lblBookName.getAttribute(IosAttributes.NAME)), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
        }
        return bookInfo;
    }

    private IButton getActionButton(String bookName, ActionButtonsForBooksAndAlertsKeys buttonKey) {
        String key = buttonKey.getDefaultLocalizedValue();
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_ANDROID, bookName, key))),
                new IosLocator(By.xpath(String.format(BUTTON_BY_BOOK_NAME_AND_BUTTON_NAME_LOC_IOS, bookName, key)))), key);
    }
}