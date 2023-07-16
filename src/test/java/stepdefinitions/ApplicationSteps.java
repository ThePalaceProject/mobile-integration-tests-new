package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import enums.timeouts.RestartAppTimeouts;
import framework.utilities.swipe.SwipeElementUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.TutorialScreen;
import screens.WelcomeScreen;
import screens.menubar.MenuBarScreen;


public class ApplicationSteps {
    private final TutorialScreen tutorialScreen;
    private final WelcomeScreen welcomeScreen;
    private final MenuBarScreen menuBarScreen;

    @Inject
    public ApplicationSteps() {
        tutorialScreen = new TutorialScreen();
        welcomeScreen = new WelcomeScreen();
        menuBarScreen = new MenuBarScreen();
    }

    @When("Restart app")
    public void restartApp(){
        AqualityServices.getApplication().getDriver().runAppInBackground(RestartAppTimeouts.TIMEOUT_RESTART_APPLICATION.getTimeoutRestart());
        AqualityServices.getApplication().getDriver().closeApp();
        AqualityServices.getApplication().getDriver().launchApp();
    }

    @When("Swipe up")
    public void swipeUp() {
        SwipeElementUtils.swipeUp();
    }

    @Then("Tutorial screen is opened")
    public void checkTutorialScreenIsOpened() {
        Assert.assertTrue("Tutorial screen is not opened!", tutorialScreen.isTutorialScreenOpened());
    }

    @When("Close tutorial screen")
    public void closeTutorialScreen() {
        tutorialScreen.closeTutorialScreen();
    }

    @Then("Each tutorial page can be opened on Tutorial screen and close tutorial screen")
    public void checkEachTutorialPageCanBeOpened() {
        tutorialScreen.getListOfPageNames().forEach(pageName -> {
            Assert.assertTrue(String.format("Tutorial page '%s' is not opened", pageName), tutorialScreen.isTutorialPageOpened(pageName));
            tutorialScreen.goToNextPage();
        });
    }

    @Then("Welcome screen is opened")
    public void isWelcomeScreenOpened() {
        Assert.assertTrue("Welcome screen is not opened!", welcomeScreen.isWelcomeScreenOpened());
    }

    @When("Close welcome screen")
    public void closeWelcomeScreen() {
        welcomeScreen.tapFindYourLibraryBtn();
    }

    @Then("There is a menu bar at the bottom of the screen")
    public void isMenuBarDisplayed(){
        Assert.assertTrue("Menu bar is not displayed", menuBarScreen.isMenuBarDisplayed());
    }

    @Then("There are tabs {string}, {string}, {string} and {string} at the bottom of the screen")
    public void checkTabsAtTheBottomInLyrasis(String tab1, String tab2, String tab3, String tab4) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(menuBarScreen.getTypeOfTab(tab1)).as("There is no " + tab1 + " tab").isEqualTo(tab1);
        softAssertions.assertThat(menuBarScreen.getTypeOfTab(tab2)).as("There is no " + tab2 + " tab").isEqualTo(tab2);
        softAssertions.assertThat(menuBarScreen.getTypeOfTab(tab3)).as("There is no " + tab3 + " tab").isEqualTo(tab3);
        softAssertions.assertThat(menuBarScreen.getTypeOfTab(tab4)).as("There is no " + tab4 + " tab").isEqualTo(tab4);
    }

    @Then("There are tabs {string}, {string} and {string} at the bottom of the screen")
    public void checkTabsAtTheBottomInPalace(String tab1, String tab2, String tab3) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(menuBarScreen.getTypeOfTab(tab1)).as("There is no " + tab1 + " tab").isEqualTo(tab1);
        softAssertions.assertThat(menuBarScreen.getTypeOfTab(tab2)).as("There is no " + tab2 + " tab").isEqualTo(tab2);
        softAssertions.assertThat(menuBarScreen.getTypeOfTab(tab3)).as("There is no " + tab3 + " tab").isEqualTo(tab3);
    }

}
