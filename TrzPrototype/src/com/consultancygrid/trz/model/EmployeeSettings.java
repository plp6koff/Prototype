package com.consultancygrid.trz.model;

// Generated Oct 20, 2014 12:53:50 AM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * EmployeeSettings generated by hbm2java
 */
@Entity
@Table(name = "EMPLOYEE_SETTINGS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"FK_PERIOD_ID", "FK_EMPLOYEE_ID" }))
public class EmployeeSettings implements java.io.Serializable {

	private UUID id;
	private Employee employee;
	private Period period;
	private BigDecimal netSalary;
	private BigDecimal brutoPoShtat;
	private BigDecimal brutoStandart;
	private BigDecimal avans;
	private BigDecimal percentAll;
	private BigDecimal percentGroup;
	private BigDecimal percentPersonal;
	private BigDecimal personAllOnboardingPercent;
	private BigDecimal personGroupOnboardingPercent;

	public EmployeeSettings() {
	}

	public EmployeeSettings(UUID id, Employee employee, Period period,
			BigDecimal personAllOnboardingPercent,
			BigDecimal personGroupOnboardingPercent) {
		this.id = id;
		this.employee = employee;
		this.period = period;
		this.personAllOnboardingPercent = personAllOnboardingPercent;
		this.personGroupOnboardingPercent = personGroupOnboardingPercent;
	}

	public EmployeeSettings(UUID id, Employee employee, Period period,
			BigDecimal netSalary, BigDecimal brutoPoShtat, BigDecimal brutoStandart,
			BigDecimal avans, BigDecimal percentAll, BigDecimal percentGroup,
			BigDecimal percentPersonal, BigDecimal personAllOnboardingPercent,
			BigDecimal personGroupOnboardingPercent) {
		this.id = id;
		this.employee = employee;
		this.period = period;
		this.netSalary =netSalary;
		this.brutoPoShtat = brutoPoShtat;
		this.brutoStandart = brutoStandart;
		this.avans = avans;
		this.percentAll = percentAll;
		this.percentGroup = percentGroup;
		this.percentPersonal = percentPersonal;
		this.personAllOnboardingPercent = personAllOnboardingPercent;
		this.personGroupOnboardingPercent = personGroupOnboardingPercent;
	}

	@GenericGenerator(name = "est_trz_uuid_gen", strategy = "com.consultancygrid.trz.model.CustomUUIDGenerator")
	@Id
	@GeneratedValue(generator = "est_trz_uuid_gen")
	@Type(type = "com.consultancygrid.trz.model.UUIDType")
	@Column(name = "ID", unique = true, nullable = false, length = 144)
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_EMPLOYEE_ID", nullable = false)
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERIOD_ID", nullable = false)
	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	@Column(name = "NET_SALARY", precision = 22, scale = 0)
	public BigDecimal getNetSalary() {
		return this.netSalary;
	}

	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
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

	@Column(name = "PERSON_ALL_ONBOARDING_PERCNT", nullable = false, precision = 22, scale = 0)
	public BigDecimal getPersonAllOnboardingPercent() {
		return this.personAllOnboardingPercent;
	}

	public void setPersonAllOnboardingPercent(
			BigDecimal personAllOnboardingPercent) {
		this.personAllOnboardingPercent = personAllOnboardingPercent;
	}
	
	@Column(name = "PERSON_GROUP_ONBOARDING_PERCNT", nullable = false, precision = 22, scale = 0)
	public BigDecimal getPersonGroupOnboardingPercent() {
		return this.personGroupOnboardingPercent;
	}

	public void setPersonGroupOnboardingPercent(
			BigDecimal personGroupOnboardingPercent) {
		this.personGroupOnboardingPercent = personGroupOnboardingPercent;
	}

}
