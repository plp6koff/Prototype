package com.consultancygrid.trz.util;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.Period;

public class EmployeeSalaryUtil {

	public static  void createSalary(EntityManager em, Period period, Employee empl,  Double vauchers) {

		Query q = em
				.createQuery(" from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.dateEnd desc");
		q.setParameter("employeeId", empl.getId());
		List<EmployeeSalary> emplSals = (List<EmployeeSalary>) q.getResultList();

		EmployeeSalary salary = new EmployeeSalary();
		if (emplSals != null && !emplSals.isEmpty()) {

			EmployeeSalary lastSalary = emplSals.get(0);
			salary.setV01(lastSalary.getV01());
			salary.setV02(lastSalary.getV02());
			salary.setV03(lastSalary.getV03());
			salary.setV04(lastSalary.getV04());
			salary.setV05(lastSalary.getV05());
			salary.setV06(lastSalary.getV06());
			salary.setV07(lastSalary.getV07());
			salary.setV08(lastSalary.getV08());
			salary.setV09(lastSalary.getV09());
			salary.setV10(lastSalary.getV10());
			salary.setV11(lastSalary.getV11());
			salary.setV12(lastSalary.getV12());
			salary.setV13(lastSalary.getV13());
			salary.setV14(lastSalary.getV14());
			salary.setV15(lastSalary.getV15());
			salary.setV16(lastSalary.getV16());
			salary.setV17(lastSalary.getV17());
			salary.setV18(lastSalary.getV18());
			salary.setV19(lastSalary.getV19());
		} 
		salary.setV14(BigDecimal.valueOf(vauchers));
		salary.setEmployee(empl);
		salary.setPeriod(period);
		em.persist(salary);
		em.flush();
	}
}
