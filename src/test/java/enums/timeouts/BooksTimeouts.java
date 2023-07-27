package enums.timeouts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BooksTimeouts {
    TIMEOUT_BOOK_CHANGES_STATUS(600000),
    SYSTEM_CHANGES_STATUS(5000),
    TIMEOUT_BOOK_PAGE_LOADED(240000);

    private long timeoutMillis;
}
