package enums.timeouts;

import java.time.Duration;

public enum RestartAppTimeouts {
    TIMEOUT_RESTART_APPLICATION(Duration.ofMillis(2000));

    private final Duration timeoutRestart;

    RestartAppTimeouts(Duration timeoutRestart) {
        this.timeoutRestart = timeoutRestart;
    }

    public Duration getTimeoutRestart() {
        return timeoutRestart;
    }
}