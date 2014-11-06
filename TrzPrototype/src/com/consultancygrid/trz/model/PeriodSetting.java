package com.consultancygrid.trz.model;

// Generated Oct 20, 2014 12:53:50 AM by Hibernate Tools 4.0.0

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
 * PeriodSetting generated by hbm2java
 */
@Entity
@Table(name = "PERIOD_SETTING", uniqueConstraints = @UniqueConstraint(columnNames = {
		"FK_TRZ_STATIC_ID", "FK_PERIOD_ID" }))
public class PeriodSetting implements java.io.Serializable {

	private UUID id;
	private TrzStatic trzStatic;
	private Period period;
	private String value;

	public PeriodSetting() {
	}

	public PeriodSetting(UUID id, TrzStatic trzStatic, Period period) {
		this.id = id;
		this.trzStatic = trzStatic;
		this.period = period;
	}

	public PeriodSetting(UUID id, TrzStatic trzStatic, Period period,
			String value) {
		this.id = id;
		this.trzStatic = trzStatic;
		this.period = period;
		this.value = value;
	}

	@GenericGenerator(name = "ps_trz_uuid_gen", strategy = "com.consultancygrid.trz.model.CustomUUIDGenerator")
	@Id
	@GeneratedValue(generator = "ps_trz_uuid_gen")
	@Type(type = "com.consultancygrid.trz.model.UUIDType")
	@Column(name = "ID", unique = true, nullable = false, length = 144)
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
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
