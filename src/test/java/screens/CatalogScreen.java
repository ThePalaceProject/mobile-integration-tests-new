package screens;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.Attributes;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import constants.appattributes.IosAttributes;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.LocatorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class CatalogScreen extends Screen {
    private final Random random = new Random();

    private final ILabel lblCatalog = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id, \"mainToolbar\")]/android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText"))), "Catalog label");
    private final ILabel lblCategoryName = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.widget.LinearLayout/android.widget.TextView[contains(@resource-id,\"feedLaneTitle\")]")),
            new IosLocator(By.xpath("//XCUIElementTypeTable/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]"))), "Category name label");
    private final GetNameOfBookTypeBtb btnBookNameTypeSection = (button -> getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath(String.format("//android.widget.RadioGroup[contains(@resource-id, \"feedHeaderTabs\")]/android.widget.RadioButton[@text=\"%s\"]", button))),
            new IosLocator(By.xpath(String.format("//XCUIElementTypeSegmentedControl/XCUIElementTypeButton[@name=\"%s\"]", button)))), String.format("%s type of sorting", button)));
    private final IButton btnLogo = getElementFactory().getButton(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id, \"mainToolbar\")]/android.widget.ImageView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[1]"))), "Logo");

    private static final String CATEGORY_NAME_LOCATOR_ANDROID = "//android.widget.TextView[contains(@resource-id, \"feedLaneTitle\") and @text=\"%1$s\"]/parent::android.widget.LinearLayout/following-sibling::*[contains(@resource-id,\"feedLaneCoversScroll\")]";
    private static final String CATEGORY_LOCATOR_ANDROID = "//androidx.recyclerview.widget.RecyclerView//android.widget.LinearLayout/android.widget.TextView[1]";
    private static final String BOOK_COVER_IN_CATEGORY_LOCATOR_ANDROID = "/android.widget.LinearLayout";
    private static final String BOOK_NAME_LOCATOR_ANDROID = "//androidx.recyclerview.widget.RecyclerView[contains(@resource-id,\"feedWithoutGroupsList\")]/android.widget.FrameLayout/android.view.ViewGroup/android.widget.TextView[1]";
    private static final String CURRENT_CATEGORY_LOCATOR_ANDROID = "//android.widget.TextView[contains(@resource-id, \"feedLaneTitle\") and @text=\"%1$s\"]";
    private static final String MORE_BUTTON_LOCATOR_ANDROID = "//android.widget.LinearLayout/android.widget.TextView[@text=\"More…\"]";
    private static final String CURRENT_SECTION_LOCATOR_IN_CATALOG_ANDROID = "//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[%d]/android.widget.LinearLayout/android.widget.TextView[1]";
    private static final String SECTION_TITLE_ANDROID = "//android.view.ViewGroup/android.widget.TextView[@text=\"%s\"]";
    private static final String CATALOG_TAB_LOCATOR_ANDROID = "//android.widget.RadioButton[@text=\"%s\"]";
    private static final String LIBRARY_BUTTON_LOCATOR_PATTERN_ANDROID = "//android.widget.TextView[contains(@resource-id,\"accountTitle\") and @text=\"%s\"]";

    private static final String CATEGORY_NAME_LOCATOR_IOS = "(//XCUIElementTypeOther[.//XCUIElementTypeButton[@name=\"%1$s\"]]/following-sibling::XCUIElementTypeCell)[1]";
    private static final String CATEGORY_LOCATOR_IOS = "//XCUIElementTypeTable/XCUIElementTypeOther/XCUIElementTypeButton[1]";
    private static final String BOOK_COVER_IN_CATEGORY_LOCATOR_IOS = "/XCUIElementTypeButton";
    private static final String BOOK_NAME_LOCATOR_IOS = "//XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeOther/XCUIElementTypeStaticText[1]";
    private static final String CURRENT_CATEGORY_LOCATOR_IOS = "//XCUIElementTypeTable/XCUIElementTypeOther/XCUIElementTypeButton[contains(@name, \"%s\")]";
    private static final String MORE_BUTTON_LOCATOR_IOS = "//XCUIElementTypeTable/XCUIElementTypeOther/XCUIElementTypeButton[contains(@name,\"More\")]";
    private static final String CURRENT_SECTION_LOCATOR_IN_CATALOG_IOS = "//XCUIElementTypeTable/XCUIElementTypeButton[%d]";
    private static final String SECTION_TITLE_IOS = "//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText[@name=\"%s\"]";
    private static final String CATALOG_TAB_LOCATOR_IOS = "//XCUIElementTypeButton[@name=\"%1$s\"]";
    private static final String LIBRARY_BUTTON_LOCATOR_PATTERN_IOS = "//XCUIElementTypeButton[@name=\"%1$s\"]";
    private static final int COUNT_OF_CATEGORIES_TO_WAIT_FOR = 5;

    public CatalogScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("feedWithGroups")),
                new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[contains(@name, \"AccessibilitySwitchLibrary\")]"))), "Catalog screen");
    }

    public boolean isCatalogScreenOpened() {
        return lblCatalog.state().waitForDisplayed();
    }

    public Set<String> getListOfBooksNameInFirstCategory() {
        String categoryName = lblCategoryName.getText();

        List<String> currentBooksNames = getValuesFromListOfLabels(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(CATEGORY_NAME_LOCATOR_ANDROID, categoryName) + BOOK_COVER_IN_CATEGORY_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(String.format(CATEGORY_NAME_LOCATOR_IOS, categoryName) + BOOK_COVER_IN_CATEGORY_LOCATOR_IOS))));

        Set<String> bookNames = new HashSet<>();
        ILabel categoryLine = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(CATEGORY_NAME_LOCATOR_ANDROID, categoryName))),
                new IosLocator(By.xpath(String.format(CATEGORY_NAME_LOCATOR_IOS, categoryName)))), String.format("Category %1$s line", categoryName));
        categoryLine.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        do {
            bookNames.addAll(currentBooksNames);
            SwipeElementUtils.swipeFromRightToLeft(categoryLine);
            currentBooksNames = getValuesFromListOfLabels(LocatorUtils.getLocator(
                    new AndroidLocator(By.xpath(String.format(CATEGORY_NAME_LOCATOR_ANDROID, categoryName) + BOOK_COVER_IN_CATEGORY_LOCATOR_ANDROID)),
                    new IosLocator(By.xpath(String.format(CATEGORY_NAME_LOCATOR_IOS, categoryName) + BOOK_COVER_IN_CATEGORY_LOCATOR_IOS))));
        } while (!bookNames.containsAll(currentBooksNames));
        return bookNames;
    }

    public List<String> getListOfBooksNames() {
        List<String> listOfBookNames = ActionProcessorUtils.doForAndroid(() -> {
            List<ILabel> listOfBooks = getElementFactory().findElements(By.xpath(BOOK_NAME_LOCATOR_ANDROID), ElementType.LABEL);
            return listOfBooks.stream().map(book -> book.getText()).collect(Collectors.toList());
        });

        if(listOfBookNames == null) {
            listOfBookNames = ActionProcessorUtils.doForIos(() -> {
                state().waitForDisplayed();
                int countOfItems = getElements(By.xpath(BOOK_NAME_LOCATOR_IOS)).size();
                AqualityServices.getConditionalWait().waitFor(() -> getElements(By.xpath(BOOK_NAME_LOCATOR_IOS)).size() <= countOfItems);
                List<String> listOfNames = getValuesFromListOfLabels(By.xpath(BOOK_NAME_LOCATOR_IOS));
                AqualityServices.getLogger().info("Found list of books from all subcategories on screen - " + listOfNames.stream().map(Object::toString)
                        .collect(Collectors.joining(", ")));
                AqualityServices.getLogger().info("amount of books from all subcategories on screen - " + listOfNames.size());
                return listOfNames;
            });
        }

        return listOfBookNames;
    }

    public void openCategory(String categoryName) {
        IButton categoryButton = getCategoryButton(categoryName);
        if (!categoryButton.state().waitForDisplayed()) {
            categoryButton.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        }
        categoryButton.click();
    }

    public Set<String> getAllCategoriesNames() {
        AqualityServices.getConditionalWait().waitFor(() -> getElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(CATEGORY_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(CATEGORY_LOCATOR_IOS)))).size() > COUNT_OF_CATEGORIES_TO_WAIT_FOR);
        List<String> currentBooksNames = geListOfCategoriesNames();
        return new HashSet<>(currentBooksNames);
    }

    public boolean areCategoryNamesDisplayed() {
        AqualityServices.getConditionalWait().waitFor(() -> getElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(CATEGORY_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(CATEGORY_LOCATOR_IOS)))).size() > COUNT_OF_CATEGORIES_TO_WAIT_FOR);
        List<String> currentBooksNames = geListOfCategoriesNames();
        return !currentBooksNames.isEmpty();
    }

    public boolean isMoreBtnPresent() {
        List<IButton> buttons = getMoreBtn();

        return buttons.stream().allMatch(button -> button.state().waitForDisplayed());
    }

    public String clickToMoreBtn() {
        List<IButton> buttons = getMoreBtn();

        int randomNumber = random.nextInt(buttons.size()) + 1;
        String sectionName = getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(CURRENT_SECTION_LOCATOR_IN_CATALOG_ANDROID, randomNumber))),
                new IosLocator(By.xpath(String.format(CURRENT_SECTION_LOCATOR_IN_CATALOG_IOS, randomNumber)))), "Book section name").getText();
        buttons.get(randomNumber - 1).click();

        return sectionName;
    }

    public boolean isBookSectionOpened(String sectionName) {
        return getElementFactory().getLabel(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(SECTION_TITLE_ANDROID, sectionName))),
                new IosLocator(By.xpath(String.format(SECTION_TITLE_IOS,sectionName)))), "Section title").getText().equals(sectionName);
    }

    private List<IButton> getMoreBtn() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(MORE_BUTTON_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(MORE_BUTTON_LOCATOR_IOS))), ElementType.BUTTON);
    }

    private List<String> geListOfCategoriesNames() {
        return getValuesFromListOfLabels(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(CATEGORY_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(CATEGORY_LOCATOR_IOS))));
    }

    public String getTheNameOfBookTypeBtn(String typeOfBookNameBtn) {
        IButton btnNameOfBookType = btnBookNameTypeSection.createBtn(typeOfBookNameBtn);
        return btnNameOfBookType.getText();
    }

    public boolean isSectionWithBookTypeOpen(String typeSection) {
        IButton btnSectionType = btnBookNameTypeSection.createBtn(typeSection);

        boolean isOpened = ActionProcessorUtils.doForIos(() -> btnSectionType.getAttribute(Attributes.VALUE).equals("1"));

        if(!isOpened) {
            isOpened = ActionProcessorUtils.doForAndroid(() -> btnSectionType.getAttribute(Attributes.CHECKED).equals(Boolean.TRUE.toString()));
        }

        return isOpened;
    }

    public void switchToCatalogTab(String catalogTab) {
        getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(CATALOG_TAB_LOCATOR_ANDROID, catalogTab))),
                new IosLocator(By.xpath(String.format(CATALOG_TAB_LOCATOR_IOS, catalogTab)))), catalogTab).click();
    }

    public void tapTheLogo() {
        btnLogo.click();
    }

    public void selectLibraryFromListOfAddedLibraries(String libraryName) {
        getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_PATTERN_ANDROID, libraryName))),
                new IosLocator(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_PATTERN_IOS, libraryName)))), "Menu").click();
    }

    private List<String> getValuesFromListOfLabels(By locator) {
        return getElements(locator)
                .stream()
                .map(x -> x.getAttribute(IosAttributes.NAME))
                .collect(Collectors.toList());
    }

    private List<IElement> getElements(By locator) {
        return getElementFactory().findElements(locator, ElementType.LABEL, ElementsCount.MORE_THEN_ZERO, ElementState.EXISTS_IN_ANY_STATE)
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    private IButton getCategoryButton(String categoryName) {
        return getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(CURRENT_CATEGORY_LOCATOR_ANDROID, categoryName))),
                new IosLocator(By.xpath(String.format(CURRENT_CATEGORY_LOCATOR_IOS, categoryName)))), categoryName);
    }

    @FunctionalInterface
    interface GetNameOfBookTypeBtb {
        IButton createBtn(String button);
    }
}
