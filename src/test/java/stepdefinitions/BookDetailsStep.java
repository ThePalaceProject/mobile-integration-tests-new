package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import constants.RegEx;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CatalogBookModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.AlertScreen;
import screens.BookDetailsScreen;

public class BookDetailsStep {

    private final BookDetailsScreen bookDetailsScreen;
    private final AlertScreen alertScreen;
    private final ScenarioContext context;

    @Inject
    public BookDetailsStep(ScenarioContext context) {
        this.context = context;
        bookDetailsScreen = new BookDetailsScreen();
        alertScreen = new AlertScreen();
    }

    @Then("Book {string} is opened on book details screen")
    public void isBookOpened(String bookInfoKey) {
        CatalogBookModel bookModel = context.get(bookInfoKey);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(bookDetailsScreen.getBookInfo().getTitle())
                .as("Expected book is not opened. Book title is wrong")
                .isEqualTo(bookModel.getTitle().replace(". Audiobook.", ""));
        softAssertions.assertThat(bookDetailsScreen.getBookInfo().getAuthor())
                .as("Expected book is not opened. Author is wrong")
                .isEqualTo(bookModel.getAuthor());
        softAssertions.assertAll();
    }

    @Then("Book {string} has correct title and author name on Book details screen")
    public void isBookInfoCorrect(String bookInfoKey) {
        CatalogBookModel bookModel = context.get(bookInfoKey);
        String bookName = bookModel.getTitle();
        String authorName = bookModel.getAuthor();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(bookName.matches(RegEx.VALID_AUTHOR_OR_TITLE)).as("Book title has invalid symbols").isTrue();
        softAssertions.assertThat(authorName.matches(RegEx.VALID_AUTHOR_OR_TITLE)).as("Author name has invalid symbols").isTrue();
        softAssertions.assertAll();
    }

    @Then("The book cover is displayed on Book details screen")
    public void isBookCoverDisplayed() {
        Assert.assertTrue("Book cover is not displayed", bookDetailsScreen.isBookHasCover());
    }

    @When("Click {} action button on Book details screen")
    public void pressOnBookDetailsScreenAtActionButton(ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        bookDetailsScreen.clickActionButton(actionButtonKey);
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && alertScreen.state().waitForDisplayed()) {
            if (actionButtonKey == ActionButtonsForBooksAndAlertsKeys.RETURN || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.DELETE
                    || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.REMOVE) {
                alertScreen.waitAndPerformAlertActionIfDisplayed(actionButtonKey);
            } else {
                AqualityServices.getApplication().getDriver().switchTo().alert().dismiss();
                AqualityServices.getLogger().info("Alert appears and dismiss alert");
            }
        }
    }

    @When("Click {} action button without closing alert on Book details screen")
    public void clickButtonWithoutClosingAlert(ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        bookDetailsScreen.clickActionButton(actionButtonKey);
    }

    @Then("Check that book contains {} action button on Book details screen")
    public void checkThatBookContainsActionButton(final ActionButtonsForBooksAndAlertsKeys key) {
        boolean isButtonPresent = bookDetailsScreen.isActionButtonDisplayed(key);
        Assert.assertTrue(String.format("Button '%1$s' is not present on book details screen!", key.getDefaultLocalizedValue()), isButtonPresent);
    }
}
