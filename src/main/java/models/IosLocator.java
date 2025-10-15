package models;

import lombok.Data;
import org.openqa.selenium.By;

@Data
public class IosLocator {

    private By locator;

    // No-arg constructor
    public IosLocator() {
    }

    // Single-arg constructor
    public IosLocator(By locator) {
        this.locator = locator;
    }
}