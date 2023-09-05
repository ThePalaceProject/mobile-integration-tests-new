package screens.epub;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
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
import java.util.stream.Collectors;

public class BookmarksEpubScreen extends Screen {

    private final ILabel lblNoBookmarks = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("empty_bookmarks_text")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@text, \"no bookmarks\")]"))), "No bookmarks label");
    private final IButton btnDelete = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.Button[contains(@resource-id,\"button1\")]")),
            new IosLocator(By.name("Delete"))), "Delete bookmark button");

    private static final String BTN_DELETE_LOC_ANDROID = "//android.widget.ImageView[contains(@resource-id,\"bookmarkDelete\")]";

    private static final String BOOKMARK_TITLE_LOC_IOS = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[1]";
    private static final String BOOKMARK_DATE_TIME_LOC_IOS = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[2]";

    public BookmarksEpubScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("tocViewPager")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@text, \"no bookmarks\")]"))), "Bookmarks epub screen");
    }

    public boolean isBookmarkScreenOpened() {
        return lblNoBookmarks.state().waitForDisplayed();
    }

    public boolean isBookmarkPresent(String expectedBookmarkTitle, String bookmarkDateTime) {
        LocalDateTime expectedLocalDateTime;
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            expectedLocalDateTime = getExpectedLocalDateTimeIos(bookmarkDateTime);
        } else {
            expectedLocalDateTime = DateUtils.getExpectedLocalDateTime(bookmarkDateTime);
        }

        AqualityServices.getLogger().info("expected bookmark info: ");
        AqualityServices.getLogger().info("expected bookmark title-" + expectedBookmarkTitle);
        AqualityServices.getLogger().info("actual year-" + expectedLocalDateTime.getYear());
        AqualityServices.getLogger().info("actual month-" + expectedLocalDateTime.getMonthValue());
        AqualityServices.getLogger().info("actual dayNumber-" + expectedLocalDateTime.getDayOfMonth());
        AqualityServices.getLogger().info("actual min-" + expectedLocalDateTime.getMinute());
        AqualityServices.getLogger().info("actual hour-" + expectedLocalDateTime.getHour());
        boolean isBookmarkPresent = false;
        for (int i = 0; i < getListOfBookmarkTitles().size(); i++) {
            String actualBookmarkTitle = getListOfBookmarkTitles().get(i);
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
        return getListOfILableOfBookmarkTitles().stream().map(label -> label.getAttribute(IosAttributes.NAME)).collect(Collectors.toList());
    }

    public List<String> getListOfBookmarkTimeDates() {
        return getListOfILableOfBookmarkDates().stream().map(label -> label.getAttribute(IosAttributes.NAME)).collect(Collectors.toList());
    }

    public void openBookmark(int bookmarkNumber) {
        getListOfILableOfBookmarkTitles().get(bookmarkNumber).click();
    }

    public void deleteBookmark(int bookmarkNumber) {
        ActionProcessorUtils.doForAndroid(() -> {
            getListOfDeleteBtns().get(bookmarkNumber).click();
            btnDelete.click();
        });

        ActionProcessorUtils.doForIos(() -> {
            ILabel lblBookmark = getListOfILableOfBookmarkTitles().get(bookmarkNumber);
            SwipeElementUtils.swipeElementLeft(lblBookmark);
            btnDelete.click();
        });
    }

    private List<IButton> getListOfDeleteBtns() {
        return getElementFactory().findElements(By.xpath(BTN_DELETE_LOC_ANDROID), ElementType.BUTTON);
    }

    private LocalDateTime getExpectedLocalDateTimeIos(String stringExpectedDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("y-M-dd HH:m:s");
        LocalDateTime expectedLocalDateTime = LocalDateTime.parse(deleteSomeCharactersForExpectedDateTime(stringExpectedDateTime), dateTimeFormatter);
        return expectedLocalDateTime;
    }

    private String deleteSomeCharactersForExpectedDateTime(String stringExpectedDateTime) {
        String expectedBookmarkDateTime = stringExpectedDateTime.split("\\+")[0].replace("T", " ");
        return expectedBookmarkDateTime;
    }

    private List<ILabel> getListOfILableOfBookmarkTitles() {
        return getElementFactory().findElements(By.xpath(BOOKMARK_TITLE_LOC_IOS), ElementType.LABEL);
    }

    private LocalDateTime getActualLocalDateTime(String stringActualDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        LocalDateTime actualLocalDateTime = LocalDateTime.parse(deleteSomeCharactersForActualDateTime(stringActualDateTime), dateTimeFormatter);
        return actualLocalDateTime;
    }

    private String deleteSomeCharactersForActualDateTime(String stringActualDateTime) {
        return stringActualDateTime.split(" - ")[0];
    }

    private List<ILabel> getListOfILableOfBookmarkDates() {
        return getElementFactory().findElements(By.xpath(BOOKMARK_DATE_TIME_LOC_IOS), ElementType.LABEL);
    }
}
