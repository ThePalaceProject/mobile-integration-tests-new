package enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BookType {
    EBOOK("ebook"),
    AUDIOBOOK("audiobook"),
    PDF("pdf");

    private String bookType;

    public String getBookType() {
        return bookType;
    }
}