package screens;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.CatalogBookModel;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.List;

public class SubcategoryScreen extends Screen {

    private final ILabel lblFirstBookTitle = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@resource-id, \"bookCellIdleTitle\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeCell//XCUIElementTypeStaticText[1]"))), "First book title label");
    private final ILabel lblFirstBookAuthor = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.TextView[contains(@resource-id, \"bookCellIdleAuthor\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeCell//XCUIElementTypeStaticText[@name][2]"))), "First book author label");
    private final ILabel lblFirstBook = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.ImageView[contains(@resource-id,\"bookCellIdleCover\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeCell//XCUIElementTypeStaticText[1]"))), "First book label");
    private final IButton btnBack = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id, \"mainToolbar\")]/android.widget.ImageView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[1]"))), "Back button");
    public final IButton btnSortingPalace = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.Button")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[1]"))), "Sort by button in Palace");
    public final IButton btnSorting = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.Button[2]")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[1]"))), "Sort by button");

    private static final String CURRENT_SUBCATEGORY_LOCATOR_ANDROID = "//*[contains(@resource-id, \"feedLaneTitle\") and @text=\"%1$s\"]";
    private static final String CURRENT_SUBCATEGORY_LOCATOR_IOS = "//XCUIElementTypeTable/XCUIElementTypeButton[contains(@name, \"%s\")]" ;

    public SubcategoryScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//androidx.recyclerview.widget.RecyclerView[contains(@resource-id,\"feedWithoutGroupsList\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeCollectionView"))), "Subcategory screen");
    }

    public void openCategory(String categoryName) {
        IButton categoryButton = getCategoryButton(categoryName);
        categoryButton.state().waitForDisplayed();
        categoryButton.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        categoryButton.click();
    }

    private IButton getCategoryButton(String categoryName) {
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(CURRENT_SUBCATEGORY_LOCATOR_ANDROID, categoryName))),
                new IosLocator(By.xpath(String.format(CURRENT_SUBCATEGORY_LOCATOR_IOS, categoryName)))), categoryName);
    }

    public CatalogBookModel getFirstBookInfo() {
        return new CatalogBookModel()
                .setTitle(lblFirstBookTitle.getText())
                .setAuthor(lblFirstBookAuthor.getText());
    }

    public void openFirstBook() {
        lblFirstBook.click();
    }

    public void tapBack() {
        btnBack.click();
    }

    public String getNameOfSorting(String library) {
        if(library.equals("Palace Bookshelf")) {
            return btnSortingPalace.getText();
        }
        return btnSorting.getText();
    }

//    public List<String> getAuthorsInfo() {
//        AqualityServices.getConditionalWait().waitFor(() -> getElements(BOOKS_LOCATOR + AUTHOR_INFO_XPATH, ElementType.LABEL).size() > COUNT_OF_ITEMS_TO_WAIT_FOR);
//        List<String> listOfNames = getValuesFromListOfLabels(BOOKS_LOCATOR + AUTHOR_INFO_XPATH);
//        AqualityServices.getLogger().info("Found list of authors - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining("; ")));
//        return listOfNames;
//    }

    private List<aquality.appium.mobile.elements.interfaces.IElement> getElements(String xpath, ElementType label) {
        return getElementFactory().findElements(By.xpath(xpath), label);
    }
}
