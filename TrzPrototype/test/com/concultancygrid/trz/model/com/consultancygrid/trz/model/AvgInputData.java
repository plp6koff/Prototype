package com.consultancygrid.trz.model;

// Generated May 11, 2015 3:52:18 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AvgInputData generated by hbm2java
 */
@Entity
@Table(name = "AVG_INPUT_DATA")
public class AvgInputData implements java.io.Serializable {

	private AvgInputDataId id;

	public AvgInputData() {
	}

	public AvgInputData(AvgInputDataId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "matchcode", column = @Column(name = "MATCHCODE", nullable = false, length = 40)),
			@AttributeOverride(name = "revenue", column = @Column(name = "REVENUE", precision = 22, scale = 0)),
			@AttributeOverride(name = "periodCode", column = @Column(name = "PERIOD_CODE", nullable = false, length = 28)) })
	public AvgInputDataId getId() {
		return this.id;
	}

	public void setId(AvgInputDataId id) {
		this.id = id;
	}

}
