package com.consultancygrid.trz.util;

import java.math.BigDecimal;

import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.TrzStatic;

public class EmployeeSallaryCalculateUtil {

	public static void calcSettings(Double b, Double h, Double r, Double v, 
			Double z, Double aa,
			EmployeeSalary emplSallary, TrzStatic DOD,
			TrzStatic OSIGUROVKI_RABOTODATEL, TrzStatic OSIGUROVKI_SLUJITEL) {

		Double oRabotodatelValue = Double.valueOf(OSIGUROVKI_RABOTODATEL
				.getValue());
		Double oRabotodatelType = (OSIGUROVKI_RABOTODATEL.getValueType()
				.equals("percent") ? Double.valueOf("0.01") : Double
				.valueOf("1.0"));

		Double d = oRabotodatelValue * oRabotodatelType * b;
		d = BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double dodValue = Double.valueOf(DOD.getValue());
		Double dodType = (DOD.getValueType().equals("percent") ? Double
				.valueOf("0.01") : Double.valueOf("1.0"));
		// =(B6-(B6*0,1))*0,129+B6*0,1
		Double oSlujitelValue = Double.valueOf(OSIGUROVKI_SLUJITEL.getValue());
		Double oSlujitelType = (OSIGUROVKI_SLUJITEL.getValueType().equals(
				"percent") ? Double.valueOf("0.01") : Double.valueOf("1.0"));
		// =(B6-(B6*0,1))*0,129+B6*0,1
		Double e = (b - (b * (dodValue * dodType)))
				* (oSlujitelValue * oSlujitelType) + b * (dodValue * dodType);
		e = BigDecimal.valueOf(e).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double c = b - e;
		c = BigDecimal.valueOf(c).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		// Double F -> =-(K6-D6+(L6-E6))
		// Double L = (H6-(H6*0,1))*0,129+H6*0,1
		Double l = (h - (h * dodValue * dodType))
				* (oSlujitelType * oSlujitelValue) + (h * dodValue * dodType);
		l = BigDecimal.valueOf(l).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double k = oRabotodatelValue * oRabotodatelType * h;
		k = BigDecimal.valueOf(k).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double f = -(k - d + (l - e));
		f = BigDecimal.valueOf(f).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		// Double l = =H6-L6
		
		Double o = emplSallary.getV12() != null ? emplSallary.getV12().doubleValue() : 0.0; 
		emplSallary.setV12(BigDecimal.valueOf(o));
		Double p = emplSallary.getV13() != null ? emplSallary.getV13().doubleValue() : 0.0;
		emplSallary.setV13(BigDecimal.valueOf(p));
		Double q = emplSallary.getV14() != null ? emplSallary.getV14().doubleValue() : 0.0;
		emplSallary.setV14(BigDecimal.valueOf(q));
		
		Double i = h - l;
		// =K6+H6
		Double j = k + h;
		Double m = b + f;
		Double u = i;
		Double s = c + o + p + q + r;
		Double w = s - u - v;
		Double ab = aa + z  + s;
		Double ac = k + ab ;

		emplSallary.setV02(BigDecimal.valueOf(c));
		emplSallary.setV03(BigDecimal.valueOf(d));
		emplSallary.setV04(BigDecimal.valueOf(e));
		emplSallary.setV05(BigDecimal.valueOf(f));
		// Double w = =S6-u-v
		emplSallary.setV07(BigDecimal.valueOf(i));
		emplSallary.setV08(BigDecimal.valueOf(j));
		emplSallary.setV09(BigDecimal.valueOf(k));
		emplSallary.setV10(BigDecimal.valueOf(l));
		emplSallary.setV11(BigDecimal.valueOf(m));
		emplSallary.setV14(BigDecimal.valueOf(i));
		emplSallary.setV16(BigDecimal.valueOf(s));
		emplSallary.setV19(BigDecimal.valueOf(w));
		emplSallary.setV20(BigDecimal.valueOf(z));
		emplSallary.setV21(BigDecimal.valueOf(aa));
		emplSallary.setV22(BigDecimal.valueOf(ab));
		emplSallary.setV23(BigDecimal.valueOf(ac));

	}
}
