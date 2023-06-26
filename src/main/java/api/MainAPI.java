package api;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.TestUtility;

public class MainAPI {

	/* Class level Variables Declared */
	private static MainAPI wrapperObj;
	private String excelPath;
	private String workSheet;
	private String testCase;
	private String uri;
	private String requestType;
	private String payload;
	private String payloadFile;
	private boolean setFileUploadFlag = false;
	private String token;
	private String contentTypeString;
	private String userName;
	private String password;
	private String authType;
	private boolean headerStatus = false;
	private Response local_response;
	private Map<String, String> headersMap = new TreeMap<String, String>();
	private Map<String, String> formPayloadMap = new TreeMap<String, String>();
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

	/* Setter Methods */
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

	public void setContentTypeString(String contentTypeString) {
		this.contentTypeString = contentTypeString;
	}

	/* Getter Methods */
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

	public String getContentTypeString(String contentTypeString) {
		return contentTypeString;
	}

	public void setFilePath(String payloadFile) {
		this.payloadFile = payloadFile;

	}

	public String getFilePath(String payloadFile) {
		return payloadFile;

	}

	public void setFileUpload(boolean setFileUploadFlag) {
		this.setFileUploadFlag = setFileUploadFlag;

	}

	public boolean isFileUpload() {
		return setFileUploadFlag;

	}

	public void setFormPayLoadMap(Map<String, String> formPayloadMap) {
		this.formPayloadMap = formPayloadMap;
	}

	public Map<String, String> getFormPayLoadMap() {
		return formPayloadMap;
	}

	public void setUsername(String userName) {
		this.userName = userName;

	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return userName;

	}

	public String getPassword() {
		return password;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getAuthType(String authType) {
		return authType;
	}

	public void setHeaderRequired(boolean headerStatus) {
		this.headerStatus = headerStatus;
	}

	public void setHeaderMap(Map<String, String> headersMap) {
		this.headersMap = headersMap;
	}

	public boolean isHeaderRequired() {
		return headerStatus;
	}

	public Map<String, String> getHeaderMap(Map<String, String> headersMap) {
		return headersMap;
	}

	/* @return the TestUtility.response */
	public Response getResponse() {
		try {
			if (paylaod()) {
				/* Check file upload */
				String body = getPayload();

				/* POST API Handles returns response */
				if (requestType.equalsIgnoreCase("post")) {
					if (contentTypeString != null && contentTypeString.equals("application/x-www-form-urlencoded")) {
						if (formPayloadMap == null) {
							local_response = getAuthSpec().relaxedHTTPSValidation().contentType(contentTypeString)
									.urlEncodingEnabled(false).post(getURI());
						} else {
							local_response = getAuthSpec().relaxedHTTPSValidation().contentType(contentTypeString)
									.formParams(formPayloadMap).urlEncodingEnabled(false).post(getURI());
						}
					} else if (contentTypeString == null) {
						if (body == null) {
							TestUtility.logger.info(getURI());
							local_response = getAuthSpec().relaxedHTTPSValidation().urlEncodingEnabled(false)
									.post(getURI());
						} else {
							local_response = getAuthSpec().relaxedHTTPSValidation().urlEncodingEnabled(false)
									.contentType("application/json").body(body).post(getURI());
						}
					} else if (contentTypeString != null && body == null) {
						local_response = getAuthSpec().relaxedHTTPSValidation().contentType(contentTypeString)
								.urlEncodingEnabled(false).post(getURI());
					} else {
						local_response = getAuthSpec().relaxedHTTPSValidation().body(body).urlEncodingEnabled(false)
								.post(getURI());
					}
					TestUtility.response.set(local_response);
				}
				/* PUT API Handles returns response */
				/* Delete API Handles returns response */
				/* Patch API Handles returns response */
			}
			/* GET API Handles returns response */
			else if (requestType.equalsIgnoreCase("get")) {
				local_response = getAuthSpec().relaxedHTTPSValidation().urlEncodingEnabled(false).get(uri);
				TestUtility.response.set(local_response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return local_response;
	}

	/* @return authentication specification for this request */

	private RequestSpecification getAuthSpec() {
		if (isHeaderRequired() && isFileUpload()) {
			return getReqSpec().headers(headersMap);
		} else if (isHeaderRequired() && !isFileUpload()) {
			return getReqSpec().headers(headersMap);
		} else if (!isHeaderRequired() && isFileUpload()) {
			return getReqSpec().multiPart("file", new File(getFilePath(payloadFile)), "application/json");
		} else {
			return getReqSpec();
		}
	}

	/* @return request specification for this request */
	private RequestSpecification getReqSpec() {
		if (authType == null || authType.equalsIgnoreCase("NA") || authType.isEmpty()) {
			return given().log().all().auth().none();
		} else if (authType.equalsIgnoreCase("Basic")) {
			return given().log().all().auth().basic(getUsername(), getPassword());
		} else if (authType.equalsIgnoreCase("preemptive")) {
			return given().log().all().auth().preemptive().basic(getUsername(), getPassword());
		} else if (authType.equalsIgnoreCase("digest")) {
			return given().log().all().auth().digest(getUsername(), getPassword());
		} else if (authType.equalsIgnoreCase("oauth2") || authType.equalsIgnoreCase("bearer")) {
			return given().log().all().auth().oauth2(getToken());
		} else {
			return null;
		}
	}

	/* @return boolean to check if payload is needed or not for this request */
	private boolean paylaod() {
		if (requestType.equalsIgnoreCase("GET")) {
			return false;
		}
		return true;
	}
}
