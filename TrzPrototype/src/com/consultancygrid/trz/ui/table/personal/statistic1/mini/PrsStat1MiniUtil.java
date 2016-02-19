package com.consultancygrid.trz.ui.table.personal.statistic1.mini;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class PrsStat1MiniUtil {

	public static void load(EntityManager em,
			PrsStat1MiniCfgEmplsTableModel model,
			String periodCode) {

		Vector tableData = new Vector();
		String queryNative = "SELECT 1,MATCHCODE, V18, V15,  V02,   V19, ZAPLATA_V_BROI, V14, CODE "
				+ " FROM EMPLOYEE_STATS  "
				+ " where 1=1 and ( MATCHCODE=:P_MATCHCODE or :P_MATCHCODE is null)  and ( CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
				+ " union all "
				+ " SELECT 2,null, sum(V18),sum(V15),sum(V02),sum(V19),sum(ZAPLATA_V_BROI),sum(V14) , null"
				+ " FROM EMPLOYEE_STATS  "
				+ " where 1=1  and ( MATCHCODE=:P_MATCHCODE or :P_MATCHCODE is null)  and ( CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
				+ " group by 2 " + " order by 1,2,3 asc ";
		Query q = em.createNativeQuery(queryNative);
		q.setParameter("P_PERIODCODE", periodCode);
		q.setParameter("P_MATCHCODE", "");
		List<?> allRows = q.getResultList();

		for (Object o : allRows) {

			Object[] currentRow = (Object[]) o;
			// TODO select for each
			Vector<Object> oneRow = new Vector<Object>();
			int i = 1;
			boolean boldRow = (null == currentRow[8] || " ".equals(currentRow[8]) || "N/A".equals(currentRow[8]));
			
			while (i <= 4) {
				setData(oneRow, currentRow, i, boldRow);
				i ++;
			}
			
			Object zpltBroi = (currentRow[6] == null ? "0.0" : currentRow[6]);
			BigDecimal zpltBroiBD = null;
			if (zpltBroi instanceof BigDecimal) {
				zpltBroiBD = (BigDecimal) zpltBroi;
			} else if (zpltBroi instanceof String) {
				zpltBroiBD = BigDecimal.valueOf(Double.valueOf((String)zpltBroi));
			}
			setBoldData(oneRow, boldRow, zpltBroiBD.toString());
			
			Object avans = (currentRow[5] == null ? "0.0" : currentRow[5]);
			BigDecimal avansBD = null;
			if (avans instanceof BigDecimal) {
				avansBD = (BigDecimal) avans;
			} else if (avans instanceof String) {
				avansBD = BigDecimal.valueOf(Double.valueOf((String)avans));
			}
			setBoldData(oneRow, boldRow, avansBD.toString());
			
			Object vaucher = (currentRow[7] == null ? "0.0" : currentRow[7]);
			BigDecimal vaucherBD = null;
			if (vaucher instanceof BigDecimal) {
				vaucherBD = (BigDecimal) vaucher;
			} else if (vaucher instanceof String) {
				vaucherBD = BigDecimal.valueOf(Double.valueOf((String)vaucher));
			}
			
			setBoldData(oneRow, boldRow, zpltBroiBD.subtract(avansBD).subtract(vaucherBD));
			for (int j = 0 ; j < oneRow.size(); j ++) {
				System.err.print(oneRow.get(j));
				System.out.print(":");
			}
			System.out.println("---------------------------------");
			tableData.add(oneRow);

		}
		model.setData(tableData);

	}
	
	private static void setData(Vector<Object> oneRow, Object[] currentRow, int index, boolean bold) {
		if (bold) {
			oneRow.add("<html><b>" + (currentRow[index] == null ? "N/A" : currentRow[index]) + "</b>");
		} else {
			oneRow.add(currentRow[index] == null ? "N/A" : currentRow[index]);
		}
	}
	
	
	private static void setBoldData(Vector<Object> oneRow,  boolean bold, Object value) {
		if (bold) {
			oneRow.add("<html><b>" + (value == null ? "N/A" : value) + "</b>");
		} else {
			oneRow.add(value == null ? "N/A" : value);
		}
	}
}
