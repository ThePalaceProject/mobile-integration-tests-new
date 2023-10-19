package enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BookType {
    EBOOK("ebook"),
    AUDIOBOOK("audiobook"),
    PDF("pdf");

    private String currentBookType;

    public String getBookType() {
        return currentBookType;
    }
}