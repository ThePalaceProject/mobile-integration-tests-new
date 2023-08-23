package screens.pdf;

import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.List;

public class ChaptersPdfScreen extends Screen {

    private static final String CHAPTER_LOCATOR_ANDROID = "//android.view.View[contains(@resource-id, \"outlineView\")]/android.view.View";
    private static final String CHAPTER_NUMBER_LOCATOR_ANDROID = "//android.view.View[contains(@resource-id, \"outlineView\")]/android.view.View[%d]/android.view.View";

    private static final String CHAPTER_LOCATOR_IOS = "//XCUIElementTypeTable//XCUIElementTypeCell/XCUIElementTypeStaticText[1]";
    private static final String CHAPTER_NUMBER_LOCATOR_IOS = "//XCUIElementTypeTable//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"%d\"]";

    public ChaptersPdfScreen () {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.View[@resource-id=\"sidebarContainer\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeTable"))), "List of chapters screen");
    }

    public boolean areChaptersDisplayed() {
        return getChapters().size() == 0 || getChapters().size() != 0;
    }

    private List<ILabel> getChapters() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(CHAPTER_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(CHAPTER_LOCATOR_IOS))), ElementType.LABEL, ElementsCount.ANY, ElementState.EXISTS_IN_ANY_STATE);
    }

    public int  openRandomChapter() {
        int chapterNumber = (int) (Math.random() * (getChapters().size()) + 1);
        ILabel chapter = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(CHAPTER_NUMBER_LOCATOR_ANDROID, chapterNumber))),
                new IosLocator(By.xpath(String.format(CHAPTER_NUMBER_LOCATOR_IOS, chapterNumber)))), "Chapter");
        chapter.click();
        return chapterNumber;
    }
}
