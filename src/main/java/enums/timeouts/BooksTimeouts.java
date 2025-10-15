package enums.timeouts;

public enum BooksTimeouts {
    TIMEOUT_ONE(3000),
    TIMEOUT_TWO(5000),
    TIMEOUT_THREE(7000),
    TIMEOUT_BOOK_CHANGES_STATUS(10000);
    private final int timeoutMs;

    BooksTimeouts(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public int getTimeoutMillis() {
        return timeoutMs;
    }

}