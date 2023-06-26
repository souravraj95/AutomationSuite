package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import api.MainAPI;
import io.restassured.response.Response;

public class TestUtility {

	/* Logger Declaration */
	public static final Logger logger = LogManager.getLogger(TestUtility.class);

	/* Class | Global Variable Declaration */

	public static Properties properties = null;
	public static String filePath = new File("").getAbsolutePath();
	public MainAPI wrapperObject = null;
	public static Map<String, TestData> testDataMap = null;
	public static ThreadLocal<Response> response = new ThreadLocal<Response>() {
		@Override
		protected Response initialValue() {
			return null;
		}
	};

	/* Constructor of the class here different wrappers instance is made */
	public TestUtility() {
		wrapperObject = MainAPI.getInstance();
	}

	/* Method to read property file */
	public static Properties readPropFile(String filePath) {
		try {
			properties = new Properties();
			FileInputStream fs = new FileInputStream(filePath);
			properties.load(fs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	/* Method to search file from directory */
	public static String searchFileFromDir(String dir, String fileName) {
		File[] files = new File(dir).listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				String loc = searchFileFromDir(f.getPath(), fileName);
				if (loc != null)
					return loc;
			} else if (f.getName().equals(fileName))
				return f.getPath();
		}
		return null;
	}

	/* Method to set Work sheet name and test case name */
	public void setWorkSheetTC(String featureValue) {
		int startIndex = featureValue.indexOf("{");
		int endIndex = featureValue.indexOf("}");
		String args = featureValue.substring(startIndex + 1, endIndex);
		String arr[] = args.split(",");
		String excelPath = arr[0];
		excelPath = TestUtility.filePath + excelPath;
		String sheetName = arr[1].trim();
		String testCase = arr[2].trim();
		wrapperObject.setExcelPath(excelPath);
		wrapperObject.setWorkSheetName(sheetName);
		wrapperObject.setTestCasename(testCase);
	}

	/* Method to find and replace values */
	public static String findAndReplace(String value) {
		int start, end = 0;
		try {
			if (value.substring(end).contains("TextMap[")) {
				start = value.indexOf("TextMap[", end);
				end = value.indexOf("]", start);
				String subString = value.substring(start, end);
				String attribute = "TextMap[" + subString + "]";
				value = value.replace(attribute, subString);
				value = findAndReplace(value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
