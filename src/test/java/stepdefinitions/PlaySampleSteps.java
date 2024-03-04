package stepdefinitions;

import com.google.inject.Inject;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.PlaySampleScreen;

public class PlaySampleSteps {

    private final PlaySampleScreen playSampleScreen;
    private final ScenarioContext context;

    @Inject
    public PlaySampleSteps(ScenarioContext context) {
        this.context = context;
        playSampleScreen = new PlaySampleScreen();
    }

    @Then("Pause button is displayed on Sample player screen")
    public void isPauseButtonDisplayed() {
        Assert.assertTrue("Pause button is not displayed", playSampleScreen.isPauseButtonDisplayed());
    }

    @Then("Play button is displayed on Sample player screen")
    public void isPlayButtonDisplayed() {
        Assert.assertTrue("Play button is not displayed", playSampleScreen.isPlayButtonDisplayed());
    }

    @Then("Check that Sample player screen of {string} book contains all necessary elements")
    public void checkElementsOnTheScreen(String bookNameKey) {
        String bookName = context.get(bookNameKey);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(playSampleScreen.isBookTitleDisplayed(bookName)).as("Sample does not contain title").isTrue();
        softAssertions.assertThat(playSampleScreen.isTimeDurationDisplayed()).as("Time duration is not displayed").isTrue();
        softAssertions.assertThat(playSampleScreen.isRewindingDisplayed()).as("Rewinding button is not displayed").isTrue();
        ActionProcessorUtils.doForIos(() -> {
            softAssertions.assertThat(playSampleScreen.isPauseButtonDisplayed()).as("Pause button is not displayed").isTrue();
        });
        ActionProcessorUtils.doForAndroid(() -> {
            softAssertions.assertThat(playSampleScreen.isPlayButtonDisplayed()).as("Play button is not displayed").isTrue();
        });
        softAssertions.assertAll();
    }

    @When("Tap pause button on Sample player screen")
    public void tapPauseBtn() {
        playSampleScreen.clickPauseButton();
    }

    @When("Tap play button on Sample player screen")
    public void tapPlayBtn() {
        playSampleScreen.clickPlayButton();
    }

    @When("Tap Back button on Sample played screen")
    public void tapBackBtn() {
        playSampleScreen.clickBackButton();
    }
}
