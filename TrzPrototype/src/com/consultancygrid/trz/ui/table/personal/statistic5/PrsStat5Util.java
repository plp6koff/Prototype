package com.consultancygrid.trz.ui.table.personal.statistic5;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class PrsStat5Util {

	public static void load(EntityManager em,
			PrsStat5CfgEmplsTableModel model, String periodCode, String matchCode) {

		Vector tableData = new Vector();
		String queryNative = "SELECT  PERIOD_CODE, MATCHCODE,  DEPARTMENT_REVENUE , SP_ORDER, SP_SALES, SP_TOUR, SP_TOUR_SERVE, AVERAGE_SP from EMPLOYEE_REVENUE ";
		String whereClause = " WHERE ";
		String periodPart = " PERIOD_CODE = :pc "; 
		String AND = " AND ";
		String marchcodePart = " MATCHCODE = :mc ";
		String orderExt = " order by PERIOD_CODE,  MATCHCODE asc";
		
		
		Query q = null;
		if ((periodCode == null || periodCode.equals(""))
			 && (matchCode == null || matchCode.equals(""))) {
			queryNative = queryNative + orderExt;
			q = em.createNativeQuery(queryNative);
		} else  {
			queryNative = queryNative + whereClause;
			if (periodCode != null && !periodCode.equals("")) {
		
				queryNative = queryNative + periodPart;
				if (matchCode != null && !matchCode.equals("")) {
					queryNative = queryNative + AND;
					queryNative = queryNative + marchcodePart;
					queryNative = queryNative + orderExt;
					q = em.createNativeQuery(queryNative);
					q.setParameter("pc", periodCode);
					q.setParameter("mc", matchCode);
				} else {
					queryNative = queryNative + orderExt;
					q = em.createNativeQuery(queryNative);
					q.setParameter("pc", periodCode);
				}
			} else if (matchCode != null && !matchCode.equals("")) {
				
				queryNative = queryNative + marchcodePart;
				queryNative = queryNative + orderExt;
				q = em.createNativeQuery(queryNative);
				q.setParameter("mc", matchCode);
			}
		}
		
		List<?> allRows = q.getResultList();

		for (Object o : allRows) {

			Object[] currentRow = (Object[]) o;
			Vector<Object> oneRow = new Vector<Object>();
			System.out.println(currentRow[1]);
			System.out.println(currentRow);
			int i = 0;
			while (i <= 7) {
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
