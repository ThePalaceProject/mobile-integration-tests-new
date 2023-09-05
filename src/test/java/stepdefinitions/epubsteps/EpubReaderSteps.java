package stepdefinitions.epubsteps;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import enums.epub.TabsTocAndBookmarksEpub;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CatalogBookModel;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import screens.epub.FontAndBackgroundSettingsEpubScreen;
import screens.epub.ReaderEpubScreen;
import screens.epub.TocEpubScreen;

import java.util.List;
import java.util.stream.IntStream;

public class EpubReaderSteps {

    private final ReaderEpubScreen readerEpubScreen;
    private final TocEpubScreen tocEpubScreen;
    private final FontAndBackgroundSettingsEpubScreen fontAndBackgroundSettingsEpubScreen;
    private final ScenarioContext context;

    @Inject
    public EpubReaderSteps(ScenarioContext context) {
        this.context = context;
        readerEpubScreen = new ReaderEpubScreen();
        tocEpubScreen = new TocEpubScreen();
        fontAndBackgroundSettingsEpubScreen = new FontAndBackgroundSettingsEpubScreen();
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

    @When("Open TOC epub screen")
    public void openTocEpubScreen() {
        readerEpubScreen.openNavigationBar();
        readerEpubScreen.getNavigationBarEpubScreen().tapTOCBookmarksButton();
    }

    @Then("Chapter {string} is opened on epub reader screen")
    public void isChapterOpened(String chapterNameKey) {
        String expectedChapterName = context.get(chapterNameKey);
        Assert.assertEquals("Chapter name is not correct. ExpectedChapterName-" + expectedChapterName.toLowerCase() + ", ActualChapterName-"
                + readerEpubScreen.getChapterName().toLowerCase(), readerEpubScreen.getChapterName().toLowerCase(), expectedChapterName.toLowerCase());
    }

    @When("Close TOC epub screen")
    public void tocEpubScreen() {
        tocEpubScreen.returnToPreviousScreen();
    }

    @When("Return to previous screen from epub")
    public void returnToPreviousScreen() {
        readerEpubScreen.openNavigationBar();
        readerEpubScreen.getNavigationBarEpubScreen().returnToPreviousScreen();
    }

    @Then("Reader epub screen is opened")
    public void isEpubReaderOpened() {
        Assert.assertTrue("Book cover is not displayed", readerEpubScreen.isBookCoverDisplayed());
    }

    @When("Scroll page forward from {int} to {int} times")
    public void swipePageForward(int minValue, int maxValue) {
        int randomScrollsCount = RandomUtils.nextInt(minValue, maxValue);
        AqualityServices.getLogger().info("Scrolling " + randomScrollsCount + " times on reader epub screen");
        IntStream.range(0, randomScrollsCount).forEachOrdered(i -> readerEpubScreen.clickRightCorner());
    }

    @Then("Random chapter of epub can be opened from toc epub screen")
    public void checkThatRandomChapterCanBeOpenedFromTocEpubScreen() {
        readerEpubScreen.openNavigationBar();
        readerEpubScreen.getNavigationBarEpubScreen().tapTOCBookmarksButton();
        tocEpubScreen.openTab(TabsTocAndBookmarksEpub.TOC);
        List<String> chapters = tocEpubScreen.getTocEpubScreen().getListOfBookChapters();
        String chapterName = chapters.get(RandomUtils.nextInt(1, chapters.size()));
        tocEpubScreen.getTocEpubScreen().openChapter(chapterName);
        Assert.assertEquals("Chapter name is not correct. ExpectedChapterName-" + chapterName.toLowerCase() + ", ActualChapterName-"
                + readerEpubScreen.getChapterName().toLowerCase(), readerEpubScreen.getChapterName().toLowerCase(), chapterName.toLowerCase());
    }

    @Then("Toc epub screen is opened")
    public void tocEpubScreenIsOpened() {
        Assert.assertTrue("Toc epub screen is not opened", tocEpubScreen.getTocEpubScreen().state().waitForDisplayed());
    }

    @When("Open font and background settings epub screen")
    public void openEpubSettings() {
        readerEpubScreen.openNavigationBar();
        readerEpubScreen.getNavigationBarEpubScreen().tapFontSettingsButton();
    }

    @Then("Font and background settings epub screen is opened")
    public void epubSettingsIsOpened() {
        Assert.assertTrue("Font and background settings epub screen is not opened", fontAndBackgroundSettingsEpubScreen.state().waitForDisplayed());
    }

    @Then("PageNumber {string} is correct")
    public void checkPageInfoPageInfoIsCorrect(String pageNumberKey) {
        String expectedPageNumber = context.get(pageNumberKey);
        String actualPageNumber = readerEpubScreen.getPageNumber();
        Assert.assertTrue(String.format("PageNumber is not correct. ExpectedPageNumber-%1$s but actualPageNumber-%2$s", expectedPageNumber, actualPageNumber), AqualityServices.getConditionalWait().waitFor(() -> expectedPageNumber.equals(actualPageNumber)));
    }
}