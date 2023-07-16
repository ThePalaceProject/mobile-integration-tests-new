package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import constants.RegEx;
import constants.localization.sortoptions.SortByKeys;
import framework.utilities.ScenarioContext;
import framework.utilities.swipe.SwipeElementUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.*;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CatalogSteps {

    private final CatalogScreen catalogScreen;
    private final MenuBarScreen menuBarScreen;
    private final SubcategoryScreen subcategoryScreen;
    private final MainToolBarScreen mainToolBarScreen;
    private final CatalogBooksScreen catalogBooksScreen;
    private final SortOptionsScreen sortOptionsScreen;
    private final ScenarioContext context;

    @Inject
    public CatalogSteps(ScenarioContext context){
        this.context = context;
        catalogScreen = new CatalogScreen();
        menuBarScreen = new MenuBarScreen();
        subcategoryScreen = new SubcategoryScreen();
        mainToolBarScreen = new MainToolBarScreen();
        catalogBooksScreen = new CatalogBooksScreen();
        sortOptionsScreen = new SortOptionsScreen();
    }

    @When("Open Catalog")
    public void openCatalog() {
        menuBarScreen.openBottomMenuTab(MenuBar.CATALOG);
    }

    @Then("Catalog screen is opened")
    public void isCatalogScreenOpened() {
        Assert.assertTrue("Catalog is not opened!", catalogScreen.isCatalogScreenOpened());
    }

    @Then("Library {string} is present on Catalog Screen")
    public void isLibraryPresent(String libraryName){
        Assert.assertTrue(String.format("Library %s is not present on Catalog Screen!", libraryName), catalogScreen.isLibraryPresent(libraryName));
    }

    @Then("Count of books in first category is more than {int}")
    public void checkCountOfBooksInFirstCategory(int countOfBooks) {
        Assert.assertTrue("Count of books is smaller than " + countOfBooks, countOfBooks <= catalogScreen.getListOfBooksNameInFirstCategory().size());
    }

    @When("Get names of books on screen and save them as {string}")
    public void getNamesOfBooksAndSaveThem(String booksNamesListKey) {
        List<String> books = catalogScreen.getListOfBooksNames();
        context.add(booksNamesListKey, books);
    }

    @When("Open categories by chain and chain starts from CategoryScreen:")
    public void openCategoriesByChainAndChainStartsFromCategoryScreen(List<String> categoriesChain) {
        SwipeElementUtils.swipeUp();
        categoriesChain.forEach(categoryName -> {
            if (catalogScreen.state().waitForDisplayed()) {
                catalogScreen.openCategory(categoryName);
            } else {
                subcategoryScreen.state().waitForDisplayed();
                subcategoryScreen.openCategory(categoryName);
            }
        });
    }

    @Then("Subcategory name is {string}")
    public void checkCurrentCategoryName(String expectedCategoryName) {
        Assert.assertTrue(String.format("Current category name is not correct! Expected '%1$s' but found '%2$s'", expectedCategoryName, mainToolBarScreen.getCategoryName()), AqualityServices.getConditionalWait().waitFor(() -> mainToolBarScreen.getCategoryName().equals(expectedCategoryName), "Wait while category become correct."));
    }

    @Then("List of books on screen is not equal to list of books saved as {string}")
    public void checkListOfBooksAndSavedListOfBooks(String booksNamesListKey) {
        List<String> expectedList = context.get(booksNamesListKey);
        List<String> actualList = catalogBooksScreen.getListOfBooks();
        Assert.assertNotEquals("Lists of books are equal" + expectedList.stream().map(Object::toString).collect(Collectors.joining(", ")), expectedList, actualList);
    }

    @When("Open first book in Subcategory List and save it as {string}")
    public void openFirstBookInSubcategoryListAndSaveIt(String bookInfoKey) {
        context.add(bookInfoKey, subcategoryScreen.getFirstBookInfo());
        subcategoryScreen.openFirstBook();
    }

    @Then("Category names are correct on Catalog screen")
    public void isCategoryNamesCorrect() {
        Set<String > categoriesNames = catalogScreen.getAllCategoriesNames();
        categoriesNames.forEach(category -> Assert.assertTrue("Category name " + category + " have invalid symbols",
                category.matches(RegEx.VALID_SYMBOLS_IN_CATALOG_NAMES)));
    }

    @Then("More button is present on each section of books on Catalog screen")
    public void isMoreBtnPresent() {
        Assert.assertTrue("More... button is not displayed on each section", catalogScreen.isMoreBtnPresent());
    }

    @When("Click More button from random book section and save name of section as {string} on Catalog screen")
    public void isMoreBtnClickable(String sectionNameKey) {
        context.add(sectionNameKey, catalogScreen.clickToMoreBtn());
    }

    @Then("Book section {string} is opened")
    public void isSectionOpened(String sectionNameKey) {
        String sectionName = context.get(sectionNameKey);
        Assert.assertTrue("Book section " + sectionName + " is not opened!", catalogScreen.isBookSectionOpened(sectionName));
    }

    @When("Tap Back button on Subcategory screen")
    public void tapBackBtn() {
        subcategoryScreen.tapBack();
    }

    @Then("Books are sorted by Author by default on subcategory screen in {string}")
    public void isSortedByDefaultInPalace(String libraryName) {
        if(AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            Assert.assertEquals("Books are not sorted by default", "Author", subcategoryScreen.getNameOfSorting(libraryName));
        }
        Assert.assertEquals("Books are not sorted by default", "Author", subcategoryScreen.getNameOfSorting(libraryName));
    }

    @Then("There are sorting by {string}, {string} and {string} on Subcategory screen")
    public void checkTypeOfSorting(String type1, String type2, String type3) {
        sortOptionsScreen.openSortBy();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type1)).as("There is no sorting type by " + type1).isEqualTo(type1);
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type2)).as("There is no sorting type by " + type2).isEqualTo(type2);
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type3)).as("There is no sorting type by " + type3).isEqualTo(type3);
        softAssertions.assertAll();
    }

    @When("Sort books by {}")
    public void sortBooksBy(SortByKeys sortingCategory) {
        sortOptionsScreen.openSortBy();
        sortOptionsScreen.changeSortByTo(sortingCategory);
    }

    @Then("Books are sorted by Author ascending")
    public void checkBooksAreSortedByAuthorAscending() {
//        List<String> list = subcategoryScreen.getAuthorsInfo();
//        List<String> listOfSurnames = getSurnames(list);
//        Assert.assertEquals("Lists of authors is not sorted properly " + list.stream().map(Object::toString).collect(Collectors.joining(", ")), getSurnames(listOfSurnames.stream().sorted().collect(Collectors.toList())), listOfSurnames);
    }
}