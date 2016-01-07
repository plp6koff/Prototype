package com.consultancygrid.trz.data;

import com.consultancygrid.trz.model.TrzStatic;

public class TrzStaticData {

	private TrzStatic DOD;
	private TrzStatic OSIGUROVKI_RABOTODATEL;
	private TrzStatic OSIGUROVKI_SLUJITEL;
	private TrzStatic CACHE_TAX;
	private Double dodValue;
	private Double oRabotodatelValue;
	private Double oSlujitelValue;
	private Double cacheTaxValue;
	private Double vauchers ;
	
	public TrzStatic getDOD() {
		return DOD;
	}
	public void setDOD(TrzStatic dOD) {
		DOD = dOD;
	}
	public TrzStatic getOSIGUROVKI_RABOTODATEL() {
		return OSIGUROVKI_RABOTODATEL;
	}
	public void setOSIGUROVKI_RABOTODATEL(TrzStatic oSIGUROVKI_RABOTODATEL) {
		OSIGUROVKI_RABOTODATEL = oSIGUROVKI_RABOTODATEL;
	}
	public TrzStatic getOSIGUROVKI_SLUJITEL() {
		return OSIGUROVKI_SLUJITEL;
	}
	public void setOSIGUROVKI_SLUJITEL(TrzStatic oSIGUROVKI_SLUJITEL) {
		OSIGUROVKI_SLUJITEL = oSIGUROVKI_SLUJITEL;
	}
	public TrzStatic getCACHE_TAX() {
		return CACHE_TAX;
	}
	public void setCACHE_TAX(TrzStatic cACHE_TAX) {
		CACHE_TAX = cACHE_TAX;
	}
	public Double getDodValue() {
		return dodValue;
	}
	public void setDodValue(Double dodValue) {
		this.dodValue = dodValue;
	}
	public Double getoRabotodatelValue() {
		return oRabotodatelValue;
	}
	public void setoRabotodatelValue(Double oRabotodatelValue) {
		this.oRabotodatelValue = oRabotodatelValue;
	}
	public Double getoSlujitelValue() {
		return oSlujitelValue;
	}
	public void setoSlujitelValue(Double oSlujitelValue) {
		this.oSlujitelValue = oSlujitelValue;
	}
	public Double getCacheTaxValue() {
		return cacheTaxValue;
	}
	public void setCacheTaxValue(Double cacheTaxValue) {
		this.cacheTaxValue = cacheTaxValue;
	}
	public Double getVauchers() {
		return vauchers;
	}
	public void setVauchers(Double vauchers) {
		this.vauchers = vauchers;
	}
	
	
}
