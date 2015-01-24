package com.consultancygrid.trz.util;

import java.math.BigDecimal;

public class FormatUtil {

	
	
	public static BigDecimal parseValue(BigDecimal initValue, String newValStr) {

		BigDecimal result = initValue;
		try {
			double parsedDoub = Double.valueOf(newValStr);
			return BigDecimal.valueOf(parsedDoub);
		} catch (Exception e) {
			return initValue;
		}
	}
}
