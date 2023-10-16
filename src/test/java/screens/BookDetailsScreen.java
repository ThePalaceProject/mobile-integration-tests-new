package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import constants.appattributes.IosAttributes;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import enums.timeouts.BooksTimeouts;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.CatalogBookModel;
import models.IosLocator;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

public class BookDetailsScreen extends Screen {

    private final ILabel lblBookTitle = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("bookDetailTitle")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther[1]//XCUIElementTypeStaticText[1]"))), "Book title label");
    private final ILabel lblBookAuthor = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("bookDetailAuthors")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Description\"]/preceding-sibling::XCUIElementTypeStaticText[2]"))), "Book author label");
    private final ILabel lblBookCover = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[contains(@resource-id, \"bookDetailCoverImage\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeOther//XCUIElementTypeImage[1]"))), "Book cover");

    private final ILabel lblProgressBar = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"bookDetailStatusInProgress\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeProgressIndicator"))), "Progress bar label");
    private final ILabel lblBookFormat = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout//android.widget.TextView[@text=\"Format\"]/following::android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"Book format\")]/following::XCUIElementTypeStaticText"))), "Book format label");
    private final ILabel lblTextInDescription = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Description\"]/following::android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Description\"]/following::XCUIElementTypeTextView"))), "Info in description section");
    private final IButton btnMoreInDescription = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@text=\"Description\"]//following::android.widget.TextView[@text=\"More…\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText//following::XCUIElementTypeButton[@name=\"More...\"]"))), "More btn in Description section");
    private final ILabel lblPublisherInfo = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout/android.widget.TextView[contains(@text,\"Publisher\")]/following::android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name,\"Publisher\")]/following::XCUIElementTypeStaticText"))), "Publisher label");
    private final ILabel lblCategories = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout/android.widget.TextView[contains(@text,\"Categor\")]/following::android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name,\"Categor\")]/following::XCUIElementTypeStaticText"))), "Categories label");
    private final ILabel lblDistributor = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout//android.widget.TextView[contains(@text,\"Distributor\")]/following::android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name,\"Distributed\")]/following::XCUIElementTypeStaticText"))), "Distributor label");
    private final IButton btnMoreInRelatedBooks = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.FrameLayout//android.widget.TextView[@text=\"More…\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeTable//XCUIElementTypeButton[@name=\"More...\"]"))), "More button in related books section");

    private static final String BOOK_NAME_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]";
    private static final String AUTHOR_NAME_LOC_ANDROID = "//android.widget.TextView[@text=\"%s\"]";
    private static final String BOOK_ACTION_BUTTON_LOC_ANDROID = "//android.widget.Button[@text=\"%s\"]";
    private static final String AUTHOR_IN_RELATED_BOOKS_LOC_ANDROID = "//android.widget.FrameLayout//android.widget.TextView[@text=\"%s\"]";
    private static final String LIST_OF_RELATED_BOOKS_LOC_ANDROID = "//androidx.recyclerview.widget.RecyclerView[contains(@resource-id, \"feedLaneCoversScroll\")]/android.widget.LinearLayout";

    private static final String BOOK_NAME_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]";
    private static final String AUTHOR_NAME_LOC_IOS = "//XCUIElementTypeStaticText[@name=\"%s\"]";
    private static final String BOOK_ACTION_BUTTON_LOC_IOS = "//XCUIElementTypeButton/XCUIElementTypeStaticText[@name=\"%s\"]";
    private static final String AUTHOR_IN_RELATED_BOOKS_LOC_IOS = "//XCUIElementTypeTable//XCUIElementTypeButton[@name=\"%s\"]";
    private static final String LIST_OF_RELATED_BOOKS_LOC_IOS = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeButton";

    public BookDetailsScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("bookDetailCover")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=//XCUIElementTypeNavigationBar/@name]"))), "Book details screen");
    }

    public CatalogBookModel getBookInfo() {

        System.out.println("title: " + lblBookTitle.getText());
        System.out.println("author: " + lblBookAuthor.getText());

        return new CatalogBookModel()
                .setTitle(lblBookTitle.getText())
                .setAuthor(lblBookAuthor.getText());
    }

    public boolean isBookTitleDisplayed(String bookName) {
        ILabel lblBookNameIos = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_NAME_LOC_ANDROID, bookName))),
                new IosLocator(By.xpath(String.format(BOOK_NAME_LOC_IOS, bookName)))), "Book name label");
        return lblBookNameIos.state().waitForDisplayed();
    }

    public boolean isAuthorNameDisplayed(String authorName) {
        ILabel lblAuthor = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(AUTHOR_NAME_LOC_ANDROID, authorName))),
                new IosLocator(By.xpath(String.format(AUTHOR_NAME_LOC_IOS, authorName)))), "Author name label");
        System.out.println(lblAuthor.getText());

        return lblAuthor.state().waitForDisplayed();
    }

    public boolean isBookHasCover() {
        return lblBookCover.state().isExist();
    }

    public void clickActionButton(ActionButtonsForBooksAndAlertsKeys buttonKeys) {
        IButton actionButton = getActionButton(buttonKeys);
        actionButton.state().waitForDisplayed();
        actionButton.click();
        if (buttonKeys == ActionButtonsForBooksAndAlertsKeys.GET || buttonKeys == ActionButtonsForBooksAndAlertsKeys.REMOVE
                || buttonKeys == ActionButtonsForBooksAndAlertsKeys.DELETE || buttonKeys == ActionButtonsForBooksAndAlertsKeys.RETURN
                || buttonKeys == ActionButtonsForBooksAndAlertsKeys.RESERVE) {
            AqualityServices.getConditionalWait().waitFor(() -> !isActionButtonDisplayed(ActionButtonsForBooksAndAlertsKeys.GET), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
            AqualityServices.getConditionalWait().waitFor(() -> !isProgressBarDisplayed(), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
        }
    }

    public boolean isActionButtonDisplayed(ActionButtonsForBooksAndAlertsKeys key) {
        return getActionButton(key).state().waitForDisplayed();
    }

    public boolean isProgressBarDisplayed() {
        return lblProgressBar.state().isDisplayed();
    }

    public boolean isBookFormatInfoExist() {
        return lblBookFormat.state().waitForDisplayed();
    }

    public String getBookFormatInfo() {
        return lblBookFormat.getText();
    }

    public boolean isDescriptionNotEmpty() {
        return !lblTextInDescription.getText().isEmpty();
    }

    public boolean isMoreBtnInDescriptionAvailable() {
        return btnMoreInDescription.state().isClickable();
    }

    public boolean isPublisherInfoExist() {
        return lblPublisherInfo.state().waitForDisplayed();
    }

    public boolean isCategoryInfoExist() {
        return lblCategories.state().waitForDisplayed();
    }

    public String getPublisherInfo() {
        String publisherInfo = ActionProcessorUtils.doForIos(() -> lblPublisherInfo.getAttribute(IosAttributes.NAME));

        if(publisherInfo == null) {
            publisherInfo = ActionProcessorUtils.doForAndroid(lblPublisherInfo::getText);
        }

        return publisherInfo;
    }

    public String getCategoryInfo() {
        String categoryInfo = ActionProcessorUtils.doForIos(() -> lblCategories.getAttribute(IosAttributes.NAME));

        if(categoryInfo == null) {
            categoryInfo = ActionProcessorUtils.doForAndroid(() -> {
                lblCategories.state().waitForDisplayed();
                return lblCategories.getText();
            });
        }
         return categoryInfo;
    }

    public String getDistributorInfo() {
        String distributorInfo = ActionProcessorUtils.doForIos(() -> lblDistributor.getAttribute(IosAttributes.NAME));

        if(distributorInfo == null) {
            distributorInfo = ActionProcessorUtils.doForAndroid(lblDistributor::getText);
        }

        return distributorInfo;
    }

    public boolean isRelatedBooksExists(String authorName) {
        ILabel lblAuthorInRelatedBooks = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(AUTHOR_IN_RELATED_BOOKS_LOC_ANDROID, authorName))),
                new IosLocator(By.xpath(String.format(AUTHOR_IN_RELATED_BOOKS_LOC_IOS, authorName)))), "Author in related books section");
        return lblAuthorInRelatedBooks.state().isDisplayed();
    }

    public boolean isListOfBooksDisplayed() {
        List<ILabel> listOfRelatedBooks = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(LIST_OF_RELATED_BOOKS_LOC_ANDROID)),
                new IosLocator(By.xpath(LIST_OF_RELATED_BOOKS_LOC_IOS))), ElementType.LABEL);
        return listOfRelatedBooks.size() != 0;
    }

    public boolean isMoreBtnAvailableInRelatedBooks() {
        return btnMoreInRelatedBooks.state().isClickable();
    }

    public void clickActionButtonForCancelTheAction(ActionButtonsForBooksAndAlertsKeys buttonKeys) {
        IButton actionButton = getActionButton(buttonKeys);
        actionButton.click();
    }

    private IButton getActionButton(ActionButtonsForBooksAndAlertsKeys buttonKey) {
        String key = buttonKey.getDefaultLocalizedValue();
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC_ANDROID, key))),
                new IosLocator(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC_IOS, key)))), key);
    }
}
