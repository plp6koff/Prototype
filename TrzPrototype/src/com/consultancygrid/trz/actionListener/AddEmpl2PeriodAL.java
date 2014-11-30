package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;
import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;

public class AddEmpl2PeriodAL extends BaseActionListener{

	private JComboBox comboBoxPeriod;
	private JComboBox comboBoxDepartment;
	private JComboBox comboBoxEmployee;
	private JTextField fieldRevenue;
	private EmployeeSettings initSettings;
	private Map<String,JTextField> map ;
	
	public AddEmpl2PeriodAL(PrototypeMainFrame mainFrame,
			JComboBox comboBoxPeriod,
			JComboBox comboBoxDepartment, 
			JComboBox comboBoxEmployee, 
			JTextField fieldRevenue, 	
			Map<String,JTextField> map,
			EmployeeSettings initSettings
			) {
		
		super(mainFrame);
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxDepartment = comboBoxDepartment;
		this.comboBoxEmployee = comboBoxEmployee;
		this.fieldRevenue = fieldRevenue;
		this.map = 	map; 
		this.initSettings = initSettings;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Period period = ((Period) comboBoxPeriod.getModel().getSelectedItem());
			Employee empl = ((Employee) comboBoxEmployee.getModel().getSelectedItem());
			Department dept = ((Department) comboBoxDepartment.getModel().getSelectedItem());

			Query q = em.createQuery(" from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.dateEnd desc");
			q.setParameter("employeeId", empl.getId());
			List<EmployeeSalary> emplSals = (List<EmployeeSalary>) q.getResultList();
			EmplDeptPeriod eDP = new EmplDeptPeriod();
			eDP.setDepartment(dept);
			eDP.setEmployee(empl);
			eDP.setPeriod(period);
			
			RevenueEmplPeriod rEP = new RevenueEmplPeriod();
			rEP.setEmployee(empl);
			rEP.setPeriod(period);
			rEP.setRevenue(BigDecimal.valueOf(Double.valueOf(fieldRevenue.getText())));
			em.persist(rEP);
			
			JTextField textBrutoStat = map.get(LabelsConstants.SET_TAB_EMPL2PER_BRUTOSTAT);
			JTextField textBrutoStad = map.get(LabelsConstants.SET_TAB_EMPL2PER_BRUTOSANDARD);
			JTextField textBrutoAvans = map.get(LabelsConstants.SET_TAB_EMPL2PER_AVANS);
			JTextField textPercentAll = map.get(LabelsConstants.SET_TAB_EMPL2PER_PERCENT_ALL);
			JTextField textPercentGroup = map.get(LabelsConstants.SET_TAB_EMPL2PER_PERCENT_GROUP);
			JTextField textPercentPerson = map.get(LabelsConstants.SET_TAB_EMPL2PER_PERSONAL_PERCENT);
			JTextField  textOnBoard = map.get(LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD);
			
			
			EmployeeSettings settings =   new EmployeeSettings();
			settings.setPeriod(period);
			settings.setEmployee(empl);
			settings.setAvans(parseValue(BigDecimal.ZERO,textBrutoAvans.getText()));
			settings.setBrutoPoShtat(parseValue(BigDecimal.ZERO,textBrutoStat.getText()));
			settings.setBrutoStandart(parseValue(BigDecimal.ZERO,textBrutoStad.getText()));
			settings.setPercentGroup(parseValue(BigDecimal.ZERO,textPercentGroup.getText()));
			settings.setPercentPersonal(parseValue(BigDecimal.ZERO,textPercentPerson.getText()));
			settings.setPercentAll(parseValue(BigDecimal.ZERO,textOnBoard.getText()));
			//TODO
			BigDecimal tempOnBoard = parseValue(BigDecimal.ZERO,textBrutoAvans.getText());
			if (BigDecimal.ONE.compareTo(tempOnBoard) == 1) {
				BigDecimal tempToAdd = parseValue(BigDecimal.ZERO,"0.15");
				settings.setPersonOnboardingPercentage(tempOnBoard.add(tempToAdd));
			} else {
				settings.setPersonOnboardingPercentage(tempOnBoard);
			}
			em.persist(settings);
				
				
			EmployeeSalary salary = new EmployeeSalary();
			if (emplSals != null && !emplSals.isEmpty()) {
				
				EmployeeSalary lastSalary = emplSals.get(0);
				salary.setV01(lastSalary.getV01());
				salary.setV02(lastSalary.getV02());
				salary.setV03(lastSalary.getV03());
				salary.setV04(lastSalary.getV04());
				salary.setV05(lastSalary.getV05());
				salary.setV06(lastSalary.getV06());
				salary.setV07(lastSalary.getV07());
				salary.setV08(lastSalary.getV08());
				salary.setV09(lastSalary.getV09());
				salary.setV10(lastSalary.getV10());
				salary.setV11(lastSalary.getV11());
				salary.setV12(lastSalary.getV12());
				salary.setV13(lastSalary.getV13());
				salary.setV14(lastSalary.getV14());
				salary.setV15(lastSalary.getV15());
				salary.setV16(lastSalary.getV16());
				salary.setV17(lastSalary.getV17());
			    salary.setV18(lastSalary.getV18());
			    salary.setV19(lastSalary.getV19());
			    salary.setV20(lastSalary.getV20());
			    salary.setV21(lastSalary.getV21());
			    salary.setV22(lastSalary.getV22());
			    salary.setV23(lastSalary.getV23());
				
				salary.setEmployee(empl);
				salary.setPeriod(period);
			} 
			em.persist(salary);
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
			}
		}	
	}
	
	private BigDecimal parseValue(BigDecimal initValue, String newValStr) {
		
		BigDecimal result = initValue;
		try {
			double parsedDoub = Double.valueOf(newValStr);
			return BigDecimal.valueOf(parsedDoub);
		} catch (Exception e)  {
			 return initValue;
		}
	}
}
