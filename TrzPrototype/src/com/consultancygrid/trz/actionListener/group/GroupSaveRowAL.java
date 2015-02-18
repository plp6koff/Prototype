package com.consultancygrid.trz.actionListener.group;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.EmployeeSallaryCalculateUtil;
import com.consultancygrid.trz.util.GroupTablPeriodLoaderUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class GroupSaveRowAL extends BaseActionListener {

	private Logger logger = Logger.getLogger(GroupSaveRowAL.class);
	
	private PersonalCfgEmplsTable personalConfTable;
	private GroupCfgEmplsTable groupConfTable1;
	private GroupCfgEmplsTable groupConfTable2;
	private JComboBox<Period> comboBoxPeriod;
	private JComboBox<Employee> comboBoxEmployee;
	private JFrame frame;
	private JTabbedPane tabbedPaneDep;

	private HashMap<String, JTextField> map;

	public GroupSaveRowAL(PrototypeMainFrame mainFrame,
			GroupCfgEmplsTable groupConfTable1,
			GroupCfgEmplsTable groupConfTable2,
			JComboBox<Period> comboBoxPeriod,
			JComboBox<Employee> comboBoxEmployee, 
			HashMap<String, JTextField> map,
			JFrame frame, JTabbedPane tabbedPaneDep,
			PersonalCfgEmplsTable personalConfTable) {

		super(mainFrame);
		this.groupConfTable1 = groupConfTable1;
		this.groupConfTable2 = groupConfTable2;
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxEmployee = comboBoxEmployee;
		this.map = map;
		this.frame = frame;
		this.tabbedPaneDep = tabbedPaneDep;
		this.personalConfTable = personalConfTable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		EntityManagerFactory factory = null;
		EntityManager em = null;
		GroupCfgEmplsTableModel model = null;
		GroupCfgEmplsTable groupConfTable = null;
		GroupTablPeriodLoaderUtil grTabPeriodLoaderUtil = new GroupTablPeriodLoaderUtil();
		GroupCfgEmplsTableModel currentModel = null;
		Double bonusAll = 0.0d;
		Double bonusGroup = 0.0d;
		Double bonusPersonal = 0.0d;
		Period period = (Period) comboBoxPeriod.getSelectedItem();
		String nameRaw1 = null;
		String nameRaw2 = null;
		int i = -1;
		try {

			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			em = factory.createEntityManager();
			em.getTransaction().begin();


			if (tabbedPaneDep.getSelectedIndex() == 0) {

				groupConfTable = groupConfTable1;
			} else if (tabbedPaneDep.getSelectedIndex() == 1) {
				groupConfTable = groupConfTable2;
			}
			i = groupConfTable.getSelectedRow();
			model = (GroupCfgEmplsTableModel) groupConfTable.getModel();

			if (period != null) {

				String nameRaw = (String) model.getValueAt(i, 1);
				nameRaw1 = nameRaw.substring(0, nameRaw.indexOf(" "));
				nameRaw2 = nameRaw.substring(nameRaw.indexOf(" ") + 1,
						nameRaw.length());

				Query q1 = em
						.createQuery("select settings from EmployeeSettings as settings join settings.employee as empl  where "
								+ "  empl.firstName = :firstName and empl.lastName = :lastName and settings.period.id = :periodId  ");

				q1.setParameter("periodId", period.getId());
				q1.setParameter("firstName", nameRaw1);
				q1.setParameter("lastName", nameRaw2);

				List<EmployeeSettings> allSettings = (List<EmployeeSettings>) q1
						.getResultList();

				if (allSettings != null && !allSettings.isEmpty()) {

					EmployeeSettings settings = allSettings.get(0);

					String percentAll = map.get(
							LabelsConstants.GROUP_CONF_COL5_VALUE1).getText();
					String percentGroup = map.get(
							LabelsConstants.GROUP_CONF_COL5_VALUE2).getText();
					String percentPersonal = map.get(
							LabelsConstants.GROUP_CONF_COL5_VALUE3).getText();
					String onBoardAll = map.get(
							LabelsConstants.GROUP_CONF_COL5_VALUE1 + "-"
									+ LabelsConstants.GROUP_CONF_HEADER_COL7)
							.getText();
					String onBoardGroup = map.get(
							LabelsConstants.GROUP_CONF_COL5_VALUE2 + "-"
									+ LabelsConstants.GROUP_CONF_HEADER_COL7)
							.getText();

					BigDecimal pABD = parseValue(settings.getPercentAll(),
							percentAll);
					settings.setPercentAll(pABD);
					BigDecimal pGBD = parseValue(settings.getPercentGroup(),
							percentGroup);
					settings.setPercentGroup(pGBD);
					BigDecimal pPBD = parseValue(settings.getPercentPersonal(),
							percentPersonal);
					settings.setPercentPersonal(pPBD);

					BigDecimal pAOnBPBD = parseValue(
							settings.getPersonAllOnboardingPercent(),
							onBoardAll);
					settings.setPersonAllOnboardingPercent(pAOnBPBD);
					BigDecimal pGOnBPBD = parseValue(
							settings.getPersonGroupOnboardingPercent(),
							onBoardGroup);
					settings.setPersonGroupOnboardingPercent(pGOnBPBD);

					currentModel = (GroupCfgEmplsTableModel) groupConfTable
							.getModel();
					Vector<Object> rowDataI = currentModel.getRowData(i);
					// i
					int profitAll = Integer.valueOf(currentModel.getValueAt(i,
							4).toString());
					int allEmployees = Integer.valueOf(currentModel.getValueAt(
							i, 3).toString());
					currentModel.setValueAt(percentAll.toString(), i, 6);
					rowDataI.set(6, percentAll.toString());
					currentModel.setValueAt(onBoardAll, i, 7);
					rowDataI.set(7, onBoardAll);
					bonusAll = grTabPeriodLoaderUtil.calculateBonusAll(
							profitAll, pABD.doubleValue(),
							pAOnBPBD.doubleValue(), 1.0d, allEmployees);
					currentModel.setValueAt(bonusAll.toString(), i, 8);
					rowDataI.set(8, bonusAll.toString());

					double allTotal = grTabPeriodLoaderUtil.getEmployeePercentAllOnboard(settings);
					currentModel.setValueAt(allTotal, i, 9);
					rowDataI.set(9, allTotal);

					i = i + 1;
					Vector<Object> rowDataI1 = currentModel.getRowData(i);
					int allEployeesDept = Integer.valueOf(currentModel
							.getValueAt(i, 3).toString());
					double profitGroup = Double.valueOf(currentModel
							.getValueAt(i, 4).toString());
					double revenueDept = Double.valueOf(currentModel
							.getValueAt(i, 2).toString());
					currentModel.setValueAt(percentGroup, i, 6);
					rowDataI1.set(6, percentGroup);
					currentModel.setValueAt(onBoardGroup, i, 7);
					rowDataI1.set(7, onBoardGroup);
					bonusGroup = grTabPeriodLoaderUtil
							.calculateBonusGroup(profitGroup,
									pGBD.doubleValue(), pGOnBPBD.doubleValue(),
									1.0d, allEployeesDept);
					currentModel.setValueAt(bonusGroup.toString(), i, 8);
					rowDataI1.set(8, bonusGroup);

					i = i + 1;
					Vector<Object> rowDataI2 = currentModel.getRowData(i);
					double profitPersonal = Double.valueOf(currentModel
							.getValueAt(i, 4).toString());
					currentModel.setValueAt(percentPersonal, i, 6);
					rowDataI2.set(6, percentPersonal);
					bonusPersonal = grTabPeriodLoaderUtil
							.calculateBonusPersonal(profitPersonal,
									pPBD.doubleValue(), 1.0d, 1.0d);
					currentModel.setValueAt(bonusPersonal.toString(), i, 8);
					rowDataI2.set(8, bonusPersonal.toString());

					double groupTotal = grTabPeriodLoaderUtil
							.calculateTotalGroup(revenueDept - profitPersonal,
									pGBD.doubleValue());
					rowDataI1.set(9, groupTotal);
					double personalTotal = grTabPeriodLoaderUtil
							.calculateTotalPersonal(1, pPBD.doubleValue());
					personalTotal = BigDecimal.valueOf(personalTotal)
							.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					rowDataI2.set(9, personalTotal);

					i = i + 1;
					Vector<Object> rowDataI4 = currentModel.getRowData(i);
					double totalBonus = bonusAll + bonusGroup + bonusPersonal;
					currentModel.setValueAt(BigDecimal.valueOf(totalBonus)
							.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue(), i, 8);
					rowDataI4.set(8, totalBonus);

					i = i + 1;
					Vector<Object> rowDataI5 = currentModel.getRowData(i);
					double totalPercent = (totalBonus * 100) / profitPersonal;
					currentModel.setValueAt(BigDecimal.valueOf(totalPercent)
							.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue(), i, 5);
					rowDataI5.set(
							5,
							BigDecimal.valueOf(totalPercent)
									.setScale(2, BigDecimal.ROUND_HALF_UP)
									.doubleValue());
					em.merge(settings);

				}


			} else {
				JOptionPane.showMessageDialog(mainFrame, ResourceLoaderUtil
						.getLabels(LabelsConstants.PERSONAL_CFG_WARN2),
						ResourceLoaderUtil
								.getLabels(LabelsConstants.ALERT_MSG_WARN),
						JOptionPane.WARNING_MESSAGE);
				return;
			}

		} catch (Exception e1) {

			logger.error(e1);
		} finally {
			if (em != null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
				factory.close();
			}
		}
		comboBoxEmployee.setSelectedIndex(0);
		saveSalary(bonusAll, bonusGroup, bonusPersonal, period, nameRaw1, nameRaw2);
		currentModel.fireTableRowsUpdated(i - 3, i + 2);
		currentModel.fireTableDataChanged();
		groupConfTable.repaint();
		((PersonalCfgEmplsTableModel)personalConfTable.getModel()).clearData();
		frame.setVisible(false);
		mainFrame.validate();

	}

	private void saveSalary(Double bonusAll, Double bonusGroup,
			Double bonusPersonal, Period period, String firstName,
			String lastName) {

		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {

			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			em = factory.createEntityManager();
			em.getTransaction().begin();

			Query qSal = em
					.createQuery("select sal from EmployeeSalary as sal  join sal.employee as empl  where "
							+ "  empl.firstName = :firstName and empl.lastName = :lastName and sal.period.id = :periodId");
			qSal.setParameter("periodId", period.getId());
			qSal.setParameter("firstName", firstName);
			qSal.setParameter("lastName", lastName);

			List<EmployeeSalary> allSallaries = (List<EmployeeSalary>) qSal
					.getResultList();
			EmployeeSalary salary = allSallaries.get(0);
			
			
			Query qPeriodTrzStatic = em
					.createQuery(" from PeriodSetting as pS where period.id = :periodId");
			qPeriodTrzStatic.setParameter("periodId", period.getId());
			
			List<PeriodSetting> settings = (List<PeriodSetting>) qPeriodTrzStatic.getResultList();
			
			TrzStatic DOD = null;
			TrzStatic OSIGUROVKI_RABOTODATEL = null;
			TrzStatic OSIGUROVKI_SLUJITEL = null;
			Double dodValue = 0.0d;
			Double oRabotodatelValue = 0.0d;
			Double oSlujitelValue = 0.0d;
			for (PeriodSetting singlePS : settings) {
				
				TrzStatic singleTrz = singlePS.getTrzStatic();
				
				if ("DOD".equals(singleTrz.getKey())) {
					DOD = singleTrz;
					dodValue = Double.valueOf(singlePS.getValue());
				} else if("OSIGUROVKI_RABOTODATEL".equals(singleTrz.getKey())) {
					OSIGUROVKI_RABOTODATEL = singleTrz;
					oRabotodatelValue = Double.valueOf(singlePS.getValue());
				} else if("OSIGUROVKI_SLUJITEL".equals(singleTrz.getKey())) {
					OSIGUROVKI_SLUJITEL = singleTrz;
					oSlujitelValue = Double.valueOf(singlePS.getValue());
				}
			}
			
			EmployeeSallaryCalculateUtil.updateSettings(
					bonusPersonal,
					bonusGroup,
					bonusAll,
					salary, 
					DOD, OSIGUROVKI_RABOTODATEL, OSIGUROVKI_SLUJITEL,
					dodValue, oRabotodatelValue, oSlujitelValue);
			em.merge(salary);
		} catch (Exception e1) {
			
			logger.error(e1);
		} finally {
			if (em != null && em.isOpen()) {
				try {
					if (em.isJoinedToTransaction()) {
						em.getTransaction().commit();
					}
				} catch (Exception e){
					logger.error(e);
				}
				em.close();
				factory.close();
			}
		}
	}

	private BigDecimal parseValue(BigDecimal initValue, String newValStr) {

		try {
			double parsedDoub = Double.valueOf(newValStr);
			return BigDecimal.valueOf(parsedDoub);
		} catch (Exception e) {
			return initValue;
		}
	}
}
