package utils;

import api.MainAPI;
import io.cucumber.java.Scenario;
import scenarioClasses.API;

public class MainDriver {

	public MainAPI wrapperObject = null;

	/* Constructor of the class here different wrappers instance is made */

	public MainDriver() {
		TestUtility.properties = TestUtility
				.readPropFile(TestUtility.searchFileFromDir(TestUtility.filePath, "common.properties"));
		wrapperObject = MainAPI.getInstance();
	}

	/* run() to take care of all features */
	public void run(String featureValue, Scenario scenario) {
		if (featureValue.startsWith("API")) {
			new API().apiRun(featureValue, scenario);
		}
	}
}
