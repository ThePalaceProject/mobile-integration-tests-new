package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/features"},
        glue = {
                "hooks",
                "stepdefinitions"
        },
        tags = "@tier2"
)

public class TestRunner {
        @BeforeClass
        public static void setup() {

        }
}
