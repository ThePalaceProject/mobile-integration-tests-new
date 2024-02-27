package screens.pdf;

import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.List;

public class BookmarksPdfScreen extends Screen {

    private final ILabel noBookmarks = getElementFactory().getLabel(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"no bookmarks\")]"), "No bookmarks label");

    private static final String BOOKMARK_LOC_IOS = "//XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell";
    private static final String CURRENT_BOOKMARK_LOC_IOS = "//XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[%d]";

    public BookmarksPdfScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"no bookmarks\")]"))), "Bookmarks screen");
    }

    public boolean isScreenEmpty() {
        return noBookmarks.state().waitForDisplayed();
    }

    public int getCountOfBookmarks() {
        return getListOfBookmarks().size();
    }

    public void openBookmark(int bookmarkNumber) {
        ILabel lblBookmark = getElementFactory().getLabel(By.xpath(String.format(CURRENT_BOOKMARK_LOC_IOS, bookmarkNumber)), "Bookmark");
        lblBookmark.click();
    }

    private List<ILabel> getListOfBookmarks() {
        return getElementFactory().findElements(By.xpath(BOOKMARK_LOC_IOS), ElementType.LABEL);
    }
}
