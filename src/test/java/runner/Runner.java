package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(glue = "stepDefinitions", plugin = {}, features = { "src/test/java/features/APITest.feature" })
public class Runner {

}
