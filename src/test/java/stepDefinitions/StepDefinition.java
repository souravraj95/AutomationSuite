package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import utils.MainDriver;

public class StepDefinition {
	public Scenario scenario;
	public MainDriver md;
	
	@Before
	public void before(Scenario scenario) {
		this.scenario = scenario;
		scenario.log("Starting the scenario : " + scenario.getName());
	}
	
	@After
	public void after(Scenario scenario) {
		scenario.log("Ending the scenario : " + scenario.getName());
	}
	
	@And("{string}")
	public void commonMethod(String value) {
		md = new MainDriver();
		md.run(value, this.scenario);
	}
}
