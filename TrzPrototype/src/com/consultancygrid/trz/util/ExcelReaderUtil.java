package com.consultancygrid.trz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author tchomakov
 */
public class ExcelReaderUtil {
	
	/**
	 * @param filePath
	 * @return sheet
	 */
	public HSSFSheet loadExcelSheetFromFile(String filePath) {
		
		FileInputStream file = null;
		
		try {
			file = new FileInputStream(new File(filePath));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			return sheet;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	
	public static HSSFSheet loadExcelSheetFromFile(File fileRaw) {
		
		
		FileInputStream file = null;
		
		try {
			file = new FileInputStream(fileRaw);
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			return sheet;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @param filePath
	 * @param sheetName - name of the sheet, we`ve been looking for
	 * @return sheet - loaded sheet
	 */
	public HSSFSheet loadExcelSheetFromFile(String filePath, String sheetName) {
		
		FileInputStream file = null;
		
		try {
			file = new FileInputStream(new File(filePath));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			
			return sheet;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @param sheet - single sheet from xls file
	 * @return map 
	 * 		-KEY: name of the testcase
	 * 		-VALUE: list of username, password, expectedTestResult and expectedErrorMessage
	 */
	public Map<String, List<String>> getTestCases(HSSFSheet sheet) {
		
		Map<String, List<String>> testCases = new HashMap<String, List<String>>();
		int lastRowIndex = sheet.getLastRowNum();
		int numberOfColumns = sheet.getRow(0).getLastCellNum();
		
		for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			String testCase = row.getCell(0).toString();
			List<String> testCaseValues = new ArrayList<String>();
			
			for (int columnIndex = 1; columnIndex < numberOfColumns; columnIndex++) {
				if (row.getCell(columnIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					testCaseValues.add(row.getCell(columnIndex).toString());
				} else {
					testCaseValues.add(null);
				}
			}
			
			testCases.put(testCase, testCaseValues);
		}
				
		return testCases;
	}
	
}
