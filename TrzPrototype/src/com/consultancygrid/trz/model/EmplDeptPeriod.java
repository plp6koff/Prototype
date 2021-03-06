package com.consultancygrid.trz.model;

// Generated Oct 20, 2014 12:53:50 AM by Hibernate Tools 4.0.0

import java.util.UUID;

import javax.persistence.CascadeType;
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
 * EmplDeptPeriod generated by hbm2java
 */
@Entity
@Table(name = "EMPL_DEPT_PERIOD", uniqueConstraints = @UniqueConstraint(columnNames = {
		"FK_EMPLOYEE_ID", "FK_DEPARTMENT_ID", "FK_PERIOD_ID" }))
public class EmplDeptPeriod implements java.io.Serializable {

	private UUID id;
	private Department department;
	private Employee employee;
	private Period period;

	public EmplDeptPeriod() {
	}

	public EmplDeptPeriod(UUID id, Department department, Employee employee,
			Period period) {
		this.id = id;
		this.department = department;
		this.employee = employee;
		this.period = period;
	}

	@GenericGenerator(name = "edp_trz_uuid_gen", strategy = "com.consultancygrid.trz.model.CustomUUIDGenerator")
	@Id
	@GeneratedValue(generator = "edp_trz_uuid_gen")
	@Type(type = "com.consultancygrid.trz.model.UUIDType")
	@Column(name = "ID", unique = true, nullable = false, length = 144)
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "FK_DEPARTMENT_ID", nullable = false)
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional= true)
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

}
