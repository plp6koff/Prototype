package com.consultancygrid.trz.model;

// Generated May 11, 2015 3:52:18 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * PeriodSetting generated by hbm2java
 */
@Entity
@Table(name = "PERIOD_SETTING", uniqueConstraints = @UniqueConstraint(columnNames = {
		"FK_TRZ_STATIC_ID", "FK_PERIOD_ID" }))
public class PeriodSetting implements java.io.Serializable {

	private String id;
	private TrzStatic trzStatic;
	private Period period;
	private String value;

	public PeriodSetting() {
	}

	public PeriodSetting(String id, TrzStatic trzStatic, Period period) {
		this.id = id;
		this.trzStatic = trzStatic;
		this.period = period;
	}

	public PeriodSetting(String id, TrzStatic trzStatic, Period period,
			String value) {
		this.id = id;
		this.trzStatic = trzStatic;
		this.period = period;
		this.value = value;
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
	@JoinColumn(name = "FK_TRZ_STATIC_ID", nullable = false)
	public TrzStatic getTrzStatic() {
		return this.trzStatic;
	}

	public void setTrzStatic(TrzStatic trzStatic) {
		this.trzStatic = trzStatic;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERIOD_ID", nullable = false)
	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	@Column(name = "VALUE", length = 400)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
