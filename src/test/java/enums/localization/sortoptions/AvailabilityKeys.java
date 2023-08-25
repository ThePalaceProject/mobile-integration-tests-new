package enums.localization.sortoptions;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Locale;

@AllArgsConstructor
public enum AvailabilityKeys implements LocalizedValue {
    ALL("all"),
    AVAILABLE_NOW("available_now"),
    YOURS_TO_KEEP("yours_to_keep");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("sortoptions.availabilityKeys");

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