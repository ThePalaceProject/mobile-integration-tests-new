package screens.epub;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.selenium.core.elements.interfaces.IElement;
import enums.epub.TabsTocAndBookmarksEpub;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class TocEpubScreen extends Screen {

    private final IButton btnFirstChapter = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[contains(@resource-id, \"chapterTitle\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeStaticText"))), "First chapter button");
    private final IButton btnBack = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton"))), "Back button");

    private static final String TOC_TAB_LOCATOR_ANDROID = "//android.widget.TextView[@text=\"%s\"]";
    private static final String CHAPTER_LOCATOR_ANDROID = "//android.widget.TextView[contains(@resource-id,\"chapterTitle\")]";
    private static final String CHAPTER_BY_NAME_LOCATOR_ANDROID = "//android.widget.TextView[contains(@resource-id,\"chapterTitle\") and @text=\"%s\"]";

    private static final String TOC_TAB_LOCATOR_IOS = "//XCUIElementTypeButton[@name=\"%s\"]";
    private static final String CHAPTER_LOCATOR_IOS = "//XCUIElementTypeTable//XCUIElementTypeCell/XCUIElementTypeStaticText";
    private static final String CHAPTER_BY_NAME_LOCATOR_IOS = "//XCUIElementTypeTable//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"%1$s\"]";

    public TocEpubScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.HorizontalScrollView[contains(@resource-id,\"tocTabs\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=\"Table of Contents\"]"))), "TOC epub screen");
    }

    public void openTab(TabsTocAndBookmarksEpub tab) {
        ILabel lblTab = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(TOC_TAB_LOCATOR_ANDROID, tab.getValue()))),
                new IosLocator(By.xpath(String.format(TOC_TAB_LOCATOR_IOS, tab.getValue())))), "Tab");
        lblTab.state().waitForDisplayed();
        lblTab.click();
    }

    public boolean isTOCOpened() {
        return btnFirstChapter.state().waitForDisplayed();
    }

    public List<String> getListOfBookChapters() {
        List<String> listOfChapters = getChapters().stream().map(IElement::getText).collect(Collectors.toList());
        AqualityServices.getLogger().info("Found chapters on toc epub - " + listOfChapters.stream().map(Object::toString).collect(Collectors.joining(", ")));
        AqualityServices.getLogger().info("amountOfChapters on toc epub - " + listOfChapters.size());
        return listOfChapters;
    }

    public void openChapter(String chapter) {
        ILabel lblChapter = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(CHAPTER_BY_NAME_LOCATOR_ANDROID, chapter))),
                new IosLocator(By.xpath(String.format(CHAPTER_BY_NAME_LOCATOR_IOS, chapter)))), chapter);
        lblChapter.click();
    }

    public void returnToPreviousScreen() {
        btnBack.click();
    }

    private List<IElement> getChapters() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.id(CHAPTER_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(CHAPTER_LOCATOR_IOS))), ElementType.LABEL).stream().limit(5).collect(Collectors.toList());
    }

}
