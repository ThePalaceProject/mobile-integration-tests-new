package constants.localization.providers;

import lombok.NonNull;

import java.util.Locale;

public interface LocalizedValue {
    /**
     * Localized value in the default locale
     *
     * @return localized value
     */
    String getDefaultLocalizedValue();

    /**
     * Localized value in the specific locale
     *
     * @param locale specific locale
     * @return localized value
     */
    String getLocalizedValueOfSpecificLocale(@NonNull Locale locale);
}
