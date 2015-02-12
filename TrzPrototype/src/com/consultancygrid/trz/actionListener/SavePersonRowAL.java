package com.consultancygrid.trz.actionListener;

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

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.EmployeeSallaryCalculateUtil;
import com.consultancygrid.trz.util.EmplsSettingsLoadUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class SavePersonRowAL extends BaseActionListener{

	private PersonalCfgEmplsTable personalConfTable;
	private JFrame popup;
	private JComboBox comboBoxEmployee;
	
	private HashMap<String, JTextField> map ;
	
	public SavePersonRowAL(PrototypeMainFrame mainFrame, PersonalCfgEmplsTable personalConfTable, JComboBox comboBoxEmployee,  HashMap<String, JTextField> map, JFrame popup) {
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
				String v09Str = map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL9).getText();
				String v12Str = map.get(LabelsConstants.PERSONAL_CFG_HEADER_COL12).getText();
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
				
				BigDecimal v9 = parseValue(emplSallary.getV09(),v09Str); 
				Double j = v9.doubleValue();
				emplSallary.setV09(v9);
				
				BigDecimal v12 = parseValue(emplSallary.getV12(),v12Str); 
				Double m = v12.doubleValue();
				emplSallary.setV12(v12);
				
				String name = v13Str;
				emplSallary.setS01(v13Str);
				
				BigDecimal v14 = parseValue(emplSallary.getV13(),v14Str); 
				Double o = v14.doubleValue();
				emplSallary.setV13(v14);
				
				Query qPeriodTrzStatic = em
						.createQuery(" from TrzStatic");
				List<TrzStatic> trzResult = (List<TrzStatic>) qPeriodTrzStatic
						.getResultList();
				TrzStatic DOD = null;
				TrzStatic OSIGUROVKI_RABOTODATEL = null;
				TrzStatic OSIGUROVKI_SLUJITEL = null;
				for (TrzStatic singleTrz : trzResult) {
					if ("DOD".equals(singleTrz.getKey())) {
						DOD = singleTrz;
					} else if("OSIGUROVKI_RABOTODATEL".equals(singleTrz.getKey())) {
						OSIGUROVKI_RABOTODATEL = singleTrz;
					} else if("OSIGUROVKI_SLUJITEL".equals(singleTrz.getKey())) {
						OSIGUROVKI_SLUJITEL = singleTrz;
					}
				}
				EmployeeSallaryCalculateUtil.calcSettings(b, j, m, o , name, emplSallary, DOD, OSIGUROVKI_RABOTODATEL, OSIGUROVKI_SLUJITEL);
				
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
