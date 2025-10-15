package enums;

public enum BookType {
    EBOOK("ebook"),
    AUDIOBOOK("audiobook"),
    PDF("pdf");

    private final String currentBookType;

    BookType(String currentBookType) {
        this.currentBookType = currentBookType;
    }

    public String getCurrentBookType() {
        return currentBookType;
    }
    public String getBookType() {
        return currentBookType;
    }
}