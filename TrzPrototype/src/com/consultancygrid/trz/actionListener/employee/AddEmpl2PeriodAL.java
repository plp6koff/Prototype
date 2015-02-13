package com.consultancygrid.trz.actionListener.employee;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

/**
 * Action listener for add employee to period and department
 * @author a.palapeshkov@gmail.com
 *
 */
public class AddEmpl2PeriodAL extends BaseActionListener{

	private JPanel panLinkPeriod2Empl;
	private JPanel createForm;
	private JComboBox comboBoxPeriod;
	private JComboBox comboBoxDepartment;
	private JComboBox comboBoxEmployee;
	private JTextField fieldRevenue;
	private EmployeeSettings initSettings;
	private RevenueEmplPeriod initREP;
	private Map<String,JTextField> map ;
	
	public AddEmpl2PeriodAL(PrototypeMainFrame mainFrame,
			JComboBox comboBoxPeriod,
			JComboBox comboBoxDepartment, 
			JComboBox comboBoxEmployee, 
			JTextField fieldRevenue, 	
			Map<String,JTextField> map,
			EmployeeSettings initSettings,
			RevenueEmplPeriod initREP,
			JPanel panLinkPeriod2Empl,
			JPanel createForm
			) {
		
		super(mainFrame);
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxDepartment = comboBoxDepartment;
		this.comboBoxEmployee = comboBoxEmployee;
		this.fieldRevenue = fieldRevenue;
		this.map = 	map; 
		this.initSettings = initSettings;
		this.initREP = initREP;
		this.panLinkPeriod2Empl = panLinkPeriod2Empl;
		this.createForm = createForm;
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
			
			createEmplDeptPeriod(em, period, empl, dept);
			createSettings(em, period, empl);
			createSalary(em, period, empl);
				
			reinitForm(em);	
			try {
				JOptionPane.showMessageDialog(mainFrame, JOptionPane.INFORMATION_MESSAGE ,
						 ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_SUCCESS), JOptionPane.INFORMATION_MESSAGE);
			} catch (HeadlessException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} catch (Exception e1) {
			Logger.error(e1);
			try {
				JOptionPane.showMessageDialog(mainFrame, JOptionPane.ERROR_MESSAGE ,
						 ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_ERROR), JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (em!= null && em.isOpen()) {
				em.getTransaction().rollback();
				em.close();
			}
		} finally {
			if (em!= null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
			}
		}	
	}
	
	private void reinitForm(EntityManager em) {
		
		createForm.setVisible(false);
		this.panLinkPeriod2Empl.remove(createForm);
		
		EmplComboBoxModel empModel = (EmplComboBoxModel) this.comboBoxEmployee.getModel();
		
		this.comboBoxEmployee.setEnabled(false);
		this.comboBoxDepartment.setEnabled(false);
		Query qPeriod = em.createQuery(" from Period");
		List<Period> allPeriods = (List<Period>) qPeriod.getResultList();
		this.comboBoxPeriod.setModel(new PeriodComboBoxModel(allPeriods));
		this.panLinkPeriod2Empl.validate();
		this.panLinkPeriod2Empl.repaint();
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
	
	private void createEmplDeptPeriod(EntityManager em, Period period,Employee empl, Department dept) {
		
		EmplDeptPeriod eDP = new EmplDeptPeriod();
		eDP.setDepartment(dept);
		eDP.setEmployee(empl);
		eDP.setPeriod(period);
		
		if (initREP != null ) {
			initREP.setRevenue(BigDecimal.valueOf(Double.valueOf(fieldRevenue.getText())));
			em.merge(initREP);
		} else {
			RevenueEmplPeriod rEP = new RevenueEmplPeriod();
			rEP.setEmployee(empl);
			rEP.setPeriod(period);
			rEP.setRevenue(BigDecimal.valueOf(Double.valueOf(fieldRevenue.getText())));
			em.persist(rEP);
		}
	}
	
	private void createSettings(EntityManager em, Period period,Employee empl) {
		
		JTextField textBrutoStat = map.get(LabelsConstants.SET_TAB_EMPL2PER_BRUTOSTAT);
		JTextField textBrutoStad = map.get(LabelsConstants.SET_TAB_EMPL2PER_BRUTOSANDARD);
		JTextField textBrutoAvans = map.get(LabelsConstants.SET_TAB_EMPL2PER_AVANS);
		JTextField textPercentAll = map.get(LabelsConstants.SET_TAB_EMPL2PER_PERCENT_ALL);
		JTextField textPercentGroup = map.get(LabelsConstants.SET_TAB_EMPL2PER_PERCENT_GROUP);
		JTextField textPercentPerson = map.get(LabelsConstants.SET_TAB_EMPL2PER_PERSONAL_PERCENT);
		JTextField  textOnBoard_all = map.get(LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_ALL);
		JTextField  textOnBoard_group = map.get(LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_GROUP);
		
		
		EmployeeSettings settings =   new EmployeeSettings();
		settings.setPeriod(period);
		settings.setEmployee(empl);
		settings.setAvans(parseValue(BigDecimal.ZERO,textBrutoAvans.getText()));
		settings.setBrutoPoShtat(parseValue(BigDecimal.ZERO,textBrutoStat.getText()));
		settings.setBrutoStandart(parseValue(BigDecimal.ZERO,textBrutoStad.getText()));
		settings.setPercentGroup(parseValue(BigDecimal.ZERO,textPercentGroup.getText()));
		settings.setPercentPersonal(parseValue(BigDecimal.ZERO,textPercentPerson.getText()));
		settings.setPercentAll(parseValue(BigDecimal.ZERO,textPercentAll.getText()));
		BigDecimal tempOnBoard_all = parseValue(BigDecimal.ZERO,textOnBoard_all.getText());
		if (BigDecimal.ONE.compareTo(tempOnBoard_all) == 1) {
			BigDecimal tempToAdd = parseValue(BigDecimal.ZERO,"0.15");
			settings.setPersonAllOnboardingPercent(tempOnBoard_all.add(tempToAdd));
		} else {
			settings.setPersonAllOnboardingPercent(tempOnBoard_all);
		}
		BigDecimal tempOnBoard_gr = parseValue(BigDecimal.ZERO,textOnBoard_group.getText());
		if (BigDecimal.ONE.compareTo(tempOnBoard_gr) == 1) {
			BigDecimal tempToAdd = parseValue(BigDecimal.ZERO,"0.15");
			settings.setPersonGroupOnboardingPercent(tempOnBoard_gr.add(tempToAdd));
		} else {
			settings.setPersonGroupOnboardingPercent(tempOnBoard_gr);
		}
		em.persist(settings);
	}
	
	
	
	private void createSalary(EntityManager em, Period period, Employee empl) {
		
		Query q = em.createQuery(" from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.dateEnd desc");
		q.setParameter("employeeId", empl.getId());
		List<EmployeeSalary> emplSals = (List<EmployeeSalary>) q.getResultList();
		
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
		} 
		salary.setEmployee(empl);
		salary.setPeriod(period);
		em.persist(salary);
	}
}
