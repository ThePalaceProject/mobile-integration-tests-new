package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import enums.BookType;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CatalogBookModel;
import org.junit.Assert;
import screens.AlertScreen;
import screens.CatalogBooksScreen;

public class CatalogBooksSteps {

    private final CatalogBooksScreen catalogBooksScreen;
    private final AlertScreen alertScreen;
    private final ScenarioContext context;

    @Inject
    public CatalogBooksSteps(ScenarioContext context) {
        this.context = context;
        catalogBooksScreen = new CatalogBooksScreen();
        alertScreen = new AlertScreen();
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

    @When("Open {} book with {} action button and {string} bookName on Catalog books screen and save book as {string}")
    public void openBookAndSaveBookInfo(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookNameKey, String bookInfoKey) {
        String bookName = context.get(bookNameKey);
        CatalogBookModel bookInfo = catalogBooksScreen.openBookAndGetBookInfo(bookType, bookName, actionButtonKey);
        context.add(bookInfoKey, bookInfo);
    }

    @When("Click {} action button on {} book with {string} bookName on Catalog books screen and save book as {string}")
    public void clickActionButtonAndSaveBookInfo(ActionButtonsForBooksAndAlertsKeys actionButtonKey, BookType bookType, String bookNameKey, String bookInfoKey) {
        String bookName = context.get(bookNameKey);
        CatalogBookModel bookInfo = catalogBooksScreen.clickActionButtonAndGetBookInfo(bookType, bookName, actionButtonKey);
        context.add(bookInfoKey, bookInfo);
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && alertScreen.state().waitForDisplayed()) {
            if (actionButtonKey == ActionButtonsForBooksAndAlertsKeys.RETURN || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.DELETE || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.REMOVE) {
                alertScreen.waitAndPerformAlertActionIfDisplayed(actionButtonKey);
            } else {
                AqualityServices.getApplication().getDriver().switchTo().alert().dismiss();
                AqualityServices.getLogger().info("Alert appears and dismiss alert");
            }
        }
    }

    @Then("{} book with {} action button and {string} bookInfo is present on Catalog books screen")
    public void isBookPresent(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        String bookName = bookInfo.getTitle();
        Assert.assertTrue(String.format("'%s' book with specific action button is not present on catalog books screen", bookName),
                catalogBooksScreen.isBookDisplayed(bookType, bookName, actionButtonKey));
    }
}
