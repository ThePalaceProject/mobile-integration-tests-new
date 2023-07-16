package stepdefinitions;

import com.google.inject.Inject;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import screens.BooksScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

public class BooksSteps {

    private final MenuBarScreen menuBarScreen;
    private final BooksScreen booksScreen;

    @Inject
    public BooksSteps() {
        menuBarScreen = new MenuBarScreen();
        booksScreen = new BooksScreen();
    }

    @When("Open Books")
    public void openBooks() {
        menuBarScreen.openBottomMenuTab(MenuBar.BOOKS);
    }

    @Then("Books screen is opened")
    public void isBooksScreenOpened() {
        Assert.assertTrue("My Books screen is not opened!", booksScreen.isScreenOpened());
    }
}
