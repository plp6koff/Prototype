package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.TrzComboBoxModel;
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

public class SettingsEmployeeComboAL extends BaseActionListener {

	
	private JPanel panLinkPeriod2Empl;
	private JComboBox comboBoxPeriod;
	private JComboBox comboBoxDepartment;
	private JComboBox comboBoxEmployee;
	private JPanel createFormPanel;
	
	public SettingsEmployeeComboAL(
			 PrototypeMainFrame mainFrame, 
			 JPanel panLinkPeriod2Empl, 
			 JComboBox comboBoxPeriod,
			 JComboBox comboBoxDepartment,
			 JComboBox comboBoxEmpl,
			 JPanel createFormPanel) {

		super(mainFrame);
		this.panLinkPeriod2Empl = panLinkPeriod2Empl;
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxDepartment = comboBoxDepartment;
		this.comboBoxEmployee = comboBoxEmpl;
		this.createFormPanel = createFormPanel;
	}

	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();

			em.getTransaction().begin();

			Period period = ((Period) comboBoxPeriod.getModel().getSelectedItem());
			Employee empl = ((Employee) comboBoxEmployee.getModel().getSelectedItem());

			Query q = em.createQuery(" from EmployeeSettings as settings  where  settings.employee.id = :employeeId order by settings.period.dateEnd desc");
			q.setParameter("employeeId", empl.getId());
			List<EmployeeSettings> emplSettingsList = (List<EmployeeSettings>) q.getResultList();
			EmployeeSettings initSettings = null;
			if (emplSettingsList != null && !emplSettingsList.isEmpty()) {
				initSettings = emplSettingsList.get(0);
			}
			Query q1 = em.createQuery(" from RevenueEmplPeriod as rEP  where  rEP.employee.id = :employeeId and rEP.period.id = :periodId");
			q1.setParameter("employeeId", empl.getId());
			q1.setParameter("periodId", period.getId());
			List<RevenueEmplPeriod> initRevenueEmplPeriods = (List<RevenueEmplPeriod>) q1.getResultList();
			RevenueEmplPeriod initREP = (initRevenueEmplPeriods!=null && !initRevenueEmplPeriods.isEmpty()) ? initRevenueEmplPeriods.get(0) : null;
			
			
			createFormPanel.setVisible(true);
			JTextField textFieldValue = (JTextField) createFormPanel.getComponent(1);
			JTextField textBrutoStat = (JTextField) createFormPanel.getComponent(5);
			JTextField textBrutoStad = (JTextField) createFormPanel.getComponent(7);
			JTextField textBrutoAvans = (JTextField) createFormPanel.getComponent(9);
			JTextField textPercentAll = (JTextField) createFormPanel.getComponent(11);
			JTextField textPercentGroup = (JTextField) createFormPanel.getComponent(13);
			JTextField textPercentPerson = (JTextField) createFormPanel.getComponent(15);
			JTextField textOnBoardAll = (JTextField) createFormPanel.getComponent(17);
			JTextField textOnBoardGroup = (JTextField) createFormPanel.getComponent(19);
			
			JButton btnSavePeriod = (JButton) createFormPanel.getComponent(2);
			
			if (initREP != null) {
				textFieldValue.setText(initREP.getRevenue().toString());
			}
			if (initSettings != null) {
				textBrutoStat.setText(initSettings.getBrutoPoShtat().toString());
				textBrutoStad.setText(initSettings.getBrutoStandart().toString());
				textBrutoAvans.setText(initSettings.getAvans().toString());
				textPercentAll.setText(initSettings.getPercentAll().toString());
				textPercentGroup.setText(initSettings.getPercentGroup().toString());
				textPercentPerson.setText(initSettings.getPercentPersonal().toString());
				textOnBoardAll.setText(initSettings.getPersonAllOnboardingPercent() != null ? initSettings.getPersonAllOnboardingPercent().toString() : "1.0");
				textOnBoardGroup.setText(initSettings.getPersonGroupOnboardingPercent() != null ? initSettings.getPersonGroupOnboardingPercent().toString() : "1.0");
			}
			
			Map<String, JTextField>  map = new HashMap<String, JTextField>();
			
			map.put(LabelsConstants.SET_TAB_EMPL2PER_BRUTOSTAT, textBrutoStat);
			map.put(LabelsConstants.SET_TAB_EMPL2PER_BRUTOSANDARD, textBrutoStad);
			map.put(LabelsConstants.SET_TAB_EMPL2PER_AVANS, textBrutoAvans);
			map.put(LabelsConstants.SET_TAB_EMPL2PER_PERCENT_ALL, textPercentAll);
			map.put(LabelsConstants.SET_TAB_EMPL2PER_PERCENT_GROUP, textPercentGroup);
			map.put(LabelsConstants.SET_TAB_EMPL2PER_PERSONAL_PERCENT, textPercentPerson);
			map.put(LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_ALL, textOnBoardAll);
			map.put(LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_GROUP, textOnBoardGroup);
			
			btnSavePeriod.addActionListener(new AddEmpl2PeriodAL(mainFrame, comboBoxPeriod, comboBoxDepartment, comboBoxEmployee, 
																 textFieldValue, map, initSettings, initREP,
																 panLinkPeriod2Empl,
																 createFormPanel));
			this.panLinkPeriod2Empl.add(createFormPanel);
			
			this.createFormPanel.repaint();
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.close();
			}
		}
	}
	
	
}
