package screens.epub;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class BookmarksEpubScreen extends Screen {

    private final ILabel lblNoBookmarks = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("empty_bookmarks_text")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@text, \"no bookmarks\")]"))), "No bookmarks label");

    public BookmarksEpubScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("tocViewPager")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@text, \"no bookmarks\")]"))), "Bookmarks epub screen");
    }

    public boolean isBookmarkScreenOpened() {
        return lblNoBookmarks.state().waitForDisplayed();
    }
}
