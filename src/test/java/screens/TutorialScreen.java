package screens;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import constants.appattributes.AndroidAttributes;
import constants.appattributes.IosAttributes;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class TutorialScreen extends Screen {

    private final IButton btnCloseTutorial = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[contains(@resource-id, \"skip_button\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeButton"))), "Close tutorial btn");
    private final ILabel lblPage = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[@content-desc=\"Tutorial page\"]")),
            new IosLocator(By.xpath("//XCUIElementTypeWindow"))), "Page label");

    private static final String TUTORIAL_TAB_LOCATOR_ANDROID = "//android.widget.LinearLayout[@content-desc]";
    private static final String TUTORIAL_TAB_BY_NAME_LOCATOR_ANDROID = "//android.widget.LinearLayout[contains(@content-desc,\"%s\")]";

    private static final String TUTORIAL_TAB_BY_NAME_LOCATOR_IOS = "//XCUIElementTypeImage[contains(@name,\"%s\")]";
    private static final String TUTORIAL_TAB_LOCATOR_IOS = "//XCUIElementTypeImage[contains(@name,\"Step\")]";


    public TutorialScreen(){
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.ImageView[@content-desc=\"Tutorial page\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeButton"))), "Tutorial screen");
    }

    public boolean isTutorialScreenOpened() {
        return btnCloseTutorial.state().waitForDisplayed();
    }

    public void closeTutorialScreen(){
        btnCloseTutorial.click();
    }

    public boolean isTutorialPageOpened(String pageName) {
        return getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(TUTORIAL_TAB_BY_NAME_LOCATOR_ANDROID, pageName))),
                new IosLocator(By.xpath(String.format(TUTORIAL_TAB_BY_NAME_LOCATOR_IOS, pageName)))), "Tutorial tab").state().waitForDisplayed();
    }

    public void goToNextPage() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.RIGHT);
    }

    public List<String> getListOfPageNames() {
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.ANDROID) {
            return getListOfLabelsOfTutorialTabs().stream().map(tab -> tab.getAttribute(AndroidAttributes.CONTENT_DESC)).collect(Collectors.toList());
        } else {
            return getListOfLabelsOfTutorialTabs().stream().map(tab -> tab.getAttribute(IosAttributes.NAME)).collect(Collectors.toList());
        }
    }

    private List<ILabel> getListOfLabelsOfTutorialTabs() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(TUTORIAL_TAB_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(TUTORIAL_TAB_LOCATOR_IOS))), ElementType.LABEL, ElementsCount.ANY, ElementState.EXISTS_IN_ANY_STATE);
    }
}
