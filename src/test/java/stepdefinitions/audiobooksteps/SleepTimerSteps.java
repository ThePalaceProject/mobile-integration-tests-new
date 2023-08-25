package stepdefinitions.audiobooksteps;

import com.google.inject.Inject;
import enums.localization.catalog.TimerKeys;
import io.cucumber.java.en.When;
import screens.audiobook.AudioPlayerScreen;

public class SleepTimerSteps {

    private final AudioPlayerScreen audioPlayerScreen;

    @Inject
    public SleepTimerSteps () {
        audioPlayerScreen = new AudioPlayerScreen();
    }

    @When("Set {} sleep timer on sleep timer audiobook screen")
    public void setSleepTimerOnSleepTimerAudiobookScreen(TimerKeys timerSetting) {
        audioPlayerScreen.openSleepTimer();
        audioPlayerScreen.getSleepTimerAudiobookScreen().setTimer(timerSetting);
    }

    @When("Close sleep timer screen")
    public void closeSleepTimer() {
        audioPlayerScreen.getSleepTimerAudiobookScreen().closeSleepTimer();
    }
}
