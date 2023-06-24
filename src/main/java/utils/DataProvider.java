package utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataProvider {

	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFCell cell;
	
	public void readFromExcel(String excelPath, String sheetName, String testCase) {
		try {
			workbook = new XSSFWorkbook(excelPath);
			sheet = workbook.getSheet(sheetName);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
