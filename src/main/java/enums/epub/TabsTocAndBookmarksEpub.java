package enums.epub;

public enum TabsTocAndBookmarksEpub {
    TOC("Table of Contents", "toc"),
    BOOKMARKS("Bookmarks", "bookmarks");

    private final String label;
    private final String id;

    TabsTocAndBookmarksEpub(String label, String id) {
        this.label = label;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }
    public String getValue() {
        return id;
    }
}