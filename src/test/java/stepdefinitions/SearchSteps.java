package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.CatalogScreen;
import screens.MainToolBarScreen;
import screens.SearchScreen;

public class SearchSteps {

    private final MainToolBarScreen mainToolBarScreen;
    private final SearchScreen searchScreen;
    private final CatalogScreen catalogScreen;
    private final ScenarioContext context;

    @Inject
    public SearchSteps(ScenarioContext context) {
        this.context = context;
        mainToolBarScreen = new MainToolBarScreen();
        searchScreen = new SearchScreen();
        catalogScreen = new CatalogScreen();
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
}
