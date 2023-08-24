package stepdefinitions;

import com.google.inject.Inject;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import screens.*;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

public class SettingsSteps {

    private final MenuBarScreen menuBarScreen;
    private final SettingsScreen settingsScreen;
    private final AboutPalaceScreen aboutPalaceScreen;
    private final PrivacyPolicyScreen privacyPolicyScreen;
    private final UserAgreementScreen userAgreementScreen;
    private final SoftwareLicensesScreen softwareLicensesScreen;

    @Inject
    public SettingsSteps(){
        menuBarScreen = new MenuBarScreen();
        settingsScreen = new SettingsScreen();
        aboutPalaceScreen = new AboutPalaceScreen();
        privacyPolicyScreen = new PrivacyPolicyScreen();
        userAgreementScreen = new UserAgreementScreen();
        softwareLicensesScreen = new SoftwareLicensesScreen();
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

    @When("Open About Palace on settings screen")
    public void openAboutPalace() {
        settingsScreen.openAboutPalace();
    }

    @Then("About Palace screen is opened")
    public void aboutPalaceIsPresent() {
        aboutPalaceScreen.isOpened();
    }

    @When("Open Privacy Policy on settings screen")
    public void openPrivacyPolicy() {
        settingsScreen.openPrivacyPolicy();
    }

    @Then("Privacy Policy screen is opened")
    public void privacyPolicyIsOpened() {
        privacyPolicyScreen.isPrivacyPolicyScreenOpened();
    }

    @When("Open User Agreement on settings screen")
    public void openUserAgreement () {
        settingsScreen.openUserAgreement();
    }

    @Then("User Agreement screen is opened")
    public void userAgreementIsOpened() {
        userAgreementScreen.isUserAgreementScreenOpened();
    }

    @When("Scroll page to link {string} on user agreement screen")
    public void scrollToLinkOnAgreement(String link) {
        userAgreementScreen.scrollThePage(link);
    }

    @Then("Link {string} is available on user agreement screen")
    public void isLinkAvailable(String link) {
        Assert.assertTrue("Link is not available", userAgreementScreen.isLinkAvailable(link));
    }

    @When("Open Software Licenses on settings screen")
    public void openSoftwareLic() {
        settingsScreen.openSoftwareLic();
    }

    @Then("Software Licenses screen is opened")
    public void softwareLicIsOpened() {
        softwareLicensesScreen.isSoftwareLicensesScreenOpened();
    }

    @When("Scroll page to link {string} on software licenses screen")
    public void scrollToLinkOnLic(String link) {
        softwareLicensesScreen.scrollThePage(link);
    }

    @Then("Link {string} is available on software licenses screen")
    public void isLinkClickable(String link) {
        Assert.assertTrue("Link is not available", softwareLicensesScreen.isLinkAvailable(link));
    }
}
