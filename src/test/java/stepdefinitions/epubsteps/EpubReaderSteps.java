package stepdefinitions.epubsteps;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CatalogBookModel;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import screens.epub.ReaderEpubScreen;

import java.util.stream.IntStream;

public class EpubReaderSteps {

    private final ReaderEpubScreen readerEpubScreen;
    private final ScenarioContext context;

    @Inject
    public EpubReaderSteps(ScenarioContext context) {
        this.context = context;
        readerEpubScreen = new ReaderEpubScreen();
    }

    @Then("{string} book is present on epub reader screen")
    public void isEpubBookPresent(String bookInfoKey) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        String expectedBookName = catalogBookModel.getTitle();
        String actualBookName = readerEpubScreen.getBookName();
        Assert.assertTrue(String.format("Book is not present on epub reader screen. Expected bookName - '%1$s', actualName - '%2$s'", expectedBookName, actualBookName), actualBookName.contains(expectedBookName));
    }

    @When("Swipe to the next page from {int} to {int} times on Reader epub screen")
    public void swipeLeftSeveralTimes(int minValue, int maxValue) {
        int randomSwipeCount = RandomUtils.nextInt(minValue, maxValue);
        AqualityServices.getLogger().info("Swiping " + randomSwipeCount + " times on reader epub screen");
        IntStream.range(0, randomSwipeCount).forEachOrdered(i -> readerEpubScreen.swipeToNextPage());
    }

    @When("Open navigation bar on reader epub screen")
    public void openNavigationBar() {
        readerEpubScreen.openNavigationBar();
    }

    @When("Save pageNumber as {string} and chapterName as {string} on epub reader screen")
    public void savePageNumberAndChapterName(String pageNumberKey, String chapterNameKey) {
        readerEpubScreen.openNavigationBar();
        context.add(pageNumberKey, readerEpubScreen.getPageNumber());
        context.add(chapterNameKey, readerEpubScreen.getChapterName());
    }

    @When("Go to next page on Reader epub screen")
    public void goToNextPage() {
        readerEpubScreen.swipeToNextPage();
    }

    @When("Go to previous page on reader epub screen")
    public void goToPrevPage() {
        readerEpubScreen.swipeToPreviousPage();
    }

    @Then("Next page is opened and old page has {string} pageNumber and {string} chapterName on epub reader screen")
    public void isNextPageOpened(String pageNumberKey, String chapterNameKey) {
        String actualPageNumberString = readerEpubScreen.getPageNumber();
        String expectedPageNumberString = context.get(pageNumberKey);
        int actualPageNumber = Integer.parseInt(actualPageNumberString);
        int expectedPageNumber = Integer.parseInt(expectedPageNumberString) + 1;
        AqualityServices.getLogger().info("actualPageNumberOfNextPage" + actualPageNumber);
        AqualityServices.getLogger().info("expectedPageNumberOfNextPage" + expectedPageNumber);
        String actualChapterName = readerEpubScreen.getChapterName();
        String expectedChapterName = context.get(chapterNameKey);
        AqualityServices.getLogger().info("actualChapterNameOfNextPage" + actualChapterName);
        AqualityServices.getLogger().info("expectedChapterNameOfNextPage" + expectedChapterName);
        Assert.assertTrue(String.format("Page number or chapter name is not correct (actualPageNumber - %d, expectedPageNumber - %d), (actualChapterName-%s, expectedChapterName-%s)", actualPageNumber, expectedPageNumber, actualChapterName, expectedChapterName), expectedPageNumber == actualPageNumber ||
                (actualPageNumber == 1 && !actualChapterName.equalsIgnoreCase(expectedChapterName)));
    }

    @When("Tap on right book corner on epub reader screen")
    public void clickOnRightBookCorner() {
        readerEpubScreen.tapRightCorner();
    }

    @When("Click on left book corner on epub reader screen")
    public void clickOnLeftBookCorner() {
        readerEpubScreen.tapLeftCorner();
    }

    @Then("Previous page is opened and old page has {string} pageNumber and {string} chapterName on epub reader screen")
    public void isPreviousPageOpened(String pageNumberKey, String chapterNameKey) {
        String actualPageNumberString = readerEpubScreen.getPageNumber();
        String expectedPageNumberString = context.get(pageNumberKey);
        int actualPageNumber = Integer.parseInt(actualPageNumberString);
        int expectedPageNumber = Integer.parseInt(expectedPageNumberString) - 1;
        AqualityServices.getLogger().info("actualPageNumberOfPreviousPage" + actualPageNumber);
        AqualityServices.getLogger().info("expectedPageNumberOfPreviousPage" + expectedPageNumber);
        String actualChapterName = readerEpubScreen.getChapterName();
        String expectedChapterName = context.get(chapterNameKey);
        AqualityServices.getLogger().info("actualChapterNameOfPreviousPage" + actualChapterName);
        AqualityServices.getLogger().info("expectedChapterNameOfPreviousPage" + expectedChapterName);
        Assert.assertTrue(String.format("Page number or chapterName is not correct (actualPageNumber - %d, expectedPageNumber - %d), (actualChapterName-%s, expectedChapterName-%s)", actualPageNumber, expectedPageNumber, actualChapterName, expectedChapterName), expectedPageNumber == actualPageNumber ||
                (actualPageNumber == 1 && !actualChapterName.equalsIgnoreCase(expectedChapterName)));
    }
}
