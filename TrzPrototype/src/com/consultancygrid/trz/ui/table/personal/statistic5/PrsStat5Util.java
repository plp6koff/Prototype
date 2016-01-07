package com.consultancygrid.trz.ui.table.personal.statistic5;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class PrsStat5Util {

	public static void load(EntityManager em,
			PrsStat5CfgEmplsTableModel model, String periodCode, String matchCode) {

		
		
		Vector tableData = new Vector();
		String queryNative = "SELECT  1, PERIOD_CODE, MATCHCODE,  DEPARTMENT_REVENUE , SP_ORDER, SP_SALES, SP_TOUR, SP_TOUR_SERVE, AVERAGE_SP from EMPLOYEE_REVENUE "
		 + " where 1=1 and ( MATCHCODE=:P_MATCHCODE or :P_MATCHCODE is null)  and ( PERIOD_CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
		 + " union all "
		 + " SELECT  2, null, MATCHCODE,  sum(DEPARTMENT_REVENUE) , sum(SP_ORDER), sum(SP_SALES), sum(SP_TOUR), sum(SP_TOUR_SERVE), sum(AVERAGE_SP) from EMPLOYEE_REVENUE "
		 + " where 1=1 and ( MATCHCODE=:P_MATCHCODE or :P_MATCHCODE is null)  and ( PERIOD_CODE like :P_PERIODCODE||'%' or :P_PERIODCODE is null) "
		 + " group by 1,MATCHCODE " 
		 + " order by 1,2,3 asc ";
		
		
		Query q = em.createNativeQuery(queryNative);
		q.setParameter("P_PERIODCODE", periodCode);
		q.setParameter("P_MATCHCODE", matchCode);
		List<?> allRows = q.getResultList();
		
		Set<String> revokeSet = new HashSet<String>();

		for (Object o : allRows) {

			Object[] currentRow = (Object[]) o;
			Vector<Object> oneRow = new Vector<Object>();
			String a = currentRow[1] != null ? (String)currentRow[1] : "";
			String b = currentRow[2] != null ? (String)currentRow[2] : "";
			
			if (!revokeSet.contains(a+b)) {
				
				boolean boldRow = (null == currentRow[1] || " ".equals(currentRow[1]) || "N/A".equals(currentRow[1]));
				int i = 1;
				while (i <= 8) {
					setData(oneRow, currentRow, i, boldRow);
					i ++;
				}
				revokeSet.add(a+b);
				tableData.add(oneRow);
			}
			

		}
		model.setData(tableData);

	}
	
	private static void setData(Vector<Object> oneRow, Object[] currentRow, int index, boolean bold) {
		if (bold) {
			oneRow.add("<html><b>" + (currentRow[index] == null ? "TOTAL:" : currentRow[index]) + "</b>");
		} else {
			oneRow.add(currentRow[index] == null ? "N/A" : currentRow[index]);
		}
	}
}
