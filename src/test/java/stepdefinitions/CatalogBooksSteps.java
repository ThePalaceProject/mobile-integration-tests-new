package stepdefinitions;

import com.google.inject.Inject;
import enums.BookType;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import screens.CatalogBooksScreen;

public class CatalogBooksSteps {

    CatalogBooksScreen catalogBooksScreen;
    private final ScenarioContext context;

    @Inject
    public CatalogBooksSteps(ScenarioContext context) {
        this.context = context;
        catalogBooksScreen = new CatalogBooksScreen();
    }

    @Then("{} book with {} action button and {string} bookName is displayed on Catalog books screen")
    public void isBookDisplayed(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookNameKey){
        String bookName = context.get(bookNameKey);
        Assert.assertTrue(String.format("'%s' book with specific button is not displayed on catalog books screen", bookName),
                catalogBooksScreen.isBookDisplayed(bookType, bookName, actionButtonKey));
    }

    @Then("Books contain word {string} on Catalog books screen")
    public void isBooksContainWord(String wordKey) {
        String word = context.get(wordKey);
        Assert.assertTrue("Search result does not contain books with " + word, catalogBooksScreen.isBooksContainWord(word));
    }

    @Then("The first book has {string} bookName on Catalog books screen")
    public void isFirstBookContainsWord(String wordKey){
        String word = context.get(wordKey);
        Assert.assertEquals("The book " + word + " is not found!", word.toLowerCase(), catalogBooksScreen.getNameOfFirstBook().toLowerCase());
    }

    @Then("There is no results on Catalog books screen")
    public void isNoResults(){
        Assert.assertTrue("Results are not empty!", catalogBooksScreen.isNoResults());
    }
}
