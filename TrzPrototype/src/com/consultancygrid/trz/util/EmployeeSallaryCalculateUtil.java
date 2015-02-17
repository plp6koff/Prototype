package com.consultancygrid.trz.util;

import java.math.BigDecimal;

import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.TrzStatic;

public class EmployeeSallaryCalculateUtil {

	public static void calcSettings(Double b, Double d , Double kMarker ,  Double nBonus, String oBonusName , 
			EmployeeSalary emplSallary, TrzStatic DOD,
			TrzStatic OSIGUROVKI_RABOTODATEL, TrzStatic OSIGUROVKI_SLUJITEL) {

		Double pVaucher = emplSallary.getV14() != null  ? emplSallary.getV14().doubleValue() : null;
		
		Double oRabotodatelValue = Double.valueOf(OSIGUROVKI_RABOTODATEL
				.getValue());
		Double oRabotodatelType = (OSIGUROVKI_RABOTODATEL.getValueType()
				.equals("percent") ? Double.valueOf("0.01") : Double
				.valueOf("1.0"));
		Double oSlujitelValue = Double.valueOf(OSIGUROVKI_SLUJITEL.getValue());
		Double oSlujitelType = (OSIGUROVKI_SLUJITEL.getValueType().equals(
				"percent") ? Double.valueOf("0.01") : Double.valueOf("1.0"));
		Double dodValue = Double.valueOf(DOD.getValue());
		Double dodType = (DOD.getValueType().equals("percent") ? Double
				.valueOf("0.01") : Double.valueOf("1.0"));

		Double e = (b - (b * (dodValue * dodType)))
				* (oSlujitelValue * oSlujitelType) + b * (dodValue * dodType);
		e = BigDecimal.valueOf(e).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		
		Double f = oRabotodatelValue * oRabotodatelType * b;
		f = BigDecimal.valueOf(f).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		// =(B6-(B6*0,1))*0,129+B6*0,1
		// =(B6-(B6*0,1))*0,129+B6*0,1
		//
		Double c = b - e;
		c = BigDecimal.valueOf(c).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		Double g = emplSallary.getV05() != null ? emplSallary.getV05().doubleValue() : 0.0; 
		emplSallary.setV12(BigDecimal.valueOf(f));
		Double h = emplSallary.getV06() != null ? emplSallary.getV06().doubleValue() : 0.0;
		emplSallary.setV13(BigDecimal.valueOf(g));
		Double i = emplSallary.getV07() != null ? emplSallary.getV07().doubleValue() : 0.0;
		emplSallary.setV14(BigDecimal.valueOf(i));
		Double j = g + i + h;
		
		Double tmpSumCI = d + j; 
		Double l = (tmpSumCI < j) ? kMarker : tmpSumCI;
		Double m = (tmpSumCI < j) ? (kMarker - tmpSumCI) : 0.0d;
		Double q = pVaucher  + nBonus + l;
		Double r = 0.0;
		Double s = (q-c)*1+f;
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
		emplSallary.setV09(BigDecimal.valueOf(j));
		emplSallary.setV10(BigDecimal.valueOf(kMarker));
		emplSallary.setV11(BigDecimal.valueOf(l));
		emplSallary.setV12(BigDecimal.valueOf(m));
		emplSallary.setV13(BigDecimal.valueOf(nBonus));
		emplSallary.setS01(oBonusName);
		
		emplSallary.setV15(BigDecimal.valueOf(q));
		emplSallary.setV16(BigDecimal.valueOf(r));
	    emplSallary.setV17(BigDecimal.valueOf(s));
	    emplSallary.setV18(BigDecimal.valueOf(t));
	}
}
