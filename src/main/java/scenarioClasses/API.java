package scenarioClasses;

import java.io.IOException;

import api.APIHeadersSetup;
import api.DeserialzedJson;
import api.MainAPI;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import utils.ExcelDataReader;
import utils.TestData;
import utils.TestUtility;

public class API {

	/* Class Level Variables Declaration */
	public MainAPI wrapperObject = null;
	public static String JWTToken = null;

	/* Constructor of the class here different wrappers instance is made */
	public API() {
		TestUtility.properties = TestUtility
				.readPropFile(TestUtility.searchFileFromDir(TestUtility.filePath, "common.properties"));
		wrapperObject = MainAPI.getInstance();
	}

	/* Method to take care all API scenarios */
	public void apiRun(String featureValue, Scenario scenario) throws IOException {
		try {
			new TestUtility().setWorkSheetTC(featureValue);
			TestUtility.testDataMap = new ExcelDataReader().readExcelData();
			TestData testData = TestUtility.testDataMap.get(wrapperObject.getTestCasename().toLowerCase());

			/*
			 * Setting up headers for API and storing into a wrapper class so we can use
			 * globally
			 */
			new APIHeadersSetup().headerSetup(testData, scenario);

			/* Taking response of API and setting to thread group */
			Response local_response = wrapperObject.getResponse();
			TestUtility.response.set(local_response);

			/* Deserialization of response Json and taking token and setting to globally */
			if (local_response.getBody().asString().contains("token")
					&& local_response.getBody().asString().contains("status")) {
				new DeserialzedJson().setTokenGlobally(local_response);
			} else {
				new DeserialzedJson().deserialzedJsonStoreData(local_response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
