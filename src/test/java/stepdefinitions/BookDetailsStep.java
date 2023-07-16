package stepdefinitions;

import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import models.CatalogBookModel;
import org.assertj.core.api.SoftAssertions;
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
}
