package framework.utilities;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;

public class PlatformUtils {

    private PlatformUtils() {}

    public static PlatformName getPlatformName() {
        return AqualityServices.getApplication().getPlatformName();
    }
}
