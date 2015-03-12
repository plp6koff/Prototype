package com.consultancygrid.trz.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import au.com.bytecode.opencsv.CSVReadProc;

public class CSVReadProcTRZ implements CSVReadProc{

	private HashMap<String, String> matchCodeRev = new HashMap<>();
	
	private HashMap<String, String> matchNameBased = new HashMap<>();
	
	private static int indexMatchCodeIndex = 0;
	private static int indexRevIndex = 3;
	
	@Override
	public void procRow(int rowIndex, String... values) {
		
		if (rowIndex > 0 ) {
			List<String> rowValues = Arrays.asList(values);
			System.out.println(rowIndex + ": " + rowValues);
			if ((rowValues.get(0) != null && !"".equals(rowValues.get(0).replace(" ", "").trim()))
					&& (rowValues.get(3) != null && !"".equals(rowValues.get(3).trim()))
					&& (!rowValues.get(3).contains("?="))
					&& (!rowValues.get(0).contains("?="))) {
				//System.out.println(rowValues.get(0));
				String tmpName =  rowValues.get(0).trim();
				//System.out.println(rowValues.get(0));
				//if (tmpName.length() > 0) {
					
					if (tmpName.contains(" ")) {
						matchNameBased.put(tmpName, rowValues.get(3));
					} else {
						matchCodeRev.put(tmpName, rowValues.get(3));
					}
			//	}
			}
	} else {
//			List<String> rowValues = Arrays.asList(values);
//			if ((rowValues.get(3) != null && !"".equals(rowValues.get(3).trim()))) {
//				if (rowValues.contains("Matchcode")) {
//					indexMatchCodeIndex = 0;
//				}
//			}
//			
//			if ((rowValues.get(0) != null && !"".equals(rowValues.get(0).trim()))) {
//				if (rowValues.contains("Matchcode")) {
//					indexMatchCodeIndex = 3;
//				}
//			}
//					
		}
	}

	public HashMap<String, String> getMatchCodeRev() {
		return matchCodeRev;
	}

	public void setMatchCodeRev(HashMap<String, String> matchCodeRev) {
		this.matchCodeRev = matchCodeRev;
	}

	public HashMap<String, String> getMatchNameBased() {
		return matchNameBased;
	}

	public void setMatchNameBased(HashMap<String, String> matchNameBased) {
		this.matchNameBased = matchNameBased;
	}
	
	

}
