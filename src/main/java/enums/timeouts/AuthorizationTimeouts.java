package enums.timeouts;

public enum AuthorizationTimeouts {
    LOGIN(5000, 10000),
    LOGOUT(1000, 2000),
    USER_LOGGED_OUT(1500, 3000);

    private final int timeoutMs;
    private final int retryDelayMs;

    AuthorizationTimeouts(int timeoutMs, int retryDelayMs) {
        this.timeoutMs = timeoutMs;
        this.retryDelayMs = retryDelayMs;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public int getRetryDelayMs() {
        return retryDelayMs;
    }
    public int getTimeoutMillis() {
        return timeoutMs;
    }

    public int getPollingMillis() {
        return retryDelayMs;
    }
}