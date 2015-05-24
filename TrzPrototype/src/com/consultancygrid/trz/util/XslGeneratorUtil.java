package com.consultancygrid.trz.util;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class XslGeneratorUtil {

	public static void main(String[]args){
    try{
    String filename="C:/NewExcelFile.xls" ;
    HSSFWorkbook workbook=new HSSFWorkbook();
    HSSFSheet sheet =  workbook.createSheet("FirstSheet");  

    HSSFRow rowhead=   sheet.createRow((short)0);
    rowhead.createCell(0).setCellValue("No.");
    rowhead.createCell(1).setCellValue("Name");
    rowhead.createCell(2).setCellValue("Address");
    rowhead.createCell(3).setCellValue("Email");

    HSSFRow row=   sheet.createRow((short)1);
    row.createCell(0).setCellValue("1");
    row.createCell(1).setCellValue("Sankumarsingh");
    row.createCell(2).setCellValue("India");
    row.createCell(3).setCellValue("sankumarsingh@gmail.com");

    FileOutputStream fileOut =  new FileOutputStream(filename);
    workbook.write(fileOut);
    fileOut.close();
    System.out.println("Your excel file has been generated!");

    } catch ( Exception ex ) {
        System.out.println(ex);

    }
    }
	private static String[] full31Header = new String[]{"a","b"};
	public static void generateFull31() {
		
	}
	
	
	
	public static void generateCore(Vector<String> headers, Vector data, String filePath, String name) {	
		  
		try{
			
			// Setup input and output files
			File xsltfile = new File(filePath, name);
			
			HSSFWorkbook workbook=new HSSFWorkbook();
			  HSSFSheet sheet =  workbook.createSheet("xsltTest.xsl");  
			    
			    
			    HSSFRow rowhead=   sheet.createRow((short)0);
			    for (int i = 0 ; i < headers.size(); i ++) {
			    	rowhead.createCell(i)
			    		.setCellValue(
			    				headers.get(i).replace("<html>","")
			    				.replace("<br>"," ")
			    				.replace("<br/>"," "));
			    }
			    
			    int rowCount = 1;
			    for (Object rawRow : data) {
			    	
			    	Vector<?> rowData =  (Vector) rawRow;
			    	 
			    	HSSFRow row =   sheet.createRow((short)rowCount);
			    	for (int i = 0 ; i < rowData.size(); i ++) {
			    		
			    		 if (rowData.get(i) instanceof BigDecimal) {
			    			 row.createCell(i).setCellValue(((BigDecimal) rowData.get(i)).toString());
			    			 
							
						} else {
							 row.createCell(i).setCellValue((String)rowData.get(i));
						}
			    		 
			    	}
			    	rowCount++;
			    }
			    
			    FileOutputStream fileOut =  new FileOutputStream(xsltfile);
			    workbook.write(fileOut);
			    fileOut.close();
			    System.out.println("Your excel file has been generated!");
		   } catch ( Exception ex ) {
		        System.out.println(ex);

		   }	    
	}
}