package com.consultancygrid.trz.model;

// Generated May 11, 2015 3:52:18 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * AvgInputDataId generated by hbm2java
 */
@Embeddable
public class AvgInputDataId implements java.io.Serializable {

	private String matchcode;
	private BigDecimal revenue;
	private String periodCode;

	public AvgInputDataId() {
	}

	public AvgInputDataId(String matchcode, String periodCode) {
		this.matchcode = matchcode;
		this.periodCode = periodCode;
	}

	public AvgInputDataId(String matchcode, BigDecimal revenue,
			String periodCode) {
		this.matchcode = matchcode;
		this.revenue = revenue;
		this.periodCode = periodCode;
	}

	@Column(name = "MATCHCODE", nullable = false, length = 40)
	public String getMatchcode() {
		return this.matchcode;
	}

	public void setMatchcode(String matchcode) {
		this.matchcode = matchcode;
	}

	@Column(name = "REVENUE", precision = 22, scale = 0)
	public BigDecimal getRevenue() {
		return this.revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	@Column(name = "PERIOD_CODE", nullable = false, length = 28)
	public String getPeriodCode() {
		return this.periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AvgInputDataId))
			return false;
		AvgInputDataId castOther = (AvgInputDataId) other;

		return ((this.getMatchcode() == castOther.getMatchcode()) || (this
				.getMatchcode() != null && castOther.getMatchcode() != null && this
				.getMatchcode().equals(castOther.getMatchcode())))
				&& ((this.getRevenue() == castOther.getRevenue()) || (this
						.getRevenue() != null && castOther.getRevenue() != null && this
						.getRevenue().equals(castOther.getRevenue())))
				&& ((this.getPeriodCode() == castOther.getPeriodCode()) || (this
						.getPeriodCode() != null
						&& castOther.getPeriodCode() != null && this
						.getPeriodCode().equals(castOther.getPeriodCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMatchcode() == null ? 0 : this.getMatchcode().hashCode());
		result = 37 * result
				+ (getRevenue() == null ? 0 : this.getRevenue().hashCode());
		result = 37
				* result
				+ (getPeriodCode() == null ? 0 : this.getPeriodCode()
						.hashCode());
		return result;
	}

}
