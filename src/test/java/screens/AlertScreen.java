package screens;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import enums.localization.catalog.ActionButtonsForBooksAndAlertsKeys;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class AlertScreen extends  Screen{

    private static final String UNIQUE_ELEMENT_LOCATOR_IOS = "//XCUIElementTypeAlert";
    private static final String ACTION_BUTTON_LOCATOR_IOS = UNIQUE_ELEMENT_LOCATOR_IOS + "//XCUIElementTypeButton[@name=\"%s\"]";

    private static final String UNIQUE_ELEMENT_LOCATOR_ANDROID = "//android.widget.LinearLayout[contains(@resource-id, \"grant_dialog\")]";
    private static final String ACTION_BUTTON_LOCATOR_ANDROID = UNIQUE_ELEMENT_LOCATOR_ANDROID + "//android.widget.Button[@text=\"%s\"]";

    private final ILabel lblAlertMessage = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath(UNIQUE_ELEMENT_LOCATOR_IOS + "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeStaticText[1]"))), "Alert message");
    private final ILabel lblNotificationAlert = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("")),
            new IosLocator(By.xpath("//XCUIElementTypeAlert[contains(@name, \"Send You Notifications\")]"))), "Alert to allow notifications");

    public AlertScreen() {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath("//android.widget.LinearLayout[contains(@resource-id, \"grant_dialog\")]")),
                new IosLocator(By.xpath("//XCUIElementTypeAlert"))), "Alert screen");
    }

    public void waitAndPerformAlertActionIfDisplayed(ActionButtonsForBooksAndAlertsKeys actionButtonNamesAlertKeys) {
        IButton actionButton = getElementFactory().getButton(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(String.format(ACTION_BUTTON_LOCATOR_ANDROID, actionButtonNamesAlertKeys.getDefaultLocalizedValue()))),
                new IosLocator(By.xpath(String.format(ACTION_BUTTON_LOCATOR_IOS, actionButtonNamesAlertKeys.getDefaultLocalizedValue())))), String.format("%s Action button alert", actionButtonNamesAlertKeys.getDefaultLocalizedValue()));
        if(actionButton.state().waitForDisplayed()){
            actionButton.click();
        }
    }

    public String getTextFromAlertHeader() {
        return lblAlertMessage.getText();
    }

    public boolean isNotificationAlertDisplayed() {
        return lblNotificationAlert.state().waitForDisplayed();
    }

    public String getTextFromAlertButton(ActionButtonsForBooksAndAlertsKeys actionButtonKey) {
        IButton actionButton = getElementFactory().getButton(By.xpath(String.format(ACTION_BUTTON_LOCATOR_IOS, actionButtonKey.getDefaultLocalizedValue())), String.format("%s button alert", actionButtonKey.getDefaultLocalizedValue()));
        return actionButton.getText();
    }
}
