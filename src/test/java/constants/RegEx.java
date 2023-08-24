package constants;

public class RegEx {
    public static final String VALID_SYMBOLS_IN_CATALOG_NAMES = "([a-zA-Z0-9&\\s:']*)";
    public static final String VALID_AUTHOR_OR_TITLE = "([a-zA-Z0-9-.:]*)([a-zA-Z0-9&,;:.\\s]*)([a-zA-Z0-9-.:()]*)";
    public static final String PAGE_NUMBER_AND_CHAPTER_NAME_REGEX_FOR_IOS = "Page (\\d+) of (\\d+) (\\w.+)";
    public static final String PAGE_NUMBER_REGEX_FOR_ANDROID = "Page (\\d+) of (\\d+)";
    public static final String VALID_PUBLISHER_OR_CATEGORY_NAME = "([a-zA-Z0-9&\\s-,;'./]*)([a-zA-Z,&\\s;:'.]*)([a-zA-Z0-9,\\s-'.]*)";
}
