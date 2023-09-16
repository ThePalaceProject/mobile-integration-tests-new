package screens.audiobook;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import constants.appattributes.AndroidAttributes;
import constants.appattributes.IosAttributes;
import framework.utilities.DateUtils;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class BookmarksAudiobookScreen extends Screen {

    private final IButton btnBookmarks = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout[@content-desc=\"Bookmarks\"]")),
            new IosLocator(By.xpath(""))), "Bookmarks tab");
    private final ILabel lblNoBookmarks = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name=\"no bookmarks\")]"))), "No bookmarks label");
    private final ILabel lblChapterName = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_toc_bookmark_item_view_title")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[1]"))), "Chapter name");
    private final ILabel lblChapterTime = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("player_toc_bookmark_item_view_offset")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[3]"))), "Chapter time");

    public BookmarksAudiobookScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.LinearLayout[@content-desc=\"Bookmarks\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"Bookmarks\"]"))), "Bookmarks audiobook screen");
    }

    public boolean isBookmarksScreenSelected() {
        if(AqualityServices.getApplication().getPlatformName() == PlatformName.ANDROID) {
            return btnBookmarks.getAttribute(AndroidAttributes.SELECTED).equals(Boolean.TRUE.toString());
        }else {
            return lblNoBookmarks.state().waitForDisplayed();
        }
    }

    public boolean isNoBookmarksMessageDisplayed() {
        return lblNoBookmarks.state().waitForDisplayed();
    }

    public String getChapterName() {
        return lblChapterName.getText();
    }

    public String getChapterTime() {
        if(AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            return DateUtils.getDuration(lblChapterTime.getAttribute(IosAttributes.VALUE)).toString();
        } else {
            return DateUtils.getDuration(lblChapterTime.getText()).toString();
        }

    }
}