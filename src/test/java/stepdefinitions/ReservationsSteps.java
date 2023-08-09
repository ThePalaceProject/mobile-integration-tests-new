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
import screens.ReservationsScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

public class ReservationsSteps {

    private final MenuBarScreen menuBarScreen;
    private final ReservationsScreen reservationsScreen;
    private final ScenarioContext context;

    @Inject
    public ReservationsSteps(ScenarioContext context) {
        this.context = context;
        menuBarScreen = new MenuBarScreen();
        reservationsScreen = new ReservationsScreen();
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
}
