package com.consultancygrid.trz.ui.table.personal.statistic3;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class PrsStat3Util {

	public static void load(EntityManager em,
			PrsStat3CfgEmplsTableModel model, String periodCode) {

		Vector tableData = new Vector();
		String queryNative = "SELECT  1, PERIOD_CODE , MECHNI_RAZHODI_ZA_TRUD , OBSHTO_NETNA_SUMA, ZAPLATA_PO_BANKA , ZAPLATA_V_BROI, DANUCI_ZA_RABOTODATEL from EMPLOYER_COSTS"
				 + " where 1=1 and  ( PERIOD_CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
				 + " union all "
				 + " SELECT  2, null, sum(MECHNI_RAZHODI_ZA_TRUD) , sum(OBSHTO_NETNA_SUMA), sum(ZAPLATA_PO_BANKA) , sum(ZAPLATA_V_BROI), sum(DANUCI_ZA_RABOTODATEL) from EMPLOYER_COSTS "
				 + " where 1=1 and  ( PERIOD_CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
				 + " group by 2 " 
				 + " order by 1,2,3 asc ";
				
				
		Query q = em.createNativeQuery(queryNative);
		q.setParameter("P_PERIODCODE", periodCode);
		List<?> allRows = q.getResultList();

		for (Object o : allRows) {

			Object[] currentRow = (Object[]) o;
			Vector<Object> oneRow = new Vector<Object>();
			boolean boldRow = (null == currentRow[1] || " ".equals(currentRow[1]) || "TOTAL".equals(currentRow[1]));
			int i = 1;
			while (i <= 6) {
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
			oneRow.add(currentRow[index] == null ? "TOTAL" : currentRow[index]);
		}
	}
}
