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
				
				//TODO select for each 
				EmployeeSettings emplSettings = null;
				Vector<Object> oneRow = new Vector<Object>();
				//0
				oneRow.add(emplSal.getPeriod().getCode());
				//1
				oneRow.add(emplSal.getV01());
				//2
				oneRow.add(emplSal.getV02());
				//3
				oneRow.add(emplSal.getV03());
				//4
				oneRow.add(emplSal.getV04());
				//5
				oneRow.add(emplSal.getV05());
				//6
				oneRow.add(Constants.EMPTY_STRING);
				//7
				oneRow.add(emplSal.getV06());
				//8
				oneRow.add(emplSal.getV07());
				//9
				oneRow.add(emplSal.getV08());
				//10
				oneRow.add(emplSal.getV09());
				//11
				oneRow.add(emplSal.getV10());
				//12
				oneRow.add(emplSal.getV11());
				//13
				oneRow.add(Constants.EMPTY_STRING);
				//14
				oneRow.add(emplSal.getV12());
				//15
				oneRow.add(emplSal.getV13());
				//16
				oneRow.add(emplSal.getV14());
				//17
				oneRow.add(emplSal.getV15());
				//18
				oneRow.add(emplSal.getV16());
				//19
				oneRow.add(Constants.EMPTY_STRING);
				//20
				oneRow.add(emplSal.getV17());
				//21
				oneRow.add(emplSal.getV18());
				//22
				oneRow.add(emplSal.getV19());
				//23
				oneRow.add(Constants.EMPTY_STRING);
				//24
				oneRow.add(emplSal.getV20());
				//25
				oneRow.add(emplSal.getV21());
				//26
				oneRow.add(emplSal.getV22());
				//27
				oneRow.add(emplSal.getV23());
				//28
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
	
	
	
	private Double calculateTotalAll(BigDecimal revenuPeriond, Double percentAll){
		
		return (revenuPeriond.doubleValue() * percentAll)/100;
	}
	
	private Double calculateBonusAll(int profitAll, double percentAll, double personalFactor, double jobDonePercent, int allEployees ){
		
		return  profitAll * (((percentAll * personalFactor) / allEployees)/(100 * jobDonePercent));
	}

	
	
	private Double calculateTotalGroup(int base, Double percentGroup){
		
		return (base * percentGroup)/100;
	}
	
	private Double calculateBonusGroup(int profitGroup, double percentGroup, double personalFactor, double jobDonePercent, int allEployeesDept){
		
		return  profitGroup * (((personalFactor * personalFactor) / allEployeesDept)/(100 * jobDonePercent));
	}

	
	
	private Double calculateTotalPersonal(int base, Double percentPersonal){
		
		return (base * percentPersonal)/100;
	}

	private Double calculateBonusPersonal(double profitPersonal, double percentPersonal, double personalFactor, double jobDonePercent){
		
		return  profitPersonal * (( percentPersonal * personalFactor )/(100 * jobDonePercent));
	}

	

	

	

}
