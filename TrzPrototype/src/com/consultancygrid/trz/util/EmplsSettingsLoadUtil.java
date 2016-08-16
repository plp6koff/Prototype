package com.consultancygrid.trz.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultancygrid.trz.data.StaticDataCache;
import com.consultancygrid.trz.data.StaticDataLoader;
import com.consultancygrid.trz.data.TrzStaticData;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;

/**
 * 
 * @author Anton Palapeshkov
 *
 */
public class EmplsSettingsLoadUtil {

	private final static String SQL1 = " from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.code desc";
	private final static String SQL2 = " from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId and emplSalary.period.code like :periodCode order by emplSalary.period.code desc";
	private final static String SQL3 = "SELECT p1_individualna_premia,p2_grupova_premia,p3_obshta_premia FROM RENUMERATIONS where FK_EMPLOYEE_ID = :employeeId AND  FK_PERIOD_ID = :periodId ";
	private final static String EMPL_ID_PARAM = "employeeId";
	private final static String PERIOD_CODE_PARAM = "periodCode";
	private final static String PERIOD_ID_PARAM = "periodId";
	
	private static Double oo1 = Double.valueOf("0.01");
	private static String PERCENT = "percent";
	private static Double one  = Double.valueOf("1.0");
	/**
	 * Main Load method  !
	 * @param employee
	 * @param year
	 * @param em
	 * @param tableData
	 * @param model
	 * @param extraCheck
	 */
	public void load(Employee employee, 
					 String year, 
					 EntityManager em,
					 Vector tableData, 
					 PersonalCfgEmplsTableModel model,
					 boolean extraCheck) {

		Query q = null;
		if (year != null && !"".equals(year)) {
			q = em.createQuery(SQL2);
			q.setParameter(EMPL_ID_PARAM, employee.getId());
			q.setParameter(PERIOD_CODE_PARAM, "%" + year + "%");
		} else {
			q = em.createQuery(SQL1);
			q.setParameter(EMPL_ID_PARAM, employee.getId());
		}

		List<EmployeeSalary> emplSals = (List<EmployeeSalary>) q.getResultList();
		model.setEmplSals(emplSals);

		for (EmployeeSalary emplSal : emplSals) {
			Query q1 = null;
			q1 = em.createNativeQuery(SQL3);
			q1.setParameter(EMPL_ID_PARAM, employee.getId().toString());
			q1.setParameter(PERIOD_ID_PARAM, emplSal.getPeriod().getId().toString());

			List<?> rsltIntrn = q1.getResultList();
			Object[] objcts = (Object[]) rsltIntrn.get(0);

			checBonusDataAndRecalculate(objcts, emplSal, em, extraCheck);

			Vector<Object> oneRow = new Vector<Object>();
			// 0
			oneRow.add(emplSal.getPeriod().getCode());
			// 1
			oneRow.add(emplSal.getV01());
			// 2
			oneRow.add(emplSal.getV02());
			// 3
			oneRow.add(emplSal.getV03());
			// 4
			oneRow.add(emplSal.getV04());
			// 4
			oneRow.add(emplSal.getV05());
			// 5
			oneRow.add(emplSal.getV06());
			// 6
			oneRow.add(emplSal.getV07());
			// 7
			oneRow.add(emplSal.getV08());
			// 8
			oneRow.add(emplSal.getV09());
			// 9
			oneRow.add(emplSal.getV10());
			//TODO is this ok 
			oneRow.add(emplSal.getV11());
			// 11
			oneRow.add(emplSal.getV12());
			// 12
			oneRow.add(emplSal.getV13());
			// 13
			oneRow.add(emplSal.getS01());
			// 14
			oneRow.add(emplSal.getV14());
			// 15
			oneRow.add(emplSal.getV15());
			// 19
			oneRow.add(emplSal.getV19());
			// 20
			oneRow.add(emplSal.getV20());
			// 16
			oneRow.add(emplSal.getV16());
			// 17
			oneRow.add(emplSal.getV17());
			// 18
			oneRow.add(emplSal.getV18());
			oneRow.add(emplSal.getS02());
			tableData.add(oneRow);
		}
	}

	public Vector<Object> reloadSingleRow(Employee employee, 
			EmployeeSalary emplSallary ,
			EntityManager em,
			PersonalCfgEmplsTableModel model) {

			Query q1 = null;
			q1 = em.createNativeQuery(SQL3);
			q1.setParameter("employeeId", employee.getId().toString());
			q1.setParameter("periodId", emplSallary.getPeriod().getId().toString());

			Vector<Object> oneRow = new Vector<Object>();
			// 0
			oneRow.add(emplSallary.getPeriod().getCode());
			// 1
			oneRow.add(emplSallary.getV01());
			// 2
			oneRow.add(emplSallary.getV02());
			// 3
			oneRow.add(emplSallary.getV03());
			// 4
			oneRow.add(emplSallary.getV04());
			// 4
			oneRow.add(emplSallary.getV05());
			// 5
			oneRow.add(emplSallary.getV06());
			// 6
			oneRow.add(emplSallary.getV07());
			// 7
			oneRow.add(emplSallary.getV08());
			// 8
			oneRow.add(emplSallary.getV09());
			// 9
			oneRow.add(emplSallary.getV10());
			// 10
			// oneRow.add(emplSal.getV11());

			Double d = emplSallary.getV03() != null ? emplSallary.getV03()
					.doubleValue() : 0.0d;

			Double g = emplSallary.getV06() != null ? emplSallary.getV06()
					.doubleValue() : 0.0d;
			Double h = emplSallary.getV07() != null ? emplSallary.getV07()
					.doubleValue() : 0.0d;
			Double i = emplSallary.getV08() != null ? emplSallary.getV08()
					.doubleValue() : 0.0d;
			Double j = g + i + h;
			emplSallary.setV09(BigDecimal.valueOf(j));

			Double kMarker = emplSallary.getV10() != null ? emplSallary.getV10()
					.doubleValue() : 0.0d;

			Double tmpSumCI = d + j;
			Double l = (tmpSumCI < kMarker) ? kMarker : tmpSumCI;

			oneRow.add(tmpSumCI);
			// 11
			oneRow.add(emplSallary.getV12());
			// 12
			oneRow.add(emplSallary.getV13());
			// 13
			oneRow.add(emplSallary.getS01());
			// 14
			oneRow.add(emplSallary.getV14());
			// 15
			oneRow.add(emplSallary.getV15());
			// 19
			oneRow.add(emplSallary.getV19());
			// 20
			if (emplSallary.getV19() != null) {
				BigDecimal temp = emplSallary.getV15().subtract(emplSallary.getV19());
				if (temp != null) {
					temp = temp.subtract(emplSallary.getV02());
				}
				oneRow.add(temp);
			} else {
				oneRow.add(emplSallary.getV15());
			}
			// 16
			oneRow.add(emplSallary.getV16());
			// 17
			oneRow.add(emplSallary.getV17());
			// 18
			oneRow.add(emplSallary.getV18());
			oneRow.add(emplSallary.getS02());
			return oneRow;

	}

	/**
	 * A simple method for recalculating the incomung bonuses
	 * @param objcts :
	 * 0. personal premium
	 * 1. group premium 
	 * 2. sum premium 
	 * @param emplSalary 
	 * @param em
	 * @param checkUpdate
	 * @return
	 */
	private EmployeeSalary checBonusDataAndRecalculate(
			Object[] objcts,
			EmployeeSalary emplSalary,
			EntityManager em, 
			boolean checkUpdate) {

		System.out.println("==========> system is going to recalculate  =======>");

		final BigDecimal v6View = (BigDecimal) objcts[0];
		final BigDecimal v7View = (BigDecimal) objcts[1];
		final BigDecimal v8View = (BigDecimal) objcts[2];
		
		boolean update = false;
		if (emplSalary.getV06() != null 
				&& !emplSalary.getV06().equals(v6View)) {
			emplSalary.setV06(v6View);
			update = true;
		}
		if (emplSalary.getV07() != null 
				&& !emplSalary.getV07().equals(v7View)) {
			emplSalary.setV07(v7View);
			update = true;
		}
		if (emplSalary.getV08() != null 
				&& !emplSalary.getV08().equals(v8View)) {
			emplSalary.setV08(v8View);
			update = true;
		}
		
		if (update) {
			
			BigDecimal j =  v6View.add(v7View).add(v8View);
			TrzStaticData trzStatData =
					StaticDataCache.getData(emplSalary.getPeriod().getId(), em);
			
			Double cacheTaxType = (trzStatData.getCACHE_TAX().getValueType()
					.equals(PERCENT) ? oo1 : one);
			BigDecimal tmpSumCI = emplSalary.getV03().add(j);
			BigDecimal kMarker = emplSalary.getV10();
			BigDecimal pVaucher = emplSalary.getV14();
			BigDecimal nBonus = emplSalary.getV13();
			BigDecimal l = (tmpSumCI.compareTo(kMarker) == -1) ? kMarker : tmpSumCI;
			BigDecimal m = (tmpSumCI.compareTo(kMarker) == -1) ? (kMarker.subtract(tmpSumCI)) : BigDecimal.valueOf(0.0d);
			BigDecimal q = pVaucher.add(nBonus).add(BigDecimal.ONE).setScale(2,BigDecimal.ROUND_HALF_UP);
			BigDecimal r = BigDecimal.ZERO;
			BigDecimal cache = BigDecimal.valueOf(trzStatData.getCacheTaxValue() * cacheTaxType).setScale(2,
					BigDecimal.ROUND_HALF_UP);;
			BigDecimal s = (q.subtract(emplSalary.getV02())).multiply(cache).add(emplSalary.getV05());
			BigDecimal t = s.add( q);
			emplSalary.setV09(j);
			emplSalary.setV10(kMarker);
			emplSalary.setV11(l);
			emplSalary.setV12(m);
			emplSalary.setV13(nBonus);
			emplSalary.setV14(pVaucher);
			emplSalary.setV15(q);
			emplSalary.setV16(r);
			emplSalary.setV17(s);
			emplSalary.setV18(t);
			emplSalary.setV19((emplSalary.getV19() != null ? emplSalary.getV19(): BigDecimal.ZERO));
			emplSalary.setV20(q.subtract(emplSalary.getV19()).subtract(emplSalary.getV02()).setScale(2,
					BigDecimal.ROUND_HALF_UP));
			if (checkUpdate) {
				em.refresh(emplSalary);
				em.merge(emplSalary);
				em.flush();
			}
		}
		return emplSalary;
	}
}
