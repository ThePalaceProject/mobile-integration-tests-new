package stepdefinitions;

import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.pdf.ChaptersPdfScreen;
import screens.pdf.ReaderPdfScreen;
import screens.pdf.TocBookmarksPdfScreen;

public class PdfSteps {

    private final ReaderPdfScreen readerPdfScreen;
    private final TocBookmarksPdfScreen tocBookmarksPdfScreen;
    private final ChaptersPdfScreen chaptersPdfScreen;
    private final ScenarioContext context;

    @Inject
    public PdfSteps(ScenarioContext context) {
        this.context = context;
        readerPdfScreen = new ReaderPdfScreen();
        tocBookmarksPdfScreen = new TocBookmarksPdfScreen();
        chaptersPdfScreen = new ChaptersPdfScreen();
    }

    @Then("Reader pdf screen is opened")
    public void isPdfReaderOpened() {
        Assert.assertTrue("PDF reader is not opened", readerPdfScreen.isReaderOpened());
    }

    @When("Save page number as {string} on pdf reader screen")
    public void savePageNumberOnReader(String pageInfoKey){
        context.add(pageInfoKey, readerPdfScreen.getPageNumber());
    }

    @When("Go to next page on reader pdf screen")
    public void goToNextPdfPage() {
        readerPdfScreen.goToNextPage();
    }

    @Then("Page number increased by 1 from {string} on pdf reader screen")
    public void isPageNumberIncreased(String pageInfoKey) {
        int numberBefore = context.get(pageInfoKey);
        Assert.assertEquals("Page number has not increased", numberBefore + 1, readerPdfScreen.getPageNumber());
    }

    @When("Go to previous page on reader pdf screen")
    public void goToPreviousPdfPage() {
        readerPdfScreen.goToPreviousPage();
    }

    @Then("Page number decreased by 1 from {string} on pdf reader screen")
    public void isPageNumberDecreased(String pageInfoKey) {
        int numberBefore = context.get(pageInfoKey);
        Assert.assertEquals("Page number has not decreased", numberBefore - 1, readerPdfScreen.getPageNumber());
    }

    @When("Open TOC on pdf reader screen")
    public void openTOC() {
        readerPdfScreen.getNavigationBarScreen().tapTocBookmarksBarButton();
    }

    @Then("There are content list with thumbnails and chapter content on pdf toc screen")
    public void checkThePresenceOfTwoContentLists() {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(tocBookmarksPdfScreen.isThumbnailsButtonDisplayed())
                .as("There is no content with thumbnails").isTrue();
        softAssertions.assertThat(tocBookmarksPdfScreen.isChaptersButtonDisplayed())
                .as("There is no content with text chapters").isTrue();
        softAssertions.assertAll();
    }

    @When("Open text chapter content on pdf toc screen")
    public void openTextContent() {
        tocBookmarksPdfScreen.tapChaptersButton();
    }

    @Then("Text chapter content is opened on pdf toc screen")
    public void isTextContentOpened(){
        Assert.assertTrue("Text chapter content is not opened", tocBookmarksPdfScreen.getChaptersPdfScreen().areChaptersDisplayed());
    }

    @When("Open content with thumbnails on pdf toc screen")
    public void openThumbnails() {
        tocBookmarksPdfScreen.tapThumbnailsButton();
    }

    @Then("Thumbnails of the book pages are displayed")
    public void areThumbnailsDisplayed() {
        Assert.assertTrue("Thumbnails of the book are not displayed", tocBookmarksPdfScreen.getThumbnailsPdfScreen().areThumbnailsDisplayed());
    }

    @When("Open search pdf screen")
    public void openSearchPdfScreen() {
        readerPdfScreen.getNavigationBarScreen().tapSearchButton();
    }

    @When("Enter {string} text and save it as {string} on search pdf screen")
    public void enterTextAndSaveIt(String text, String searchedTextKey) {
        readerPdfScreen.getSearchPdfScreen().enterText(text);
        context.add(searchedTextKey, text);
    }

    @Then("The field allows to enter characters and contains {string} on search pdf screen")
    public void checkTheEnteredCharacters(String searchedTextKey) {
        String expectedSearchedText = context.get(searchedTextKey);
        String actualSearchedText = readerPdfScreen.getSearchPdfScreen().getTextFromSearchTxb();
        Assert.assertEquals("The field is empty!", expectedSearchedText, actualSearchedText);
    }

    @When("Enter {string} text on search pdf screen")
    public void enterSearchedText(String text) {
        readerPdfScreen.getSearchPdfScreen().enterText(text);
    }

    @When("Open random found text and save page number as {string} on search pdf screen")
    public void openRandomTextAndSavePageNumber(String pageNumberKey) {
        context.add(pageNumberKey, readerPdfScreen.getSearchPdfScreen().openRandomFoundText());
    }

    @Then("Page number is equal to {string} on pdf reader screen")
    public void comparePageNumbers(String pageInfoKey) {
        int pageNumber = context.get(pageInfoKey);
        Assert.assertEquals("Page number is wrong", pageNumber, readerPdfScreen.getPageNumber());
    }

    @When("Delete text in search line on search pdf screen")
    public void deleteTextFromSearchLine() {
        readerPdfScreen.getSearchPdfScreen().deleteText();
    }

    @Then("Search field is empty on search pdf screen")
    public void isSearchFieldEmpty() {
        Assert.assertTrue("Search field is not empty", readerPdfScreen.getSearchPdfScreen().isSearchFieldEmpty());
    }

    @When("Close pdf reader by back button")
    public void closePdfReader() {
        readerPdfScreen.getNavigationBarScreen().tapBackButton();
    }

    @When("Open random thumbnail and save the number as {string} on pdf toc screen")
    public void openRandomThumbnail(String pageInfoKey) {
        context.add(pageInfoKey, tocBookmarksPdfScreen.getThumbnailsPdfScreen().openRandomThumbnail());
    }

    @When("Return to pdf reader screen from pdf toc screen")
    public void returnToReaderFromTOC() {
        if (tocBookmarksPdfScreen.getThumbnailsPdfScreen().areThumbnailsDisplayed() || tocBookmarksPdfScreen.getChaptersPdfScreen().areChaptersDisplayed()) {
            tocBookmarksPdfScreen.returnToReaderPdfScreen();
        }
    }

    @When("Open random chapter and save the number as {string} on pdf toc screen")
    public void openRandomChapter(String pageInfoKey){
        context.add(pageInfoKey, chaptersPdfScreen.openRandomChapter());
    }

    @When("Open pdf settings screen on pdf reader screen")
    public void openSettings() {
        readerPdfScreen.getNavigationBarScreen().tapSettingsButton();
    }

    @Then("PDF settings screen is opened")
    public void isSettingsOpened() {
        Assert.assertTrue("Settings screen is not opened!", readerPdfScreen.getSettingsPdfScreen().isSettingsScreenOpened());
    }

    @Then("Vertical scrolling is chosen by default on settings screen")
    public void checkDefaultScrolling(){
        Assert.assertTrue("Vertical scrolling is not default", readerPdfScreen.getSettingsPdfScreen().isVerticalScrollingChosen());
    }

    @When("Scroll page down on pdf reader screen")
    public void scrollPageDown() {
        readerPdfScreen.swipePageDown();
    }

    @Then("Page number is not equal to {string} on pdf reader screen")
    public void isPageNotEqual(String pageInfoKey) {
        int pageNumber = context.get(pageInfoKey);
        Assert.assertNotEquals("Page number is wrong", pageNumber, readerPdfScreen.getPageNumber());
    }

    @When("Scroll page up on pdf reader screen")
    public void scrollPageUp(){
        readerPdfScreen.swipePageUp();
    }

    @Then("The book name is {string} on pdf reader screen")
    public void checkBookTitle(String bookNameInfoKey) {
//        String expectedBookName = context.get(bookNameInfoKey);
//        String actualBookName = readerPdfScreen.getBookName();
//        Assert.assertEquals("The book title is incorrect", expectedBookName, actualBookName);
    }

    @Then("PDF toc screen is closed")
    public void tocScreenIsClosed() {
//        Assert.assertTrue("TOC is opened", tocBookmarksPdfScreen.isTocClosed());
    }

    @Then("Close pdf toc screen by back button")
    public void closeTOCByBackBtn() {
//        readerPdfScreen.getNavigationBarScreen().tapBackButton();
    }

    @When("Save the number of the last page as {string} on pdf reader screen")
    public void saveLastPage(String lastPageInfoKey) {
//        context.add(lastPageInfoKey, readerPdfScreen.getLastPageNumber());
    }

    @When("Tap Go to last page button on pdf settings screen")
    public void tapGoToLastPage(){
//        readerPdfScreen.getSettingsPdfScreen().tapGoToLastPage();
    }

    @When("Tap Go to first page button on pdf settings screen")
    public void tapGoToFirstPage() {
//        readerPdfScreen.getSettingsPdfScreen().tapGoToFirstPage();
    }

    @Then("The first page is opened on pdf reader screen")
    public void isFirstPageOpened(){
//        Assert.assertEquals("The first page is not opened", 1, readerPdfScreen.getPageNumber());
    }

    @When("Tap Vertical scrolling on pdf settings screen")
    public void tapVerticalScrolling(){
//        readerPdfScreen.getSettingsPdfScreen().tapVerticalScrolling();
    }

    @Then("Vertical scrolling is chosen on settings screen")
    public void isVerticalScrollingChosen() {
//        Assert.assertTrue("Vertical scrolling is not chosen", readerPdfScreen.getSettingsPdfScreen().isVerticalScrollingChosen());
    }

    @Then("Spreads options are available on settings screen")
    public void areSpreadsAvailable() {
//        Assert.assertTrue("No spreads option is not available", readerPdfScreen.getSettingsPdfScreen().isNoSpreadsAvailable());
//        Assert.assertTrue("Odd spreads option is not available", readerPdfScreen.getSettingsPdfScreen().isOddSpreadsAvailable());
//        Assert.assertTrue("Even spreads is not available", readerPdfScreen.getSettingsPdfScreen().isEvenSpreadsAvailable());
    }

    @When("Tap Horizontal scrolling on pdf settings screen")
    public void tapHorizontalScrolling() {
//        readerPdfScreen.getSettingsPdfScreen().tapHorizontalScrolling();
    }

    @Then("Horizontal scrolling is chosen on settings screen")
    public void isHorizontalScrollingChosen(){
//        Assert.assertTrue("Horizontal scrolling is not chosen", readerPdfScreen.getSettingsPdfScreen().isHorizontalScrollingChosen());
    }

    @Then("Spreads options are not available on settings screen")
    public void areSpreadsUnavailable() {
//        Assert.assertEquals("No spreads option is available", readerPdfScreen.getSettingsPdfScreen().isNoSpreadsAvailable(), Boolean.FALSE);
//        Assert.assertEquals("Odd spreads option is available", readerPdfScreen.getSettingsPdfScreen().isOddSpreadsAvailable(), Boolean.FALSE);
//        Assert.assertEquals("Even spreads is available", readerPdfScreen.getSettingsPdfScreen().isEvenSpreadsAvailable(), Boolean.FALSE);
    }

    @When("Tap Wrapped scrolling on pdf settings screen")
    public void tapWrappedScrolling(){
//        readerPdfScreen.getSettingsPdfScreen().tapWrappedScrolling();
    }

    @Then("Wrapped scrolling is chosen on settings screen")
    public void isWrappedScrollingChosen(){
//        Assert.assertTrue("Wrapped scrolling is not chosen", readerPdfScreen.getSettingsPdfScreen().isWrappedScrollingChosen());
    }

    @When("Swipe pdf page forward from {int} to {int} times on reader pdf screen")
    public void swipePageForwardSeveralTimes(int minValue, int maxValue) {
//        int swipeCount = RandomUtils.nextInt(minValue, maxValue);
//        AqualityServices.getLogger().info("Swipe " + swipeCount + " times on reader pdf screen");
//        IntStream.range(0, swipeCount).forEach(i -> readerPdfScreen.goToNextPage());
    }

    @When("Swipe pdf page down from {int} to {int} times on reader pdf screen")
    public void swipePageDownSeveralTimes(int minValue, int maxValue) {
//        int swipeCount = RandomUtils.nextInt(minValue, maxValue);
//        AqualityServices.getLogger().info("Swipe " + swipeCount + " times on reader pdf screen");
//        IntStream.range(0, swipeCount).forEach(i -> readerPdfScreen.swipePageDown());
    }

    @Then("Search pdf screen is opened")
    public void checkSearchPdfScreenIsOpened() {
//        Assert.assertTrue("Search screen is not opened", readerPdfScreen.getSearchPdfScreen().isSearchPdfScreenOpened());
    }

    @Then("Elements on pdf search screen are translated correctly")
    public void checkTranslationOfSearchPDFScreen() {
//        SoftAssertions softAssertions = new SoftAssertions();
//        softAssertions.assertThat(readerPdfScreen.getSearchPdfScreen().getTextFromDoneBtn()).as("Done button is not translated").isEqualTo(SpanishIos.DONE);
//        softAssertions.assertThat(readerPdfScreen.getSearchPdfScreen().getTextFromSearchTxb()).as("Search field is not translated").isEqualTo(SpanishIos.SEARCH_PDF);
//        softAssertions.assertAll();
    }

    @Then("Elements on pdf search screen are translated correctly in Italian")
    public void checkTranslationOfSearchPDFScreenIT() {
//        SoftAssertions softAssertions = new SoftAssertions();
//        softAssertions.assertThat(readerPdfScreen.getSearchPdfScreen().getTextFromDoneBtn()).as("Done button is not translated").isEqualTo(ItalianIos.DONE);
//        softAssertions.assertThat(readerPdfScreen.getSearchPdfScreen().getTextFromSearchTxb()).as("Search field is not translated").isEqualTo(ItalianIos.SEARCH_PDF);
//        softAssertions.assertAll();
    }

    @When("Close pdf search screen")
    public void closeSearchScreen() {
//        readerPdfScreen.getSearchPdfScreen().closeSearchScreen();
    }

    @Then("Found lines should contain {string} in themselves on search pdf screen")
    public void checkThatPdfFoundLinesContainText(String text) {
//        SoftAssertions softAssertions = new SoftAssertions();
//        readerPdfScreen.getSearchPdfScreen().getListOfFoundTexts().forEach(foundText -> softAssertions.assertThat(foundText.toLowerCase().contains(text.toLowerCase())).
//                as(String.format("Found text '%1$s' does not contain text '%2$s'. ", foundText, text)).isTrue());
//        softAssertions.assertAll();
    }

    @When("Slide page slider {} on reader pdf screen")
    public void slidePdfPageSlider() {
//        readerPdfScreen.slidePageSlider(entireScreenDragDirection);
    }

    @Then("The {string} saved page number is greater than the current page number on the reader pdf screen")
    public void checkThatSavedPdfPageNumberIsGreaterThanCurrentPdfPageNumber(String pageNumberKey) {
//        int savedPageNumber = context.get(pageNumberKey);
//        int currentPageNumber = readerPdfScreen.getPageNumber();
//        Assert.assertTrue("Saved page number is less than current page number on reader pdf screen. SavedPageNumber - " +
//                savedPageNumber + ", currentPageNumber - " + currentPageNumber, savedPageNumber > currentPageNumber);
    }

    @Then("The {string} saved page number is less than the current page number on the reader pdf screen")
    public void checkThatSavedPdfPageNumberIsLessThanCurrentPdfPageNumber(String pageNumberKey) {
//        int savedPageNumber = context.get(pageNumberKey);
//        int currentPageNumber = readerPdfScreen.getPageNumber();
//        Assert.assertTrue("Saved page number is greater that current page number on reader pdf screen. SavedPageNumber - " +
//                savedPageNumber + ", currentPageNumber - " + currentPageNumber, savedPageNumber < currentPageNumber);
    }

    @When("Open bookmarks pdf screen")
    public void openBookmarksPdfScreen() {
//        readerPdfScreen.getNavigationBarScreen().tapTocBookmarksBarButton();
//        tocBookmarksPdfScreen.tapBookmarksButton();
    }

    @Then("Bookmarks pdf screen is opened")
    public void checkBookmarksPdfScreenIsOpened() {
//        Assert.assertTrue("Bookmarks pdf screen is not opened", tocBookmarksPdfScreen.getBookmarksPdfScreen().state().waitForDisplayed());
    }

    @Then("Elements on pdf bookmarks screen are translated correctly")
    public void checkTranslationOnPDFBookmarksScreen() {
//        SoftAssertions softAssertions = new SoftAssertions();
//        softAssertions.assertThat(tocBookmarksPdfScreen.getTextFromResumeBtn()).as("Resume button is not translated").isEqualTo(SpanishIos.RESUME);
//        softAssertions.assertThat(tocBookmarksPdfScreen.getBookmarksPdfScreen().getTextFromBookmarksScreen()).as("No bookmarks text is not translated").isEqualTo(SpanishIos.NO_BOOKMARKS);
//        softAssertions.assertAll();
    }

    @Then("Elements on pdf bookmarks screen are translated correctly in Italian")
    public void checkTranslationOnPDFBookmarksScreenIT() {
//        SoftAssertions softAssertions = new SoftAssertions();
//        softAssertions.assertThat(tocBookmarksPdfScreen.getTextFromResumeBtn()).as("Resume button is not translated").isEqualTo(ItalianIos.RESUME);
//        softAssertions.assertThat(tocBookmarksPdfScreen.getBookmarksPdfScreen().getTextFromBookmarksScreen()).as("No bookmarks text is not translated").isEqualTo(ItalianIos.NO_BOOKMARKS);
//        softAssertions.assertAll();
    }

    @Then("Amount of bookmarks is {int} on bookmarks pdf screen")
    public void checkThatAmountOfBookmarksIsCorrect(int expectedAmountOfBookmarks) {
//        int actualAmountOfBookmarks = tocBookmarksPdfScreen.getBookmarksPdfScreen().getCountOfBookmarks();
//        Assert.assertEquals(String.format("Amount of bookmarks is not correct on bookmarks pdf screen. ExpectedAmountOfBookmarks-%d, actualAmountOfBookmarks-%d", expectedAmountOfBookmarks, actualAmountOfBookmarks), expectedAmountOfBookmarks, actualAmountOfBookmarks);
    }

    @When("Close toc bookmarks pdf screen")
    public void closeTocBookmarksGalleryScreen() {
//        tocBookmarksPdfScreen.tapResumeButton();
    }

    @When("Add bookmark on reader pdf screen")
    public void addBookmarkOnReaderPdfScreen() {
//        readerPdfScreen.getNavigationBarScreen().tapBookmarkButton();
    }

    @When("Open the {int} bookmark on bookmarks pdf screen")
    public void openBookmark(int bookmarkNumber) {
//        tocBookmarksPdfScreen.getBookmarksPdfScreen().openBookmark(bookmarkNumber);
    }

    @When("Enter word {} and save as {string} on search pdf screen")
    public void enterData(String word, String infoKey) {
//        readerPdfScreen.getSearchPdfScreen().enterText(word);
//        context.add(infoKey, word);
    }

    @Then("Search result is empty on search pdf screen")
    public void isSearchResultEmpty() {
//        Assert.assertTrue("Search results is not empty", readerPdfScreen.getSearchPdfScreen().isSearchResultEmpty());
    }

    @Then("Search result is shown on search pdf screen")
    public void isSearchResultShown() {
//        Assert.assertFalse("Search results is empty", readerPdfScreen.getSearchPdfScreen().isSearchFieldEmpty());
    }
}
