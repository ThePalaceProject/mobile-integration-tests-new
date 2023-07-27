package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.openqa.selenium.By;

@Data
@AllArgsConstructor
@Builder
public class IosLocator {

    private By locator;
}
