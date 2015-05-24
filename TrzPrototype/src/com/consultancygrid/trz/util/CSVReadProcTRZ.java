package com.consultancygrid.trz.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import au.com.bytecode.opencsv.CSVReadProc;

public class CSVReadProcTRZ implements CSVReadProc{

	private HashMap<String, String> matchCodeRev = new HashMap<>();
	
	private HashMap<String, String> matchNameBased = new HashMap<>();
	
	
	@Override
	public void procRow(int rowIndex, String... values) {
		
		if (rowIndex > 0 ) {
			List<String> rowValues = Arrays.asList(values);
			System.out.println(rowIndex + ": " + rowValues);
			if ((rowValues.get(0) != null)
					&& (rowValues.get(1) != null && !"".equals(rowValues.get(1).trim()))
					&& (!rowValues.get(1).contains("?="))
					&& (!rowValues.get(0).contains("?="))) {
				
				String tmpName =  rowValues.get(0).replace(" ", "").trim();
				if (tmpName.length() > 0) {
					
					if (tmpName.contains(" ")) {
						matchNameBased.put(tmpName, rowValues.get(1));
					} else {
						matchCodeRev.put(tmpName, rowValues.get(1));
					}
				} else {
					matchCodeRev.put("SDIMITROV", rowValues.get(1));
				}
			}
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
