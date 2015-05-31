package com.consultancygrid.trz.ui.table.personal.statistic3;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class PrsStat3Util {

	public static void load(EntityManager em,
			PrsStat3CfgEmplsTableModel model, String periodCode) {

		Vector tableData = new Vector();
							
		String queryNative = "select  PERIOD_CODE , MECHNI_RAZHODI_ZA_TRUD , OBSHTO_NETNA_SUMA, ZAPLATA_PO_BANKA , ZAPLATA_V_BROI, DANUCI_ZA_RABOTODATEL from EMPLOYER_COSTS";
		String whereClause = " WHERE ";
		String periodPart = " PERIOD_CODE = :pc "; 
		
		
		Query q = null;
		if ((periodCode == null || periodCode.equals(""))) {
			
			q = em.createNativeQuery(queryNative);
		} else  {
			queryNative = queryNative + whereClause;
			queryNative = queryNative + periodPart;
			q = em.createNativeQuery(queryNative);
			q.setParameter("pc", periodCode);
		}
		
		List<?> allRows = q.getResultList();

		for (Object o : allRows) {

			Object[] currentRow = (Object[]) o;
			Vector<Object> oneRow = new Vector<Object>();
			int i = 0;
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
