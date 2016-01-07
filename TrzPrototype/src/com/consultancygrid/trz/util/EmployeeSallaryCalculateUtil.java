package com.consultancygrid.trz.util;

import java.math.BigDecimal;

import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.TrzStatic;

public class EmployeeSallaryCalculateUtil {

	public static void calcSettings(
			Double b, 
			Double d , 
			Double kMarker ,  
			Double nBonus, 
			String oBonusName , 
			EmployeeSalary emplSallary, 
			TrzStatic DOD,
			TrzStatic OSIGUROVKI_RABOTODATEL, 
			TrzStatic OSIGUROVKI_SLUJITEL,
			TrzStatic CACHE_TAX, 
			Double dodValue,
			Double oRabotodatelValue,
			Double oSlujitelValue,
			Double cacheTaxValue,
			BigDecimal v19,
			String notes) {

		Double pVaucher = emplSallary.getV14() != null  ? emplSallary.getV14().doubleValue() :  0.0d;
		
		Double oRabotodatelType = (OSIGUROVKI_RABOTODATEL.getValueType()
				.equals("percent") ? Double.valueOf("0.01") : Double
				.valueOf("1.0"));
		Double oSlujitelType = (OSIGUROVKI_SLUJITEL.getValueType().equals(
				"percent") ? Double.valueOf("0.01") : Double.valueOf("1.0"));
		Double dodType = (DOD.getValueType().equals("percent") ? Double
				.valueOf("0.01") : Double.valueOf("1.0"));
		Double cacheTaxType = (CACHE_TAX.getValueType().equals("percent") ? Double
				.valueOf("0.01") : Double.valueOf("1.0"));
		
		Double e =  (b * (dodValue * dodType)) + (b * (oSlujitelValue * oSlujitelType));
		e = BigDecimal.valueOf(e).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		
		Double f = oRabotodatelValue * oRabotodatelType * b;
		f = BigDecimal.valueOf(f).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double c = b - e;
		c = BigDecimal.valueOf(c).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		Double g = emplSallary.getV06() != null ? emplSallary.getV06().doubleValue() : 0.0d; 
		Double h = emplSallary.getV07() != null ? emplSallary.getV07().doubleValue() : 0.0d;
		Double i = emplSallary.getV08() != null ? emplSallary.getV08().doubleValue() : 0.0d;
		Double j = g + i + h;
		
		Double tmpSumCI = d + j; 
		Double l = (tmpSumCI < kMarker) ? kMarker : tmpSumCI;
		Double m = (tmpSumCI < kMarker) ? (kMarker - tmpSumCI) : 0.0d;
		Double q = pVaucher  + nBonus + l;
		Double r = 0.0;
		Double cache = cacheTaxValue * cacheTaxType;
		Double s = (q-c)*cache+f;
		Double t = s + q;
		//Populate Salary
		emplSallary.setV01(BigDecimal.valueOf(b));
		emplSallary.setV02(BigDecimal.valueOf(c));
		emplSallary.setV03(BigDecimal.valueOf(d));
		emplSallary.setV04(BigDecimal.valueOf(e));
		emplSallary.setV05(BigDecimal.valueOf(f));
		emplSallary.setV06(BigDecimal.valueOf(g));
		emplSallary.setV07(BigDecimal.valueOf(h));
		emplSallary.setV08(BigDecimal.valueOf(i));
		emplSallary.setV09(BigDecimal.valueOf(j).setScale(2, BigDecimal.ROUND_HALF_UP));
		emplSallary.setV10(BigDecimal.valueOf(kMarker));
		emplSallary.setV11(BigDecimal.valueOf(l).setScale(2, BigDecimal.ROUND_HALF_UP));
		emplSallary.setV12(BigDecimal.valueOf(m));
		emplSallary.setV13(BigDecimal.valueOf(nBonus));
		emplSallary.setS01(oBonusName);
		emplSallary.setV14(BigDecimal.valueOf(pVaucher));
		emplSallary.setV15(BigDecimal.valueOf(q).setScale(2, BigDecimal.ROUND_HALF_UP));
		emplSallary.setV16(BigDecimal.valueOf(r));
	    emplSallary.setV17(BigDecimal.valueOf(s).setScale(2, BigDecimal.ROUND_HALF_UP));
	    emplSallary.setV18(BigDecimal.valueOf(t).setScale(2, BigDecimal.ROUND_HALF_UP));
	    emplSallary.setV19((emplSallary.getV19() != null ?  emplSallary.getV19() : BigDecimal.ZERO));
	    emplSallary.setV20(  BigDecimal.valueOf(q).subtract(emplSallary.getV19()).subtract(BigDecimal.valueOf(c)).setScale(2, BigDecimal.ROUND_HALF_UP));
	    emplSallary.setS02(notes);
	}
	
	
	
	public static void updateSettings( 
			Double g , 
			Double h , 
			Double i,   
			EmployeeSalary emplSallary, 
			TrzStatic DOD,
			TrzStatic OSIGUROVKI_RABOTODATEL, 
			TrzStatic OSIGUROVKI_SLUJITEL,
			TrzStatic CACHE_TAX, 
			Double dodValue,
			Double oRabotodatelValue,
			Double oSlujitelValue,
			Double cacheTaxValue) {

		Double b = emplSallary.getV01() != null ? emplSallary.getV01().doubleValue() : 0.0d; 
		Double d = emplSallary.getV03() != null ? emplSallary.getV03().doubleValue() : 0.0d;
		Double kMarker = emplSallary.getV10() != null ? emplSallary.getV10().doubleValue() : 0.0d;
		Double nBonus = emplSallary.getV13() != null ? emplSallary.getV13().doubleValue() : 0.0d;
		
		Double pVaucher = emplSallary.getV14() != null  ? emplSallary.getV14().doubleValue() :  0.0d;
		
		Double oRabotodatelType = (OSIGUROVKI_RABOTODATEL.getValueType()
				.equals("percent") ? Double.valueOf("0.01") : Double
				.valueOf("1.0"));
		Double oSlujitelType = (OSIGUROVKI_SLUJITEL.getValueType().equals(
				"percent") ? Double.valueOf("0.01") : Double.valueOf("1.0"));
		Double dodType = (DOD.getValueType().equals("percent") ? Double
				.valueOf("0.01") : Double.valueOf("1.0"));
		Double cacheTaxType = (CACHE_TAX.getValueType().equals("percent") ? Double
				.valueOf("0.01") : Double.valueOf("1.0"));

		Double e =  (b * (dodValue * dodType)) + (b * (oSlujitelValue * oSlujitelType));
		e = BigDecimal.valueOf(e).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		
		Double f = oRabotodatelValue * oRabotodatelType * b;
		f = BigDecimal.valueOf(f).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double c = b - e;
		c = BigDecimal.valueOf(c).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		Double j = g + i + h;
		
		Double tmpSumCI = d + j; 
		Double l = (tmpSumCI < tmpSumCI) ? kMarker : tmpSumCI;
		Double m = (tmpSumCI < tmpSumCI) ? (kMarker - tmpSumCI) : 0.0d;
		Double q = pVaucher  + nBonus + l;
		Double r = 0.0;
		
		Double cache = cacheTaxValue * cacheTaxType;
		
		Double s = (q-c)*cache+f;
		Double t = s + q;
		//Populate Salary
		emplSallary.setV01(BigDecimal.valueOf(b));
		emplSallary.setV02(BigDecimal.valueOf(c));
		emplSallary.setV03(BigDecimal.valueOf(d));
		emplSallary.setV04(BigDecimal.valueOf(e));
		emplSallary.setV05(BigDecimal.valueOf(f));
		emplSallary.setV06(BigDecimal.valueOf(g));
		emplSallary.setV07(BigDecimal.valueOf(h));
		emplSallary.setV08(BigDecimal.valueOf(i));
		emplSallary.setV09(BigDecimal.valueOf(j).setScale(2, BigDecimal.ROUND_HALF_UP));
		emplSallary.setV10(BigDecimal.valueOf(kMarker));
		emplSallary.setV11(BigDecimal.valueOf(l).setScale(2, BigDecimal.ROUND_HALF_UP));
		emplSallary.setV12(BigDecimal.valueOf(m));
		emplSallary.setV13(BigDecimal.valueOf(nBonus));
		emplSallary.setV15(BigDecimal.valueOf(q).setScale(2, BigDecimal.ROUND_HALF_UP));
		emplSallary.setV16(BigDecimal.valueOf(r));
	    emplSallary.setV17(BigDecimal.valueOf(s).setScale(2, BigDecimal.ROUND_HALF_UP));
	    emplSallary.setV18(BigDecimal.valueOf(t).setScale(2, BigDecimal.ROUND_HALF_UP));
	    if (emplSallary.getV19() == null) {
	    	emplSallary.setV19(BigDecimal.ZERO);
	    } else {
	    	emplSallary.setV19(emplSallary.getV19());
	    }
	    emplSallary.setV20(  BigDecimal.valueOf(q).subtract(emplSallary.getV19()).subtract(BigDecimal.valueOf(c)).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
}
