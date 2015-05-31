package com.consultancygrid.trz.ui.table.personal.statistic1.mini;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class PrsStat1MiniUtil {

	public static void load(EntityManager em,
			PrsStat1MiniCfgEmplsTableModel model) {

		Vector tableData = new Vector();
		String queryNative = "SELECT 1,MATCHCODE,  V18,  V15"
				+ " FROM EMPLOYEE_STATS  "
				+ " where 1=1 and ( MATCHCODE=:P_MATCHCODE or :P_MATCHCODE is null)  and ( CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
				+ " union all "
				+ " SELECT 2,MATCHCODE,   sum(  V18), sum(  V15) "
				+ " FROM EMPLOYEE_STATS  "
				+ " where 1=1  and ( MATCHCODE=:P_MATCHCODE or :P_MATCHCODE is null)  and ( CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
				+ " group by 2,MATCHCODE " + " order by 1,2,3 asc ";
		Query q = em.createNativeQuery(queryNative);
		q.setParameter("P_PERIODCODE", "2015");
		q.setParameter("P_MATCHCODE", "");
		List<?> allRows = q.getResultList();

		for (Object o : allRows) {

			Object[] currentRow = (Object[]) o;
			// TODO select for each
			Vector<Object> oneRow = new Vector<Object>();
			System.out.println(currentRow[1]);
			System.out.println(currentRow);
			int i = 1;
			while (i <= 5) {
				setData(oneRow, currentRow, i);
				i ++;
			}
			tableData.add(oneRow);

		}
		model.setData(tableData);

	}
	
	private static void setData(Vector<Object> oneRow, Object[] currentRow, int index) {
		oneRow.add(currentRow[index] == null ? "N/A" : currentRow[index]);
	}
}
