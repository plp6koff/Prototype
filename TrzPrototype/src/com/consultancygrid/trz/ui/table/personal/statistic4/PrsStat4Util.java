package com.consultancygrid.trz.ui.table.personal.statistic4;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class PrsStat4Util {

	public static void load(EntityManager em,
			PrsStat4CfgEmplsTableModel model, String periodCode, String matchCode) {

		Vector tableData = new Vector();
		String queryNative = "SELECT  1, PERIOD_CODE, MATCHCODE,  SP , INDIVIDUALNA_PREMIA, GRUPOVA_PREMIA, OBSHTA_PREMIA, OBSHTO_PREMII, OTNOSHENIE_OBSHTO_PREMIA_SP,TBD from EMPLOYEE_BONUSES "
				+ " where 1=1 and ( MATCHCODE=:P_MATCHCODE or :P_MATCHCODE is null)  and ( PERIOD_CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
				+ " union all "
				+ " SELECT  2, null , MATCHCODE,  sum(SP) , sum(INDIVIDUALNA_PREMIA), sum(GRUPOVA_PREMIA), sum(OBSHTA_PREMIA), sum(OBSHTO_PREMII), sum(OTNOSHENIE_OBSHTO_PREMIA_SP),sum(TBD) from EMPLOYEE_BONUSES "
				+ " where 1=1 and ( MATCHCODE=:P_MATCHCODE or :P_MATCHCODE is null)  and ( PERIOD_CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
				+ " group by 2,MATCHCODE " 
				+ " order by 1,2,3 asc ";
		
		
		Query q = em.createNativeQuery(queryNative);
		q.setParameter("P_PERIODCODE", periodCode);
		q.setParameter("P_MATCHCODE", matchCode);
		List<?> allRows = q.getResultList();
		

		for (Object o : allRows) {

			Object[] currentRow = (Object[]) o;
			Vector<Object> oneRow = new Vector<Object>();
			boolean boldRow = (null == currentRow[1] || " ".equals(currentRow[1]) || "TOTAL".equals(currentRow[1]));
			int i = 1;
			while (i <= 9) {
				setData(oneRow, currentRow, i, boldRow);
				i ++;
			}
			tableData.add(oneRow);

		}
		model.setData(tableData);

	}
	
	private static void setData(Vector<Object> oneRow, Object[] currentRow, int index, boolean bold) {
		if (bold) {
			oneRow.add("<html><b>" + (currentRow[index] == null ? "TOTAL" : currentRow[index]) + "</b>");
		} else {
			oneRow.add(currentRow[index] == null ? "N/A" : currentRow[index]);
		}
	}
}
