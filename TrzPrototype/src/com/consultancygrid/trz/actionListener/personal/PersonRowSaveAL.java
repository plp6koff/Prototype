package com.consultancygrid.trz.actionListener.personal;

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
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.TrzStatic;
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
	
	private HashMap<String, JTextField> map ;
	
	public PersonRowSaveAL(PrototypeMainFrame mainFrame,
			PersonalCfgEmplsTable personalConfTable, 
			JComboBox<Employee> comboBoxEmployee,  
			HashMap<String, JTextField> map,
			JFrame popup) {
		super(mainFrame);
		this.personalConfTable = personalConfTable;
		this.comboBoxEmployee = comboBoxEmployee;
		this.map = map;
		this.popup = popup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Employee employee =  (Employee)comboBoxEmployee.getSelectedItem();
			
			PersonalCfgEmplsTableModel model = (PersonalCfgEmplsTableModel) personalConfTable.getModel();
			int i = personalConfTable.getSelectedRow();
			
			
			if (employee != null) {
				EmployeeSalary emplSallary = model.getEmplSals().get(i);
				String v01Str = map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL1).getText();
				String v03Str = map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL3).getText();
				String v10Str = map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL10).getText();
				String v13Str = map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL13).getText();
				String v14Str = map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL14).getText();
				
				
				Query q1 = em.createQuery(" from EmployeeSettings as settings  where  settings.period.id = :periodId and settings.employee.id = :emplId ");
				q1.setParameter("periodId", emplSallary.getPeriod().getId());
				q1.setParameter("emplId", employee.getId());
				EmployeeSettings settings = ((List<EmployeeSettings>) q1.getResultList()).get(0);
				
				
				BigDecimal v1 = parseValue(emplSallary.getV01(),v01Str); 
				Double b = v1.doubleValue();
				emplSallary.setV01(v1);
				settings.setBrutoStandart(v1);
				
				BigDecimal v3 = parseValue(emplSallary.getV03(),v03Str); 
				Double d = v3.doubleValue();
				emplSallary.setV03(v3);
				settings.setNetSalary(v3);
				
				BigDecimal v10 = parseValue(emplSallary.getV10(),v10Str); 
				Double kMarker = v10.doubleValue();
				emplSallary.setV10(v10);
				
				BigDecimal v13 = parseValue(emplSallary.getV13(),v13Str); 
				Double nBonus = v13.doubleValue();
				emplSallary.setV13(v13);
				
				String oBonusName = v14Str;
				emplSallary.setS01(v14Str);
				
				
				Query qPeriodTrzStatic = em
						.createQuery(" from PeriodSetting as pS where period.id = :periodId");
				qPeriodTrzStatic.setParameter("periodId", emplSallary.getPeriod().getId());
				
				List<PeriodSetting> periodSettings 
				   = (List<PeriodSetting>) qPeriodTrzStatic.getResultList();
				
				TrzStatic DOD = null;
				TrzStatic OSIGUROVKI_RABOTODATEL = null;
				TrzStatic OSIGUROVKI_SLUJITEL = null;
				TrzStatic CACHE_TAX = null;
				Double dodValue = 0.0d;
				Double oRabotodatelValue = 0.0d;
				Double oSlujitelValue = 0.0d;
				Double cacheTaxValue = 0.0d;
				for (PeriodSetting singlePS : periodSettings) {
					
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
					} else if("CACHE_TAX".equals(singleTrz.getKey())) {
						CACHE_TAX = singleTrz;
						cacheTaxValue = Double.valueOf(singlePS.getValue());
					} 
				}	
				EmployeeSallaryCalculateUtil.calcSettings(
						 b, 
						 d, 
						 kMarker,
						 nBonus, 
						 oBonusName, 
						 emplSallary, 
						 DOD, 
						 OSIGUROVKI_RABOTODATEL,
						 OSIGUROVKI_SLUJITEL,
						 CACHE_TAX,
						 dodValue, oRabotodatelValue, oSlujitelValue, cacheTaxValue);
				
				em.merge(emplSallary);
				em.merge(settings);
				
				Vector tableData  = new Vector();
				EmplsSettingsLoadUtil emplsComboUtil = new EmplsSettingsLoadUtil();
				emplsComboUtil.load(employee, em, tableData, model);
				model.setData(tableData);
				popup.setVisible(false);
			    personalConfTable.clearSelection();
			    personalConfTable.revalidate();
			    personalConfTable.repaint();
			} else {
				JOptionPane.showMessageDialog(mainFrame, ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_WARN2) ,
					 ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_WARN), JOptionPane.WARNING_MESSAGE);
				return;
			}
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
				factory.close();
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
