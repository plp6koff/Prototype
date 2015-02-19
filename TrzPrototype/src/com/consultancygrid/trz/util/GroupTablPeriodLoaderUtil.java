package com.consultancygrid.trz.util;

import static com.consultancygrid.trz.base.Constants.EMPTY_STRING;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;

public class GroupTablPeriodLoaderUtil {

	
	public Set<UUID>  loadData(Period period , EntityManager em, Vector tableData) throws IOException {
		
		Query q = em.createQuery(" select emplDeptP from EmplDeptPeriod as emplDeptP "
				+ " join emplDeptP.period  as period "
				+ " join emplDeptP.department as department "
				+ " join  emplDeptP.employee as empl"
				+ "  where  period.id = :periodId and department is not null order by department.code ,empl.firstName ");
		
		q.setParameter("periodId", period.getId());
		List<EmplDeptPeriod> emplsDepartments = (List<EmplDeptPeriod>) q.getResultList();
		Query qAllEmployees = em.createQuery("select count(empl.id) from Employee empl  ");
		Set<UUID> employeeSetingsIds = new HashSet<UUID>();
		Long allEmployeesCount = (Long) qAllEmployees.getSingleResult();
	
			for (EmplDeptPeriod emplDeptPeriod  :emplsDepartments) {
				
				final Department department = emplDeptPeriod.getDepartment();
				final BigInteger depBonus = getDepartmentRevenue(em, period, department);
				
				if ((period.getId()  != null) && period.getId().equals(emplDeptPeriod.getPeriod().getId())) {
					
					Employee employee = emplDeptPeriod.getEmployee();
					EmployeeSettings emplSettings = null;
					if (employee.getEmployeeSettingses() != null && employee.getEmployeeSettingses().size() >0) {
						emplSettings = employee.getEmployeeSettingses().iterator().next();
					}
					employeeSetingsIds.add(emplSettings.getId());
					final Double percentPersonal = getEmployeePercentPersonal(emplSettings);
					final BigInteger emplBonus = getEmployeeRevenue(em, period, employee);
					final double bonusAll = add1thRow(tableData, employee, department, allEmployeesCount.intValue(), period, emplBonus,  emplSettings);
					final double bonusGroup = add2thRow(tableData, employee, department, depBonus, emplBonus,  emplSettings);
					final double bonusPersonal = add3thRow(tableData, employee, department, emplBonus, percentPersonal);
					final double totalBonus = bonusAll + bonusGroup + bonusPersonal;
					final double totalPercent = (totalBonus * 100)/emplBonus.intValue();
					
					add4thRow(tableData, employee, department, totalBonus);
					add5thRow(tableData, employee, department, totalPercent);
					addEmptyDelimeterRow(tableData, employee);
					addEmptyDelimeterRow(tableData, employee);
					
					Query sq = em.createQuery(" from EmployeeSalary as sal  where  sal.period.id = :periodId and sal.employee.id = :emplId");
					sq.setParameter("periodId", period.getId());
					sq.setParameter("emplId", employee.getId());
					EmployeeSalary salary = ((List<EmployeeSalary>) sq.getResultList()).get(0);
					if (checkIsPropertyInited(salary.getV06()) 
						|| checkIsPropertyInited(salary.getV07())
						|| checkIsPropertyInited(salary.getV08())) {
						
						salary.setV06(BigDecimal.valueOf(bonusAll).setScale(2, BigDecimal.ROUND_HALF_UP));
						salary.setV07(BigDecimal.valueOf(bonusGroup).setScale(2, BigDecimal.ROUND_HALF_UP));
						salary.setV08(BigDecimal.valueOf(bonusPersonal).setScale(2, BigDecimal.ROUND_HALF_UP));
						salary.setV09(BigDecimal.valueOf(bonusAll + bonusGroup + bonusPersonal).setScale(2, BigDecimal.ROUND_HALF_UP));
						em.merge(salary);
					}
			}	
		}
			return employeeSetingsIds;
	}	
	
	private boolean checkIsPropertyInited(BigDecimal property) {
		return property == null || BigDecimal.ZERO.equals(property);
	}
	
	private boolean checkIsPropertyChanged(BigDecimal property, BigDecimal oldVal) {
		return property.compareTo(oldVal) != 0;
	}
	
	
	public void loadData2(Period period , EntityManager em, Vector tableData) throws IOException {
		
		
		Query q = em.createQuery(" select empl.id from EmplDeptPeriod as emplDeptP "
					+ " join emplDeptP.period  as period "
					+ " join emplDeptP.department as department "
					+ " join  emplDeptP.employee as empl"
					+ "  where  period.id = :periodId and department is not null order by department.code ,empl.firstName ");
		q.setParameter("periodId", period.getId());
		List<UUID> emplsIds = (List<UUID>) q.getResultList();
		
		
		
		Query emplSettingsQ = em.createQuery(" from EmployeeSettings as emplS  where  emplS.period.id = :periodId and emplS.employee.id not in (:revokeList)");
		emplSettingsQ.setParameter("periodId", period.getId());
		emplSettingsQ.setParameter("revokeList", emplsIds);
		List<EmployeeSettings> allSettings = (List<EmployeeSettings>)emplSettingsQ.getResultList();
		for (EmployeeSettings emplSettings : allSettings){
			
				Employee employee = emplSettings.getEmployee();
				
				
				final Double percentPersonal = getEmployeePercentPersonal(emplSettings);
				final BigInteger emplBonus = getEmployeeRevenue(em, period, employee);
				final double bonusAll = add1thRow(tableData, employee, period, emplBonus,  emplSettings);
				final double bonusGroup = add2thRow(tableData, employee, emplBonus,  emplSettings);
				final double bonusPersonal = add3thRow(tableData, employee, emplBonus, percentPersonal);
				final double totalBonus = bonusAll + bonusGroup + bonusPersonal;
				final double totalPercent = (totalBonus * 100)/(emplBonus.intValue() == 0 ? 1 : emplBonus.intValue());
				add4thRow(tableData, employee, null, totalBonus);
				add5thRow(tableData, employee, null, totalPercent);
				addEmptyDelimeterRow(tableData, employee);
				addEmptyDelimeterRow(tableData, employee);
				
				Query sq = em.createQuery(" from EmployeeSalary as sal  where  sal.period.id = :periodId and sal.employee.id = :emplId");
				sq.setParameter("periodId", period.getId());
				sq.setParameter("emplId", employee.getId());
				EmployeeSalary salary = ((List<EmployeeSalary>) sq.getResultList()).get(0);
				salary.setV06(BigDecimal.valueOf(bonusAll).setScale(2, BigDecimal.ROUND_HALF_UP));
				if (checkIsPropertyInited(salary.getV06()) 
						|| checkIsPropertyInited(salary.getV07())
						|| checkIsPropertyInited(salary.getV08())) {
						
						salary.setV06(BigDecimal.valueOf(bonusAll).setScale(2, BigDecimal.ROUND_HALF_UP));
						salary.setV07(BigDecimal.valueOf(bonusGroup).setScale(2, BigDecimal.ROUND_HALF_UP));
						salary.setV08(BigDecimal.valueOf(bonusPersonal).setScale(2, BigDecimal.ROUND_HALF_UP));
						salary.setV09(BigDecimal.valueOf(bonusAll + bonusGroup + bonusPersonal).setScale(2, BigDecimal.ROUND_HALF_UP));
						em.merge(salary);
					}
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
			  return temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
	
	private Double getEmployeePercentGroup(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPercentGroup() != null ? emplSettings.getPercentGroup() : BigDecimal.valueOf(1);
			  return temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
	
	private Double getEmployeePercentPersonal(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPercentPersonal() != null ? emplSettings.getPercentPersonal() : BigDecimal.valueOf(1);
			  return temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
	
	
	public Double getEmployeePercentAllOnboard(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPersonAllOnboardingPercent() != null ? emplSettings.getPersonAllOnboardingPercent() : BigDecimal.valueOf(1);
			  return temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
	
	private Double getEmployeePercentGroupOnboard(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPersonGroupOnboardingPercent() != null ? emplSettings.getPersonGroupOnboardingPercent() : BigDecimal.valueOf(1);
			  return temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
	
	/**
	 * Responsible for population of group settings first row for each Employee
	 * 
	 * @param tableData
	 * @param employee
	 * @param department
	 * @param tableHeaders
	 * @throws IOException
	 */
	private double add1thRow(Vector tableData, Employee employee,
			Department department, int allEmployeesCount, Period period, BigInteger emplDept, EmployeeSettings employeeSettings) throws IOException {

		Vector<Object> oneRow = new Vector<Object>();
		//a0
		String departmetnCode = department != null ? department.getCode() : "N/A";  
		oneRow.add(departmetnCode);
		//b1
		oneRow.add(employee.getFirstName() + " " + employee.getLastName());
		//c2
		oneRow.add(period.getRevenue().toString());// To be extracted from db
		//d3
		oneRow.add(allEmployeesCount);// To be provided as param
		int profitAll = period.getRevenue().intValue() - emplDept.intValue();
		//e4
		oneRow.add(profitAll);
		//f5
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE1));
		//g6
		Double percentAll = getEmployeePercentAll(employeeSettings);
		oneRow.add(percentAll);
		// TODO comment until it is required
		double personalFactor =  getEmployeePercentAllOnboard(employeeSettings);
		double jobDonePercent = 1.0;
		oneRow.add(personalFactor);
		//oneRow.add("1");

		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusAll(profitAll, percentAll, personalFactor, jobDonePercent, allEmployeesCount);
		oneRow.add(result);
		oneRow.add(calculateTotalAll(period.getRevenue(),percentAll));
		tableData.add(oneRow);
		return result;

	}
	
	private Vector add1thRow(Employee employee,
			Department department, int allEmployeesCount, Period period, BigInteger emplDept, EmployeeSettings employeeSettings) throws IOException {

		Vector<Object> oneRow = new Vector<Object>();
		//a0
		String departmetnCode = department != null ? department.getCode() : "N/A";  
		oneRow.add(departmetnCode);
		//b1
		oneRow.add(employee.getFirstName() + " " + employee.getLastName());
		//c2
		oneRow.add(period.getRevenue().toString());// To be extracted from db
		//d3
		oneRow.add(allEmployeesCount);// To be provided as param
		int profitAll = period.getRevenue().intValue() - emplDept.intValue();
		//e4
		oneRow.add(profitAll);
		//f5
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE1));
		//g6
		Double percentAll = getEmployeePercentAll(employeeSettings);
		oneRow.add(percentAll);
		// TODO comment until it is required
		double personalFactor =  getEmployeePercentAllOnboard(employeeSettings);
		double jobDonePercent = 1.0;
		oneRow.add(personalFactor);
		//oneRow.add("1");

		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusAll(profitAll, percentAll, personalFactor, jobDonePercent, allEmployeesCount);
		oneRow.add(result);
		oneRow.add(calculateTotalAll(period.getRevenue(),percentAll));
		return oneRow;

	}
	
	private double add1thRow(Vector tableData, Employee employee, Period period, BigInteger emplDept, EmployeeSettings employeeSettings) throws IOException {

		Vector<Object> oneRow = new Vector<Object>();
		//a0
		String departmetnCode = "N/A";  
		oneRow.add(departmetnCode);
		//b1
		oneRow.add(employee.getFirstName() + " " + employee.getLastName());
		//c2
		oneRow.add(period.getRevenue().toString());// To be extracted from db
		//d3
		oneRow.add(1);// To be provided as param
		int profitAll = period.getRevenue().intValue() - emplDept.intValue();
		//e4
		oneRow.add(profitAll);
		//f5
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE1));
		//g6
		Double percentAll = getEmployeePercentAll(employeeSettings);
		oneRow.add(percentAll);
		// TODO comment until it is required
		double personalFactor =  getEmployeePercentAllOnboard(employeeSettings);
		double jobDonePercent = 1.0d;
		oneRow.add(personalFactor);
		//oneRow.add("1");

		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusAll(profitAll, percentAll, personalFactor, jobDonePercent, 1);
		oneRow.add(result);
		oneRow.add(calculateTotalAll(period.getRevenue(),percentAll));
		tableData.add(oneRow);
		return result;

	}
	
	private Double calculateTotalAll(BigDecimal revenuPeriond, Double percentAll){
		
		return BigDecimal.valueOf((revenuPeriond.doubleValue() * percentAll)/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public Double calculateBonusAll(int profitAll, double percentAll, double personalFactor, double jobDonePercent, int allEployees ){
		
		return  BigDecimal.valueOf(profitAll * (((percentAll * personalFactor) / allEployees)/(100 * jobDonePercent)))
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private double add2thRow(Vector tableData, Employee employee,
			Department department,
			BigInteger revenueDept,
			BigInteger emplDept,
			EmployeeSettings employeeSettings) throws IOException {
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add(department.getCode());
		oneRow.add(EMPTY_STRING);
		oneRow.add(revenueDept.toString());// To be extracted from db
		int allEmployeesDept = department.getEmplDeptPeriods().size();
		oneRow.add(allEmployeesDept);// To be provided as
	    int profitGroup = revenueDept.intValue()-emplDept.intValue();
		oneRow.add(profitGroup);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE2));

		Double percentGroup =  getEmployeePercentGroup(employeeSettings);
		oneRow.add(percentGroup);
		// TODO comment until it is required
		double personalFactor = getEmployeePercentGroupOnboard(employeeSettings);
		oneRow.add(personalFactor);
		//oneRow.add("1");
		double jobDonePercent = 1.0d;
		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusGroup(profitGroup, percentGroup, personalFactor, jobDonePercent, allEmployeesDept); 
		oneRow.add(result);
		oneRow.add(calculateTotalGroup(revenueDept.intValue()-emplDept.intValue(), percentGroup));
		tableData.add(oneRow);
		return result;
	}
	
	private double add2thRow(Vector tableData, 
			Employee employee,
		 BigInteger emplDept, 
			 EmployeeSettings employeeSettings) throws IOException {
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add("N/A");
		oneRow.add(EMPTY_STRING);
		oneRow.add("0");// To be extracted from db
		int allEmployeesDept = 1;
		oneRow.add(allEmployeesDept);// To be provided as
	    int profitGroup = 0;
		oneRow.add(profitGroup);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE2));

		Double percentGroup =  getEmployeePercentGroup(employeeSettings);
		oneRow.add(percentGroup);
		// TODO comment until it is required
		double personalFactor = getEmployeePercentGroupOnboard(employeeSettings);
		oneRow.add(personalFactor);
		//oneRow.add("1");
		double jobDonePercent = 1.0;
		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusGroup(profitGroup, percentGroup, personalFactor, jobDonePercent, allEmployeesDept); 
		oneRow.add(result);
		oneRow.add(calculateTotalGroup(0, percentGroup));
		tableData.add(oneRow);
		return result;
	}
	
	
	public Double calculateTotalGroup(int base, Double percentGroup){
		
		return (base * percentGroup)/100;
	}
	
	public Double calculateTotalGroup(double base, Double percentGroup){
		
		return (base * percentGroup)/100;
	}
	
	
	public Double calculateBonusGroup(double profitGroup, double percentGroup, double personalFactor, double jobDonePercent, int allEployeesDept){
		
		return  BigDecimal.valueOf(profitGroup * ((percentGroup * personalFactor) / 100 )).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private double add3thRow(Vector tableData, Employee employee,
			Department department, BigInteger revenueEmp, Double percentPersonal) throws IOException {
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add(department.getCode());
		oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);// To be extracted from db
		oneRow.add(EMPTY_STRING);// To be provided as param
		final double profitPersonal = revenueEmp.doubleValue();
		oneRow.add(profitPersonal);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE3));

		oneRow.add(percentPersonal);
		// TODO comment until it is required
		final double personalFactor = 1.0;
		final double jobDonePercent = 1.0;
		oneRow.add("1");
		//oneRow.add("1");
		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusPersonal(profitPersonal, percentPersonal.doubleValue(), personalFactor, jobDonePercent); 
		oneRow.add(result);
		oneRow.add(calculateTotalPersonal(1, percentPersonal));
		tableData.add(oneRow);
		return result;
	}
	
	private double add3thRow(Vector tableData, Employee employee, BigInteger revenueEmp, Double percentPersonal) throws IOException {
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add("N/A");
		oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);// To be extracted from db
		oneRow.add(EMPTY_STRING);// To be provided as param
		final double profitPersonal = revenueEmp.doubleValue();
		oneRow.add(profitPersonal);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE3));

		oneRow.add(percentPersonal);
		// TODO comment until it is required
		final double personalFactor = 1.0;
		final double jobDonePercent = 1.0;
		oneRow.add("1");
		//oneRow.add("1");
		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusPersonal(profitPersonal, percentPersonal.doubleValue(), personalFactor, jobDonePercent); 
		oneRow.add(result);
		oneRow.add(calculateTotalPersonal(1, percentPersonal));
		tableData.add(oneRow);
		return result;
	}
	
	public Double calculateTotalPersonal(int base, Double percentPersonal){
		return BigDecimal.valueOf((base * percentPersonal)/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public Double calculateBonusPersonal(double profitPersonal, double percentPersonal, double personalFactor, double jobDonePercent){
		
		return BigDecimal.valueOf(profitPersonal * (( percentPersonal * personalFactor )/(100 * jobDonePercent))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private void add4thRow(Vector tableData, Employee employee,
			Department department, double bonus) throws IOException {
		
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add(department!=null ? department.getCode() : "N/A");
		oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);// To be extracted from db
		oneRow.add(EMPTY_STRING);// To be provided as param
		oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);
		// FIXME how to extract the follow 3 digits
		//oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE));
		// TODO last 2 columns to be token from the Excel
		oneRow.add(bonus);
		oneRow.add(EMPTY_STRING);
		tableData.add(oneRow);
	}
	
	

	private void add5thRow(Vector tableData, Employee employee,
			Department department, double totalPercent) throws IOException {
		
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add(department!=null ? department.getCode() : "N/A");
		oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);// To be extracted from db
		oneRow.add(EMPTY_STRING);// To be provided as param
		oneRow.add(EMPTY_STRING);
		if (!Double.isNaN(totalPercent)) {
			oneRow.add(BigDecimal.valueOf(totalPercent).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		} else {
			oneRow.add("NAN");
		}
		// FIXME how to extract the follow 3 digits
		oneRow.add(EMPTY_STRING);// TO BE detected the formula;
		// TODO comment until it is required
		oneRow.add(EMPTY_STRING);
		//oneRow.add(EMPTY_STRING);
		// TODO last 2 columns to be token from the Excel
		oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);
		tableData.add(oneRow);
	}

	private void addEmptyDelimeterRow(Vector tableData, Employee employee) {

		Vector<Object> oneRow = new Vector<Object>();
		int i = 0;
		// TODO comment until it is required made them 11
		while (i < 10) {
			oneRow.add(EMPTY_STRING);
			i++;
		}
		tableData.add(oneRow);
	}
}
