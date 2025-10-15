package models;

import lombok.Data;
import org.openqa.selenium.By;

@Data
public class AndroidLocator {

    private By locator;

    // No-arg constructor
    public AndroidLocator() {
    }

    // Single-arg constructor
    public AndroidLocator(By locator) {
        this.locator = locator;
    }
}