package com.consultancygrid.trz.util;

import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSV;

/**
 * 
 * @author Anton
 *
 */
public class CSVReaderUtil {

	private static Logger log = Logger.getLogger(CSVReaderUtil.class);
	
	private HashMap<String, String> matchCodeRev = new HashMap<>();

	private HashMap<String, String> matchNameBased = new HashMap<>();


	public void readCSVcarloFibu(File csvFile) {
		
		try {
			
			CSV csv = CSV.separator(',').charset("Cp1251").create();
			CSVReadProcTRZ csvReadProcTRZ = new CSVReadProcTRZ();
			csv.read(csvFile, csvReadProcTRZ);
			log.info("File Parsed!");
			this.matchCodeRev =  csvReadProcTRZ.getMatchCodeRev();
			this.matchNameBased =  csvReadProcTRZ.getMatchNameBased();
		} catch (Exception e){
			log.error(e);
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
