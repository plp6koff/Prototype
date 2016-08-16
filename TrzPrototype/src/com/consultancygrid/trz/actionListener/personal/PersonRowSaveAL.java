package com.consultancygrid.trz.actionListener.personal;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.data.StaticDataCache;
import com.consultancygrid.trz.data.TrzStaticData;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.EmployeeSallaryCalculateUtil;
import com.consultancygrid.trz.util.EmplsSettingsLoadUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class PersonRowSaveAL extends BaseActionListener{

	private PersonalCfgEmplsTable personalConfTable;
	private JFrame popup;
	private JComboBox<Employee> comboBoxEmployee;
	private JComboBox<String> comboBoxYear;
	PersonalCfgEmplsTableModel model;
	private HashMap<String, Object> map ;
	
	public PersonRowSaveAL(
				PrototypeMainFrame mainFrame,
				PersonalCfgEmplsTable personalConfTable, 
				JComboBox<Employee> comboBoxEmployee,  
				JComboBox<String> comboBoxYear,
				HashMap<String, Object> map,
				JFrame popup) {
		super(mainFrame);
		this.personalConfTable = personalConfTable;
		this.comboBoxEmployee = comboBoxEmployee;
		this.comboBoxYear = comboBoxYear;
		this.map = map;
		this.popup = popup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Employee employee =  (Employee)comboBoxEmployee.getSelectedItem();
		String year = (String) comboBoxYear.getSelectedItem();
		model = (PersonalCfgEmplsTableModel) personalConfTable.getModel();
		
		init();
		int i = personalConfTable.getSelectedRow();
		EmployeeSalary emplSallary = model.getEmplSals().get(i);
		//Transaction 1
		saveOperation(employee, emplSallary, year);
		//Transaction 2
		loadOpeartion(employee, emplSallary, year);
		
	}
	
	/**
	 * The real save action
	 * @param employee
	 * @param year
	 */
	private void saveOperation(Employee employee, EmployeeSalary emplSallary, String year) {
		
		try {
			
			openTransaction();
			
			if (employee != null) {
				String v01Str = ((JTextField)map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL1)).getText();
				String v03Str = ((JTextField)map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL3)).getText();
				String v10Str = ((JTextField)map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL10)).getText();
				String v13Str = ((JTextField)map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL13)).getText();
				String v14Str = ((JTextField)map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL14)).getText();
				String v19Str = ((JTextField)map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL20)).getText();
				String vNotesStr = ((JTextArea)map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL22)).getText();
				
				final String SQL2FindSett1 
					= " from EmployeeSettings as settings  where  settings.period.id = :periodId and settings.employee.id = :emplId ";
				final String SQL2FindSett2 
				= " from EmployeeSettings as settings  where  settings.period.code like :periodCode and settings.employee.id = :emplId ";
				
				Query q1 = null;
				
				if (year == null || "".equals(year)) {
					q1 = em.createQuery(SQL2FindSett1);
					q1.setParameter("periodId", emplSallary.getPeriod().getId());
				} else {
					q1 = em.createQuery(SQL2FindSett2);
					q1.setParameter("periodCode", "%"+year+"%");
				}
				q1.setParameter("emplId", employee.getId());
				
				EmployeeSettings settings = ((List<EmployeeSettings>) q1.getResultList()).get(0);
				
				
				final BigDecimal v1 = parseValue(emplSallary.getV01(),v01Str); 
				final Double b = v1.doubleValue();
				emplSallary.setV01(v1);
				settings.setBrutoStandart(v1);
				
				final BigDecimal v3 = parseValue(emplSallary.getV03(),v03Str); 
				final Double d = v3.doubleValue();
				emplSallary.setV03(v3);
				settings.setNetSalary(v3);
				
				final BigDecimal v10 = parseValue(emplSallary.getV10(),v10Str); 
				final Double kMarker = v10.doubleValue();
				emplSallary.setV10(v10);
				
				final BigDecimal v13 = parseValue(emplSallary.getV13(),v13Str); 
				final Double nBonus = v13.doubleValue();
				emplSallary.setV13(v13);
				
				final String oBonusName = v14Str;
				emplSallary.setS01(v14Str);
				
				
				final BigDecimal v19 = parseValue(emplSallary.getV19(),v19Str); 
				final Double avans = v19.doubleValue();
				emplSallary.setV19(v19);
				
				if(vNotesStr == null || "".equals(vNotesStr)) {
					vNotesStr = emplSallary.getS02();
				} else {
					emplSallary.setS02(vNotesStr);
				}
				
				TrzStaticData trzStatData 
							= StaticDataCache.getData(emplSallary.getPeriod().getId(), em);
				EmployeeSallaryCalculateUtil.calcSettings(
						 b, 
						 d, 
						 kMarker,
						 nBonus, 
						 oBonusName, 
						 emplSallary, 
						 v19, 
						 vNotesStr, 
						 trzStatData);
				
				em.merge(emplSallary);
				em.flush();
				
				
			} else {
				JOptionPane.showMessageDialog(mainFrame, ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_WARN2) ,
					 ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_WARN), JOptionPane.WARNING_MESSAGE);
				return;
			}
		
		} catch (Exception e1) {
			Logger.error(e1);
			rollBack();
		} finally {
			commit();
		}
		
	}
	
	/**
	 * This method to be use for loading acttion
	 * @param employee
	 * @param year
	 */
	private void loadOpeartion(Employee employee, EmployeeSalary emplSallary, String year) {
		
		try {
			init();
			EmplsSettingsLoadUtil util = new EmplsSettingsLoadUtil();
			Vector newRow = util.reloadSingleRow(employee, emplSallary, em,  model);
			model.setRowAt(newRow, personalConfTable.getSelectedRow());
			popup.setVisible(false);
		    personalConfTable.clearSelection();
		    personalConfTable.revalidate();
		    personalConfTable.repaint();
		} catch (Exception e1) {
			Logger.error(e1);
			rollBack();
		} finally {
			commit();
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
