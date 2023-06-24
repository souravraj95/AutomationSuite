package utils;

import java.util.List;
import java.util.Map;

public class TestData {
	private String testCaseName;
	private String url;
	private String requestType;
	private String contentType;
	private String payload;
	private String authType;
	private String authDetails;
	private Map<String, String> headers;
	private String cookie;
	private List<String> jsonKeys;
	private String sqlQuery;

	// Getters and Setters

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getAuthDetails() {
		return authDetails;
	}

	public void setAuthDetails(String authDetails) {
		this.authDetails = authDetails;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public List<String> getJsonKeys() {
		return jsonKeys;
	}

	public void setJsonKeys(List<String> jsonKeys) {
		this.jsonKeys = jsonKeys;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	@Override
	public String toString() {
		return "TestData{" + "testCaseName='" + testCaseName + '\'' + ", url='" + url + '\'' + ", requestType='"
				+ requestType + '\'' + ", contentType='" + contentType + '\'' + ", payload='" + payload + '\''
				+ ", authType='" + authType + '\'' + ", authDetails='" + authDetails + '\'' + ", headers=" + headers
				+ ", cookie='" + cookie + '\'' + ", jsonKeys='" + jsonKeys + '\'' + ", sqlQuery='" + sqlQuery + '\''
				+ '}';
	}
}
