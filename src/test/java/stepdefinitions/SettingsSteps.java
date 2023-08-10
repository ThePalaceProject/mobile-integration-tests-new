package stepdefinitions;

import com.google.inject.Inject;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import screens.SettingsScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

public class SettingsSteps {

    private final MenuBarScreen menuBarScreen;
    private final SettingsScreen settingsScreen;

    @Inject
    public SettingsSteps(){
        menuBarScreen = new MenuBarScreen();
        settingsScreen = new SettingsScreen();
    }

    @When("Open Settings")
    public void openSettings() {
        menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
    }

    @Then("Settings screen is opened")
    public void isSettingsScreenOpened() {
        Assert.assertTrue("Settings screen is not opened!", settingsScreen.isScreenOpened());
    }

    @When("Open Libraries on Settings screen")
    public void openLibraries() {
        settingsScreen.openLibraries();
    }

    @When("Open {string} library on Setting screen")
    public void openLibrary(String libraryName) {
        settingsScreen.openLibrary(libraryName);
    }
}
