package enums.localization.account;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;

import java.util.Locale;

public enum AccountScreenSignInStatus implements LocalizedValue {

    SIGN_IN("sign_in"),
    SIGN_OUT("sign_out");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("account.accountScreenSignInStatus");

    private final String key;

    // ✅ Explicit constructor
    AccountScreenSignInStatus(String key) {
        this.key = key;
    }

    @Override
    public String getDefaultLocalizedValue() {
        return localizationProvider.getLocalization(key);
    }

    @Override
    public String getLocalizedValueOfSpecificLocale(Locale locale) {
        return localizationProvider.getLocalization(key, locale);
    }
}