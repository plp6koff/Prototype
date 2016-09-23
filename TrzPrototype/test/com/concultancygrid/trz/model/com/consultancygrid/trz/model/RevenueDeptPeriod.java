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

/**
 * RevenueDeptPeriod generated by hbm2java
 */
@Entity
@Table(name = "REVENUE_DEPT_PERIOD")
public class RevenueDeptPeriod implements java.io.Serializable {

	private String id;
	private Department department;
	private Period period;
	private BigDecimal revenue;

	public RevenueDeptPeriod() {
	}

	public RevenueDeptPeriod(String id, Department department, Period period) {
		this.id = id;
		this.department = department;
		this.period = period;
	}

	public RevenueDeptPeriod(String id, Department department, Period period,
			BigDecimal revenue) {
		this.id = id;
		this.department = department;
		this.period = period;
		this.revenue = revenue;
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
	@JoinColumn(name = "FK_DEPARTMENT_ID", nullable = false)
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERIOD_ID", nullable = false)
	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	@Column(name = "REVENUE", precision = 22, scale = 0)
	public BigDecimal getRevenue() {
		return this.revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

}