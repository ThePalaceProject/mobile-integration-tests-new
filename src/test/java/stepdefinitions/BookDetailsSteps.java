package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import constants.RegEx;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.ScenarioContext;
import framework.utilities.swipe.SwipeElementUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CatalogBookModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.AlertScreen;
import screens.BookDetailsScreen;

public class BookDetailsSteps {
    private final BookDetailsScreen bookDetailsScreen;
    private final AlertScreen alertScreen;
    private final ScenarioContext context;

    @Inject
    public BookDetailsSteps(ScenarioContext context) {
        this.context = context;
        bookDetailsScreen = new BookDetailsScreen();
        alertScreen = new AlertScreen();
    }

    @Then("Book {string} is opened on book details screen")
    public void isBookOpened(String bookInfoKey) {
        CatalogBookModel bookModel = context.get(bookInfoKey);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(bookDetailsScreen.isBookTitleDisplayed(bookModel.getTitle().replace(". Audiobook.", "")))
                .as("Expected book is not opened. Book title is wrong")
                .isTrue();
        softAssertions.assertThat(bookDetailsScreen.isAuthorNameDisplayed(bookModel.getAuthor()))
                .as("Expected book is not opened. Author is wrong")
                .isTrue();
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

    @Then("Book format in Information section is displayed on Book details screen")
    public void isBookFormatDisplayed() {
        Assert.assertTrue("Book format is not displayed", bookDetailsScreen.isBookFormatInfoExist());
    }

    @Then("Book format in Information section is {string} on Book details screen")
    public void isBookFormatCorrect(String bookFormat) {
        Assert.assertEquals("Book format is not correct", bookFormat, bookDetailsScreen.getBookFormatInfo());
    }

    @Then("Description exists on Book details screen")
    public void isDescriptionNotEmpty() {
        Assert.assertTrue("Description section is empty", bookDetailsScreen.isDescriptionExists());
    }

    @Then("Button More in Description is available on Book details screen")
    public void moreBtnIsAvailable() {
        Assert.assertTrue("More button is not available", bookDetailsScreen.isMoreBtnInDescriptionAvailable());
    }

    @Then("Publisher and Categories in Information section are displayed on book details screen")
    public void isInformationSectionFull() {
        SoftAssertions softAssertions = new SoftAssertions();
        if (AqualityServices.getApplication().getPlatformName()==PlatformName.ANDROID) {
            SwipeElementUtils.swipeDown();
        }

        softAssertions.assertThat(bookDetailsScreen.isPublisherInfoExist()).as("Publisher field is not displayed").isTrue();
        softAssertions.assertThat(bookDetailsScreen.isCategoryInfoExist()).as("Categories field is not displayed").isTrue();
        softAssertions.assertAll();
    }

    @Then("Publisher and Categories in Information section are correct on book details screen")
    public void isInformationSectionIsCorrect() {
        SoftAssertions softAssertions = new SoftAssertions();
        if (AqualityServices.getApplication().getPlatformName()==PlatformName.ANDROID) {
            SwipeElementUtils.swipeDown();
        }

        String publisher = bookDetailsScreen.getPublisherInfo();
        softAssertions.assertThat(publisher.matches(RegEx.VALID_PUBLISHER_OR_CATEGORY_NAME)).as("Publisher field has invalid symbols").isTrue();
        String categories = bookDetailsScreen.getCategoryInfo();
        softAssertions.assertThat(categories.matches(RegEx.VALID_PUBLISHER_OR_CATEGORY_NAME)).as("Category field has invalid symbols").isTrue();
        softAssertions.assertAll();
    }

    @Then("Distributor is equal to {string} on book details screen")
    public void isDistributorCorrect(String distributor) {
        String distributorFromScreen = bookDetailsScreen.getDistributorInfo();
        Assert.assertEquals("Distributor is not correct", distributor.toLowerCase(), distributorFromScreen.toLowerCase());
    }

    @Then("Related books section of {string} book is displayed on book details screen")
    public void isRelatedBooksExists(String bookInfoKey) {
        CatalogBookModel bookModel = context.get(bookInfoKey);
        String authorName = bookModel.getAuthor();
        Assert.assertTrue("Related books section is not displayed", bookDetailsScreen.isRelatedBooksExists(authorName));
    }

    @Then("There is a list of related books on book details screen")
    public void listOfBooksIsDisplayed() {
        Assert.assertTrue("List of related books is empty", bookDetailsScreen.isListOfBooksDisplayed());
    }

    @Then("More button in related books section is available on book details screen")
    public void isMoreBtnInRelatedBooksAvailable() {
        Assert.assertTrue("More button in related books section is not available", bookDetailsScreen.isMoreBtnAvailableInRelatedBooks());
    }

    @When("Click {} action button and cancel downloading by click {} button on book detail screen")
    public void cancelBookDownloading(ActionButtonsForBooksAndAlertsKeys actionButtonKey, ActionButtonsForBooksAndAlertsKeys actionButtonCancel) {
        bookDetailsScreen.clickActionButtonForCancelTheAction(actionButtonKey);
        bookDetailsScreen.clickActionButtonForCancelTheAction(actionButtonCancel);
    }

    @When("Click {} button but cancel the action by clicking {} button on the alert")
    public void cancelBookReturningAndRemoving(ActionButtonsForBooksAndAlertsKeys actionButtonKey, ActionButtonsForBooksAndAlertsKeys alertButtonCancel) {
        bookDetailsScreen.clickActionButton(actionButtonKey);
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && alertScreen.state().waitForDisplayed()) {
            if (actionButtonKey == ActionButtonsForBooksAndAlertsKeys.RETURN || actionButtonKey == ActionButtonsForBooksAndAlertsKeys.DELETE ||
                    actionButtonKey == ActionButtonsForBooksAndAlertsKeys.REMOVE) {
                alertScreen.waitAndPerformAlertActionIfDisplayed(alertButtonCancel);
            }
        }
    }

    @When("Click {} action button on book details screen and click {} action button on alert. Only for ios")
    public void pressActionButtonAndAlertActionButtonOnBookDetailsScreen(ActionButtonsForBooksAndAlertsKeys actionBookButtonKey, ActionButtonsForBooksAndAlertsKeys actionAlertButtonKey) {
        bookDetailsScreen.clickActionButton(actionBookButtonKey);
        alertScreen.waitAndPerformAlertActionIfDisplayed(actionAlertButtonKey);
    }
}
