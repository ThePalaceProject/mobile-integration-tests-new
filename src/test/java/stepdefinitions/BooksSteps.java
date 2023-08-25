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
import screens.MyBooksScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.util.List;
import java.util.stream.Collectors;

public class BooksSteps {

    private final ScenarioContext context;
    private final MenuBarScreen menuBarScreen;
    private final MyBooksScreen myBooksScreen;

    @Inject
    public BooksSteps(ScenarioContext context) {
        this.context = context;
        menuBarScreen = new MenuBarScreen();
        myBooksScreen = new MyBooksScreen();
    }

    @When("Open Books")
    public void openBooks() {
        menuBarScreen.openBottomMenuTab(MenuBar.BOOKS);
    }

    @Then("Books screen is opened")
    public void isBooksScreenOpened() {
        Assert.assertTrue("My Books screen is not opened!", myBooksScreen.isScreenOpened());
    }

    @Then("Added books from {string} are displayed on books screen")
    public void areBooksDisplayed(String listKey) {
        Assert.assertTrue("Added books are not displayed on books screen", myBooksScreen.areBooksDisplayed(context.get(listKey)));
    }

    @Then("Books are sorted by Author ascending on books screen")
    public void areBooksSortedByAuthor() {
        List<String> authorsList = myBooksScreen.getListOfAuthors();
        Assert.assertEquals("Books are not sorted by author", authorsList.stream().sorted().collect(Collectors.toList()), authorsList);
    }

    @Then("Books are sorted by Title ascending on books screen")
    public void areBooksSortedByTitle() {
        List<String> titlesList = myBooksScreen.getListOfTitles();
        Assert.assertEquals("Books are not sorted by title", titlesList.stream().sorted().collect(Collectors.toList()), titlesList);
    }

    @Then("{} book with {} action button and {string} bookInfo is present on books screen")
    public void isBookPresent(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        String bookName = bookInfo.getTitle();
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && bookType == BookType.AUDIOBOOK) {
            bookName = bookName + ". Audiobook.";
        }
        Assert.assertTrue(String.format("'%s' book with specific action button is not present on books screen", bookName),
                myBooksScreen.isBookDisplayed(bookType, bookName, actionButtonKey));
    }

    @When("Open {} book with {} action button and {string} bookInfo on books screen")
    public void openBook(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        String bookName = bookInfo.getTitle();
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && bookType == BookType.AUDIOBOOK) {
            bookName = bookName + ". Audiobook.";
        }
        myBooksScreen.openBook(bookType, bookName, actionButtonKey);
    }

    @Then("{} book with {} action button and {string} bookInfo is not present on books screen")
    public void isBookNotPresent(BookType bookType, ActionButtonsForBooksAndAlertsKeys actionButtonKey, String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        String bookName = bookInfo.getTitle();
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && bookType == BookType.AUDIOBOOK) {
            bookName = bookName + ". Audiobook.";
        }
        Assert.assertFalse(String.format("'%s' book with specific action button is present on books screen", bookName),
                myBooksScreen.isBookDisplayed(bookType, bookName, actionButtonKey));
    }

    @Then("There are not books on books screen")
    public void areBooksNotPresent() {
        Assert.assertTrue("Books are present on books screen", myBooksScreen.isNoBooksMessagePresent());
    }

    @Then("Amount of books is equal to {int} on books screen")
    public void isAmountOfBooksEqualTo(int expectedAmountOfBooks) {
        Assert.assertEquals("Amount of books is not correct on books screen", expectedAmountOfBooks, myBooksScreen.getCountOfBooks());
    }

    @When("Refresh list of books on books screen")
    public void refreshListOfBooks() {
        myBooksScreen.refreshList();
    }
}
