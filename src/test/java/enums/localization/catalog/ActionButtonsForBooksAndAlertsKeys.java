package enums.localization.catalog;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Locale;

@AllArgsConstructor
public enum ActionButtonsForBooksAndAlertsKeys implements LocalizedValue {
    GET("get"),
    READ("read"),
    RESERVE("reserve"),
    DELETE("delete"),
    RETURN("return"),
    CANCEL("cancel"),
    DOWNLOAD("download"),
    LISTEN("listen"),
    REMOVE("remove"),
    SIGN_OUT("signOut"),
    NOT_NOW("notNow"),
    OK("ok"),
    VIEW_SAMPLE("view_sample"),
    PLAY_SAMPLE("play_sample"),
    ALLOW("allow"),
    DO_NOT_ALLOW("do_not_allow");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("catalog.actionButtonsForBooksAndAlerts");

    private final String key;

    @Override
    public String getDefaultLocalizedValue() {
        return localizationProvider.getLocalization(key);
    }

    @Override
    public String getLocalizedValueOfSpecificLocale(@NonNull Locale locale) {
        return localizationProvider.getLocalization(key, locale);
    }
}