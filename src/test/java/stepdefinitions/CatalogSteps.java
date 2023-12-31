package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.RegEx;
import constants.localization.catalog.BookActionButtonNames;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import enums.localization.sortoptions.AvailabilityKeys;
import enums.localization.sortoptions.SortByKeys;
import framework.utilities.ScenarioContext;
import framework.utilities.swipe.SwipeElementUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.*;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<String> actualList = catalogScreen.getListOfBooksNames();
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

    @Then("Category names are loaded on Catalog screen")
    public void areCategoryNamesDisplayed() {
        Assert.assertTrue("Category names are not loaded!", catalogScreen.areCategoryNamesDisplayed());
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

    @When("Swipe sort options")
    public void swipeSortOptions() {
        double fromX = 870;
        double fromY = 350;
        double toX = 100;
        double toY = 350;

        SwipeElementUtils.swipeByCoordinates(fromX, fromY, toX, toY);
        SwipeElementUtils.swipeByCoordinates(fromX, fromY, toX, toY);
        SwipeElementUtils.swipeByCoordinates(fromX, fromY, toX, toY);
    }

    @Then("Books are sorted by Author by default on subcategory screen in {string}")
    public void isSortedByDefaultInPalace(String libraryName) {
        Assert.assertEquals("Books are not sorted by default", "Author", subcategoryScreen.getNameOfSorting(libraryName));
    }

    @Then("There are sorting by {string}, {string} and {string} on Subcategory screen in {string}")
    public void checkTypeOfSorting(String type1, String type2, String type3, String libraryName) {
        sortOptionsScreen.openSortBy(libraryName);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type1)).as("There is no sorting type by " + type1).isEqualTo(type1);
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type2)).as("There is no sorting type by " + type2).isEqualTo(type2);
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type3)).as("There is no sorting type by " + type3).isEqualTo(type3);
        softAssertions.assertAll();
    }

    @When("Sort books by {} in {string}")
    public void sortBooksBy(SortByKeys sortingCategory, String libraryName) {
        sortOptionsScreen.openSortBy(libraryName);
        sortOptionsScreen.changeSortByTo(sortingCategory);
    }

    @Then("Books are sorted by Author ascending")
    public void checkBooksAreSortedByAuthorAscending() {
        List<String> list = subcategoryScreen.getAuthorsInfo();
        List<String> listOfSurnames = getSurnames(list);
        Assert.assertEquals("Lists of authors is not sorted properly " + list.stream().map(Object::toString).collect(Collectors.joining(", ")), getSurnames(listOfSurnames.stream().sorted().collect(Collectors.toList())), listOfSurnames);
    }

    @Then("Books are sorted by Title ascending")
    public void booksAreSortedByTitleAscending() {
        List<String> list = subcategoryScreen.getTitlesInfo();
        Assert.assertEquals("Lists of authors is not sorted properly" + list.stream().map(Object::toString).collect(Collectors.joining(", ")), list.stream().sorted().collect(Collectors.toList()), list);
    }

    @When("Save list of books as {string}")
    public void saveListOfBooks(String booksInfoKey) {
        context.add(booksInfoKey, subcategoryScreen.getBooksInfo());
    }

    @Then("List of books on subcategory screen is not equal to list of books saved as {string}")
    public void checkListOfBooksOnSubcategoryScreenIsNotEqualToListOfSavedBooks(String booksNamesListKey) {
        List<String> expectedList = context.get(booksNamesListKey);
        Assert.assertNotEquals("Lists of books are equal" + expectedList.stream().map(Object::toString).collect(Collectors.joining(", ")), expectedList, subcategoryScreen.getBooksInfo());
    }

    @Then("There are types {string}, {string} and {string} of books on catalog book screen:")
    public void checkTypesOfBook(String type1, String type2, String type3) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(catalogScreen.getTheNameOfBookTypeBtn(type1)).as("There is no " + type1 + " book type section ").isEqualTo(type1);
        softAssertions.assertThat(catalogScreen.getTheNameOfBookTypeBtn(type2)).as("There is no " + type2 + " book type section ").isEqualTo(type2);
        softAssertions.assertThat(catalogScreen.getTheNameOfBookTypeBtn(type3)).as("There is no " + type3 + " book type section ").isEqualTo(type3);
    }

    @Then("Section with books of {string} type is opened on catalog book screen")
    public void isSectionIsOpened(String type) {
        Assert.assertTrue("Section with books " + type + " type are not opened", catalogScreen.isSectionWithBookTypeOpen(type));
    }

    @When("Switch to {string} catalog tab")
    public void switchToCatalogTab(String catalogTab) {
        catalogScreen.switchToCatalogTab(catalogTab);
        catalogScreen.state().waitForDisplayed();
    }

    @Then("The book availability is ALL by default on Subcategory screen")
    public void isAvailabilityByDefault() {
        Assert.assertEquals("Book availability is not by default", "All", subcategoryScreen.getAvailability());
    }

    @Then("There are availability by {string}, {string} and {string} on Subcategory screen")
    public void checkTypeOfAvailability(String type1, String type2, String type3) {
        sortOptionsScreen.openAvailability();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type1)).as("There is no sorting type by " + type1).isEqualTo(type1);
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type2)).as("There is no sorting type by " + type2).isEqualTo(type2);
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type3)).as("There is no sorting type by " + type3).isEqualTo(type3);
        softAssertions.assertAll();
    }

    @When("Change books visibility to show {}")
    public void checkThatActionButtonTextEqualToExpected(AvailabilityKeys availabilityKeys) {
        sortOptionsScreen.openAvailability();
        sortOptionsScreen.changeAvailabilityTo(availabilityKeys);
    }

    @Then("All books can be loaned or downloaded")
    public void checkAllBooksCanBeLoanedOrDownloaded() {
        Assert.assertTrue("Not all present books can be loaned or downloaded", subcategoryScreen.getAllButtonsNames()
                .stream()
                .allMatch(x -> x.equals(BookActionButtonNames.GET_BUTTON_NAME) || x.equals(BookActionButtonNames.DOWNLOAD_BUTTON_NAME)));
    }

    @Then("All books can be downloaded")
    public void checkAllBooksCanBeDownloaded() {
        Assert.assertTrue("Not all present books can be downloaded", subcategoryScreen.getAllButtonsNames()
                .stream()
                .allMatch(x -> x.equals(BookActionButtonNames.DOWNLOAD_BUTTON_NAME)));
    }

    @When("Collections is Everything by default on subcategory screen")
    public void isCollectionsByDefault() {
        Assert.assertEquals("Collection type is not by default", "Everything", subcategoryScreen.getCollectionName());
    }

    @Then("There are collection type by {string} and {string} on subcategory screen")
    public void checkTypeOfCollection(String type1, String type2) {
        sortOptionsScreen.openCollection();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type1)).as("There is no sorting type by " + type1).isEqualTo(type1);
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(type2)).as("There is no sorting type by " + type2).isEqualTo(type2);
        softAssertions.assertAll();
    }

    @When("Open book with {} action button and {string} bookName on catalog books screen")
    public void openBook(ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookNameInfoKey) {
        String bookName = context.get(bookNameInfoKey);
        catalogBooksScreen.openBook(actionButtonKey, bookName);
    }

    @Then("Subcategory screen is opened")
    public void checkSubcategoryScreenIsPresent() {
        boolean isScreenPresent = subcategoryScreen.state().waitForDisplayed();
        Assert.assertTrue("Subcategory screen is not present" , isScreenPresent);
    }

    @When("Switch to {string} from side menu")
    public void openLibraryFromSideMenu(String libraryName) {
        menuBarScreen.openBottomMenuTab(MenuBar.CATALOG);
        mainToolBarScreen.chooseAnotherLibrary();
        catalogScreen.selectLibraryFromListOfAddedLibraries(libraryName);
    }

    private List<String> getSurnames(List<String> list) {
        List<String> listOfSurnames = new ArrayList<>();
        for (String authorName : list) {
            String[] separatedName = authorName.split("\\s");
            List<String> sortedNames = Arrays.stream(separatedName).sorted().collect(Collectors.toList());
            listOfSurnames.add(sortedNames.get(0));
        }
        return listOfSurnames;
    }
}