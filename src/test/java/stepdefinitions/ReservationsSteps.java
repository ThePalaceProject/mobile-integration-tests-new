package stepdefinitions;

import com.google.inject.Inject;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import screens.ReservationsScreen;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

public class ReservationsSteps {

    private final MenuBarScreen menuBarScreen;
    private final ReservationsScreen reservationsScreen;

    @Inject
    public ReservationsSteps() {
        menuBarScreen = new MenuBarScreen();
        reservationsScreen = new ReservationsScreen();
    }

    @When("Open Reservations")
    public void openReservations() {
        menuBarScreen.openBottomMenuTab(MenuBar.RESERVATIONS);
    }

    @Then("Reservations screen is opened")
    public void isScreenOpened() {
        Assert.assertTrue("Reservations screen is not opened!", reservationsScreen.isScreenOpened());
    }
}
