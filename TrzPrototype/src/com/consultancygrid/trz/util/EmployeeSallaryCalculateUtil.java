package com.consultancygrid.trz.util;

import java.math.BigDecimal;

import com.consultancygrid.trz.data.TrzStaticData;
import com.consultancygrid.trz.model.EmployeeSalary;

public class EmployeeSallaryCalculateUtil {

	
	private static Double oo1 = Double.valueOf("0.01");
	private static String PERCENT = "percent";
	private static Double one  = Double.valueOf("1.0");
	/**
	 * Static comming from outside
	 * 
	 * @param b
	 * @param d
	 * @param kMarker
	 * @param nBonus
	 * @param oBonusName
	 * @param emplSallary
	 * @param v19
	 * @param notes
	 * @param trzStatData
	 */
	public static void calcSettings(Double b, 
							        Double d, 
							        Double kMarker,
							        Double nBonus, 
							        String oBonusName, 
							        EmployeeSalary emplSallary,
							        BigDecimal v19, 
							        String notes, 
							        TrzStaticData trzStatData) {

		final Double pVaucher = emplSallary.getV14() != null ? emplSallary.getV14()
				.doubleValue() : 0.0d;

		final Double oRabotodatelType = (trzStatData.getOSIGUROVKI_SLUJITEL().getValueType()
				.equals(PERCENT) ? oo1 : one);
		final Double oSlujitelType = (trzStatData.getOSIGUROVKI_SLUJITEL().getValueType()
				.equals(PERCENT) ? oo1 : one);
		final Double dodType = (trzStatData.getDOD().getValueType()
				.equals(PERCENT) ? oo1 : one);
		final Double cacheTaxType = (trzStatData.getCACHE_TAX().getValueType()
				.equals(PERCENT) ? oo1 : one);

		Double e = (b * (trzStatData.getDodValue() * dodType))
				+ (b * (trzStatData.getoSlujitelValue() * oSlujitelType));
		e = BigDecimal.valueOf(e).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();

		Double f = trzStatData.getoRabotodatelValue() * oRabotodatelType * b;
		f = BigDecimal.valueOf(f).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		Double c = b - e;
		c = BigDecimal.valueOf(c).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();

		final Double g = emplSallary.getV06() != null ? emplSallary.getV06()
				.doubleValue() : 0.0d;
		final Double h = emplSallary.getV07() != null ? emplSallary.getV07()
				.doubleValue() : 0.0d;
		final Double i = emplSallary.getV08() != null ? emplSallary.getV08()
				.doubleValue() : 0.0d;
		final Double j = g + i + h;

		final Double tmpSumCI = d + j;
		final Double l = (tmpSumCI < kMarker) ? kMarker : tmpSumCI;
		final Double m = (tmpSumCI < kMarker) ? (kMarker - tmpSumCI) : 0.0d;
		final Double q = pVaucher + nBonus + l;
		final Double r = 0.0d;
		final Double cache = trzStatData.getCacheTaxValue() * cacheTaxType;
		final Double s = (q - c) * cache + f;
		final Double t = s + q;
		// Populate Salary
		emplSallary.setV01(BigDecimal.valueOf(b));
		emplSallary.setV02(BigDecimal.valueOf(c));
		emplSallary.setV03(BigDecimal.valueOf(d));
		emplSallary.setV04(BigDecimal.valueOf(e));
		emplSallary.setV05(BigDecimal.valueOf(f));
		emplSallary.setV06(BigDecimal.valueOf(g));
		emplSallary.setV07(BigDecimal.valueOf(h));
		emplSallary.setV08(BigDecimal.valueOf(i));
		emplSallary.setV09(BigDecimal.valueOf(j).setScale(2,BigDecimal.ROUND_HALF_UP));
		emplSallary.setV10(BigDecimal.valueOf(kMarker));
		emplSallary.setV11(BigDecimal.valueOf(l).setScale(2,BigDecimal.ROUND_HALF_UP));
		emplSallary.setV12(BigDecimal.valueOf(m));
		emplSallary.setV13(BigDecimal.valueOf(nBonus));
		emplSallary.setS01(oBonusName);
		emplSallary.setV14(BigDecimal.valueOf(pVaucher));
		emplSallary.setV15(BigDecimal.valueOf(q).setScale(2,BigDecimal.ROUND_HALF_UP));
		emplSallary.setV16(BigDecimal.valueOf(r));
		emplSallary.setV17(BigDecimal.valueOf(s).setScale(2,BigDecimal.ROUND_HALF_UP));
		emplSallary.setV18(BigDecimal.valueOf(t).setScale(2,BigDecimal.ROUND_HALF_UP));
		emplSallary.setV19((emplSallary.getV19() != null ? emplSallary.getV19() : BigDecimal.ZERO));
		emplSallary.setV20(BigDecimal.valueOf(q)
					.subtract(emplSallary.getV19())
						.subtract(BigDecimal.valueOf(c)).setScale(2, BigDecimal.ROUND_HALF_UP));
		emplSallary.setS02(notes);
	}

	/**
	 * Internal calculation of the static
	 * 
	 * @param b
	 * @param d
	 * @param kMarker
	 * @param nBonus
	 * @param oBonusName
	 * @param emplSallary
	 * @param v19
	 * @param notes
	 */

	public static void updateSettings(Double g, Double h, Double i,
			EmployeeSalary emplSallary, TrzStaticData trzStatData) {

		final Double b = emplSallary.getV01() != null ? emplSallary.getV01()
				.doubleValue() : 0.0d;
	    final Double d = emplSallary.getV03() != null ? emplSallary.getV03()
				.doubleValue() : 0.0d;
		final Double kMarker = emplSallary.getV10() != null ? emplSallary.getV10()
				.doubleValue() : 0.0d;
		final Double nBonus = emplSallary.getV13() != null ? emplSallary.getV13()
				.doubleValue() : 0.0d;

		final Double pVaucher = emplSallary.getV14() != null ? emplSallary.getV14()
				.doubleValue() : 0.0d;

		final Double oRabotodatelType = (trzStatData.getOSIGUROVKI_SLUJITEL().getValueType()
						.equals(PERCENT) ? oo1 : one);
		final Double oSlujitelType = (trzStatData.getOSIGUROVKI_SLUJITEL().getValueType()
						.equals(PERCENT) ? oo1 : one);
		final Double dodType = (trzStatData.getDOD().getValueType()
						.equals(PERCENT) ? oo1 : one);
		final Double cacheTaxType = (trzStatData.getCACHE_TAX().getValueType()
						.equals(PERCENT) ? oo1 : one);
		Double e = (b * (trzStatData.getDodValue() * dodType))
				+ (b * (trzStatData.getoSlujitelValue() * oSlujitelType));
		e = BigDecimal.valueOf(e).setScale(2, BigDecimal.ROUND_HALF_UP)	.doubleValue();
		Double f = trzStatData.getoRabotodatelValue() * oRabotodatelType * b;
		f = BigDecimal.valueOf(f).setScale(2, BigDecimal.ROUND_HALF_UP)	.doubleValue();
		Double c = b - e;
		c = BigDecimal.valueOf(c).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		final Double j = g + i + h;
		final Double tmpSumCI = d + j;
		final Double l = (tmpSumCI < tmpSumCI) ? kMarker : tmpSumCI;
		final Double m = (tmpSumCI < tmpSumCI) ? (kMarker - tmpSumCI) : 0.0d;
		final Double q = pVaucher + nBonus + l;
		final Double r = 0.0;
		final Double cache = trzStatData.getCacheTaxValue() * cacheTaxType;
		final Double s = (q - c) * cache + f;
		final Double t = s + q;
		// Populate Salary
		emplSallary.setV01(BigDecimal.valueOf(b));
		emplSallary.setV02(BigDecimal.valueOf(c));
		emplSallary.setV03(BigDecimal.valueOf(d));
		emplSallary.setV04(BigDecimal.valueOf(e));
		emplSallary.setV05(BigDecimal.valueOf(f));
		emplSallary.setV06(BigDecimal.valueOf(g));
		emplSallary.setV07(BigDecimal.valueOf(h));
		emplSallary.setV08(BigDecimal.valueOf(i));
		emplSallary.setV09(BigDecimal.valueOf(j).setScale(2,BigDecimal.ROUND_HALF_UP));
		emplSallary.setV10(BigDecimal.valueOf(kMarker));
		emplSallary.setV11(BigDecimal.valueOf(l).setScale(2,BigDecimal.ROUND_HALF_UP));
		emplSallary.setV12(BigDecimal.valueOf(m));
		emplSallary.setV13(BigDecimal.valueOf(nBonus));
		emplSallary.setV15(BigDecimal.valueOf(q).setScale(2,BigDecimal.ROUND_HALF_UP));
		emplSallary.setV16(BigDecimal.valueOf(r));
		emplSallary.setV17(BigDecimal.valueOf(s).setScale(2,BigDecimal.ROUND_HALF_UP));
		emplSallary.setV18(BigDecimal.valueOf(t).setScale(2,BigDecimal.ROUND_HALF_UP));
		if (emplSallary.getV19() == null) {
			emplSallary.setV19(BigDecimal.ZERO);
		} else {
			emplSallary.setV19(emplSallary.getV19());
		}
		emplSallary.setV20(BigDecimal.valueOf(q).subtract(emplSallary.getV19())
				.subtract(BigDecimal.valueOf(c)).setScale(2, BigDecimal.ROUND_HALF_UP));
	}

}
