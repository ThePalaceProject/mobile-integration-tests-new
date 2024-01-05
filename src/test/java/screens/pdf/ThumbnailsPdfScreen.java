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

public class ThumbnailsPdfScreen extends Screen {

    private static final String THUMBNAIL_LOCATOR_ANDROID = "//android.view.View[contains(@content-desc, \"Thumbnail of Page\")]";
    private static final String THUMBNAIL_NUMBER_LOCATOR_ANDROID = "//android.view.View[@content-desc=\"Thumbnail of Page %d\"]";

    private static final String THUMBNAIL_LOCATOR_IOS = "//XCUIElementTypeCollectionView/XCUIElementTypeCell";
    private static final String THUMBNAIL_NUMBER_LOCATOR_IOS = "//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[%d]/XCUIElementTypeStaticText";

    public ThumbnailsPdfScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.view.View[@resource-id=\"thumbnailView\"]")),
                new IosLocator(By.xpath("//XCUIElementTypeCollectionView"))), "Thumbnails screen");
    }

    public boolean areThumbnailsDisplayed() {
        return getThumbnails().size() != 0;
    }

    private List<ILabel> getThumbnails(){
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(THUMBNAIL_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(THUMBNAIL_LOCATOR_IOS))), ElementType.LABEL, ElementsCount.ANY, ElementState.EXISTS_IN_ANY_STATE);
    }

    public int openRandomThumbnail() {
        int thumbnailNumber = (int) (Math.random() * (getThumbnails().size()) + 1);
        ILabel thumbnail = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(THUMBNAIL_NUMBER_LOCATOR_ANDROID, thumbnailNumber))),
                new IosLocator(By.xpath(String.format(THUMBNAIL_NUMBER_LOCATOR_IOS, thumbnailNumber)))), "Thumbnail");
        thumbnail.click();
        return thumbnailNumber;
    }

    public void openThumbnail(int thumbnailNumber) {
        ILabel thumbnail = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(THUMBNAIL_NUMBER_LOCATOR_ANDROID, thumbnailNumber))),
                new IosLocator(By.xpath(String.format(THUMBNAIL_NUMBER_LOCATOR_IOS, thumbnailNumber)))), "Thumbnail");
        thumbnail.click();
    }
}
