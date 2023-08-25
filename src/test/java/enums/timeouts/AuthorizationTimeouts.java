package enums.timeouts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthorizationTimeouts {
    DEBUG_MENU_IS_OPENED(40000, 1000),
    USER_LOGGED_OUT(120000, 1000);

    private long timeoutMillis;
    private long pollingMillis;
}