package com.consultancygrid.trz.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;

public class EmplsSettingsLoadUtil {

	
	
	public void load(Employee employee, EntityManager em, Vector tableData, PersonalCfgEmplsTableModel model) {
	
		Query q = em.createQuery(" from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.dateEnd desc");
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
			//5
			oneRow.add(emplSal.getV05());
			//6
			oneRow.add(emplSal.getV06());
			//7
			oneRow.add(emplSal.getV07());
			//8
			oneRow.add(emplSal.getV08());
			//9
			oneRow.add(emplSal.getV09());
			//10
			oneRow.add(emplSal.getV10());
			//11
			oneRow.add(emplSal.getV11());
			//12
			oneRow.add(emplSal.getV12());
			//13
			oneRow.add(emplSal.getS01());
			//14
			oneRow.add(emplSal.getV13());
			//15
			oneRow.add(emplSal.getV14());
			//16
			oneRow.add(emplSal.getV15());
			//17
			oneRow.add(emplSal.getV16());
			//18
			oneRow.add(emplSal.getV17());
			//19
			oneRow.add(emplSal.getV18());
			tableData.add(oneRow);
			
		}
		
	}
	
	private BigInteger getEmployeeRevenue (EntityManager em, Period period , Employee employee) {
		Query tempPerEmpl = em.createQuery(" from RevenueEmplPeriod as revEmplPer  where  revEmplPer.period.id = :periodId and revEmplPer.employee.id = :emplId ");
		tempPerEmpl.setParameter("periodId", period.getId());
		tempPerEmpl.setParameter("emplId", employee.getId());
		List<RevenueEmplPeriod> revenueEmplPeriods = (List<RevenueEmplPeriod>)tempPerEmpl.getResultList();
		if (revenueEmplPeriods != null && revenueEmplPeriods.size() > 0 ) {
			return  revenueEmplPeriods.get(0).getRevenue().toBigInteger();
		} else {
			return  BigInteger.ZERO;
		}
			
	}
	
	private BigInteger getDepartmentRevenue (EntityManager em, Period period , Department department) {
		
		Query tempPer = em.createQuery(" from RevenueDeptPeriod as revDeptPer  where  revDeptPer.period.id = :periodId and revDeptPer.department.id = :deptId ");
		tempPer.setParameter("periodId", period.getId());
		tempPer.setParameter("deptId", department.getId());
		
		RevenueDeptPeriod revenueDeptPeriod = (RevenueDeptPeriod)tempPer.getSingleResult();
		return  revenueDeptPeriod.getRevenue() != null   ? revenueDeptPeriod.getRevenue().toBigInteger() : BigInteger.ZERO;
	}
	
	
	private Double getEmployeePercentAll(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPercentAll() != null ? emplSettings.getPercentAll() : BigDecimal.valueOf(1);
			  return temp.doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).doubleValue();
		}
	}
	
	private Double getEmployeePercentGroup(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPercentGroup() != null ? emplSettings.getPercentGroup() : BigDecimal.valueOf(1);
			  return temp.doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).doubleValue();
		}
	}
	
	private Double getEmployeePercentPersonal(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPercentPersonal() != null ? emplSettings.getPercentPersonal() : BigDecimal.valueOf(1);
			  return temp.doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).doubleValue();
		}
	}
	
	
	
	private Double calculateTotalAll(BigDecimal revenuPeriond, Double percentAll){
		
		return (revenuPeriond.doubleValue() * percentAll)/100;
	}
	
	private Double calculateBonusAll(int profitAll, double percentAll, double personalFactor, double jobDonePercent, int allEployees ){
		
		return  profitAll * (((percentAll * personalFactor) / allEployees)/(100 * jobDonePercent));
	}

	
	
	private Double calculateTotalGroup(int base, Double percentGroup){
		
		return (base * percentGroup)/100;
	}
	
	private Double calculateBonusGroup(int profitGroup, double percentGroup, double personalFactor, double jobDonePercent, int allEployeesDept){
		
		return  profitGroup * (((personalFactor * personalFactor) / allEployeesDept)/(100 * jobDonePercent));
	}

	
	
	private Double calculateTotalPersonal(int base, Double percentPersonal){
		
		return (base * percentPersonal)/100;
	}

	private Double calculateBonusPersonal(double profitPersonal, double percentPersonal, double personalFactor, double jobDonePercent){
		
		return  profitPersonal * (( percentPersonal * personalFactor )/(100 * jobDonePercent));
	}

}
