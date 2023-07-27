package framework.configuration;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Environment {
    private Environment() {
    }

    static ISettingsFile getEnvironment() {
        Path resourcePath = Paths.get("config.json");
        return new JsonSettingsFile(resourcePath.toString());
    }
}
