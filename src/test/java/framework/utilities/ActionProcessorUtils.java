package framework.utilities;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;

public class ActionProcessorUtils {

    private static final PlatformName PLATFORM_NAME = AqualityServices.getApplication().getPlatformName();

    private static void doForPlatform(PlatformName platformName, Runnable action) {
        if(PLATFORM_NAME.equals(platformName)) {
            action.run();
        }
    }

    public static void doForAndroid(Runnable action){
        doForPlatform(PlatformName.ANDROID, action);
    }

    public static void doForIos(Runnable action) {
        doForPlatform(PlatformName.IOS, action);
    }
}
