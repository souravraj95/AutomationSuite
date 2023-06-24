package scenarioClasses;

import java.io.IOException;

import api.MainAPI;
import io.cucumber.java.Scenario;
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
			
			if(testData.getHeaders().containsKey("Authorization")) {
				testData.getHeaders().put("Authorization", "Bearer " + JWTToken);
			}
			scenario.log("Authorization Bearer "+ JWTToken);
			wrapperObject.setURI(testData.getUrl());
			wrapperObject.setrequestType(testData.getRequestType());
			
			
			
			
			
			System.out.println("URL: " + testData.getUrl());
			System.out.println("Request Type: " + testData.getRequestType());
			System.out.println("Header Map: " + testData.getHeaders());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
