package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import enums.timeouts.BooksTimeouts;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.CatalogBookModel;
import models.IosLocator;
import org.openqa.selenium.By;

import java.time.Duration;

public class BookDetailsScreen extends Screen {

    private final ILabel lblBookTitle = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("bookDetailTitle")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther[1]//XCUIElementTypeStaticText[1]"))), "Book title label");
    private final ILabel lblBookAuthor = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("bookDetailAuthors")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther[1]//XCUIElementTypeStaticText[2]"))), "Book author label");
    private final ILabel lblBookCover = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[contains(@resource-id, \"bookDetailCoverImage\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeOther//XCUIElementTypeImage[1]"))), "Book cover");

    private final ILabel lblProgressBar = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"bookDetailStatusInProgress\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeProgressIndicator"))), "Progress bar label");

    private static final String BOOK_ACTION_BUTTON_LOC_ANDROID = "//android.widget.Button[@text=\"%s\"]";

    private static final String BOOK_ACTION_BUTTON_LOC_IOS = "//XCUIElementTypeButton/XCUIElementTypeStaticText[@name=\"%s\"]";

    public BookDetailsScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("bookDetailCover")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=//XCUIElementTypeNavigationBar/@name]"))), "Book details screen");
    }

    public CatalogBookModel getBookInfo() {
        return new CatalogBookModel()
                .setTitle(lblBookTitle.getText())
                .setAuthor(lblBookAuthor.getText());
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

    private IButton getActionButton(ActionButtonsForBooksAndAlertsKeys buttonKey) {
        String key = buttonKey.getDefaultLocalizedValue();
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC_ANDROID, key))),
                new IosLocator(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC_IOS, key)))), key);
    }
}
