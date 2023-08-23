package screens.pdf;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import constants.appattributes.IosAttributes;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.CoordinatesClickUtils;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import models.AndroidLocator;
import models.IosLocator;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

public class ReaderPdfScreen extends Screen {

    private final NavigationBarPdfScreen navigationBarPdfScreen;
    private final SearchPdfScreen searchPdfScreen;
    private final SettingsPdfScreen settingsPdfScreen;

    private final ILabel lblPage = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.View[@resource-id=\"viewerContainer\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeTextView"))), "Page pdf");
    private final ILabel lblPageNumber = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.EditText[@resource-id=\"pageNumber\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeStaticText[contains(@value,\"/\")]"))), "Page number label");

    public ReaderPdfScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.View[@resource-id=\"viewerContainer\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeTextView"))), "Reader pdf screen");
        navigationBarPdfScreen = new NavigationBarPdfScreen();
        searchPdfScreen = new SearchPdfScreen();
        settingsPdfScreen = new SettingsPdfScreen();
    }

    public boolean isReaderOpened() {
        return lblPage.state().waitForDisplayed();
    }

    public int getPageNumber() {
        if(AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            openNavigationBar();
            return Integer.parseInt(StringUtils.substringBefore(lblPageNumber.getAttribute(IosAttributes.NAME), "/"));
        } else {
            return Integer.parseInt(StringUtils.substringBefore(lblPageNumber.getText(), ","));
        }
    }

    public void openNavigationBar() {
        if (!navigationBarPdfScreen.state().waitForDisplayed()) {
            CoordinatesClickUtils.clickAtCenterOfScreen();
        }
    }

    public void goToNextPage() {
        ActionProcessorUtils.doForIos(() -> {
            SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.RIGHT);
        });
    }

    public void goToPreviousPage() {
        ActionProcessorUtils.doForIos(() -> {
            SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.LEFT);
        });
    }

    public NavigationBarPdfScreen getNavigationBarScreen() {
        return navigationBarPdfScreen;
    }

    public SearchPdfScreen getSearchPdfScreen() {
        return searchPdfScreen;
    }

    public SettingsPdfScreen getSettingsPdfScreen() {
        return settingsPdfScreen;
    }

    public void swipePageDown() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.DOWN);
    }

    public void swipePageUp() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.UP);
    }
}
