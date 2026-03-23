package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.appium.mobile.application.AqualityServices;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import java.time.Duration;

public class MainToolBarScreen extends Screen {

    private final IButton btnSearch = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.id("search_button")),
            new IosLocator(By.xpath("//XCUIElementTypeButton[@name=\"search.button\"] | //XCUIElementTypeButton[@name=\"Search\"] | //XCUIElementTypeButton[@label=\"Search\"] | //XCUIElementTypeNavigationBar/XCUIElementTypeButton[2]"))), "Search button");
    private final ILabel lblCategoryName = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//*[contains(@resource-id,\"mainToolbar\")]/android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText"))), "Category name");
    private final ILabel lblSearchFieldIos = getElementFactory().getLabel(By.xpath("//XCUIElementTypeSearchField"), "Search field iOS");
    private final IButton btnChooseAnotherLibrary = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//*[contains(@resource-id,\"mainToolbar\")]/android.widget.ImageView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name=\"librarySwitchButton\"]"))), "Change library account");

    public MainToolBarScreen(){
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//*[contains(@resource-id,\"mainToolbar\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar"))), "Main tool bar screen");
    }

    public void openSearchModal(){
        ActionProcessorUtils.doForAndroid(() -> btnSearch.click());

        ActionProcessorUtils.doForIos(() -> {
            if (btnSearch.state().isDisplayed()) {
                btnSearch.click();
                if (isSearchFieldDisplayed()) {
                    return;
                }
            }

            tapSearchButtonByCoordinates();
        });
    }

    public String getCategoryName() {
        return lblCategoryName.getText();
    }

    public void chooseAnotherLibrary() {
        btnChooseAnotherLibrary.click();
    }

    private void tapSearchButtonByCoordinates() {
        Dimension screenSize = AqualityServices.getApplication().getDriver().manage().window().getSize();
        int[][] points = new int[][]{
                {(int) (screenSize.getWidth() * 0.93), (int) (screenSize.getHeight() * 0.06)},
                {(int) (screenSize.getWidth() * 0.90), (int) (screenSize.getHeight() * 0.06)},
                {(int) (screenSize.getWidth() * 0.96), (int) (screenSize.getHeight() * 0.06)},
                {(int) (screenSize.getWidth() * 0.93), (int) (screenSize.getHeight() * 0.08)},
                {(int) (screenSize.getWidth() * 0.96), (int) (screenSize.getHeight() * 0.08)}
        };

        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        for (int[] point : points) {
            action.tap(PointOption.point(point[0], point[1])).perform();
            if (AqualityServices.getConditionalWait().waitFor(this::isSearchFieldDisplayed, Duration.ofSeconds(1))) {
                return;
            }
        }
    }

    private boolean isSearchFieldDisplayed() {
        return lblSearchFieldIos.state().isDisplayed();
    }
}
