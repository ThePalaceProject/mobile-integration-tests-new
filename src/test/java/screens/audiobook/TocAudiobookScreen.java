package screens.audiobook;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import constants.appattributes.AndroidAttributes;
import constants.appattributes.IosAttributes;
import enums.timeouts.BooksTimeouts;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class TocAudiobookScreen extends Screen {

    private final ILabel lblFirstChapter = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//androidx.recyclerview.widget.RecyclerView//android.widget.LinearLayout[1]")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[1]"))), "First chapter");
    private final IButton btnChapters = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout[@content-desc=\"Chapters\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Chapters\"]"))), "Chapters tab");
    private final IButton btnBookmarks = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout[@content-desc=\"Bookmarks\"]")),
            new IosLocator(By.name("Bookmarks"))), "Bookmarks tab");
    private final ILabel lblChapterName = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[1]"))), "Chapter name");
    private final IButton btnBack = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageButton[@content-desc=\"Back\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton"))), "Back button");

    private static final String CHAPTERS_LOC_ANDROID = "//android.widget.LinearLayout//android.widget.TextView[contains(@resource-id, \"player_toc_chapter_item_view_title\")]";
    private static final String DOWNLOADING_PROGRESS_LOC_ANDROID = "//androidx.recyclerview.widget.RecyclerView//android.widget.RelativeLayout[%d]//android.view.View";

    private static final String CHAPTERS_LOC_IOS = "//XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText";
    private static final String CHAPTER_NAME_BY_CHAPTER_NUMBER_LOC_IOS = "//XCUIElementTypeCollectionView/XCUIElementTypeCell[%d]//XCUIElementTypeStaticText[1]";

    public TocAudiobookScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//androidx.recyclerview.widget.RecyclerView[contains(@resource-id,\"list\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeTable"))), "TOC audiobook screen");
    }

    public String openChapterAndGetChapterName(int chapterNumber) {
        String chapterName = ActionProcessorUtils.doForIos(() -> {
            ILabel lblChapter = getElementFactory().getLabel(By.xpath(String.format(CHAPTER_NAME_BY_CHAPTER_NUMBER_LOC_IOS, chapterNumber)), "Chapter");
            String chapterText = lblChapter.getAttribute(IosAttributes.NAME);
            lblChapter.click();
            return chapterText;
        });

        if(chapterName == null) {
            chapterName = ActionProcessorUtils.doForAndroid(() -> {
                IElement lblChapterText = getChapters().get(chapterNumber);
                String chapterText = lblChapterText.getAttribute(AndroidAttributes.TEXT);
                ILabel downloadProgress = getElementFactory().getLabel(By.xpath(String.format(DOWNLOADING_PROGRESS_LOC_ANDROID, chapterNumber - 1)), "Downloading progress");
                AqualityServices.getConditionalWait().waitFor(() -> !downloadProgress.state().isDisplayed(), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
                lblChapterText.click();
                return chapterText;
            });
        }

        return chapterName;
    }

    public int getCountOfChapters() {
        return getChapters().size();
    }

    public boolean isTheFirstChapterLoaded() {
        return lblFirstChapter.state().waitForDisplayed();
    }

    public String getChapterName(int chapterNumber) {
        IElement lblChapterName = getChapters().get(chapterNumber);
        return lblChapterName.getAttribute(IosAttributes.NAME);
    }

    public boolean isContentTabDisplayed() {
        return btnChapters.state().waitForDisplayed();
    }

    public boolean isBookmarksTabDisplayed() {
        return btnBookmarks.state().waitForDisplayed();
    }

    public void openBookmarks() {
        btnBookmarks.click();
    }

    public void openChapters() {
        btnChapters.click();
    }

    public boolean isChaptersSelected() {
        boolean isSelected = ActionProcessorUtils.doForIos(() -> lblChapterName.state().waitForDisplayed());

        if(!isSelected) {
            isSelected = ActionProcessorUtils.doForAndroid(() -> btnChapters.getAttribute(AndroidAttributes.SELECTED).equals(Boolean.TRUE.toString()));
        }

        return isSelected;
    }

    public void clickBackBtn() {
        btnBack.click();
    }

    private List<IElement> getChapters() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(CHAPTERS_LOC_ANDROID)),
                new IosLocator(By.xpath(CHAPTERS_LOC_IOS))), ElementType.LABEL).stream().limit(10).collect(Collectors.toList());
    }
}
