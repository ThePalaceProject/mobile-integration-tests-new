package stepdefinitions;

import com.google.inject.Inject;
import io.cucumber.java.en.When;
import screens.SettingsScreen;
import screens.TestModeScreen;

public class TestModeSteps {

    private final TestModeScreen testModeScreen;
    private final SettingsScreen settingsScreen;

    @Inject
    public TestModeSteps() {
        testModeScreen = new TestModeScreen();
        settingsScreen = new SettingsScreen();
    }
    @When("Turn on test mode")
    public void turnOnTestMode() {
        settingsScreen.openTestMode(); // performs long press or tap, then taps "Testing"
    }
    @When("Enable hidden libraries")
    public void enableHiddenLibraries() {
        testModeScreen.enableHiddenLibraries();
    }
}
