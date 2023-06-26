package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import api.MainAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.*;
import java.util.Map;

public class ExcelDataReader {

	public static MainAPI wrapperObject = null;
	static int headerLastRow;

	/* Constructor of the class here different wrappers instance is made */
	public ExcelDataReader() {
		wrapperObject = MainAPI.getInstance();
	}

	public Map<String, TestData> readExcelData() throws IOException {
		Map<String, TestData> testDataMap = new HashMap<String, TestData>();
		Workbook workbook = null;
		Sheet sheet = null;
		try {
			workbook = new XSSFWorkbook(wrapperObject.getExcelPath());
			sheet = workbook.getSheet(wrapperObject.getWorkSheetName());
			DataFormatter dataFormatter = new DataFormatter();

			// Get the header row to determine the column indexes
			Row headerRow = sheet.getRow(0);
			int testCaseNameColIndex = -1;
			int urlColIndex = -1;
			int requestTypeColIndex = -1;
			int contentTypeColIndex = -1;
			int payloadColIndex = -1;
			int authTypeColIndex = -1;
			int authDetailsColIndex = -1;
			int headerKeyColIndex = -1;
			int headerValueColIndex = -1;
			int cookieColIndex = -1;
			int jsonKeysColIndex = -1;
			int sqlQueryColIndex = -1;

			// Find the column indexes based on header cell values
			for (Cell cell : headerRow) {
				String cellValue = dataFormatter.formatCellValue(cell);
				if (cellValue.equalsIgnoreCase("TestCaseName")) {
					testCaseNameColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("url")) {
					urlColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("RequestType")) {
					requestTypeColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("ContentType")) {
					contentTypeColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("Payload")) {
					payloadColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("authType")) {
					authTypeColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("authDetails")) {
					authDetailsColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("HeaderKey")) {
					headerKeyColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("HeaderValue")) {
					headerValueColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("Cookie")) {
					cookieColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("JsonKeys")) {
					jsonKeysColIndex = cell.getColumnIndex();
				} else if (cellValue.equalsIgnoreCase("SqlQuery")) {
					sqlQueryColIndex = cell.getColumnIndex();
				}
			}

			// Iterate over the rows to populate the map
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);

				// Check if the current row is part of a merged region
				if (isMergedRow(sheet, rowIndex, testCaseNameColIndex)) {
					Cell cell = row.getCell(testCaseNameColIndex);
					if (cell != null) {
						String testCaseName = dataFormatter.formatCellValue(cell);
						if (!testCaseName.isEmpty()) {
							// Create a TestData object for the current row
							TestData testData = new TestData();
							testData.setTestCaseName(testCaseName);
							testData.setUrl(dataFormatter.formatCellValue(row.getCell(urlColIndex)));
							testData.setRequestType(dataFormatter.formatCellValue(row.getCell(requestTypeColIndex)));
							testData.setContentType(dataFormatter.formatCellValue(row.getCell(contentTypeColIndex)));
							testData.setPayload(dataFormatter.formatCellValue(row.getCell(payloadColIndex)));
							testData.setAuthType(dataFormatter.formatCellValue(row.getCell(authTypeColIndex)));
							testData.setAuthDetails(dataFormatter.formatCellValue(row.getCell(authDetailsColIndex)));
							testData.setCookie(dataFormatter.formatCellValue(row.getCell(cookieColIndex)));
							testData.setSqlQuery(dataFormatter.formatCellValue(row.getCell(sqlQueryColIndex)));

							/* Storing jsonKeys values into the jsonKeys mao */
							List<String> jsonKeyValue = new ArrayList<String>();
							String value = "";
							for (int headerRowIndex = rowIndex; headerRowIndex <= headerLastRow; headerRowIndex++) {
								headerRow = sheet.getRow(headerRowIndex);
								value = dataFormatter.formatCellValue(headerRow.getCell(jsonKeysColIndex));
								if (!value.isEmpty()) {
									jsonKeyValue.add(value);
								}
									

							}
							testData.setJsonKeys(jsonKeyValue);

							/* Storing Header values into the headers map */
							Map<String, String> headers = new HashMap<String, String>();
							String headerKey = "";
							String headerValue = "";
							for (int headerRowIndex = rowIndex; headerRowIndex <= headerLastRow; headerRowIndex++) {
								headerRow = sheet.getRow(headerRowIndex);
								headerKey = dataFormatter.formatCellValue(headerRow.getCell(headerKeyColIndex));
								headerValue = dataFormatter.formatCellValue(headerRow.getCell(headerValueColIndex));
								headers.put(headerKey, headerValue);
							}
							testData.setHeaders(headers);

							testDataMap.put(testCaseName.toLowerCase(), testData);
						}
					}
				}
				/*Without header and cookies*/
				else {
					Cell cell = row.getCell(testCaseNameColIndex);
					if (cell != null) {
						String testCaseName = dataFormatter.formatCellValue(cell);
						if (!testCaseName.isEmpty()) {
							// Create a TestData object for the current row
							TestData testData = new TestData();
							testData.setTestCaseName(testCaseName);
							testData.setUrl(dataFormatter.formatCellValue(row.getCell(urlColIndex)));
							testData.setRequestType(dataFormatter.formatCellValue(row.getCell(requestTypeColIndex)));
							testData.setContentType(dataFormatter.formatCellValue(row.getCell(contentTypeColIndex)));
							testData.setPayload(dataFormatter.formatCellValue(row.getCell(payloadColIndex)));
							testData.setAuthType(dataFormatter.formatCellValue(row.getCell(authTypeColIndex)));
							testData.setAuthDetails(dataFormatter.formatCellValue(row.getCell(authDetailsColIndex)));
							testData.setCookie(dataFormatter.formatCellValue(row.getCell(cookieColIndex)));
							testData.setSqlQuery(dataFormatter.formatCellValue(row.getCell(sqlQueryColIndex)));
							testDataMap.put(testCaseName.toLowerCase(), testData);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return testDataMap;
	}

	private static boolean isMergedRow(Sheet sheet, int rowIndex, int columnIndex) {
		CellRangeAddress mergedRegion = getMergedRegion(sheet, rowIndex, columnIndex);
		return mergedRegion != null && mergedRegion.getFirstRow() == rowIndex;
	}

	private static CellRangeAddress getMergedRegion(Sheet sheet, int rowIndex, int columnIndex) {
		for (CellRangeAddress mergedRegion : sheet.getMergedRegions()) {
			if (mergedRegion.isInRange(rowIndex, columnIndex)) {
				headerLastRow = mergedRegion.getLastRow();
				return mergedRegion;
			}
		}
		return null;
	}

}
