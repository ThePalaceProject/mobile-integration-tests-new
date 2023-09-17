package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import enums.timeouts.RestartAppTimeouts;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.ScenarioContext;
import framework.utilities.swipe.SwipeElementUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.*;
import screens.epub.ReaderEpubScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;
import screens.pdf.ReaderPdfScreen;

import java.time.Duration;
import java.util.List;


public class ApplicationSteps {
    private final ScenarioContext context;
    private final TutorialScreen tutorialScreen;
    private final WelcomeScreen welcomeScreen;
    private final MenuBarScreen menuBarScreen;
    private final SettingsScreen settingsScreen;
    private final ReaderEpubScreen readerEpubScreen;
    private final ReaderPdfScreen readerPdfScreen;
    private final CatalogScreen catalogScreen;
    private final FindYourLibraryScreen findYourLibraryScreen;
    private final AddLibraryScreen addLibraryScreen;
    private final AlertScreen alertScreen;

    @Inject
    public ApplicationSteps(ScenarioContext context) {
        this.context = context;
        tutorialScreen = new TutorialScreen();
        welcomeScreen = new WelcomeScreen();
        menuBarScreen = new MenuBarScreen();
        settingsScreen = new SettingsScreen();
        readerEpubScreen = new ReaderEpubScreen();
        readerPdfScreen = new ReaderPdfScreen();
        catalogScreen = new CatalogScreen();
        findYourLibraryScreen = new FindYourLibraryScreen();
        addLibraryScreen = new AddLibraryScreen();
        alertScreen = new AlertScreen();
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
        if(alertScreen.state().waitForDisplayed()) {
            alertScreen.waitAndPerformAlertActionIfDisplayed(ActionButtonsForBooksAndAlertsKeys.ALLOW);
        }
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

    @When("Wait for {int} seconds")
    public void waitSeveralSeconds(Integer secondsCount) {
        if (secondsCount > 10) {
            AqualityServices.getConditionalWait().waitFor(() -> false, Duration.ofSeconds(secondsCount / 3));
            AqualityServices.getApplication().getDriver().getContext();
            AqualityServices.getConditionalWait().waitFor(() -> false, Duration.ofSeconds(secondsCount / 3));
            AqualityServices.getApplication().getDriver().getContext();
            AqualityServices.getConditionalWait().waitFor(() -> false, Duration.ofSeconds(secondsCount / 3));
        } else {
            AqualityServices.getConditionalWait().waitFor(() -> false, Duration.ofSeconds(secondsCount));
        }
    }

    @When("Turn on test mode")
    public void turnOnTestMode() {
        menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
        settingsScreen.openTestMode();
    }

    @When("Return to previous screen for epub and pdf")
    public void returnToPreviousScreenForEpubAndPdf() {
        ActionProcessorUtils.doForAndroid(() -> AqualityServices.getApplication().getDriver().navigate().back());

        ActionProcessorUtils.doForIos(() -> {
            if (readerEpubScreen.state().isDisplayed()) {
                readerEpubScreen.openNavigationBar();
                readerEpubScreen.getNavigationBarEpubScreen().returnToPreviousScreen();
            } else if (readerPdfScreen.state().isDisplayed()) {
                if (AqualityServices.getApplication().getPlatformName() == PlatformName.ANDROID) {
                    readerPdfScreen.getNavigationBarScreen().tapBackButton();
                } else if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
                    readerPdfScreen.getNavigationBarScreen().tapBackButton();
                }
            }
        });
    }

    @When("Add libraries by the logo:")
    public void addSevLibraries(List<String> libraries){
        libraries.forEach(library -> {
            catalogScreen.tapTheLogo();
            findYourLibraryScreen.tapAddLibrary();
            addLibraryScreen.addLibraryViaSearch(library);
            catalogScreen.state().waitForDisplayed();
        });
    }

    @When("Save {int} amount as {string}")
    public void addAmountOfLibraries(int listSize, String sizeKey) {
        context.add(sizeKey, listSize);
    }

    @When("Tap the logo on catalog screen")
    public void tapTheLogo() {
        catalogScreen.tapTheLogo();
    }

    @Then("The sorting of {string} libraries is alphabetical on find your library screen")
    public void isSortingCorrect(String amountKey) {
        int amount = context.get(amountKey);
        Assert.assertTrue("The list of libraries is not in alphabetical ored", findYourLibraryScreen.isSortingAlphabetical(amount));
    }

    @When("Tap cancel button on find your library screen")
    public void tapCloseBtn() {
        findYourLibraryScreen.tapCancelBtn();
    }

    @When("Choose {string} library on find your library screen")
    public void chooseLibrary(String libName) {
        findYourLibraryScreen.tapToLibrary(libName);
    }
}
