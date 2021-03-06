package com.consultancygrid.trz.ui.table.personal.statistic1;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class PrsStat1Util {

	public static void load(EntityManager em,
			PrsStat1CfgEmplsTableModel model,
			String periodCode) {

		Vector tableData = new Vector();
		String queryNative = "SELECT 1,MATCHCODE,  CODE,  V01,  V02,  V03,  V04,  V05,  V06,  V07,  V08,  V09,  V10,  V11,  V12,  V13,  S01,  V14,  V15,  V16,  V17,  V18 "
				+ " FROM EMPLOYEE_STATS  "
				+ " where 1=1 and ( MATCHCODE=:P_MATCHCODE or :P_MATCHCODE is null)  and ( CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
				+ " union all "
				+ " SELECT 2,null,  null,  sum(V01),sum( V02),sum(  V03),sum(  V04),sum(  V05),sum(  V06),sum(  V07),sum(  V08),sum(  V09),sum(  V10),sum(  V11),sum(  V12),sum(  V13), null,sum(  V14),sum(  V15),sum(  V16),sum(  V17),sum(  V18)  "
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
			System.out.println(currentRow[1]);
			System.out.println(currentRow);
			int i = 1;
			
			boolean boldRow = (null == currentRow[2] || " ".equals(currentRow[2]) || "N/A".equals(currentRow[2]));
			
			while (i <= 21) {
				setData(oneRow, currentRow, i, boldRow);
				i ++;
			}
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
}
