package screens;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.CatalogBookModel;
import models.IosLocator;
import org.openqa.selenium.By;

public class BookDetailsScreen extends Screen {

    private final ILabel lblBookTitle = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("bookDetailTitle")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther[1]//XCUIElementTypeStaticText[1]"))), "Book title label");
    private final ILabel lblBookAuthor = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.id("bookDetailAuthors")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther[1]//XCUIElementTypeStaticText[2]"))), "Book author label");
    private final ILabel lblBookCover = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[contains(@resource-id, \"bookDetailCoverImage\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeOther//XCUIElementTypeImage[1]"))), "Book cover");

    public BookDetailsScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("bookDetailCover")),
                new IosLocator(By.xpath("//XCUIElementTypeStaticText[@name=//XCUIElementTypeNavigationBar/@name]"))), "Book details screen");
    }

    public CatalogBookModel getBookInfo() {
        return new CatalogBookModel()
                .setTitle(lblBookTitle.getText())
                .setAuthor(lblBookAuthor.getText());
    }

    public boolean isBookHasCover() {
        return lblBookCover.state().isExist();
    }
}
