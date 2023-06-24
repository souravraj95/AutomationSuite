package scenarioClasses;

import api.MainAPI;
import io.cucumber.java.Scenario;
import utils.TestUtility;

public class API {

	/* Class Level Variables Declaration */
	public MainAPI wrapperObject = null;

	/* Constructor of the class here different wrappers instance is made */
	public API() {
		TestUtility.properties = TestUtility
				.readPropFile(TestUtility.searchFileFromDir(TestUtility.filePath, "common.properties"));
		wrapperObject = MainAPI.getInstance();
	}

	/* Method to take care all API scenarios */
	public void apiRun(String featureValue, Scenario scenario) {

	}

}
