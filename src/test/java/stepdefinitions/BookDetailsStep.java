package stepdefinitions;

import com.google.inject.Inject;
import constants.RegEx;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import models.CatalogBookModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.BookDetailsScreen;

public class BookDetailsStep {

    private final BookDetailsScreen bookDetailsScreen;
    private final ScenarioContext context;

    @Inject
    public BookDetailsStep(ScenarioContext context) {
        this.context = context;
        bookDetailsScreen = new BookDetailsScreen();
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
}
