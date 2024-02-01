package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class SearchSteps {

    private static final SecureRandom random = new SecureRandom();
    private final MainToolBarScreen mainToolBarScreen;
    private final SearchScreen searchScreen;
    private final CatalogScreen catalogScreen;
    private final SubcategoryScreen subcategoryScreen;
    private final CatalogBooksScreen catalogBooksScreen;
    private final ScenarioContext context;

    @Inject
    public SearchSteps(ScenarioContext context) {
        this.context = context;
        mainToolBarScreen = new MainToolBarScreen();
        searchScreen = new SearchScreen();
        catalogScreen = new CatalogScreen();
        subcategoryScreen = new SubcategoryScreen();
        catalogBooksScreen = new CatalogBooksScreen();
    }

    @When("Open search modal")
    public void openSearchModal(){
        mainToolBarScreen.openSearchModal();
    }

    @When("Search for {string} and save bookName as {string}")
    public void searchFor(String textForSearch, String bookNameInfoKey) {
        context.add(bookNameInfoKey, textForSearch);
        Assert.assertTrue("Search modal is not present!", searchScreen.isSearchScreenOpened());
        searchScreen.setSearchedText(textForSearch);
        searchScreen.applySearch();
    }

    @When("Type text {string} and save it as {string}")
    public void typeBook(String bookName, String bookNameKey) {
        context.add(bookNameKey, bookName);
        Assert.assertTrue("Search modal is not present!", searchScreen.isSearchScreenOpened());
        searchScreen.setSearchedText(bookName);
    }

    @When("Clear search field on Catalog books screen")
    public void clearSearchField() {
        searchScreen.clearSearchField();
    }

    @Then("Search field is empty on Catalog books screen")
    public void isSearchFieldEmpty(){
        Assert.assertTrue("Search field is not empty!", searchScreen.isSearchFieldEmpty());
    }

    @When("Search for word {} and save as {string} on Catalog books screen")
    public void typeWord(String word, String wordKey) {
        context.add(wordKey, word);
        Assert.assertTrue("Search modal is not present!", searchScreen.isSearchScreenOpened());
        searchScreen.setSearchedText(word);
        searchScreen.applySearch();
    }

    @Then("Placeholder contains {string} text in search field")
    public void checkTextIsSearchField(String expectedText){
        String actualText = searchScreen.getTextFromSearchField();
        Assert.assertTrue(String.format("Search field does not contain %s!", expectedText), actualText.contains(expectedText));
    }

    @Then("Placeholder contains word {string} text in search field")
    public void checkNewTextInSearchField(String textKey) {
        String actualText = searchScreen.getTextFromSearchField();
        String expectedText = context.get(textKey);
        Assert.assertTrue(String.format("Search field does not contain %s!", expectedText), actualText.contains(expectedText));
    }

    @When("Edit data by adding characters in search field and save it as {string}")
    public void editData(String textToAddKey){
        searchScreen.deleteSomeData();
        searchScreen.deleteSomeData();
        searchScreen.inputCharacterK();
        searchScreen.inputCharacterK();
        searchScreen.inputCharacterK();
        context.add(textToAddKey, searchScreen.getTextFromSearchField());
    }

    @Then("There is no possibility to search with empty search field")
    public void checkSearchingWithEmptyField() {
        if(AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            Assert.assertFalse("Search button is clickable!", searchScreen.isSearchButtonClickable());
        }
        else {
            searchScreen.applySearch();
            Assert.assertTrue("Search modal disappear!", searchScreen.isSearchScreenOpened());
        }
    }

    @Then("The search field is displayed and contains {string} book")
    public void checkTheDisplayingOfSearchField(String bookNameKey){
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(searchScreen.isSearchLineDisplayed()).as("Search field is not displayed!").isTrue();
        softAssertions.assertThat(searchScreen.getTextFromSearchField()).as("Book name is not displayed!").isEqualTo(context.get(bookNameKey));
        softAssertions.assertAll();
    }

    @Then("The search field is displayed")
    public void isSearchFieldDisplayed() {
        Assert.assertTrue("Search field is not displayed!", searchScreen.isSearchLineDisplayed());
    }

    @When("Search several books and save them in list as {string}:")
    public void searchSeveralBooks(String listKey, List<String> listOfBooks) {
        List<String> savedBooks = new ArrayList<>();
        listOfBooks.forEach(book -> {
            savedBooks.add(book);
            searchScreen.setSearchedText(book);
            searchScreen.applySearch();
            Assert.assertTrue(String.format("Search results page for value '%s' is not present", book), subcategoryScreen.state().waitForDisplayed());
            catalogBooksScreen.clickActionButton(ActionButtonsForBooksAndAlertsKeys.GET, book);
            searchScreen.clearSearchField();
        });
        context.add(listKey, savedBooks);
    }

    @When("Return back from search modal")
    public void returnBack() {
        searchScreen.closeSearchScreen();
    }

    @When("Search a book from the list {string} and save book name as {string}")
    public void searchFromList(String listNameKey, String bookNameInfoKey) {
        List<String> bookList = context.get(listNameKey);
        int bookIndex = random.nextInt(bookList.size());
        String bookName = bookList.get(bookIndex);
        searchScreen.setSearchedText(bookName);
        searchScreen.applySearch();
        context.add(bookNameInfoKey, bookName);
    }
}
