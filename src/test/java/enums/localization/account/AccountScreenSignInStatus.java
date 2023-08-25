package enums.localization.account;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Locale;

@AllArgsConstructor
public enum AccountScreenSignInStatus implements LocalizedValue {

    SIGN_IN("sign_in"),
    SIGN_OUT("sign_out");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("account.accountScreenSignInStatus");

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