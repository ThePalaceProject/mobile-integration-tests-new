package screens.epub;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import constants.RegEx;
import constants.appattributes.IosAttributes;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.CoordinatesClickUtils;
import framework.utilities.LocatorUtils;
import framework.utilities.RegExUtil;
import framework.utilities.swipe.SwipeElementUtils;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class ReaderEpubScreen extends Screen {

    private final NavigationBarScreen navigationBarScreen;

    private final ILabel lblPage = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.webkit.WebView[1]")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@name, \"Page\")]/preceding-sibling::XCUIElementTypeOther"))), "Reader page label");
    private final ILabel lblPageNumber = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("reader2_position_page")),
            new IosLocator(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText[contains(@name, \"Page\")]"))), "Page number label");
    private final ILabel lblChapterName = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@resource-id,\"reader2_position_title\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText[contains(@name, \"Page\")]"))), "Chapter name label");
    private final ILabel lblBookName = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@resource-id,\"titleText\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText[1]"))), "Book name label");
    private final ILabel lblBookCover = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.View[@resource-id=\"titlepage\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeSlider"))), "Book cover");

    public ReaderEpubScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"readerToolbar\")]")),
                new IosLocator(By.xpath("//*[contains(@name,\"Page\")]"))), "Reader epub screen");
        navigationBarScreen = new NavigationBarScreen();
    }

    public void swipeToNextPage() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.RIGHT);
    }

    public void swipeToPreviousPage() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.LEFT);
    }

    public void openNavigationBar() {
        if (!navigationBarScreen.state().isDisplayed()) {
            CoordinatesClickUtils.clickAtCenterOfScreen();
        }
    }

    public String getBookName() {
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            String text = lblBookName.getAttribute(IosAttributes.NAME);
            AqualityServices.getLogger().info("Book name on epub reader screen - " + text);
            return text;
        } else {
            hideNavigationBar();
            String text = lblBookName.getText();
            AqualityServices.getLogger().info("Book name on epub reader screen - " + text);
            return text;
        }
    }

    public void hideNavigationBar() {
        if (navigationBarScreen.state().isDisplayed()) {
            CoordinatesClickUtils.clickAtCenterOfScreen();
        }
    }

    public String getPageNumber() {
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            String pageNumber = lblPageNumber.getAttribute(IosAttributes.NAME);
            pageNumber = RegExUtil.deleteBracketsFromText(pageNumber);
            return RegExUtil.getStringFromFirstGroup(pageNumber, RegEx.PAGE_NUMBER_AND_CHAPTER_NAME_REGEX_FOR_IOS);
        } else {
            String pageNumber = lblPageNumber.getText();
            return RegExUtil.getStringFromFirstGroup(pageNumber, RegEx.PAGE_NUMBER_REGEX_FOR_ANDROID);
        }
    }

    public String getChapterName() {
        if(AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            String chapterName = lblChapterName.getAttribute(IosAttributes.NAME);
            chapterName = RegExUtil.deleteBracketsFromText(chapterName);
            return RegExUtil.getStringFromThirdGroup(chapterName, RegEx.PAGE_NUMBER_AND_CHAPTER_NAME_REGEX_FOR_IOS);
        } else {
            return lblChapterName.getText();
        }
    }

    public void tapRightCorner() {
        ActionProcessorUtils.doForIos(() -> {
            TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
            action.tap(PointOption.point(lblPage.getElement().getSize().getWidth(), lblPage.getElement().getCenter().y)).perform();
        });

        ActionProcessorUtils.doForAndroid(() -> {
            TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
            int height = lblPage.getElement().getSize().height;
            int width = lblPage.getElement().getSize().width;
            action.tap(PointOption.point(width - 10, height / 2)).perform();
        });
    }

    public void tapLeftCorner() {
        ActionProcessorUtils.doForIos(() -> {
            TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
            action.tap(PointOption.point(20, lblPage.getElement().getCenter().y)).perform();
        });

        ActionProcessorUtils.doForAndroid(() -> {
            TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
            action.tap(PointOption.point(0, lblPage.getElement().getCenter().y)).perform();
        });
    }

    public NavigationBarScreen getNavigationBarEpubScreen() {
        return navigationBarScreen;
    }

    public boolean isBookCoverDisplayed() {
        return lblBookCover.state().waitForDisplayed();
    }

    public void clickRightCorner() {
        ActionProcessorUtils.doForIos(() -> {
            TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
            action.tap(PointOption.point(lblPage.getElement().getSize().getWidth(), lblPage.getElement().getCenter().y)).perform();
        });

        ActionProcessorUtils.doForAndroid(() -> {
            TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
            int height = lblPage.getElement().getSize().height;
            int width = lblPage.getElement().getSize().width;
            action.tap(PointOption.point(width - 10, height / 2)).perform();
        });
    }
}
