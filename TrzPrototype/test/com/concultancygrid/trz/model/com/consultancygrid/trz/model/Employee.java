package com.consultancygrid.trz.model;

// Generated May 11, 2015 3:52:18 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Employee generated by hbm2java
 */
@Entity
@Table(name = "EMPLOYEE")
public class Employee implements java.io.Serializable {

	private String id;
	private String firstName;
	private String lastName;
	private String isActive;
	private String matchcode;
	private Set<TargetEmplPeriod> targetEmplPeriods = new HashSet<TargetEmplPeriod>(
			0);
	private Set<RevenueEmplPeriod> revenueEmplPeriods = new HashSet<RevenueEmplPeriod>(
			0);
	private Set<EmplDeptPeriod> emplDeptPeriods = new HashSet<EmplDeptPeriod>(0);
	private Set<EmployeeSalary> employeeSalaries = new HashSet<EmployeeSalary>(
			0);
	private Set<EmployeeSettings> employeeSettingses = new HashSet<EmployeeSettings>(
			0);

	public Employee() {
	}

	public Employee(String id, String firstName, String lastName,
			String isActive) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isActive = isActive;
	}

	public Employee(String id, String firstName, String lastName,
			String isActive, String matchcode,
			Set<TargetEmplPeriod> targetEmplPeriods,
			Set<RevenueEmplPeriod> revenueEmplPeriods,
			Set<EmplDeptPeriod> emplDeptPeriods,
			Set<EmployeeSalary> employeeSalaries,
			Set<EmployeeSettings> employeeSettingses) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isActive = isActive;
		this.matchcode = matchcode;
		this.targetEmplPeriods = targetEmplPeriods;
		this.revenueEmplPeriods = revenueEmplPeriods;
		this.emplDeptPeriods = emplDeptPeriods;
		this.employeeSalaries = employeeSalaries;
		this.employeeSettingses = employeeSettingses;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 144)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "FIRST_NAME", nullable = false, length = 400)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME", nullable = false, length = 400)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "IS_ACTIVE", nullable = false, length = 4)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "MATCHCODE", length = 50)
	public String getMatchcode() {
		return this.matchcode;
	}

	public void setMatchcode(String matchcode) {
		this.matchcode = matchcode;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<TargetEmplPeriod> getTargetEmplPeriods() {
		return this.targetEmplPeriods;
	}

	public void setTargetEmplPeriods(Set<TargetEmplPeriod> targetEmplPeriods) {
		this.targetEmplPeriods = targetEmplPeriods;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<RevenueEmplPeriod> getRevenueEmplPeriods() {
		return this.revenueEmplPeriods;
	}

	public void setRevenueEmplPeriods(Set<RevenueEmplPeriod> revenueEmplPeriods) {
		this.revenueEmplPeriods = revenueEmplPeriods;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<EmplDeptPeriod> getEmplDeptPeriods() {
		return this.emplDeptPeriods;
	}

	public void setEmplDeptPeriods(Set<EmplDeptPeriod> emplDeptPeriods) {
		this.emplDeptPeriods = emplDeptPeriods;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<EmployeeSalary> getEmployeeSalaries() {
		return this.employeeSalaries;
	}

	public void setEmployeeSalaries(Set<EmployeeSalary> employeeSalaries) {
		this.employeeSalaries = employeeSalaries;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<EmployeeSettings> getEmployeeSettingses() {
		return this.employeeSettingses;
	}

	public void setEmployeeSettingses(Set<EmployeeSettings> employeeSettingses) {
		this.employeeSettingses = employeeSettingses;
	}

}