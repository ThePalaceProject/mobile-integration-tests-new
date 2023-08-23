package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import constants.keysForContext.ScenarioContextKey;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.configuration.Configuration;
import framework.configuration.Credentials;
import framework.utilities.ScenarioContext;
import framework.utilities.returningBooksUtil.APIUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.AlertScreen;
import screens.SignInScreen;

import java.util.HashMap;
import java.util.Map;

public class SignInSteps {

    private final SignInScreen signInScreen;
    private final AlertScreen alertScreen;
    private final ScenarioContext context;

    @Inject
    public SignInSteps (ScenarioContext context) {
        this.context = context;
        signInScreen = new SignInScreen();
        alertScreen = new AlertScreen();
    }

    @Then("Sing in screen is opened")
    public void isSignInScreenOpened() {
        signInScreen.state().waitForDisplayed();
    }

    @Then("All fields and links are displayed on Sign in screen")
    public void checkTheVisibilityOfFieldsAndLinks() {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(signInScreen.isLibraryCardDisplayed()).as("Library card textbox is not displayed").isTrue();
        softAssertions.assertThat(signInScreen.isPasswordDisplayed()).as("Password textbox is not displayed").isTrue();
        softAssertions.assertThat(signInScreen.isSignInBtnDisplayed()).as("Sign in is not displayed").isTrue();
        softAssertions.assertThat(signInScreen.isLinkToTheLicenseAgreementDisplayed()).as("License agreement link is not displayed").isTrue();
        softAssertions.assertAll();
    }

    @When("Enter valid credentials fot {string} library on Sign in screen")
    public void enterValidCredentials(String libraryName) {
        Credentials credentials = Configuration.getCredentials(libraryName);
        storeCredentials(credentials);
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && alertScreen.state().waitForDisplayed()) {
            AqualityServices.getApplication().getDriver().switchTo().alert().dismiss();
            AqualityServices.getLogger().info("Alert appears and dismiss alert");
        }
        signInScreen.enterLibraryCard(credentials.getBarcode());
        signInScreen.enterPassword(credentials.getPin());
        signInScreen.tapSignIn();
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS && alertScreen.state().waitForDisplayed()) {
            alertScreen.waitAndPerformAlertActionIfDisplayed(ActionButtonsForBooksAndAlertsKeys.ALLOW);
        }
    }

    @Then("Activate sync bookmarks on Sign in screen")
    public void activateSyncBookmarks() {
        signInScreen.activateSyncBookmarks();
    }

    @Then("There is a placeholder {string} in the Password field on Sign in screen")
    public void isPlaceholderInPasswordDisplayed(String placeholderName) {
        Assert.assertEquals("A placeholder " + placeholderName + " is not displayed", placeholderName, signInScreen.getTextFromPassword());
    }

    @Then("There is a placeholder Library Card in the Library Card field on Sign in screen")
    public void isPlaceholderInLibCardDisplayed() {
        Assert.assertEquals("A placeholder Library Card is not displayed", "Library Card", signInScreen.getTextFromLibraryCard());
    }

    @When("Enter a valid Password for {string} library on Sign in screen")
    public void enterPassword(String libraryName) {
        Credentials credentials = Configuration.getCredentials(libraryName);
        String password = credentials.getPin();
        signInScreen.enterPassword(password);
    }

    @Then("Sign in button is disabled on Sign in screen")
    public void isSignInButtonDisabled() {
        signInScreen.tapSignIn();
        Assert.assertFalse("Sign in screen not displayed", signInScreen.state().waitForNotDisplayed());
    }

    @When("Enter a valid Library card {string} on Sign in screen")
    public void enterLibCard(String libraryCard) {
        signInScreen.enterLibraryCard(libraryCard);
    }

    @When("Enter a Library card with {int} numbers and save it as {string} on Sign in screen")
    public void enterLibCardAndSave(int libraryCardLength, String libraryCardKey) {
        String libraryCard = RandomStringUtils.randomNumeric(libraryCardLength);
        signInScreen.enterLibraryCard(libraryCard);
        context.add(libraryCardKey, libraryCard);
    }

    @When("Edit data by adding {string} in Library card field and save it as {string} on sign in screen")
    public void editLibraryCard(String addedData, String newLibCardKey) {
        signInScreen.deleteSomeDataInLibCard();
        signInScreen.deleteSomeDataInLibCard();
        signInScreen.setTextInLibCard(addedData);
        context.add(newLibCardKey, signInScreen.getTextFromLibraryCard());
    }

    @Then("There is a placeholder {string} in the Library Card field on Sign in screen")
    public void isNewPlaceholderDisplayed(String placeholderKey) {
        Assert.assertEquals("New placeholder is not displayed", context.get(placeholderKey), signInScreen.getTextFromLibraryCard());
    }

    @When("Enter a Library card with {int} latin letters and save it as {string} on Sign in screen")
    public void enterLatinLettersInLibCard(int cardLength, String libraryCardKey) {
        String libraryCard = RandomStringUtils.randomAlphabetic(cardLength);
        signInScreen.enterLibraryCard(libraryCard);
        context.add(libraryCardKey, libraryCard);
    }

    @When("Tap the Sign in button on Sign in screen")
    public void tapSignIn() {
        signInScreen.tapSignIn();
    }

    @Then("There is an alert {string} on Sign in screen")
    public void isAlertInvalidCredDisplayed(String alertMessage) {
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.IOS) {
            Assert.assertEquals("Alert \"" + alertMessage + "\" is not displayed", alertMessage, alertScreen.getTextFromAlertHeader());
        } else {
            Assert.assertTrue("There is nor error message \"Invalid credentials\". Actual - " + signInScreen.getErrorMessage(), signInScreen.getErrorMessage().contains(alertMessage));
        }
    }

    private void storeCredentials(Credentials credentials) {
        String barcode = credentials.getBarcode();
        String pin = credentials.getPin();
        APIUtil.enterBookAfterOpeningAccount(credentials);
        if (context.get(ScenarioContextKey.LIST_OF_CREDENTIALS_KEY) == null) {
            Map<String, String> hashMap = new HashMap<>();
            context.add(ScenarioContextKey.LIST_OF_CREDENTIALS_KEY, hashMap);
        }
        Map<String, String> map = context.get(ScenarioContextKey.LIST_OF_CREDENTIALS_KEY);
        map.put(barcode, pin);
        context.add(ScenarioContextKey.LIST_OF_CREDENTIALS_KEY, map);
    }
}
