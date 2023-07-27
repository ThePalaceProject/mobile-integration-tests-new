package screens;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.selenium.core.elements.interfaces.IElement;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.CatalogBookModel;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

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
    private final IButton btnSortingPalace = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.Button[2]")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[1]"))), "Sort by button in Palace");
    private final IButton btnSorting = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.Button")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[1]"))), "Sort by button");
    private final IButton btnAvailability = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.HorizontalScrollView//android.widget.Button[1]")),
            new IosLocator(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[2]"))), "Availability btn");
    private final IButton btnCollectionIos = getElementFactory().getButton(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[3]"), "Collection button");

    private static final String CURRENT_SUBCATEGORY_LOCATOR_ANDROID = "//*[contains(@resource-id, \"feedLaneTitle\") and @text=\"%1$s\"]";
    private static final String AUTHOR_INFO_LOCATOR_ANDROID = "//android.widget.TextView[contains(@resource-id, \"bookCellIdleAuthor\")]";
    private static final String BOOK_NAME_LOCATOR_ANDROID = "//android.widget.TextView[contains(@resource-id, \"bookCellIdleTitle\")]";
    private static final String BOOK_BUTTON_LOCATOR_ANDROID = "//android.widget.LinearLayout[contains(@resource-id,\"bookCellIdleButtons\")]/android.widget.Button";

    private static final String CURRENT_SUBCATEGORY_LOCATOR_IOS = "//XCUIElementTypeTable/XCUIElementTypeButton[contains(@name, \"%s\")]" ;
    private static final String AUTHOR_INFO_LOCATOR_IOS = "//XCUIElementTypeCell//XCUIElementTypeStaticText[@name][2]";
    private static final String BOOK_NAME_LOCATOR_IOS = "//XCUIElementTypeCell//XCUIElementTypeStaticText[1]";
    private static final String BOOK_BUTTON_LOCATOR_IOS = "//XCUIElementTypeCell//XCUIElementTypeButton";

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

    public List<String> getAuthorsInfo() {
        List<String> listOfNames = getValuesFromListOfLabels(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(AUTHOR_INFO_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(AUTHOR_INFO_LOCATOR_IOS))));
        AqualityServices.getLogger().info("Found list of authors - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining("; ")));
        return listOfNames;
    }

    public List<String> getTitlesInfo() {
        List<String> listOfNames = getValuesFromListOfLabels(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_NAME_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(BOOK_NAME_LOCATOR_IOS))));
        AqualityServices.getLogger().info("Found list of titles - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining("; ")));
        return listOfNames;
    }

    public String getAvailability() {
        return btnAvailability.getText();
    }

    public List<String> getBooksInfo() {
        List<String> listOfNames = getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_NAME_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(BOOK_NAME_LOCATOR_IOS))), ElementType.LABEL)
                .stream()
                .map(IElement::getText)
                .collect(Collectors.toList());
        AqualityServices.getLogger().info("Found list of books - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return listOfNames;
    }

    public List<String> getAllButtonsNames() {
        List<String> listOfNames = getValuesFromListOfLabels(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(BOOK_BUTTON_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(BOOK_BUTTON_LOCATOR_IOS))));
        AqualityServices.getLogger().info("Found list of buttons names - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining("; ")));
        return listOfNames;
    }

    public String getCollectionName() {
        return btnCollectionIos.getText();
    }


    private List<String> getValuesFromListOfLabels(By locator) {
        return getElementFactory().findElements(locator, ElementType.LABEL)
                .stream()
                .map(IElement::getText)
                .collect(Collectors.toList());
    }

    private List<aquality.appium.mobile.elements.interfaces.IElement> getElements(String xpath, ElementType label) {
        return getElementFactory().findElements(By.xpath(xpath), label);
    }
}
