package com.consultancygrid.trz.model;

// Generated May 11, 2015 3:52:18 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * EmployeeSettings generated by hbm2java
 */
@Entity
@Table(name = "EMPLOYEE_SETTINGS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"FK_PERIOD_ID", "FK_EMPLOYEE_ID" }))
public class EmployeeSettings implements java.io.Serializable {

	private String id;
	private Period period;
	private Employee employee;
	private BigDecimal brutoPoShtat;
	private BigDecimal brutoStandart;
	private BigDecimal avans;
	private BigDecimal percentAll;
	private BigDecimal percentGroup;
	private BigDecimal percentPersonal;
	private BigDecimal personOnboardingPercentage;
	private BigDecimal personAllOnboardingPercnt;
	private BigDecimal personGroupOnboardingPercnt;
	private BigDecimal netSalary;

	public EmployeeSettings() {
	}

	public EmployeeSettings(String id, Period period, Employee employee,
			BigDecimal personOnboardingPercentage) {
		this.id = id;
		this.period = period;
		this.employee = employee;
		this.personOnboardingPercentage = personOnboardingPercentage;
	}

	public EmployeeSettings(String id, Period period, Employee employee,
			BigDecimal brutoPoShtat, BigDecimal brutoStandart,
			BigDecimal avans, BigDecimal percentAll, BigDecimal percentGroup,
			BigDecimal percentPersonal, BigDecimal personOnboardingPercentage,
			BigDecimal personAllOnboardingPercnt,
			BigDecimal personGroupOnboardingPercnt, BigDecimal netSalary) {
		this.id = id;
		this.period = period;
		this.employee = employee;
		this.brutoPoShtat = brutoPoShtat;
		this.brutoStandart = brutoStandart;
		this.avans = avans;
		this.percentAll = percentAll;
		this.percentGroup = percentGroup;
		this.percentPersonal = percentPersonal;
		this.personOnboardingPercentage = personOnboardingPercentage;
		this.personAllOnboardingPercnt = personAllOnboardingPercnt;
		this.personGroupOnboardingPercnt = personGroupOnboardingPercnt;
		this.netSalary = netSalary;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 144)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERIOD_ID", nullable = false)
	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_EMPLOYEE_ID", nullable = false)
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Column(name = "BRUTO_PO_SHTAT", precision = 22, scale = 0)
	public BigDecimal getBrutoPoShtat() {
		return this.brutoPoShtat;
	}

	public void setBrutoPoShtat(BigDecimal brutoPoShtat) {
		this.brutoPoShtat = brutoPoShtat;
	}

	@Column(name = "BRUTO_STANDART", precision = 22, scale = 0)
	public BigDecimal getBrutoStandart() {
		return this.brutoStandart;
	}

	public void setBrutoStandart(BigDecimal brutoStandart) {
		this.brutoStandart = brutoStandart;
	}

	@Column(name = "AVANS", precision = 22, scale = 0)
	public BigDecimal getAvans() {
		return this.avans;
	}

	public void setAvans(BigDecimal avans) {
		this.avans = avans;
	}

	@Column(name = "PERCENT_ALL", precision = 22, scale = 0)
	public BigDecimal getPercentAll() {
		return this.percentAll;
	}

	public void setPercentAll(BigDecimal percentAll) {
		this.percentAll = percentAll;
	}

	@Column(name = "PERCENT_GROUP", precision = 22, scale = 0)
	public BigDecimal getPercentGroup() {
		return this.percentGroup;
	}

	public void setPercentGroup(BigDecimal percentGroup) {
		this.percentGroup = percentGroup;
	}

	@Column(name = "PERCENT_PERSONAL", precision = 22, scale = 0)
	public BigDecimal getPercentPersonal() {
		return this.percentPersonal;
	}

	public void setPercentPersonal(BigDecimal percentPersonal) {
		this.percentPersonal = percentPersonal;
	}

	@Column(name = "PERSON_ONBOARDING_PERCENTAGE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getPersonOnboardingPercentage() {
		return this.personOnboardingPercentage;
	}

	public void setPersonOnboardingPercentage(
			BigDecimal personOnboardingPercentage) {
		this.personOnboardingPercentage = personOnboardingPercentage;
	}

	@Column(name = "PERSON_ALL_ONBOARDING_PERCNT", precision = 22, scale = 0)
	public BigDecimal getPersonAllOnboardingPercnt() {
		return this.personAllOnboardingPercnt;
	}

	public void setPersonAllOnboardingPercnt(
			BigDecimal personAllOnboardingPercnt) {
		this.personAllOnboardingPercnt = personAllOnboardingPercnt;
	}

	@Column(name = "PERSON_GROUP_ONBOARDING_PERCNT", precision = 22, scale = 0)
	public BigDecimal getPersonGroupOnboardingPercnt() {
		return this.personGroupOnboardingPercnt;
	}

	public void setPersonGroupOnboardingPercnt(
			BigDecimal personGroupOnboardingPercnt) {
		this.personGroupOnboardingPercnt = personGroupOnboardingPercnt;
	}

	@Column(name = "NET_SALARY", precision = 22, scale = 0)
	public BigDecimal getNetSalary() {
		return this.netSalary;
	}

	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}

}