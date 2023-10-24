package framework.utilities;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class ActionProcessorUtils {

    private static final PlatformName PLATFORM_NAME = AqualityServices.getApplication().getPlatformName();

    private ActionProcessorUtils() {}

    private static void doForPlatform(PlatformName platformName, Runnable action) {
        if(PLATFORM_NAME.equals(platformName)) {
            action.run();
        }
    }

    private static <T> T doForPlatform(PlatformName platformName, Supplier<T> action) {
        if(PLATFORM_NAME.equals(platformName)) {
            return action.get();
        }
        return null;
    }

    private static boolean doForPlatform(PlatformName platformName, BooleanSupplier action) {
        if(PLATFORM_NAME.equals(platformName)) {
            return action.getAsBoolean();
        }
        return false;
    }

    public static void doForAndroid(Runnable action){
        doForPlatform(PlatformName.ANDROID, action);
    }

    public static void doForIos(Runnable action) {
        doForPlatform(PlatformName.IOS, action);
    }

    public static <T> T doForAndroid(Supplier<T> action) {
        return doForPlatform(PlatformName.ANDROID, action);
    }

    public static <T> T doForIos(Supplier<T> action) {
        return doForPlatform(PlatformName.IOS, action);
    }

    public static boolean doForAndroid(BooleanSupplier action) {
        return doForPlatform(PlatformName.ANDROID, action);
    }

    public static boolean doForIos(BooleanSupplier action) {
        return doForPlatform(PlatformName.IOS, action);
    }
}