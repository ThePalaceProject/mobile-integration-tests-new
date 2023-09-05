package stepdefinitions.audiobooksteps;

import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.audiobook.BookmarksAudiobookScreen;
import screens.audiobook.TocAudiobookScreen;

public class TocAudiobookSteps {
    private final ScenarioContext context;
    private final TocAudiobookScreen tocAudiobookScreen;
    private final BookmarksAudiobookScreen bookmarksAudiobookScreen;

    @Inject
    public TocAudiobookSteps(ScenarioContext context) {
        this.context = context;
        tocAudiobookScreen = new TocAudiobookScreen();
        bookmarksAudiobookScreen = new BookmarksAudiobookScreen();
    }

    @When("Open the {int} chapter on toc audiobook screen and save the chapter name as {string}")
    public void openSpecificChapterOnTocAudiobookScreenAndSaveChapterName(int chapterNumber, String keyChapterName) {
        String chapter = tocAudiobookScreen.openChapterAndGetChapterName(chapterNumber - 1);
        context.add(keyChapterName, chapter);
    }

    @When("Open random chapter on toc audiobook screen and save chapter name as {string}")
    public void openRandomChapterOnTocAudiobookScreenAndSaveChapterName(String keyChapterName) {
        int countOfChapters = tocAudiobookScreen.getCountOfChapters();
        String chapterName = tocAudiobookScreen.openChapterAndGetChapterName(RandomUtils.nextInt(1, countOfChapters));
        context.add(keyChapterName, chapterName);
    }

    @Then("The first chapter is loaded")
    public void isChapterLoaded(){
        Assert.assertTrue("The first chapter is not loaded", tocAudiobookScreen.isTheFirstChapterLoaded());
    }

    @When("Open the {int} chapter on toc audiobook screen and save the chapter name as {string} and chapter number as {string}")
    public void openChapterAndSaveNameAndNumber(int chapterNumber, String chapterNameKey, String chapterNumberKey) {
        String chapter = tocAudiobookScreen.openChapterAndGetChapterName(chapterNumber - 1);
        context.add(chapterNameKey, chapter);
        context.add(chapterNumberKey, chapterNumber);
    }

    @Then("Chapter name next to {string} on toc audiobook screen is equal to {string} saved chapter name")
    public void checkChapterNameOnToc(String chapterNumberKey, String chapterNameKey) {
        int chapterNumber = context.get(chapterNumberKey);
        String chapterNameFromToc = tocAudiobookScreen.getChapterName(chapterNumber);
        String chapterNameFromScreen = context.get(chapterNameKey);
        String cutChapterName = chapterNameFromScreen.substring(0, chapterNameFromScreen.indexOf('(') - 1);
        Assert.assertEquals("Chapter does not change to next. ", cutChapterName, chapterNameFromToc);
    }

    @Then("There are two tabs Content and Bookmarks on toc audiobook screen")
    public void checkTabsContentAndBookmarks() {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(tocAudiobookScreen.isContentTabDisplayed()).as("Content tab is not displayed").isTrue();
        softAssertions.assertThat(tocAudiobookScreen.isBookmarksTabDisplayed()).as("Bookmarks tab is not displayed").isTrue();
        softAssertions.assertAll();
    }

    @When("Open Bookmarks on toc audiobook screen")
    public void openBookmarks() {
        tocAudiobookScreen.openBookmarks();
    }

    @Then("Bookmarks screen is opened")
    public void isBookmarksScreenOpened() {
        Assert.assertTrue("Bookmarks screen is not displayed", bookmarksAudiobookScreen.isBookmarksScreenSelected());
    }

    @Then("There is no bookmarks message on Bookmarks screen")
    public void checkNoBookmarksMessage() {
        Assert.assertTrue("There is \"No bookmarks\" message on the screen", bookmarksAudiobookScreen.isNoBookmarksMessageDisplayed());
    }

    @When("Open Chapters on toc audiobook screen")
    public void openChapters() {
        tocAudiobookScreen.openChapters();
    }

    @Then("Chapters screen is opened")
    public void isChaptersOpened() {
        Assert.assertTrue(tocAudiobookScreen.isChaptersSelected());
    }

    @Then("Bookmark for the chapter {string} with the time {string} is saved on Bookmarks screen")
    public void checkIfBookmarkedSaved(String chapterNameKey, String chapterTimeKey) {
        String expectedChapterName = context.get(chapterNameKey);
        String expectedChapterTime = context.get(chapterTimeKey).toString();
        String actualChapterName = bookmarksAudiobookScreen.getChapterName();
        String actualChapterTime = bookmarksAudiobookScreen.getChapterTime();
        Assert.assertEquals("There is no correct chapter name", expectedChapterName, actualChapterName);
        Assert.assertEquals("There is no correct chapter time", expectedChapterTime, actualChapterTime);
    }

    @When("Close toc audiobook screen")
    public void closeTocAudiobookScreen(){
        tocAudiobookScreen.clickBackBtn();
    }
}
