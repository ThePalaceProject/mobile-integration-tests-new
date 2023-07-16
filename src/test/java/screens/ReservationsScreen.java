package screens;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import framework.utilities.LocatorUtils;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class ReservationsScreen extends Screen {

    private final ILabel lblReservations = getElementFactory().getLabel(LocatorUtils.getLocator(
            new AndroidLocator(By.xpath("//android.view.ViewGroup[contains(@resource-id,\"mainToolbar\")]/android.widget.TextView")),
            new IosLocator(By.xpath("//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText"))), "Reservations label");

    public ReservationsScreen() {
        super(By.xpath("//reservationsScreen"), "Reservations screen");
    }

    public boolean isScreenOpened() {
        return lblReservations.state().waitForDisplayed();
    }
}
