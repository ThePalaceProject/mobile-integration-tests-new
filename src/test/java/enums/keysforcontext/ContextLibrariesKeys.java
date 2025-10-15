package enums.keysforcontext;

public enum ContextLibrariesKeys {
    LOG_OUT("librariesForLogOut");

    private final String key;

    // ✅ Explicit constructor
    ContextLibrariesKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}