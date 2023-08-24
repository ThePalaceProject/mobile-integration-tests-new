package stepdefinitions;

import com.google.inject.Inject;
import io.cucumber.java.en.When;
import screens.TestModeScreen;

public class TestModeSteps {

    private final TestModeScreen testModeScreen;

    @Inject
    public TestModeSteps() {
        testModeScreen = new TestModeScreen();
    }

    @When("Enable hidden libraries")
    public void enableHiddenLibraries() {
        testModeScreen.enableHiddenLibraries();
    }
}
