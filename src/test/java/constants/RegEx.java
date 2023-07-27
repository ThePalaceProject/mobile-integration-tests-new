package constants;

public class RegEx {
    public static final String VALID_SYMBOLS_IN_CATALOG_NAMES = "([a-zA-Z0-9&\\s:']*)";
    public static final String VALID_AUTHOR_OR_TITLE = "([a-zA-Z0-9-.:]*)([a-zA-Z0-9&,;:.\\s]*)([a-zA-Z0-9-.:()]*)";
}
