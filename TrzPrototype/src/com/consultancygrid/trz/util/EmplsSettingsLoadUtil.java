package com.consultancygrid.trz.util;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;

public class EmplsSettingsLoadUtil {

	
	
	public void load(Employee employee, EntityManager em, Vector tableData, PersonalCfgEmplsTableModel model) {
	
		Query q = em.createQuery(" from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.code desc");
		q.setParameter("employeeId", employee.getId());
		List<EmployeeSalary> emplSals = (List<EmployeeSalary>) q.getResultList();
		model.setEmplSals(emplSals);
		
		for (EmployeeSalary emplSal : emplSals) {
			
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
			oneRow.add(emplSal.getV06());
			//6
			oneRow.add(emplSal.getV07());
			//7
			oneRow.add(emplSal.getV08());
			//8
			oneRow.add(emplSal.getV09());
			//9
			oneRow.add(emplSal.getV10());
			//10
			oneRow.add(emplSal.getV11());
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
			//16
			oneRow.add(emplSal.getV16());
			//17
			oneRow.add(emplSal.getV17());
			//18
			oneRow.add(emplSal.getV18());
			tableData.add(oneRow);
			
		}
		
	}
}
