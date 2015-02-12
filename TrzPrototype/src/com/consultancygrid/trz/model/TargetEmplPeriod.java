package com.consultancygrid.trz.model;

// Generated Feb 8, 2015 1:19:13 PM by Hibernate Tools 3.4.0.CR1

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
 * TargetEmplPeriod generated by hbm2java
 */
@Entity
@Table(name = "TARGET_EMPL_PERIOD", uniqueConstraints = @UniqueConstraint(columnNames = {
		"FK_TARGET_PERIOD_ID", "FK_EMPLOYEE_ID" }))
public class TargetEmplPeriod implements java.io.Serializable {

	private UUID id;
	private TargetPeriod targetPeriod;
	private Employee employee;
	private BigDecimal targetValue;

	public TargetEmplPeriod() {
	}

	public TargetEmplPeriod(UUID id, TargetPeriod targetPeriod,
			Employee employee, BigDecimal targetValue) {
		this.id = id;
		this.targetPeriod = targetPeriod;
		this.employee = employee;
		this.targetValue = targetValue;
	}

	@GenericGenerator(name = "trgEmplPer_trz_uuid_gen", strategy = "com.consultancygrid.trz.model.CustomUUIDGenerator")
	@Id
	@GeneratedValue(generator = "trgEmplPer_trz_uuid_gen")
	@Type(type = "com.consultancygrid.trz.model.UUIDType")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TARGET_PERIOD_ID", nullable = false)
	public TargetPeriod getTargetPeriod() {
		return this.targetPeriod;
	}

	public void setTargetPeriod(TargetPeriod targetPeriod) {
		this.targetPeriod = targetPeriod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_EMPLOYEE_ID", nullable = false)
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Column(name = "TARGET_VALUE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getTargetValue() {
		return this.targetValue;
	}

	public void setTargetValue(BigDecimal targetValue) {
		this.targetValue = targetValue;
	}

}
