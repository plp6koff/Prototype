package com.consultancygrid.trz.util;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.TrzStatic;

public class EmployeeSalaryUtil {

	public static  void createSalary(EntityManager em, Period period, Employee empl,  Double vauchers,
			TrzStatic DOD,
			TrzStatic OSIGUROVKI_RABOTODATEL, 
			TrzStatic OSIGUROVKI_SLUJITEL,
			TrzStatic CACHE_TAX, 
			Double dodValue,
			Double oRabotodatelValue,
			Double oSlujitelValue,
			Double cacheTaxValue) {

		Query q = em
				.createQuery(" from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.code desc");
		q.setParameter("employeeId", empl.getId());
		List<EmployeeSalary> emplSals = (List<EmployeeSalary>) q.getResultList();

		
		String SQL3 = "SELECT p1_individualna_premia,p2_grupova_premia,p3_obshta_premia FROM RENUMERATIONS where FK_EMPLOYEE_ID = :employeeId AND  FK_PERIOD_ID = :periodId";
		
		EmployeeSalary salary = new EmployeeSalary();
		if (emplSals != null && !emplSals.isEmpty()) {

			Query q1 = null;
			q1 =  em.createNativeQuery(SQL3);
			q1.setParameter("employeeId",empl.getId().toString());
			q1.setParameter("periodId", period.getId().toString());
			
			EmployeeSalary lastSalary = emplSals.get(0);
			//to be copied
			salary.setV01(lastSalary.getV01());
			//to be copied
			salary.setV03(lastSalary.getV03());
			//View check
			List<?> rsltIntrn = q1.getResultList();
			if (rsltIntrn != null && !rsltIntrn.isEmpty()) {
				Object[] objcts = (Object[] ) rsltIntrn.get(0);
				salary.setV06((BigDecimal)objcts[0]);
				salary.setV07((BigDecimal)objcts[1]);
				salary.setV08((BigDecimal)objcts[2]);
			} else {
				salary.setV06(lastSalary.getV06());
				salary.setV07(lastSalary.getV07());
				salary.setV08(lastSalary.getV08());
			}
		
			
			//to be copied
			salary.setV10(lastSalary.getV10());
			//to be copied
			salary.setV13(lastSalary.getV13());
			salary.setS01(lastSalary.getS01());
			salary.setV14(lastSalary.getV14());
			//to be copied
			salary.setV20(BigDecimal.ZERO);
			salary.setV21(lastSalary.getV15());
			salary.setV14(BigDecimal.valueOf(vauchers));
			
			
			EmployeeSallaryCalculateUtil
			.calcSettings(
					extractValue(lastSalary.getV01()), 
					extractValue(lastSalary.getV03()), 
					extractValue(lastSalary.getV10()), 
					extractValue(lastSalary.getV13()), 
					lastSalary.getS01(), 
					salary, 
					DOD, OSIGUROVKI_RABOTODATEL, OSIGUROVKI_SLUJITEL, CACHE_TAX,
					dodValue, oRabotodatelValue, oSlujitelValue, cacheTaxValue,
					lastSalary.getV19(),
					null);
		} 
		
		salary.setEmployee(empl);
		salary.setPeriod(period);
		em.persist(salary);
		em.flush();
	}
	
	private static Double extractValue(BigDecimal param) {
		return param != null ? param.doubleValue() : BigDecimal.ZERO.doubleValue();
	}
}
