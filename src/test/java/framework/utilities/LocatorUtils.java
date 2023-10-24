package framework.utilities;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

public class LocatorUtils {

    private static final PlatformName PLATFORM_NAME = AqualityServices.getApplication().getPlatformName();

    private LocatorUtils() {}

    public static By getLocator(AndroidLocator androidLocator, IosLocator iosLocator) {
        switch (PLATFORM_NAME) {
            case ANDROID:
                return androidLocator.getLocator();
            case IOS:
                return iosLocator.getLocator();
            default:
                throw new RuntimeException("Platform is not supported. Can't get locator for element");
        }
    }
}
