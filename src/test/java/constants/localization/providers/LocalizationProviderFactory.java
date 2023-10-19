package constants.localization.providers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalizationProviderFactory {
    private static Map<String, AbstractILocalizationProvider> providers = new ConcurrentHashMap<>();

    private LocalizationProviderFactory () { }

    public static AbstractILocalizationProvider getProvider(String fileName) {
        return providers.computeIfAbsent(fileName, v -> new AbstractILocalizationProvider(v) {
            // default
        });
    }
}