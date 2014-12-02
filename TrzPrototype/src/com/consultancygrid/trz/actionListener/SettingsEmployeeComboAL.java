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
	private JTabbedPane tabbedPaneSettings;
	
	public SettingsEmployeeComboAL(PrototypeMainFrame mainFrame,JPanel panLinkPeriod2Empl,JComboBox comboBoxPeriod,JComboBox comboBoxDepartment,JComboBox comboBoxEmpl,JTabbedPane tabbedPaneSettings) {

		super(mainFrame);
		this.panLinkPeriod2Empl = panLinkPeriod2Empl;
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxDepartment = comboBoxDepartment;
		this.comboBoxEmployee = comboBoxEmpl;
		this.tabbedPaneSettings = tabbedPaneSettings;
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
			
			JPanel createFormPanel = new JPanel();
			createFormPanel.setLayout(null);
			createFormPanel.setBounds(10, 45 , 1000, 600);
			
			JLabel lblEmpl1 = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_REVENUE));
			lblEmpl1.setBounds(50, 100 , 100, 25);
			createFormPanel.add(lblEmpl1);
			
			Map<String, JTextField>  map = new HashMap<String, JTextField>();
			
			JTextField textFieldValue = new JTextField();
			textFieldValue.setBounds(150, 100 , 200, 25);
			if (initREP != null) {
				textFieldValue.setText(initREP.getRevenue().toString());
			} else {
				textFieldValue.setText("0.0");	
			}
			
			createFormPanel.add(textFieldValue);
			
			JButton btnSavePeriod = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_SAVE));
			btnSavePeriod.setBounds(20, 300, 250 ,25);
			createFormPanel.add(btnSavePeriod);
			
			JLabel lblFieldSettings = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_EMPL_SET));
			lblFieldSettings.setBounds(20, 150 , 200, 25);
			createFormPanel.add(lblFieldSettings);
			
			
			JLabel lblBrutoStat = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_BRUTOSTAT));
			lblBrutoStat.setBounds(50, 200 , 100, 25);
			createFormPanel.add(lblBrutoStat);
			JTextField textBrutoStat = new JTextField();
			textBrutoStat.setBounds(200, 200 , 100, 25);
			createFormPanel.add(textBrutoStat);
			
			
			JLabel lblBrutoStad = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_BRUTOSANDARD));
			lblBrutoStad.setBounds(350, 200 , 100, 25);
			createFormPanel.add(lblBrutoStad);
			JTextField textBrutoStad = new JTextField();
			textBrutoStad.setBounds(520, 200 , 100, 25);
			createFormPanel.add(textBrutoStad);
			
			
			JLabel lblAvans = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_AVANS));
			lblAvans.setBounds(50, 230 , 100, 25);
			createFormPanel.add(lblAvans);
			JTextField textBrutoAvans = new JTextField();
			textBrutoAvans.setBounds(200, 230 , 100, 25);
			createFormPanel.add(textBrutoAvans);
			
			JLabel lblBrutoPercentAll = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_PERCENT_ALL));
			lblBrutoPercentAll.setBounds(350, 230 , 200, 25);
			createFormPanel.add(lblBrutoPercentAll);
			JTextField textPercentAll = new JTextField();
			textPercentAll.setBounds(520, 230 , 100, 25);
			createFormPanel.add(textPercentAll);
			
			
			JLabel lblPercentGroup = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_PERCENT_GROUP));
			lblPercentGroup.setBounds(650, 230 , 100, 25);
			createFormPanel.add(lblPercentGroup);
			JTextField textPercentGroup = new JTextField();
			textPercentGroup.setBounds(810, 230 , 100, 25);
			createFormPanel.add(textPercentGroup);
			
			
			JLabel lblPercentPerson = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_PERSONAL_PERCENT));
			lblPercentPerson.setBounds(50, 260 , 200, 25);
			createFormPanel.add(lblPercentPerson);
			JTextField textPercentPerson = new JTextField();
			textPercentPerson.setBounds(200, 260 , 100, 25);
			createFormPanel.add(textPercentPerson);
			
			
			JLabel lblOnBoardAll = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_ALL));
			lblOnBoardAll.setBounds(350, 260 , 200, 25);
			createFormPanel.add(lblOnBoardAll);
			JTextField textOnBoardAll = new JTextField();
			textOnBoardAll.setBounds(520, 260 , 100, 25);
			createFormPanel.add(textOnBoardAll);
			
			JLabel lblOnBoardGroup = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_GROUP));
			lblOnBoardGroup.setBounds(650, 260 , 200, 25);
			createFormPanel.add(lblOnBoardGroup);
			JTextField textOnBoardGroup = new JTextField();
			textOnBoardGroup.setBounds(810, 260 , 100, 25);
			createFormPanel.add(textOnBoardGroup);
			
						
			
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
			createFormPanel.validate();
			this.panLinkPeriod2Empl.revalidate();
			this.panLinkPeriod2Empl.repaint();
			this.tabbedPaneSettings.revalidate();
			this.tabbedPaneSettings.repaint();
			textOnBoardGroup.validate();
			this.mainFrame.validate();
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.close();
			}
		}
	}
	
	
}
