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
 * EmployeeSalary generated by hbm2java
 */
@Entity
@Table(name = "EMPLOYEE_SALARY", uniqueConstraints = @UniqueConstraint(columnNames = {
		"FK_PERIOD_ID", "FK_EMPLOYEE_ID" }))
public class EmployeeSalary implements java.io.Serializable {

	private UUID id;
	private Employee employee;
	private Period period;
	private BigDecimal v01;
	private BigDecimal v02;
	private BigDecimal v03;
	private BigDecimal v04;
	private BigDecimal v05;
	private BigDecimal v06;
	private BigDecimal v07;
	private BigDecimal v08;
	private BigDecimal v09;
	private BigDecimal v10;
	private BigDecimal v11;
	private BigDecimal v12;
	private BigDecimal v13;
	private BigDecimal v14;
	private BigDecimal v15;
	private BigDecimal v16;
	private BigDecimal v17;
	private BigDecimal v18;
	private BigDecimal v19;
	private BigDecimal v20;
	private BigDecimal v21;
	private BigDecimal v22;
	private BigDecimal v23;
	private String s01;
	
	public EmployeeSalary() {
	}

	public EmployeeSalary(UUID id, Employee employee, Period period) {
		this.id = id;
		this.employee = employee;
		this.period = period;
	}

	public EmployeeSalary(UUID id, Employee employee, Period period,
			BigDecimal v01, BigDecimal v02, BigDecimal v03, BigDecimal v04,
			BigDecimal v05, BigDecimal v06, BigDecimal v07, BigDecimal v08,
			BigDecimal v09, BigDecimal v10, BigDecimal v11, BigDecimal v12,
			BigDecimal v13, BigDecimal v14, BigDecimal v15, BigDecimal v16,
			BigDecimal v17, BigDecimal v18, BigDecimal v19, BigDecimal v20,
			BigDecimal v21, BigDecimal v22, BigDecimal v23,String s01) {
		this.id = id;
		this.employee = employee;
		this.period = period;
		this.v01 = v01;
		this.v02 = v02;
		this.v03 = v03;
		this.v04 = v04;
		this.v05 = v05;
		this.v06 = v06;
		this.v07 = v07;
		this.v08 = v08;
		this.v09 = v09;
		this.v10 = v10;
		this.v11 = v11;
		this.v12 = v12;
		this.v13 = v13;
		this.v14 = v14;
		this.v15 = v15;
		this.v16 = v16;
		this.v17 = v17;
		this.v18 = v18;
		this.v19 = v19;
		this.v20 = v20;
		this.v21 = v21;
		this.v22 = v22;
		this.v23 = v23;
		this.s01 = s01;
	}

	@GenericGenerator(name = "es_trz_uuid_gen", strategy = "com.consultancygrid.trz.model.CustomUUIDGenerator")
	@Id
	@GeneratedValue(generator = "es_trz_uuid_gen")
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

	@Column(name = "V01", precision = 22, scale = 0)
	public BigDecimal getV01() {
		return this.v01;
	}

	public void setV01(BigDecimal v01) {
		this.v01 = v01;
	}

	@Column(name = "V02", precision = 22, scale = 0)
	public BigDecimal getV02() {
		return this.v02;
	}

	public void setV02(BigDecimal v02) {
		this.v02 = v02;
	}

	@Column(name = "V03", precision = 22, scale = 0)
	public BigDecimal getV03() {
		return this.v03;
	}

	public void setV03(BigDecimal v03) {
		this.v03 = v03;
	}

	@Column(name = "V04", precision = 22, scale = 0)
	public BigDecimal getV04() {
		return this.v04;
	}

	public void setV04(BigDecimal v04) {
		this.v04 = v04;
	}

	@Column(name = "V05", precision = 22, scale = 0)
	public BigDecimal getV05() {
		return this.v05;
	}

	public void setV05(BigDecimal v05) {
		this.v05 = v05;
	}

	@Column(name = "V06", precision = 22, scale = 0)
	public BigDecimal getV06() {
		return this.v06;
	}

	public void setV06(BigDecimal v06) {
		this.v06 = v06;
	}

	@Column(name = "V07", precision = 22, scale = 0)
	public BigDecimal getV07() {
		return this.v07;
	}

	public void setV07(BigDecimal v07) {
		this.v07 = v07;
	}

	@Column(name = "V08", precision = 22, scale = 0)
	public BigDecimal getV08() {
		return this.v08;
	}

	public void setV08(BigDecimal v08) {
		this.v08 = v08;
	}

	@Column(name = "V09", precision = 22, scale = 0)
	public BigDecimal getV09() {
		return this.v09;
	}

	public void setV09(BigDecimal v09) {
		this.v09 = v09;
	}

	@Column(name = "V10", precision = 22, scale = 0)
	public BigDecimal getV10() {
		return this.v10;
	}

	public void setV10(BigDecimal v10) {
		this.v10 = v10;
	}

	@Column(name = "V11", precision = 22, scale = 0)
	public BigDecimal getV11() {
		return this.v11;
	}

	public void setV11(BigDecimal v11) {
		this.v11 = v11;
	}

	@Column(name = "V12", precision = 22, scale = 0)
	public BigDecimal getV12() {
		return this.v12;
	}

	public void setV12(BigDecimal v12) {
		this.v12 = v12;
	}

	@Column(name = "V13", precision = 22, scale = 0)
	public BigDecimal getV13() {
		return this.v13;
	}

	public void setV13(BigDecimal v13) {
		this.v13 = v13;
	}

	@Column(name = "V14", precision = 22, scale = 0)
	public BigDecimal getV14() {
		return this.v14;
	}

	public void setV14(BigDecimal v14) {
		this.v14 = v14;
	}

	@Column(name = "V15", precision = 22, scale = 0)
	public BigDecimal getV15() {
		return this.v15;
	}

	public void setV15(BigDecimal v15) {
		this.v15 = v15;
	}

	@Column(name = "V16", precision = 22, scale = 0)
	public BigDecimal getV16() {
		return this.v16;
	}

	public void setV16(BigDecimal v16) {
		this.v16 = v16;
	}

	@Column(name = "V17", precision = 22, scale = 0)
	public BigDecimal getV17() {
		return this.v17;
	}

	public void setV17(BigDecimal v17) {
		this.v17 = v17;
	}

	@Column(name = "V18", precision = 22, scale = 0)
	public BigDecimal getV18() {
		return this.v18;
	}

	public void setV18(BigDecimal v18) {
		this.v18 = v18;
	}

	@Column(name = "V19", precision = 22, scale = 0)
	public BigDecimal getV19() {
		return this.v19;
	}

	public void setV19(BigDecimal v19) {
		this.v19 = v19;
	}

	@Column(name = "V20", precision = 22, scale = 0)
	public BigDecimal getV20() {
		return this.v20;
	}

	public void setV20(BigDecimal v20) {
		this.v20 = v20;
	}

	@Column(name = "V21", precision = 22, scale = 0)
	public BigDecimal getV21() {
		return this.v21;
	}

	public void setV21(BigDecimal v21) {
		this.v21 = v21;
	}

	@Column(name = "V22", precision = 22, scale = 0)
	public BigDecimal getV22() {
		return this.v22;
	}

	public void setV22(BigDecimal v22) {
		this.v22 = v22;
	}

	@Column(name = "V23", precision = 22, scale = 0)
	public BigDecimal getV23() {
		return this.v23;
	}

	public void setV23(BigDecimal v23) {
		this.v23 = v23;
	}
	@Column(name = "S01", length = 500)
	public String getS01() {
		return this.s01;
	}

	public void setS01(String s01) {
		this.s01 = s01;
	}

}
