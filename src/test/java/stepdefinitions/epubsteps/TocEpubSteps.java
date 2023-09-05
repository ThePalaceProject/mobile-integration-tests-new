package stepdefinitions.epubsteps;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import enums.epub.TabsTocAndBookmarksEpub;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import screens.epub.BookmarksEpubScreen;
import screens.epub.ReaderEpubScreen;
import screens.epub.TocEpubScreen;

import java.util.List;

public class TocEpubSteps {

    private final ReaderEpubScreen readerEpubScreen;
    private final TocEpubScreen tocEpubScreen;
    private final BookmarksEpubScreen bookmarksEpubScreen;
    private final ScenarioContext context;

    @Inject
    public TocEpubSteps(ScenarioContext context) {
        this.context = context;
        readerEpubScreen = new ReaderEpubScreen();
        tocEpubScreen = new TocEpubScreen();
        bookmarksEpubScreen = new BookmarksEpubScreen();
    }

    @When("Open bookmarks epub screen")
    public void openBookmarksEpubScreen(){
        readerEpubScreen.openNavigationBar();
        readerEpubScreen.getNavigationBarEpubScreen().tapTOCBookmarksButton();
        tocEpubScreen.openTab(TabsTocAndBookmarksEpub.BOOKMARKS);
    }

    @Then("TOC screen is opened")
    public void isTOCScreenOpened() {
        Assert.assertTrue("Screen with chapters is not opened!", tocEpubScreen.isTOCOpened());
    }

    @When("Switch to bookmarks on toc epub screen")
    public void switchToBookmarks() {
        tocEpubScreen.openTab(TabsTocAndBookmarksEpub.BOOKMARKS);
    }

    @Then("Bookmark epub screen is opened")
    public void isBookmarkScreenOpened() {
        Assert.assertTrue("Bookmarks screen is not opened!", bookmarksEpubScreen.isBookmarkScreenOpened());
    }

    @When("Open random chapter of epub and save it as {string} from toc epub screen")
    public void checkThatRandomChapterCanBeOpenedFromTocEpubScreen(String chapterNameKey) {
        readerEpubScreen.openNavigationBar();
        readerEpubScreen.getNavigationBarEpubScreen().tapTOCBookmarksButton();
        tocEpubScreen.openTab(TabsTocAndBookmarksEpub.TOC);
        List<String> chapters = tocEpubScreen.getListOfBookChapters();
        String chapterName = chapters.get(RandomUtils.nextInt(1, chapters.size()));
        tocEpubScreen.openChapter(chapterName);
        context.add(chapterNameKey, chapterName);
    }

    @When("Add bookmark on reader epub screen")
    public void addBookmark(){
        readerEpubScreen.openNavigationBar();
        readerEpubScreen.getNavigationBarEpubScreen().tapAddBookmarkButton();
    }

    @Then("Bookmark is displayed on reader epub screen")
    public void checkBookmarkIsDisplayedOnReaderEpubScreen(){
        readerEpubScreen.openNavigationBar();
        Assert.assertTrue("Bookmark is not displayed on reader epub screen", readerEpubScreen.getNavigationBarEpubScreen().isBookmarkDisplayed());
    }

    @When("Save device time and date as {string}")
    public void saveDeviceTimeDate(String deviceTimeDateKey){
        String deviceTimeDate = AqualityServices.getApplication().getDriver().getDeviceTime();
        context.add(deviceTimeDateKey, deviceTimeDate);
    }

    @Then("Bookmark with {string} and {string} is displayed on bookmarks epub screen")
    public void checkBookmarkIsDisplayedOnBookmarksEpubScreen(String chapterNameKey, String deviceTimeDateKey){
        String bookmarkTitle = context.get(chapterNameKey);
        String deviceTimeDate = context.get(deviceTimeDateKey);
        Assert.assertTrue(String.format("Bookmark with '%s' bookmarkTitle and '%s' deviceTimeDate is not displayed on bookmarks epub screen", bookmarkTitle, deviceTimeDate),
                tocEpubScreen.getBookmarksEpubScreen().isBookmarkPresent(bookmarkTitle, deviceTimeDate));
    }

    @When("Open random bookmark and save chapter name as {string} on bookmarks epub screen")
    public void openRandomBookmarkAndSaveBookmarkChapterName(String chapterNameKey){
        BookmarksEpubScreen bookmarksEpubScreen = tocEpubScreen.getBookmarksEpubScreen();
        int amountOfBookmarks = bookmarksEpubScreen.getListOfBookmarkTitles().size();
        int randomBookmarkNumber = RandomUtils.nextInt(0, amountOfBookmarks);
        context.add(chapterNameKey, bookmarksEpubScreen.getListOfBookmarkTitles().get(randomBookmarkNumber));
        bookmarksEpubScreen.openBookmark(randomBookmarkNumber);
    }

    @And("{string} chapter name is displayed on reader epub screen")
    public void checkPageInfoPageInfoIsCorrect(String chapterNameKey) {
        String expectedChapterName = context.get(chapterNameKey);
        String actualChapterName = readerEpubScreen.getChapterName();
        Assert.assertEquals(String.format("'%s Chapter name is not displayed. Actual chapter name-%s", expectedChapterName, actualChapterName), expectedChapterName.toLowerCase(), actualChapterName.toLowerCase());
    }

    @When("Delete bookmark on reader epub screen")
    public void deleteBookmarkOnReaderEpubScreen(){
        readerEpubScreen.openNavigationBar();
        readerEpubScreen.getNavigationBarEpubScreen().tapDeleteBookmarkButton();
    }

    @Then("Bookmark is not displayed on reader epub screen")
    public void checkBookmarkIsNotDisplayedOnReaderEpubScreen(){
        readerEpubScreen.openNavigationBar();
        Assert.assertFalse("Bookmark is displayed on reader epub screen", readerEpubScreen.getNavigationBarEpubScreen().isBookmarkDisplayed());
    }

    @When("Delete bookmark on bookmarks epub screen")
    public void deleteBookmarkOnBookmarksEpubScreen(){
        tocEpubScreen.getBookmarksEpubScreen().deleteBookmark(0);
    }

    @Then("Bookmark with {string} and {string} is not displayed on bookmarks epub screen")
    public void checkBookmarkIsNotDisplayedOnBookmarksEpubScreen(String chapterNameKey, String deviceTimeDateKey){
        String bookmarkTitle = context.get(chapterNameKey);
        String deviceTimeDate = context.get(deviceTimeDateKey);
        Assert.assertFalse(String.format("Bookmark with '%s' bookmarkTitle and '%s' deviceTimeDate is displayed on bookmarks epub screen", bookmarkTitle, deviceTimeDate),
                tocEpubScreen.getBookmarksEpubScreen().isBookmarkPresent(bookmarkTitle, deviceTimeDate));
    }

    @When("Return to reader epub screen from toc bookmarks epub screen")
    public void returnToReaderEpubScreen(){
        tocEpubScreen.returnToPreviousScreen();
    }
}
