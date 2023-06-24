package api;

import io.cucumber.java.Scenario;

public class MainAPI {

	/* Class level Variables Declared */
	private static MainAPI wrapperObj;
	private String excelPath;
	private String workSheet;
	private String testCase;
	private String uri;
	private String requestType;
	private String payload;
	private String token;
	private Scenario currentScenario = null;

	public static MainAPI getInstance() {
		if (wrapperObj == null) {
			/* Synchronized block to remove overhead */
			synchronized (MainAPI.class) {
				if (wrapperObj == null) {
					/* If instance is null, initialize */
					wrapperObj = new MainAPI();
				}
			}
		}
		return wrapperObj;
	}

	/*Setter Methods*/
	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public void setWorkSheetName(String workSheet) {
		this.workSheet = workSheet;
	}

	public void setTestCasename(String testCase) {
		this.testCase = testCase;
	}
	
	public void setURI(String uri) {
		this.uri = uri;
	}
	
	public void setCurrentScenario(Scenario currentScenario) {
		this.currentScenario = currentScenario;
	}
	
	public void setrequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	/*Getter Methods*/
	public String getExcelPath() {
		return excelPath;
	}

	public String getWorkSheetName() {
		return workSheet;
	}
	
	public String getTestCasename() {
		return testCase;
	}
	
	public String getURI() {
		return uri;
	}
	
	public Scenario getCurrentScenario() {
		return currentScenario;
	}
	
	public String getrequestType() {
		return requestType;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public String getToken() {
		return token;
	}

}
