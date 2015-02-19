package com.consultancygrid.trz.util;

import java.io.File;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSV;

/**
 * 
 * @author Anton
 *
 */
public class CSVReaderUtil {

	private HashMap<String, String> matchCodeRev = new HashMap<>();

	private HashMap<String, String> matchNameBased = new HashMap<>();


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
