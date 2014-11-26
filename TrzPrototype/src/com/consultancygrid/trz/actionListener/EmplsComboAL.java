package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;
















import static com.consultancygrid.trz.base.Constants.*;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class EmplsComboAL extends BaseActionListener {

	
	JComboBox comboBox;
	JTable table;
	
	public EmplsComboAL(PrototypeMainFrame mainFrame,JComboBox comboBox,JTable table) {

		super(mainFrame);
		this.comboBox = comboBox;
		this.table = table;
	}

	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();

			em.getTransaction().begin();

			
			Employee employee = ((Employee) comboBox.getModel().getSelectedItem());
			Query q = em.createQuery(" from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.dateEnd desc");
			q.setParameter("employeeId", employee.getId());
			List<EmployeeSalary> emplSals = (List<EmployeeSalary>) q.getResultList();
			((PersonalCfgEmplsTableModel)table.getModel()).setEmplSals(emplSals);
		    Vector tableData = new Vector();
			for (EmployeeSalary emplSal : emplSals) {
				Vector<Object> oneRow = new Vector<Object>();
				oneRow.add(emplSal.getPeriod().getCode());
				oneRow.add(emplSal.getV01());
				oneRow.add(emplSal.getV02());
				oneRow.add(emplSal.getV03());
				oneRow.add(emplSal.getV04());
				oneRow.add(emplSal.getV05());
				oneRow.add(emplSal.getV06());
				oneRow.add(emplSal.getV07());
				oneRow.add(emplSal.getV08());
				oneRow.add(emplSal.getV09());
				oneRow.add(emplSal.getV10());
				oneRow.add(emplSal.getV11());
				oneRow.add(emplSal.getV12());
				oneRow.add(emplSal.getV13());
				oneRow.add(emplSal.getV14());
				oneRow.add(emplSal.getV15());
				oneRow.add(emplSal.getV16());
				oneRow.add(emplSal.getV17());
				oneRow.add(emplSal.getV18());
				oneRow.add(emplSal.getV19());
				oneRow.add(emplSal.getV20());
				oneRow.add(emplSal.getV21());
				oneRow.add(emplSal.getV22());
				oneRow.add(emplSal.getV23());
				oneRow.add(Constants.EMPTY_STRING);
				oneRow.add(Constants.EMPTY_STRING);
				oneRow.add(Constants.EMPTY_STRING);
				tableData.add(oneRow);
				
			}

			if (comboBox.getModel().getSelectedItem() != null) {
				
				PersonalCfgEmplsTableModel currentModel = (PersonalCfgEmplsTableModel)table.getModel();
				currentModel.setData(tableData);
				table.setModel(currentModel);
				mainFrame.validate();
			}
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.close();
			}
		}

	}

	
	private BigInteger getEmployeeRevenue (EntityManager em, Period period , Employee employee) {
		Query tempPerEmpl = em.createQuery(" from RevenueEmplPeriod as revEmplPer  where  revEmplPer.period.id = :periodId and revEmplPer.employee.id = :emplId ");
		tempPerEmpl.setParameter("periodId", period.getId());
		tempPerEmpl.setParameter("emplId", employee.getId());
		List<RevenueEmplPeriod> revenueEmplPeriods = (List<RevenueEmplPeriod>)tempPerEmpl.getResultList();
		if (revenueEmplPeriods != null && revenueEmplPeriods.size() > 0 ) {
			return  revenueEmplPeriods.get(0).getRevenue().toBigInteger();
		} else {
			return  BigInteger.ZERO;
		}
			
	}
	
	private BigInteger getDepartmentRevenue (EntityManager em, Period period , Department department) {
		
		Query tempPer = em.createQuery(" from RevenueDeptPeriod as revDeptPer  where  revDeptPer.period.id = :periodId and revDeptPer.department.id = :deptId ");
		tempPer.setParameter("periodId", period.getId());
		tempPer.setParameter("deptId", department.getId());
		
		RevenueDeptPeriod revenueDeptPeriod = (RevenueDeptPeriod)tempPer.getSingleResult();
		return  revenueDeptPeriod.getRevenue() != null   ? revenueDeptPeriod.getRevenue().toBigInteger() : BigInteger.ZERO;
	}
	
	
	private Double getEmployeePercentAll(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPercentAll() != null ? emplSettings.getPercentAll() : BigDecimal.valueOf(1);
			  return temp.doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).doubleValue();
		}
	}
	
	private Double getEmployeePercentGroup(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPercentGroup() != null ? emplSettings.getPercentGroup() : BigDecimal.valueOf(1);
			  return temp.doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).doubleValue();
		}
	}
	
	private Double getEmployeePercentPersonal(EmployeeSettings emplSettings) {
		if (emplSettings != null) {
			BigDecimal temp 
			  = emplSettings.getPercentPersonal() != null ? emplSettings.getPercentPersonal() : BigDecimal.valueOf(1);
			  return temp.doubleValue();
		} else {
			//TODO defailt value
			return BigDecimal.valueOf(1).doubleValue();
		}
	}
	
	/**
	 * Responsible for population of group settings first row for each Employee
	 * 
	 * @param tableData
	 * @param employee
	 * @param department
	 * @param tableHeaders
	 * @throws IOException
	 */
	private double add1thRow(Vector tableData, Employee employee,
			Department department, int allEmployeesCount, Period period, BigInteger emplDept, Double percentAll) throws IOException {

		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add(department.getCode());
		oneRow.add(employee.getFirstName() + " " + employee.getLastName());
		oneRow.add(period.getRevenue().toString());// To be extracted from db
		oneRow.add(allEmployeesCount);// To be provided as param
		int profitAll = period.getRevenue().intValue() - emplDept.intValue();
		oneRow.add(profitAll);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE1));

		oneRow.add(percentAll);
		// TODO comment until it is required
		double personalFactor = 1.0;
		double jobDonePercent = 1.0;
		//oneRow.add("1");
		//oneRow.add("1");

		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusAll(profitAll, percentAll, personalFactor, jobDonePercent, allEmployeesCount);
		oneRow.add(result);
		oneRow.add(calculateTotalAll(period.getRevenue(),percentAll));
		tableData.add(oneRow);
		return result;

	}
	
	private Double calculateTotalAll(BigDecimal revenuPeriond, Double percentAll){
		
		return (revenuPeriond.doubleValue() * percentAll)/100;
	}
	
	private Double calculateBonusAll(int profitAll, double percentAll, double personalFactor, double jobDonePercent, int allEployees ){
		
		return  profitAll * (((percentAll * personalFactor) / allEployees)/(100 * jobDonePercent));
	}

	private double add2thRow(Vector tableData, Employee employee,
			Department department, BigInteger revenueDept, BigInteger emplDept, Double percentGroup) throws IOException {
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add(department.getCode());
		oneRow.add(EMPTY_STRING);
		oneRow.add(revenueDept.toString());// To be extracted from db
		int allEmployeesDept = department.getEmplDeptPeriods().size();
		oneRow.add(allEmployeesDept);// To be provided as
	    int profitGroup = revenueDept.intValue()-emplDept.intValue();
		oneRow.add(profitGroup);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE2));

		oneRow.add(percentGroup);
		// TODO comment until it is required
		//oneRow.add("1");
		//oneRow.add("1");
		double personalFactor = 1.0;
		double jobDonePercent = 1.0;
		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusGroup(profitGroup, percentGroup, personalFactor, jobDonePercent, allEmployeesDept); 
		oneRow.add(result);
		oneRow.add(calculateTotalGroup(revenueDept.intValue()-emplDept.intValue(), percentGroup));
		tableData.add(oneRow);
		return result;
	}
	
	private Double calculateTotalGroup(int base, Double percentGroup){
		
		return (base * percentGroup)/100;
	}
	
	private Double calculateBonusGroup(int profitGroup, double percentGroup, double personalFactor, double jobDonePercent, int allEployeesDept){
		
		return  profitGroup * (((personalFactor * personalFactor) / allEployeesDept)/(100 * jobDonePercent));
	}

	private double add3thRow(Vector tableData, Employee employee,
			Department department, BigInteger revenueEmp, Double percentPersonal) throws IOException {
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add(department.getCode());
		oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);// To be extracted from db
		oneRow.add(EMPTY_STRING);// To be provided as param
		final double profitPersonal = revenueEmp.doubleValue();
		oneRow.add(profitPersonal);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE3));

		oneRow.add(percentPersonal);
		// TODO comment until it is required
		final double personalFactor = 1.0;
		final double jobDonePercent = 1.0;
		//oneRow.add("1");
		//oneRow.add("1");
		// TODO last 2 columns to be token from the Excel
		double result = calculateBonusPersonal(profitPersonal, percentPersonal.doubleValue(), personalFactor, jobDonePercent); 
		oneRow.add(result);
		oneRow.add(calculateTotalPersonal(1, percentPersonal));
		tableData.add(oneRow);
		return result;
	}
	
	private Double calculateTotalPersonal(int base, Double percentPersonal){
		
		return (base * percentPersonal)/100;
	}

	private Double calculateBonusPersonal(double profitPersonal, double percentPersonal, double personalFactor, double jobDonePercent){
		
		return  profitPersonal * (( percentPersonal * personalFactor )/(100 * jobDonePercent));
	}

	private void add4thRow(Vector tableData, Employee employee,
			Department department, double bonus) throws IOException {
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add(department.getCode());
		oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);// To be extracted from db
		oneRow.add(EMPTY_STRING);// To be provided as param
		oneRow.add(EMPTY_STRING);
		//oneRow.add(EMPTY_STRING);

		// FIXME how to extract the follow 3 digits
		//oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE));

		// TODO last 2 columns to be token from the Excel
		oneRow.add(bonus);
		oneRow.add(EMPTY_STRING);
		tableData.add(oneRow);
	}

	private void add5thRow(Vector tableData, Employee employee,
			Department department) throws IOException {
		Vector<Object> oneRow = new Vector<Object>();
		oneRow.add(department.getCode());
		oneRow.add(EMPTY_STRING);
		oneRow.add(EMPTY_STRING);// To be extracted from db
		oneRow.add(EMPTY_STRING);// To be provided as param
		oneRow.add(EMPTY_STRING);
		oneRow.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_COL8_VALUE));

		// FIXME how to extract the follow 3 digits
		oneRow.add("9.5- HOW TO GET THIS PERCENT??");// TO BE detected the formula;
		// TODO comment until it is required
		//oneRow.add(EMPTY_STRING);
		//oneRow.add(EMPTY_STRING);

		// TODO last 2 columns to be token from the Excel
		oneRow.add("DIGIT_HOW TO CALCULATE");
		oneRow.add(EMPTY_STRING);
		tableData.add(oneRow);
	}

	private void addEmptyDelimeterRow(Vector tableData, Employee employee,
			Department department) {

		Vector<Object> oneRow = new Vector<Object>();
		int i = 0;
		// TODO comment until it is required made them 11
		while (i < 9) {
			oneRow.add(EMPTY_STRING);
			i++;
		}
		tableData.add(oneRow);
	}

}
