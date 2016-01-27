package com.consultancygrid.trz.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;

/**
 * 
 * @author Anton Palapeshkov
 *
 */
public class EmplsSettingsLoadUtil {

	private final static String SQL1 = " from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.code desc";
	private final static String SQL2 = " from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId and emplSalary.period.code like :periodCode";
	private final static String SQL3 = "SELECT p1_individualna_premia,p2_grupova_premia,p3_obshta_premia FROM RENUMERATIONS where FK_EMPLOYEE_ID = :employeeId AND  FK_PERIOD_ID = :periodId";
	public void load(Employee employee,
					String year,
					EntityManager em, 
					Vector tableData, 
					PersonalCfgEmplsTableModel model) {
	
		Query q = null;
		
		if (year != null && !"".equals(year)) {
			
			q = em.createQuery(SQL2);
			q.setParameter("employeeId", employee.getId());
			q.setParameter("periodCode", "%" + year + "%");
			
		
		} else {
			
			q = em.createQuery(SQL1);
			q.setParameter("employeeId", employee.getId());
			
		}
		
		
		List<EmployeeSalary> emplSals = (List<EmployeeSalary>) q.getResultList();
		model.setEmplSals(emplSals);
		
		for (EmployeeSalary emplSal : emplSals) {
			
			Query q1 = null;
			q1 =  em.createNativeQuery(SQL3);
			q1.setParameter("employeeId", employee.getId().toString());
			q1.setParameter("periodId", emplSal.getPeriod().getId().toString());
			
			List<?> rsltIntrn = q1.getResultList();
			Object[] objcts = (Object[] ) rsltIntrn.get(0);
			System.out.println("A:" + objcts[0]);
			System.out.println("B:" + objcts[1]);
			System.out.println("C:" + objcts[2]);
			
			//TODO select for each 
			EmployeeSettings emplSettings = null;
			Vector<Object> oneRow = new Vector<Object>();
			
			//0
			oneRow.add(emplSal.getPeriod().getCode());
			//1
			oneRow.add(emplSal.getV01());
			//2
			oneRow.add(emplSal.getV02());
			//3
			oneRow.add(emplSal.getV03());
			//4
			oneRow.add(emplSal.getV04());
			//4
			oneRow.add(emplSal.getV05());
			//5
			oneRow.add(objcts[0]);
			oneRow.add(objcts[1]);
			//6
			//7
			oneRow.add(objcts[2]);
			//8
			oneRow.add(emplSal.getV09());
			//9
			oneRow.add(emplSal.getV10());
			//10
			//oneRow.add(emplSal.getV11());
			
			Double d = emplSal.getV03() != null ? emplSal.getV03().doubleValue() : 0.0d; 
			
			Double g = emplSal.getV06() != null ? emplSal.getV06().doubleValue() : 0.0d; 
			Double h = emplSal.getV07() != null ? emplSal.getV07().doubleValue() : 0.0d;
			Double i = emplSal.getV08() != null ? emplSal.getV08().doubleValue() : 0.0d;
			Double j = g + i + h;
			
			Double kMarker = emplSal.getV10() != null ? emplSal.getV10().doubleValue() : 0.0d;
			
			Double tmpSumCI = d + j; 
			Double l = (tmpSumCI < kMarker) ? kMarker : tmpSumCI;
			
			oneRow.add(tmpSumCI);
			//11
			oneRow.add(emplSal.getV12());
			//12
			oneRow.add(emplSal.getV13());
			//13
			oneRow.add(emplSal.getS01());
			//14
			oneRow.add(emplSal.getV14());
			//15
			oneRow.add(emplSal.getV15());
			//19
			oneRow.add(emplSal.getV19());
			//20
			if (emplSal.getV19() != null) {
				BigDecimal temp = emplSal.getV15().subtract(emplSal.getV19());
				if (temp != null) {
					temp = temp.subtract(emplSal.getV02());
				}
				oneRow.add(temp);
			} else {
				oneRow.add(emplSal.getV15());
			}
			//16
			oneRow.add(emplSal.getV16());
			//17
			oneRow.add(emplSal.getV17());
			//18
			oneRow.add(emplSal.getV18());
			oneRow.add(emplSal.getS02());
			tableData.add(oneRow);
			
			
		}
		
	}
}
