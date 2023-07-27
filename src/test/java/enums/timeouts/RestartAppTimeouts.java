package enums.timeouts;

import lombok.AllArgsConstructor;

import java.time.Duration;

@AllArgsConstructor
public enum RestartAppTimeouts {
    TIMEOUT_RESTART_APPLICATION(Duration.ofMillis(2000));

    private final Duration timeoutRestart;

    public Duration getTimeoutRestart(){
        return timeoutRestart;
    }
}
