package stepdefinitions.audiobooksteps;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import enums.localization.catalog.TimerKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CatalogBookModel;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.audiobook.AudioPlayerScreen;

import java.time.Duration;

public class AudioPlayerSteps {

    private final AudioPlayerScreen audioPlayerScreen;
    private final ScenarioContext context;

    @Inject
    public AudioPlayerSteps(ScenarioContext context) {
        this.context = context;
        audioPlayerScreen = new AudioPlayerScreen();
    }

    @Then("Audio player screen of book {string} is opened")
    public void isPlayerOpened(String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        String bookName = bookInfo.getTitle();
        Assert.assertTrue("Player of book " + bookName + " is not opened", audioPlayerScreen.isPlayerOpened(bookName));
    }

    @When("Return to previous screen from audio player screen")
    public void returnToPreviousScreenFromAudioPlayerScreen() {
        audioPlayerScreen.returnToPreviousScreen();
    }

    @When("Tap play button on audio player screen")
    public void tapPlayButtonOnAudioPlayerScreen() {
        audioPlayerScreen.tapPlayBtn();
    }

    @Then("Pause button is present on audio player screen")
    public void checkThatPauseButtonIsPresentOnAudioPlayerScreen() {
        Assert.assertTrue("Pause button is not present on audio player screen", audioPlayerScreen.isPauseButtonPresent());
    }

    @When("Tap pause button on audio player screen")
    public void tapPauseButtonOnAudioPlayerScreen() {
        audioPlayerScreen.tapPauseBtn();
    }

    @When("Stop playing audiobook")
    public void stopPlaying() {
        if(audioPlayerScreen.isPauseButtonPresent())
            audioPlayerScreen.tapPauseBtn();
    }

    @Then("Play button is present on audio player screen")
    public void checkThatPlayButtonIsPresentOnAudioPlayerScreen() {
        Assert.assertTrue("Play button is not present on audio player screen", audioPlayerScreen.isPlayButtonPresent());
    }

    @Then("Book is not playing on audio player screen")
    public void checkThatBookIsNotPlayingOnAudioPlayerScreen() {
        Duration firstTiming = audioPlayerScreen.getLeftTime();
        //todo tread sleep
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        Assert.assertEquals("Book is playing on audio player screen", firstTiming, audioPlayerScreen.getLeftTime());
    }

    @When("Open toc audiobook screen")
    public void openTocAudiobookScreen() {
        audioPlayerScreen.openToc();
    }

    @Then("Chapter name on audio player screen is equal to {string} saved chapter name")
    public void checkThatChapterNameOnAudioPlayerScreenIsEqualToSavedChapterName(String keyChapter) {
        String expectedChapterName = context.get(keyChapter);
        String actualChapterName = audioPlayerScreen.getChapterName();
        Assert.assertEquals(String.format("Chapter name on audio player screen is not equal to saved chapter name. " +
                "Expected chapter name - %s; actual chapter name - %s", expectedChapterName, actualChapterName), expectedChapterName.toLowerCase(), actualChapterName.toLowerCase());
    }

    @When("Save book play time as {string} on audio player screen")
    public void saveBookPlayTimeOnAudioPlayerScreen(String dateKey) {
        context.add(dateKey, audioPlayerScreen.getLeftTime());
    }

    @Then("Play time is the same with {string} play time before restart on books detail screen")
    public void checkPlayTimeAfterReload(String dateKey) {
        Duration playTimeBefore = context.get(dateKey);
        Duration playTimeAfter = audioPlayerScreen.getLeftTime();
        Assert.assertTrue("Play time is different", playTimeBefore.getSeconds() == playTimeAfter.getSeconds()
                || playTimeBefore.getSeconds() == (playTimeAfter.getSeconds() - 1));
    }

    @When("Save chapter time as {string} on audio player screen")
    public void saveChapterTime(String chapterTimeKey) {
        context.add(chapterTimeKey, audioPlayerScreen.getRightTime());
    }

    @When("Skip ahead 30 seconds on audio player screen")
    public void skipAheadOnAudioPlayerScreen() {
        audioPlayerScreen.skipAhead();
    }

    @Then("Playback has been moved forward by {int} seconds from {string} and {string} seconds on audio player screen")
    public void checkThatPlaybackHasBeenMovedForwardOnAudioPlayerScreen(long secondsForward, String timeKey, String chapterTimeKey) {
        Duration chapterTime = context.get(chapterTimeKey);
        Duration savedDate = context.get(timeKey);
        long secondsBefore = savedDate.getSeconds();
        long secondsOfChapterTime = chapterTime.getSeconds();
        long actualTime = audioPlayerScreen.getLeftTime().getSeconds();
        long expectedTime;

        if(secondsOfChapterTime <= secondsForward) {
            expectedTime = secondsForward - secondsOfChapterTime;
        } else {
            expectedTime = secondsBefore + secondsForward;
        }

        Assert.assertTrue("Date is not moved forward by " + secondsForward + " seconds", expectedTime == actualTime || expectedTime == actualTime + 1 || expectedTime == actualTime - 1);
    }

    @When("Skip behind 30 seconds on audio player screen")
    public void skipBehindOnAudioPlayerScreen() {
        audioPlayerScreen.skipBehind();
    }

    @Then("Playback has been moved behind by {long} seconds from {string} and {string} seconds on audio player screen")
    public void checkThatPlaybackHasBeenMovedBehindOnAudioPlayerScreen(long secondsBehind, String timeKey, String chapterTimeKey) {
        Duration savedDate = context.get(timeKey);
        Duration chapterTime = context.get(chapterTimeKey);
        long secondsBefore = savedDate.getSeconds();
        long secondsOfChapterTime = chapterTime.getSeconds();
        long actualTime = audioPlayerScreen.getLeftTime().getSeconds();
        long expectedTime;

        if(secondsOfChapterTime <= secondsBehind) {
            expectedTime = secondsOfChapterTime - (secondsBehind - secondsBefore);
        } else {
            expectedTime = secondsBefore - secondsBehind;
        }

        Assert.assertTrue("Date is not moved behind by " + secondsBehind + " seconds, Date is moved behind by ", actualTime == expectedTime || actualTime + 1 == expectedTime);
    }

    @When("Stretch slider on the time tracking line forward on audio player screen")
    public void stretchSliderForward() {
        audioPlayerScreen.stretchPlaySliderForward();
    }

    @When("Stretch slider on the time tracking line to the end of playback")
    public void stretchSliderToTheEnd() {
        audioPlayerScreen.stretchPlaySliderToTheEnd();
    }

    @When("Listen a chapter on audio player screen")
    public void waitTheEndOfChapter() {
        AqualityServices.getConditionalWait().waitFor(()-> {
            boolean isNull = false;
            long timer = audioPlayerScreen.getRightTime().getSeconds();
            if(timer == 0 || audioPlayerScreen.isPlayButtonPresent())
                isNull = true;
            return  isNull;
        });
    }

    @When("Save the name of chapter as {string} on audio player screen")
    public void saveChapterName(String chapterNameKey) {
        String chapterName = audioPlayerScreen.getChapterName();
        context.add(chapterNameKey, chapterName);
    }

    @Then("Line for time remaining is displayed on audio player screen")
    public void isLineRemainingDisplayed() {
        Assert.assertTrue("Line for time remaining is not displayed", audioPlayerScreen.isLineRemainingDisplayed());
    }

    @Then("Next chapter play automatically and chapter name is not {string} on audio player screen")
    public void isChapterPlaying(String chapterNameKey) {
        String chapterName = context.get(chapterNameKey);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(audioPlayerScreen.isPlayButtonPresent()).as("Play button is not displayed").isTrue();
        softAssertions.assertThat(audioPlayerScreen.getChapterName().equals(chapterName)).as("Chapter name does not change").isFalse();
    }

    @Then("The speed by default is {string}X")
    public void isPlaySpeedNormal(String playbackSpeed) {
        if(AqualityServices.getApplication().getPlatformName()== PlatformName.IOS) {
            String speedValue = audioPlayerScreen.getPlaySpeedValue();
            Assert.assertTrue("Play speed is not default: " + playbackSpeed, speedValue.contains(playbackSpeed));
        }
        else {
            Assert.assertEquals("Play speed is not default", "1.0x", audioPlayerScreen.getPlaySpeedValue());
        }
    }

    @When("Open playback speed on audio player screen")
    public void openPlaybackSpeed() {
        audioPlayerScreen.openPlaybackSpeed();
    }

    @Then("Sleep timer is set to endOfChapter on audio player screen")
    public void checkThatSleepTimerIsSetToEndOfChapterOnAudioPLayerScreen() {
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.ANDROID) {
            Assert.assertTrue("Timer value is not correct", audioPlayerScreen.isTimerSetTo(TimerKeys.END_OF_CHAPTER));
        } else if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            Assert.assertTrue("Timer value is not correct", audioPlayerScreen.isTimerEqualTo(audioPlayerScreen.getRightTime()));
        }
    }

    @When("Open sleep timer on audio player screen")
    public void openSleepTimer() {
        audioPlayerScreen.openSleepTimer();
    }

    @Then("Playing time is not equal to {string} on audio playing screen")
    public void compareTimes(String timeKey) {
        Duration time = context.get(timeKey);
        Assert.assertNotEquals("Times are equals", time.getSeconds(), audioPlayerScreen.getLeftTime().getSeconds());
    }

    @When("Stretch slider on the time tracking line back on audio player screen")
    public void stretchSliderBack() {
        audioPlayerScreen.stretchPlaySliderBack();
    }

    @When("Tap on the time bar forward on audio player screen")
    public void tapForward() {
        audioPlayerScreen.tapOnPlayBarForward();
    }

    @Then("Play times {string} and {string} are equals")
    public void playTimesAreEquals(String timeBehindKey, String timeAheadKey) {
        Duration timeBehind = context.get(timeBehindKey);
        Duration timeAhead = context.get(timeAheadKey);
        Assert.assertEquals("Time is changed", timeBehind.getSeconds(), timeAhead.getSeconds());
    }

    @When("Tap on the time bar back on audio player screen")
    public void tapBackward() {
        audioPlayerScreen.tapOnPlayBarBackward();
    }

    @Then("Current playback speed value is {double}X on audio player screen")
    public void checkCurrentPlaybackSpeedValueIsCorrectOnAudioPlayerScreen(Double playbackSpeedDouble) {
        String playbackSpeed = String.valueOf(playbackSpeedDouble);
        Assert.assertTrue("Current playback speed value is not correct on audio player screen", audioPlayerScreen.isPlaybackSpeedPresent(playbackSpeed));
    }

    @When("Tap bookmark icon on audio player screen")
    public void tapBookmarkIcon() {
        audioPlayerScreen.tapBookmarkIcon();
    }

    @Then("The message Bookmark added appears on audio player screen")
    public void checkBookmarkMessageAppears() {
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            Assert.assertTrue("Bookmark added message is not displayed", audioPlayerScreen.isBookmarkAddedMessageDisplayed());
        }
    }

    @Then("Book is playing on audio player screen")
    public void checkThatBookIsPlayingOnAudioPlayerScreen() {
        Duration firstTiming = audioPlayerScreen.getLeftTime();
        Assert.assertTrue("Book is not playing on audio player screen",
                AqualityServices.getConditionalWait().waitFor(() -> !firstTiming.equals(audioPlayerScreen.getLeftTime())));
    }

    @Then("Chapter number is {string} on audio player screen")
    public void checkChapterNumber(String chapterNumberKey) {
        int expectedChapterNumber = context.get(chapterNumberKey);
        String chapterName = audioPlayerScreen.getChapterName();
        int actualChapterNumber = Integer.parseInt(StringUtils.substringBetween(chapterName, "file ", " of"));
        Assert.assertEquals("Chapter number is not " + expectedChapterNumber, expectedChapterNumber, actualChapterNumber);
    }
}