package api;

import java.util.Map;
import java.util.TreeMap;

import io.cucumber.java.Scenario;
import utils.TestData;
import utils.TestUtility;

public class APIHeadersSetup {
	/* Class Level Variables Declaration */
	public MainAPI wrapperObject = null;
	public static String JWTToken = null;
	static String fPath = TestUtility.filePath;
	static Map<String, String> formPayLoadMap = new TreeMap<String, String>();
	String payload = null;

	/* Constructor of the class here different wrappers instance is made */
	public APIHeadersSetup() {
		wrapperObject = MainAPI.getInstance();
	}

	/* Method to set all headers value */
	/*
	 * Setting up headers for API and storing into a wrapper class so we can use
	 * globally
	 */
	public void headerSetup(TestData testData, Scenario scenario) {
		try {
			/* JWT Setup */
			if (testData.getHeaders() == null) {
				wrapperObject.setURI(testData.getUrl());
				wrapperObject.setrequestType(testData.getRequestType());
			} else {
				if (testData.getHeaders().containsKey("Authorization")) {
					testData.getHeaders().put("Authorization", "Bearer " + JWTToken);
				}
				scenario.log("Authorization Bearer " + JWTToken);

				/*
				 * Set other parameters like uri,requestType, payload, content - type,
				 * form-urlencoded, cookies, authentication
				 */
				wrapperObject.setURI(testData.getUrl());
				wrapperObject.setrequestType(testData.getRequestType());

				/* Content type stored */
				if (testData.getContentType() == null) {
					if (testData.getHeaders().containsKey("ContentType")) {
						wrapperObject.setContentTypeString(testData.getHeaders().get("ContentType"));
					} else if (testData.getHeaders().containsKey("Content-Type")) {
						wrapperObject.setContentTypeString(testData.getHeaders().get("Content-Type"));
					}
				} else {
					wrapperObject.setContentTypeString(testData.getContentType());
				}

				/* Payload stored */
				payload = testData.getPayload();
				if (!payload.isEmpty()) {
					if ((payload.endsWith(".txt")) || (payload.endsWith(".csv")) || (payload.endsWith(".json"))
							|| (payload.endsWith(".xml")) || (payload.endsWith(".xlsx"))) {
						payload = fPath + payload;
						wrapperObject.setFilePath(payload);
						wrapperObject.setFileUpload(true);
					} else {
						wrapperObject.setPayload(payload);
					}
				}

				/* form - url encoded stored */
				if (!(null == testData.getHeaders().get("Content-Type"))) {
					if (testData.getHeaders().get("Content-Type").toLowerCase().contains("form-urlencoded")
							|| testData.getContentType().toLowerCase().contains("form-urlencoded")) {
						String[] pairs = payload.split(",");
						for (String pair : pairs) {
							pair = pair.replaceAll("\u00A0", "");
							String[] formValues = pair.split(":");
							formPayLoadMap.put(TestUtility.findAndReplace(formValues[0].trim()),
									TestUtility.findAndReplace(formValues[1].trim()));
						}

						wrapperObject.setFormPayLoadMap(formPayLoadMap);
						wrapperObject.setContentTypeString("application/x-www-form-urlencoded");
					}
				}

				/* Authentication details - authType and authDetails stored */
				if (!testData.getAuthDetails().isEmpty()) {
					if (testData.getAuthDetails().equalsIgnoreCase("Basic")
							|| testData.getAuthDetails().equalsIgnoreCase("Preemptive")
							|| testData.getAuthDetails().equalsIgnoreCase("Digest")) {
						wrapperObject.setAuthType(testData.getAuthDetails());

						if (testData.getAuthDetails() != null) {
							String[] str = testData.getAuthDetails().split(",");
							String userName = str[0];
							String password = str[1];
							wrapperObject.setUsername(userName);
							wrapperObject.setPassword(password);
						}
					} else {
						wrapperObject.setAuthType(testData.getAuthDetails());
						if (testData.getAuthDetails() != null) {
							String token = testData.getAuthDetails();
							wrapperObject.setToken(token);
						} else {
							wrapperObject.setToken("");
						}
					}
				}

				/* Cookies and Header Map stored */

				if (!testData.getCookie().isEmpty()) {
					testData.getHeaders().put("cookie", testData.getCookie());
				}

				if (!(testData.getHeaders().isEmpty())) {
					wrapperObject.setHeaderRequired(true);
					wrapperObject.setHeaderMap(testData.getHeaders());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
