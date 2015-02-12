package com.consultancygrid.trz.util;

import java.math.BigDecimal;

import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.TrzStatic;

public class EmployeeSallaryCalculateUtil {

	public static void calcSettings(Double b, Double j ,Double m, Double o, String name , 
			EmployeeSalary emplSallary, TrzStatic DOD,
			TrzStatic OSIGUROVKI_RABOTODATEL, TrzStatic OSIGUROVKI_SLUJITEL) {

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

		Double d = (b - (b * (dodValue * dodType)))
				* (oSlujitelValue * oSlujitelType) + b * (dodValue * dodType);
		d = BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		
		Double e = oRabotodatelValue * oRabotodatelType * b;
		e = BigDecimal.valueOf(e).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		// =(B6-(B6*0,1))*0,129+B6*0,1
		// =(B6-(B6*0,1))*0,129+B6*0,1
		//
		Double c = b - d;
		c = BigDecimal.valueOf(c).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		Double f = emplSallary.getV05() != null ? emplSallary.getV05().doubleValue() : 0.0; 
		emplSallary.setV12(BigDecimal.valueOf(f));
		Double g = emplSallary.getV06() != null ? emplSallary.getV06().doubleValue() : 0.0;
		emplSallary.setV13(BigDecimal.valueOf(g));
		Double h = emplSallary.getV07() != null ? emplSallary.getV07().doubleValue() : 0.0;
		emplSallary.setV14(BigDecimal.valueOf(h));
		Double i = f + g + h;
		
		Double tmpSumCI = c + i; 
		Double k = (tmpSumCI < j) ? j : tmpSumCI;
		Double l = (tmpSumCI < j) ? j : j - tmpSumCI;
		Double p = m  + o + k;
		Double q;
		Double r = 0.0;
		Double s = r + p;
		if ((tmpSumCI < j)) {
			q = ((j-c)+l)* Double.valueOf("0.15")+e;
		} else {
			q = (i * m)* Double.valueOf("0.15")+e;
		}

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
		emplSallary.setV10(BigDecimal.valueOf(k));
		emplSallary.setV11(BigDecimal.valueOf(l));
		emplSallary.setV12(BigDecimal.valueOf(m));
		emplSallary.setV13(BigDecimal.valueOf(o));
		emplSallary.setV14(BigDecimal.valueOf(p));
		emplSallary.setV15(BigDecimal.valueOf(q));
	    emplSallary.setV16(BigDecimal.valueOf(r));
	    emplSallary.setV17(BigDecimal.valueOf(s));
	    emplSallary.setS01(name);
	}
}
