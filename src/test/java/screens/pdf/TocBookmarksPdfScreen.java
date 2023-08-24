package screens.pdf;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class TocBookmarksPdfScreen extends Screen {

    protected ChaptersPdfScreen chaptersPdfScreen;
    protected ThumbnailsPdfScreen thumbnailsPdfScreen;

    private final IButton btnThumbnails = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.RadioButton[@resource-id=\"viewThumbnail\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeSegmentedControl/XCUIElementTypeButton[@name=\"Mission Control\"]"))), "TOC with thumbnails button");
    private final IButton btnChapters = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.RadioButton[@resource-id=\"viewOutline\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeSegmentedControl/XCUIElementTypeButton[@name=\"List\"]"))), "TOC with list of chapters button");
    private final IButton btnResume = getElementFactory().getButton(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeOther//XCUIElementTypeButton[1]"), "Resume button");

    public TocBookmarksPdfScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.View[@resource-id=\"toolbarSidebar\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeButton[@name = \"Resume\"]"))), "Navigation bar with toc and bookmarks screen");
        chaptersPdfScreen = new ChaptersPdfScreen();
        thumbnailsPdfScreen = new ThumbnailsPdfScreen();
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
        ActionProcessorUtils.doForAndroid(() -> AqualityServices.getApplication().getDriver().navigate().back());
    }
}
