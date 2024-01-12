package screens.pdf;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class TocBookmarksPdfScreen extends Screen {

    private final ChaptersPdfScreen chaptersPdfScreen;
    private final ThumbnailsPdfScreen thumbnailsPdfScreen;
    private final BookmarksPdfScreen bookmarksPdfScreen;

    private final IButton btnThumbnails = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.RadioButton[@resource-id=\"viewThumbnail\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeSegmentedControl/XCUIElementTypeButton[@name=\"Mission Control\"]"))), "TOC with thumbnails button");
    private final IButton btnChapters = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.RadioButton[@resource-id=\"viewOutline\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeSegmentedControl/XCUIElementTypeButton[@name=\"List\"]"))), "TOC with list of chapters button");
    private final IButton btnBookmarks = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar//XCUIElementTypeSegmentedControl/XCUIElementTypeButton[3]"))), "Bookmarks button");
    private final IButton btnResume = getElementFactory().getButton(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeOther//XCUIElementTypeButton[1]"), "Resume button");

    public TocBookmarksPdfScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.View[@resource-id=\"toolbarSidebar\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeButton[@name = \"Resume\"]"))), "Navigation bar with toc and bookmarks screen");
        chaptersPdfScreen = new ChaptersPdfScreen();
        thumbnailsPdfScreen = new ThumbnailsPdfScreen();
        bookmarksPdfScreen = new BookmarksPdfScreen();
    }

    public boolean isThumbnailsButtonDisplayed() {
        return btnThumbnails.state().waitForDisplayed();
    }

    public boolean isChaptersButtonDisplayed() {
        return btnChapters.state().waitForDisplayed();
    }

    public void tapChaptersButton() {
        btnChapters.click();
    }

    public ChaptersPdfScreen getChaptersPdfScreen() {
        return chaptersPdfScreen;
    }

    public void tapThumbnailsButton() {
        btnThumbnails.click();
    }

    public ThumbnailsPdfScreen getThumbnailsPdfScreen() {
        return thumbnailsPdfScreen;
    }

    public void returnToReaderPdfScreen() {
        ActionProcessorUtils.doForIos(btnResume::click);
    }

    public void tapBookmarksButton() {
        btnBookmarks.click();
    }

    public BookmarksPdfScreen getBookmarksPdfScreen() {
        return bookmarksPdfScreen;
    }

    public void tapResumeButton() {
        btnResume.click();
    }

    public boolean isTocClosed() {
        return btnThumbnails.state().waitForNotDisplayed();
    }
}
