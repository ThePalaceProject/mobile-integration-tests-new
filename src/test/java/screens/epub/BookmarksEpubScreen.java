package screens.epub;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.selenium.core.elements.interfaces.IElement;
import constants.appattributes.IosAttributes;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.DateUtils;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookmarksEpubScreen extends Screen {

    private final ILabel lblNoBookmarks = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("empty_bookmarks_text")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@text, \"no bookmarks\")]"))), "No bookmarks label");
    private final ILabel lblBookmarksTab = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("tocBookmarksList")),
            new IosLocator(By.xpath("//XCUIElementTypeTable"))), "Bookmarks tab");
    private final IButton btnDelete = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Button[@text=\"Delete\"]")),
            new IosLocator(By.name("Delete"))), "Delete bookmark button");

    private static final String BTN_DELETE_LOC_ANDROID = "//android.widget.ImageView[contains(@resource-id,\"bookmarkDelete\")]";
    private static final String BOOKMARK_TITLE_LOC_ANDROID = "//android.widget.TextView[contains(@resource-id, \"bookmarkTitle\")]";
    private static final String BOOKMARK_DATE_TIME_LOC_ANDROID = "//android.widget.TextView[contains(@resource-id, \"bookmarkDate\")]";

    private static final String BOOKMARK_TITLE_LOC_IOS = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[1]";
    private static final String BOOKMARK_DATE_TIME_LOC_IOS = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[2]";

    public BookmarksEpubScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("tocViewPager")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@text, \"no bookmarks\")]"))), "Bookmarks epub screen");
    }

    public boolean isBookmarkScreenOpened() {
        boolean isOpened = ActionProcessorUtils.doForAndroid(() -> lblBookmarksTab.state().waitForDisplayed());

        if (!isOpened) {
            isOpened = ActionProcessorUtils.doForIos(() -> lblNoBookmarks.state().waitForDisplayed() || lblBookmarksTab.state().waitForDisplayed());
        }
        return isOpened;
    }

    public boolean isBookmarkPresent(String expectedBookmarkTitle, String bookmarkDateTime) {
        LocalDateTime expectedLocalDateTime = DateUtils.getExpectedLocalDateTime(bookmarkDateTime);
        if (expectedBookmarkTitle == null) {
            expectedBookmarkTitle = "";
        }

        AqualityServices.getLogger().info("expected bookmark info: ");
        AqualityServices.getLogger().info("expected bookmark title-" + expectedBookmarkTitle);
        AqualityServices.getLogger().info("expected year-" + expectedLocalDateTime.getYear());
        AqualityServices.getLogger().info("expected month-" + expectedLocalDateTime.getMonthValue());
        AqualityServices.getLogger().info("expected dayNumber-" + expectedLocalDateTime.getDayOfMonth());
        AqualityServices.getLogger().info("expected min-" + expectedLocalDateTime.getMinute());
        AqualityServices.getLogger().info("expected hour-" + expectedLocalDateTime.getHour());
        boolean isBookmarkPresent = false;

        for (int i = 0; i < getListOfBookmarkTitles().size(); i++) {
            String actualBookmarkTitle = getListOfBookmarkTitles().get(i);
            if(actualBookmarkTitle == null) {
                actualBookmarkTitle = "";
            }
            LocalDateTime actualLocalDateTime = getActualLocalDateTime(getListOfBookmarkTimeDates().get(i));
            AqualityServices.getLogger().info("bookmark number " + i + " info: ");
            AqualityServices.getLogger().info("actual bookmark title-" + actualBookmarkTitle);
            AqualityServices.getLogger().info("actual year-" + actualLocalDateTime.getYear());
            AqualityServices.getLogger().info("actual month-" + actualLocalDateTime.getMonth().getValue());
            AqualityServices.getLogger().info("actual dayNumber-" + actualLocalDateTime.getDayOfMonth());
            AqualityServices.getLogger().info("actual min-" + actualLocalDateTime.getMinute());
            AqualityServices.getLogger().info("actual hour-" + actualLocalDateTime.getHour());
            if (actualBookmarkTitle.equalsIgnoreCase(expectedBookmarkTitle) && expectedLocalDateTime.getHour() == actualLocalDateTime.getHour()
                    && expectedLocalDateTime.getMonthValue() == actualLocalDateTime.getMonthValue() && expectedLocalDateTime.getDayOfMonth() == actualLocalDateTime.getDayOfMonth()
                    && expectedLocalDateTime.getYear() == actualLocalDateTime.getYear()
                    && (expectedLocalDateTime.getMinute() == actualLocalDateTime.getMinute() || expectedLocalDateTime.getMinute() == actualLocalDateTime.getMinute() + 1
                    || expectedLocalDateTime.getMinute() == actualLocalDateTime.getMinute() + 2)) {
                isBookmarkPresent = true;
                break;
            }
        }
        return isBookmarkPresent;
    }

    public List<String> getListOfBookmarkTitles() {
        List<String> bookmarkTitles = ActionProcessorUtils.doForIos(() -> getListOfILabelOfBookmarkTitles().stream().map(label -> label.getAttribute(IosAttributes.NAME)).collect(Collectors.toList()));

        if(bookmarkTitles == null) {
            bookmarkTitles = ActionProcessorUtils.doForAndroid(() -> getListOfILabelOfBookmarkTitles().stream().map(IElement::getText).collect(Collectors.toList()));
        }

        return bookmarkTitles;
    }

    public List<String> getListOfBookmarkTimeDates() {
        List<String> bookmarkTimeDates = ActionProcessorUtils.doForIos(() -> getListOfILableOfBookmarkDates().stream().map(label -> label.getAttribute(IosAttributes.NAME)).collect(Collectors.toList()));

        if (bookmarkTimeDates == null) {
            bookmarkTimeDates = ActionProcessorUtils.doForAndroid(() -> getListOfILableOfBookmarkDates().stream().map(IElement::getText).collect(Collectors.toList()));
        }
        return bookmarkTimeDates;
    }

    public void openBookmark(int bookmarkNumber) {
        getListOfILabelOfBookmarkTitles().get(bookmarkNumber).click();
    }

    public void deleteBookmark(int bookmarkNumber) {
        ActionProcessorUtils.doForAndroid(() -> {
            getListOfDeleteBtns().get(bookmarkNumber + 1).click();
            btnDelete.click();
        });

        ActionProcessorUtils.doForIos(() -> {
            ILabel lblBookmark = getListOfILabelOfBookmarkTitles().get(bookmarkNumber);
            SwipeElementUtils.swipeElementLeft(lblBookmark);
            btnDelete.click();
        });
    }

    private List<IButton> getListOfDeleteBtns() {
        return getElementFactory().findElements(By.xpath(BTN_DELETE_LOC_ANDROID), ElementType.BUTTON);
    }

    private String deleteSomeCharactersForExpectedDateTime(String stringExpectedDateTime) {
        return stringExpectedDateTime.split("\\+")[0].replace("T", " ");
    }

    private List<ILabel> getListOfILabelOfBookmarkTitles() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOKMARK_TITLE_LOC_ANDROID)),
                new IosLocator(By.xpath(BOOKMARK_TITLE_LOC_IOS))), ElementType.LABEL);
    }

    private LocalDateTime getActualLocalDateTime(String stringActualDateTime) {
        DateTimeFormatter dateTimeFormatter = ActionProcessorUtils.doForAndroid(() -> DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if(dateTimeFormatter == null) {
            dateTimeFormatter = ActionProcessorUtils.doForIos(() -> DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"));
        }
        return LocalDateTime.parse(deleteSomeCharactersForActualDateTime(stringActualDateTime), Objects.requireNonNull(dateTimeFormatter));
    }

    private String deleteSomeCharactersForActualDateTime(String stringActualDateTime) {
        return stringActualDateTime.split(" - ")[0];
    }

    private List<ILabel> getListOfILableOfBookmarkDates() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOKMARK_DATE_TIME_LOC_ANDROID)),
                new IosLocator(By.xpath(BOOKMARK_DATE_TIME_LOC_IOS))), ElementType.LABEL);
    }
}
