package com.consultancygrid.trz.util;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;

public class CSVReaderUtil {

	private HashMap<String, String> matchCodeRev = new HashMap<>();

	private HashMap<String, String> matchNameBased = new HashMap<>();

	// private String[][] csvHolder;

	// public void readCSVcarloFibu(String csvFilePath) {
	//
	// this.csvHolder=new String[10000][80];
	//
	// CSV csv = CSV.separator(';').charset("Cp1251").create();
	//
	// final String[][] k = new String[10000][100];
	//
	// csv.read(csvFilePath, new CSVReadProc() {
	//
	// @Override
	// public void procRow(int rowIndex, String... values) {
	// System.out.println(rowIndex + ": " + Arrays.asList(values));
	// k[rowIndex]=values;
	//
	// }
	// });
	//
	// this.csvHolder = k;
	// System.out.println("File parsed.");
	//
	// }
	//

	public void readCSVcarloFibu(File csvFile) {

		CSV csv = CSV.separator(';').charset("Cp1251").create();
		CSVReadProcTRZ csvReadProcTRZ = new CSVReadProcTRZ();
		csv.read(csvFile, csvReadProcTRZ);
		System.out.println("File parsed.");
		this.matchCodeRev =  csvReadProcTRZ.getMatchCodeRev();
		this.matchNameBased =  csvReadProcTRZ.getMatchNameBased();
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
