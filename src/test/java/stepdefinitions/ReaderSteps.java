package stepdefinitions;

import com.google.inject.Inject;
import enums.BookType;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import models.CatalogBookModel;
import org.junit.Assert;
import screens.audiobook.AudioPlayerScreen;
import screens.epub.ReaderEpubScreen;
import screens.pdf.ReaderPdfScreen;

public class ReaderSteps {

    private final ScenarioContext context;
    private final ReaderEpubScreen readerEpubScreen;
    private final AudioPlayerScreen audioPlayerScreen;
    private final ReaderPdfScreen readerPdfScreen;

    @Inject
    public ReaderSteps(ScenarioContext context) {
        this.context = context;
        readerEpubScreen = new ReaderEpubScreen();
        audioPlayerScreen = new AudioPlayerScreen();
        readerPdfScreen = new ReaderPdfScreen();
    }

    @Then("Book {string} with {} type is present on epub or pdf or audiobook screen")
    public void readerScreenForEbookTypeIsPresent(String bookInfoKey, BookType bookType) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        String bookName = catalogBookModel.getTitle();
        switch (bookType) {
            case EBOOK:
                if (readerEpubScreen.state().waitForDisplayed()) {
                    assertBookNameForEpub(catalogBookModel);
                } else {
                    assertBookNameForPdf(catalogBookModel);
                }
                break;
            case AUDIOBOOK:
                Assert.assertTrue("AudiobookName is not present on audiobook screen", audioPlayerScreen.isAudiobookNamePresent(bookName));
                break;
        }
    }

    private void assertBookNameForEpub(CatalogBookModel catalogBookModel) {
        String expectedBookName = catalogBookModel.getTitle().toLowerCase();
        String actualBookName = readerEpubScreen.getBookName().toLowerCase();
        Assert.assertTrue(String.format("BookName(epub) is not correct. Expected bookName - '%1$s', actualName - '%2$s'", expectedBookName, actualBookName), actualBookName.contains(expectedBookName));
    }

    private void assertBookNameForPdf(CatalogBookModel catalogBookModel) {
        String expectedBookName = catalogBookModel.getTitle().toLowerCase();
        String actualBookName = readerPdfScreen.getBookName().toLowerCase();
        Assert.assertTrue(String.format("BookName(pdf) is not correct. Expected bookName - '%1$s', actualName - '%2$s'", expectedBookName, actualBookName), actualBookName.contains(expectedBookName));
    }
}
