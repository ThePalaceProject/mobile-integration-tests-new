package stepdefinitions;

import com.google.inject.Inject;
import enums.keysforcontext.ContextLibrariesKeys;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.ScenarioContext;
import framework.utilities.swipe.SwipeElementUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.*;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.util.ArrayList;
import java.util.List;

public class AccountSteps {

    private final AddLibraryScreen addLibraryScreen;
    private final MenuBarScreen menuBarScreen;
    private final SettingsScreen settingsScreen;
    private final LibrariesScreen librariesScreen;
    private final AccountScreen accountScreen;
    private final CatalogScreen catalogScreen;
    private final FindYourLibraryScreen findYourLibraryScreen;
    private final AlertScreen alertScreen;
    private final ScenarioContext context;

    @Inject
    public AccountSteps(ScenarioContext context) {
        this.context = context;
        addLibraryScreen = new AddLibraryScreen();
        menuBarScreen = new MenuBarScreen();
        settingsScreen = new SettingsScreen();
        librariesScreen = new LibrariesScreen();
        accountScreen = new AccountScreen();
        catalogScreen = new CatalogScreen();
        findYourLibraryScreen = new FindYourLibraryScreen();
        alertScreen = new AlertScreen();
    }

    @Then("Add library screen is opened")
    public void isAddLibraryScreenIsOpened() {
        Assert.assertTrue("Add Library screen is not opened!", addLibraryScreen.isAddLibraryScreenOpened());
    }

    @When("Add library {string} on Add library screen")
    public void addLibrary(String libraryName) {
        addLibraryScreen.addLibraryViaSearch(libraryName);
    }

    @Then("Library {string} is opened on Libraries screen")
    public void isLibraryPresent(String libraryName) {
        openAccounts();
        Assert.assertTrue(libraryName + " is not present on Libraries screen", librariesScreen.isLibraryPresent(libraryName));
    }

    @When("Type {string} library and save name as {string} on Add library screen")
    public void typeLibraryName(String libraryName, String libraryNameKey) {
        context.add(libraryNameKey, libraryName);
        addLibraryScreen.typeLibraryName(libraryName);
    }

    @Then("Library {string} is displayed on Add library screen")
    public void isLibraryDisplayed(String libraryNameKey) {
        String libraryName = context.get(libraryNameKey);
        Assert.assertTrue(libraryName + " not found", addLibraryScreen.isLibraryDisplayed(libraryName));
    }

    @When("Clear search field on Add library screen")
    public void clickDeleteButton(){
        addLibraryScreen.clearSearchField();
    }

    @Then("Search field is empty on Add library screen")
    public void isSearchFieldEmpty() {
        Assert.assertTrue("Search field is not empty!", addLibraryScreen.isSearchFieldEmpty());
    }

    @When("Type word {} and save as {string} on Add library screen")
    public void typeWord(String word, String wordKey){
        context.add(wordKey, word);
        addLibraryScreen.typeLibraryName(word);
    }

    @Then("Libraries contain word {string} on Add library screen")
    public void isLibraryContainsWord(String wordKey) {
        String word = context.get(wordKey);
        Assert.assertTrue("Search result does not contain libraries with " + word, addLibraryScreen.isLibraryContainsWord(word.toLowerCase()));
    }

    @Then("Search result is empty on Add library screen")
    public void isSearchResultEmpty() {
        Assert.assertTrue("Search result contains data!", addLibraryScreen.isSearchResultEmpty());
    }

    @When("Add {string} library in Libraries screen")
    public void addLibraryInLibScreen(String libraryName){
        openAccounts();
        librariesScreen.addLibrary();
        addLibraryScreen.addLibraryViaSearch(libraryName);

        if(libraryName.equalsIgnoreCase("LYRASIS Reads")){
            saveLibraryInContext(ContextLibrariesKeys.LOG_OUT.getKey(), libraryName);
        }
    }

    @Then("Library {string} is opened on Account screen")
    public void isLibraryOpened(String libraryName) {
        Assert.assertTrue(String.format("Library %s is not opened!", libraryName), accountScreen.isLibraryOpened(libraryName));
    }

    @Then("All fields and links are displayed on Account screen")
    public void checkAllFieldsAndLinks() {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(accountScreen.isLibraryCardFieldDisplayed()).as("Library card text box is not displayed!").isTrue();
        softAssertions.assertThat(accountScreen.isPasswordFieldDisplayed()).as("Password text box is not displayed!").isTrue();
        softAssertions.assertThat(accountScreen.isSignInBtnDisplayed()).as("Sign in button is not displayed!").isTrue();
        softAssertions.assertThat(accountScreen.isForgotPasswordMessageDisplayed()).as("Forgot password label is not displayed!").isTrue();
        softAssertions.assertThat(accountScreen.isLicenseAgreementLinkDisplayed()).as("License agreement link is not displayed!").isTrue();
        softAssertions.assertAll();
    }

    @When("Open Content Licenses on Account screen")
    public void openAccountLicenses() {
        ActionProcessorUtils.doForAndroid(SwipeElementUtils::swipeDown);
        accountScreen.openContentLicenses();
    }

    @Then("Content Licenses screen is opened")
    public void isContentLicensesOpened() {
        Assert.assertTrue("Content Licenses is not opened", accountScreen.isContentLicOpened());
    }

    @When("Add {string} account by the logo")
    public void addAccountByTheLogo(String libraryName) {
        catalogScreen.tapTheLogo();
        findYourLibraryScreen.tapAddLibrary();
        addLibraryScreen.addLibraryViaSearch(libraryName);
        catalogScreen.state().waitForDisplayed();
    }

    @When("Open User license agreement on account screen")
    public void openLicAgreement(){
        accountScreen.openLicenseAgreement();
    }

    @Then("User License Agreement link is opened")
    public void isUserLicAgreementOpened() {
        Assert.assertTrue("User License Agreement is not opened", accountScreen.isLicenseAgreementOpened());
    }

    @When("Open Advanced on account screen")
    public void openAdvanced(){
        accountScreen.openAdvanced();
    }

    @Then("Advanced screen contains {string} button")
    public void isAdvancedContainsButton(String buttonName) {
        Assert.assertTrue("Advanced screen does not contain " + buttonName + " button", accountScreen.isButtonDisplayed(buttonName));
    }

    @When("Click {string} button and cancel it on Advanced screen")
    public void clickDelete(String buttonName) {
        accountScreen.clickDelete(buttonName);
        if(alertScreen.state().waitForDisplayed()) {
            alertScreen.waitAndPerformAlertActionIfDisplayed(ActionButtonsForBooksAndAlertsKeys.CANCEL);
        }
    }

    @Then("Button Add Library is displayed on libraries screen")
    public void isBtnAddLibDisplayed() {
        Assert.assertTrue("Add library button is not displayed", librariesScreen.isAddLibraryBtnDisplayed());
    }

    @When("Click Add library button on libraries screen")
    public void clickAddLibrary() {
        librariesScreen.addLibrary();
    }

    @Then("Libraries are sorted in alphabetical order on add account screen")
    public void isSortingCorrect() {
        Assert.assertTrue("Libraries are not sorted in alphabetical order", addLibraryScreen.isSortingOfLibrariesCorrect());
    }

    @When("Add libraries through settings:")
    public void addSeveralLibraries(List<String > libraries) {
        libraries.forEach(library -> {
            openAccounts();
            librariesScreen.addLibrary();
            addLibraryScreen.addLibraryViaSearch(library);
            catalogScreen.state().waitForDisplayed();
        });
    }

    @Then("Libraries are sorted in alphabetical order on libraries screen")
    public void isLibrariesSorted() {
        Assert.assertTrue("Libraries are not sorted in alphabetical order", librariesScreen.isLibrariesAreSorted());
    }

    @When("Click to {string} and save library name as {string} on libraries screen")
    public void clickToLibrary(String libraryName, String libraryNameKay) {
        context.add(libraryNameKay, libraryName);
        librariesScreen.openLibrary(libraryName);
    }

    @Then("The screen with settings for {string} library is opened")
    public void isLibraryScreenOpened(String libraryNameKey) {
        String libraryName = context.get(libraryNameKey);
        Assert.assertTrue("The screen with settings of library " + libraryName + " is not opened", librariesScreen.isLibrarySettingsOpened(libraryName));
    }

    @When("Remove {string} library")
    public void removeAccount(String libraryName) {
        openAccounts();
        librariesScreen.deleteLibrary(libraryName);
    }

    @Then("Library {string} is not present on Libraries screen")
    public void checkAccountIsNotPresent(String libraryName) {
        openAccounts();
        Assert.assertFalse(libraryName + " is present on Accounts screen", librariesScreen.isLibraryPresent(libraryName));
    }

    @When("Open library {string}")
    public void openAccount(String libraryName) {
        openAccounts();
        librariesScreen.openLibrary(libraryName);
    }

    private void saveLibraryInContext(String key, String libraryName) {
        List<String> listOfLibraries = context.containsKey(key)
                ? context.get(key)
                : new ArrayList<>();

        listOfLibraries.add(libraryName);
        context.add(key, listOfLibraries);
    }

    private void openAccounts() {
        menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
        menuBarScreen.openBottomMenuTab(MenuBar.SETTINGS);
        settingsScreen.openLibraries();
    }
}
