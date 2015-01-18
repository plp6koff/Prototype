package com.consultancygrid.trz.model;
// default package
// Generated Jan 17, 2015 6:48:33 PM by Hibernate Tools 3.4.0.CR1

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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * InputData generated by hbm2java
 */
@Entity
@Table(name = "INPUT_DATA")
public class InputData implements java.io.Serializable {

	private UUID id;
	private InputFileType inputFileType;
	private Period period;
	private String matchcode;
	private BigDecimal revenue;

	public InputData() {
	}

	public InputData(UUID id, InputFileType inputFileType, Period period,
			String matchcode, BigDecimal revenue) {
		this.id = id;
		this.inputFileType = inputFileType;
		this.period = period;
		this.matchcode = matchcode;
		this.revenue = revenue;
	}

	
	
	@GenericGenerator(name = "i_trz_uuid_gen", strategy = "com.consultancygrid.trz.model.CustomUUIDGenerator")
	@Id
	@GeneratedValue(generator = "i_trz_uuid_gen")
	@Type(type = "com.consultancygrid.trz.model.UUIDType")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_INPUT_FILE_TYPE_ID", nullable = false)
	public InputFileType getInputFileType() {
		return this.inputFileType;
	}

	public void setInputFileType(InputFileType inputFileType) {
		this.inputFileType = inputFileType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERIOD_ID", nullable = false)
	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	@Column(name = "MATCHCODE", nullable = false, length = 50)
	public String getMatchcode() {
		return this.matchcode;
	}

	public void setMatchcode(String matchcode) {
		this.matchcode = matchcode;
	}

	@Column(name = "REVENUE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getRevenue() {
		return this.revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

}
