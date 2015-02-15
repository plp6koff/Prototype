//package com.consultancygrid.trz.util;
//
//import java.math.BigDecimal;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.consultancygrid.trz.model.EmployeeSalary;
//import com.consultancygrid.trz.model.TrzStatic;
//
//public class TestPersonRowCalculation {
//
//	private  static TrzStatic dod = null;
//	private  static   TrzStatic oR = null;
//	private  static   TrzStatic oS = null;
//	private  static   BigDecimal b = null;
//	private  static   BigDecimal h = null;
//	private  static   BigDecimal u = null;
//	private  static   BigDecimal r = null;
//	private  static   EmployeeSalary emplSalary = null;
//
//	@Before
//	public void setUp() {
//
//		dod = new TrzStatic();
//		dod.setValueType("percent");
//		dod.setValue("10");
//
//		oR = new TrzStatic();
//		oR.setValueType("percent");
//		oR.setValue("17.8");
//
//		oS = new TrzStatic();
//		oS.setValueType("percent");
//		oS.setValue("12.9");
//
//		emplSalary = new EmployeeSalary();
//
//		b = BigDecimal.valueOf(Double.valueOf(1000.0));
//		h = BigDecimal.valueOf(Double.valueOf(800.0));
//		u = BigDecimal.valueOf(Double.valueOf(100.0));
//		r = BigDecimal.valueOf(Double.valueOf(500.0));
//	}
//
//	@Test
//	public void testCalculation() {
//
//		EmployeeSallaryCalculateUtil.calcSettings(
//				b.doubleValue(), 
//				h.doubleValue(), 
//				r.doubleValue(), 
//				u.doubleValue(), 
//				"Test", emplSalary, dod, oR, oS);
//		Assert.assertEquals(BigDecimal.valueOf(Double.valueOf("783.9")), emplSalary.getV02());
//		Assert.assertEquals(BigDecimal.valueOf(Double.valueOf("178.0")), emplSalary.getV03());
//		Assert.assertEquals(BigDecimal.valueOf(Double.valueOf("216.1")), emplSalary.getV04());
//		Assert.assertEquals(BigDecimal.valueOf(Double.valueOf("78.82")), emplSalary.getV05());
//		Assert.assertEquals(BigDecimal.valueOf(Double.valueOf("627.12")), emplSalary.getV07());
//		Assert.assertEquals(BigDecimal.valueOf(Double.valueOf("942.4")), emplSalary.getV08());
//		Assert.assertEquals(BigDecimal.valueOf(Double.valueOf("142.4")), emplSalary.getV09());
//		Assert.assertEquals(BigDecimal.valueOf(Double.valueOf("172.88")), emplSalary.getV10());
//		Assert.assertEquals(BigDecimal.valueOf(Double.valueOf("1078.82")), emplSalary.getV11());
//
//	}
//}
