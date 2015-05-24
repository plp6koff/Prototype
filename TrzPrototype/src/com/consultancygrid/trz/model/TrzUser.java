package com.consultancygrid.trz.model;

// Generated Feb 20, 2015 9:08:58 PM by Hibernate Tools 3.4.0.CR1

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * TrzUser generated by hbm2java
 */
@Entity
@Table(name = "TRZ_USER")
public class TrzUser implements java.io.Serializable {

	private UUID id;
	private String login;
	private String password;
	private String tabs;

	public TrzUser() {
	}

	public TrzUser(UUID id, String login, String password, String tabs) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.tabs = tabs;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GenericGenerator(name = "usr_trz_uuid_gen", strategy = "com.consultancygrid.trz.model.CustomUUIDGenerator")
	@GeneratedValue(generator = "usr_trz_uuid_gen")
	@Type(type = "com.consultancygrid.trz.model.UUIDType")
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Column(name = "LOGIN", nullable = false, length = 100)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "PASSWORD", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "TABS", nullable = false, length = 1000)
	public String getTabs() {
		return this.tabs;
	}

	public void setTabs(String tabs) {
		this.tabs = tabs;
	}

}
