package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import screens.AlertScreen;

public class AlertSteps {

    private final AlertScreen alertScreen;

    @Inject
    public AlertSteps() {
        alertScreen = new AlertScreen();
    }

    @When("Tap {} button on the alert")
    public void tapActionButton(ActionButtonsForBooksAndAlertsKeys actionButtons) {
        alertScreen.waitAndPerformAlertActionIfDisplayed(actionButtons);
    }

    @When("Don't Allow notifications on the alert")
    public void tapDoNotAllow() {
        AqualityServices.getApplication().getDriver().switchTo().alert().dismiss();
    }

    @Then("There is an alert to allow notifications")
    public void isAlertForNotificationsDisplayed() {
        Assert.assertTrue("Alert is not displayed!", alertScreen.isNotificationAlertDisplayed());
    }

    @Then("There is an alert to remove reservations with {} and {} buttons")
    public void checkButtonsToRemoveReservations(ActionButtonsForBooksAndAlertsKeys keyRemove, ActionButtonsForBooksAndAlertsKeys keyCancel) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(alertScreen.getTextFromAlertButton(keyRemove)).as(String.format("Button is not %s", keyRemove.getDefaultLocalizedValue())).isEqualTo(keyRemove.getDefaultLocalizedValue());
        softAssertions.assertThat(alertScreen.getTextFromAlertButton(keyCancel)).as(String.format("Button is not %s", keyCancel.getDefaultLocalizedValue())).isEqualTo(keyCancel.getDefaultLocalizedValue());
        softAssertions.assertAll();
    }

    @Then("Alert to allow notification is not displayed")
    public void isAlertForNotificationNotDisplayed() {
        Assert.assertFalse("Alert is displayed!", alertScreen.isNotificationAlertDisplayed());
    }
}
