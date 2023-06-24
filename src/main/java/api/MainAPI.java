package api;

public class MainAPI {

	/* Class level Variables Declared */
	private static MainAPI wrapperObj;
	private String excelPath;
	private String workSheet;
	private String testCase;

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

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public void setWorkSheetName(String workSheet) {
		this.workSheet = workSheet;
	}

	public void setTestCasename(String testCase) {
		this.testCase = testCase;
	}

}
