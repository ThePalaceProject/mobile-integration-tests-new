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
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.ReservationsScreen;
import screens.SortOptionsScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationsSteps {

    private final MenuBarScreen menuBarScreen;
    private final ReservationsScreen reservationsScreen;
    private final SortOptionsScreen sortOptionsScreen;
    private final ScenarioContext context;

    @Inject
    public ReservationsSteps(ScenarioContext context) {
        this.context = context;
        menuBarScreen = new MenuBarScreen();
        reservationsScreen = new ReservationsScreen();
        sortOptionsScreen = new SortOptionsScreen();
    }

    @When("Open Reservations")
    public void openReservations() {
        menuBarScreen.openBottomMenuTab(MenuBar.RESERVATIONS);
    }

    @Then("Reservations screen is opened")
    public void isScreenOpened() {
        Assert.assertTrue("Reservations screen is not opened!", reservationsScreen.isScreenOpened());
    }

    @Then("{} book with {} action button and {string} bookInfo is present on Reservations screen")
    public void isBookPresent(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        String bookName = bookInfo.getTitle();
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && bookType == BookType.AUDIOBOOK) {
            bookName = bookName + ". Audiobook.";
        }
        Assert.assertTrue(String.format("'%s' book with specific action button is not present on Reservations screen!", bookName),
                reservationsScreen.isBookDisplayed(bookType, bookName, actionButtonKey));
    }

    @When("Open {} book with {} action button and {string} bookInfo on Reservations screen")
    public void openBook(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        String bookName = bookInfo.getTitle();
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && bookType == BookType.AUDIOBOOK) {
            bookName = bookName + ". Audiobook.";
        }
        reservationsScreen.openBook(bookType, bookName, actionButtonKey);
    }

    @Then("{} book with {} action button and {string} bookInfo is not present on Reservations screen")
    public void isBookNotPresent(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        String bookName = bookInfo.getTitle();
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && bookType == BookType.AUDIOBOOK) {
            bookName = bookName + ". Audiobook.";
        }
        Assert.assertFalse(String.format("'%s' book with specific action button is present on holds screen", bookName),
                reservationsScreen.isBookDisplayed(bookType, bookName, actionButtonKey));
    }

    @Then("Books are sorted by Title by default on Reservations screen")
    public void checkDefaultSorting() {
        Assert.assertEquals("Book are not sorting by default", "Title", reservationsScreen.getNameOfSorting());
    }

    @Then("Books are sorted by Title ascending on Reservations screen")
    public void areBooksSortedByTitleOnHolds() {
        List<String> titlesList = reservationsScreen.getListOfTitles();
        Assert.assertEquals("Books are not sorted by title", titlesList.stream().sorted().collect(Collectors.toList()), titlesList);
    }

    @Then("Books are sorted by Author ascending on Reservations screen")
    public void areBooksSortedByAuthorOnHolds() {
        List<String> authorsList = reservationsScreen.getListOfAuthors();
        Assert.assertEquals("Books are not sorted by author", authorsList.stream().sorted().collect(Collectors.toList()), authorsList);
    }

    @Then("There are sorting by {string} and {string} in {string} on Reservations screen")
    public void checkTypeOfSorting(String title, String author, String library) {
        reservationsScreen.sortBy();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(title)).as("There is no sorting by " + title).isEqualTo(title);
        softAssertions.assertThat(sortOptionsScreen.getTypeVariantsOfBtn(author)).as("There is no sorting by " + author).isEqualTo(author);
        softAssertions.assertAll();
    }
}