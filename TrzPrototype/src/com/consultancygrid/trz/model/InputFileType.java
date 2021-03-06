package com.consultancygrid.trz.model;
// default package
// Generated Jan 17, 2015 6:48:33 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * InputFileType generated by hbm2java
 */
@Entity
@Table(name = "INPUT_FILE_TYPE", uniqueConstraints = @UniqueConstraint(columnNames = "CODE"))
public class InputFileType implements java.io.Serializable {

	private UUID id;
	private String code;
	private String prefix;
	private BigDecimal value;
	private Set<InputData> inputDatas = new HashSet<InputData>(0);

	public InputFileType() {
	}

	public InputFileType(UUID id, String code, String prefix, BigDecimal value) {
		this.id = id;
		this.code = code;
		this.prefix = prefix;
		this.value = value;
	}

	public InputFileType(UUID id, String code, String prefix,
			BigDecimal value, Set<InputData> inputDatas) {
		this.id = id;
		this.code = code;
		this.prefix = prefix;
		this.value = value;
		this.inputDatas = inputDatas;
	}

	@GenericGenerator(name = "it_trz_uuid_gen", strategy = "com.consultancygrid.trz.model.CustomUUIDGenerator")
	@Id
	@GeneratedValue(generator = "it_trz_uuid_gen")
	@Type(type = "com.consultancygrid.trz.model.UUIDType")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Column(name = "CODE", unique = true, nullable = false, length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "PREFIX", nullable = false, length = 100)
	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name = "VALUE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "inputFileType")
	public Set<InputData> getInputDatas() {
		return this.inputDatas;
	}

	public void setInputDatas(Set<InputData> inputDatas) {
		this.inputDatas = inputDatas;
	}

}
