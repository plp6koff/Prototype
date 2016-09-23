package com.consultancygrid.trz.model;

// Generated May 11, 2015 3:52:18 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TrzStatic generated by hbm2java
 */
@Entity
@Table(name = "TRZ_STATIC")
public class TrzStatic implements java.io.Serializable {

	private String id;
	private String key;
	private String value;
	private String valueType;
	private String keyDescription;
	private Set<PeriodSetting> periodSettings = new HashSet<PeriodSetting>(0);

	public TrzStatic() {
	}

	public TrzStatic(String id, String key) {
		this.id = id;
		this.key = key;
	}

	public TrzStatic(String id, String key, String value, String valueType,
			String keyDescription, Set<PeriodSetting> periodSettings) {
		this.id = id;
		this.key = key;
		this.value = value;
		this.valueType = valueType;
		this.keyDescription = keyDescription;
		this.periodSettings = periodSettings;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 144)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "KEY", nullable = false, length = 400)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "VALUE", length = 400)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "VALUE_TYPE", length = 400)
	public String getValueType() {
		return this.valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	@Column(name = "KEY_DESCRIPTION", length = 800)
	public String getKeyDescription() {
		return this.keyDescription;
	}

	public void setKeyDescription(String keyDescription) {
		this.keyDescription = keyDescription;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trzStatic")
	public Set<PeriodSetting> getPeriodSettings() {
		return this.periodSettings;
	}

	public void setPeriodSettings(Set<PeriodSetting> periodSettings) {
		this.periodSettings = periodSettings;
	}

}