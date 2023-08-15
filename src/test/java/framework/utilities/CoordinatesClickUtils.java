package framework.utilities;

import aquality.appium.mobile.application.AqualityServices;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;

public class CoordinatesClickUtils {

    private CoordinatesClickUtils() {
    }

    public static void clickAtCenterOfScreen() {
        Dimension dimension = AqualityServices.getApplication().getDriver().manage().window().getSize();
        TouchAction<?> action = new TouchAction<>(AqualityServices.getApplication().getDriver());
        action.tap(PointOption.point(dimension.width / 2, dimension.height / 2)).perform();
    }
}
