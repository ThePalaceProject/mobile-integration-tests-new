package stepdefinitions.audiobooksteps;

import com.google.inject.Inject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import screens.audiobook.AudioPlayerScreen;

public class PlaybackSpeedSteps {

    private final AudioPlayerScreen audioPlayerScreen;

    @Inject
    public PlaybackSpeedSteps() {
        audioPlayerScreen = new AudioPlayerScreen();
    }

    @And("Select {string}X playback speed on playback speed audiobook screen")
    public void selectPlaybackSpeedOnPlaybackSpeedAudiobookScreen(String playbackSpeed) {
        audioPlayerScreen.openPlaybackSpeed();
        audioPlayerScreen.getPlaybackSpeedAudiobookScreen().selectPlaybackSpeed(playbackSpeed);
    }

    @When("Close playback speed screen")
    public void cancelPlaybackSpeed() {
        audioPlayerScreen.getPlaybackSpeedAudiobookScreen().closePlaybackScreen();
    }
}
