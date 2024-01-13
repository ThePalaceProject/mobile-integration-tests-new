package screens.pdf;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import constants.appattributes.IosAttributes;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.CoordinatesClickUtils;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import framework.utilities.swipe.directions.EntireScreenDragDirection;
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
    private final ILabel lblBookName = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup/android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeToolbar/parent::XCUIElementTypeOther/preceding-sibling::XCUIElementTypeOther[2]/XCUIElementTypeStaticText"))), "Book name");
    private final ILabel lblNumberOfPages = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[@resource-id=\"numPages\"]")),
            new IosLocator(By.xpath(""))), "Last page number");
    private final ILabel lblPageNumberSlider = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath("//XCUIElementTypeOther[contains(@value,\"Page\")]"))), "lblPageNumberSlider");

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
        Integer pageNumber = ActionProcessorUtils.doForIos(() -> {
            openNavigationBar();
            String pageNumberStr = StringUtils.substringBetween(lblPageNumber.getAttribute(IosAttributes.NAME), "(", "/");
            if(StringUtils.isEmpty(pageNumberStr))
                pageNumberStr = StringUtils.substringBefore(lblPageNumber.getAttribute(IosAttributes.NAME), "/");
            return Integer.parseInt(pageNumberStr);
        });

        if(pageNumber == null) {
            pageNumber = ActionProcessorUtils.doForAndroid(() -> Integer.parseInt(StringUtils.substringBefore(lblPageNumber.getText(), ",")));
        }

        return pageNumber;
    }

    public void openNavigationBar() {
        if (!navigationBarPdfScreen.state().waitForDisplayed()) {
            CoordinatesClickUtils.clickAtCenterOfScreen();
        }
    }

    public void goToNextPage() {
        ActionProcessorUtils.doForIos(() -> SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.RIGHT));
    }

    public void goToPreviousPage() {
        ActionProcessorUtils.doForIos(() -> SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.LEFT));
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
        SwipeElementUtils.swipeDown();
    }

    public void swipePageUp() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.UP);
    }

    public String getBookName() {
        String bookName = ActionProcessorUtils.doForIos(() -> {
            openNavigationBar();
            return lblBookName.getAttribute(IosAttributes.NAME);
        });

        if(bookName == null) {
            bookName = ActionProcessorUtils.doForAndroid(lblBookName::getText);
        }

        return bookName;
    }

    public int getLastPageNumber() {
        return Integer.parseInt(StringUtils.substringBetween(lblNumberOfPages.getText(), "of ", ")"));
    }

    public void slidePageSlider(EntireScreenDragDirection entireScreenDragDirection) {
        openNavigationBar();
        openNavigationBar();
        if (entireScreenDragDirection == EntireScreenDragDirection.RIGHT) {
            SwipeElementUtils.swipeThroughEntireElement(lblPageNumberSlider, EntireElementSwipeDirection.RIGHT);
        } else {
            SwipeElementUtils.swipeThroughEntireElement(lblPageNumberSlider, EntireElementSwipeDirection.LEFT);
        }
    }
}
