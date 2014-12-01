package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class SaveGroupRowAL extends BaseActionListener{

	private GroupCfgEmplsTable groupConfTable;
	private JComboBox comboBoxPeriod;
	
	private HashMap<TrzStatic, JTextField> map ;
	private HashMap<Department, JTextField> mapDept;
	
	public SaveGroupRowAL(PrototypeMainFrame mainFrame, GroupCfgEmplsTable groupConfTable, JComboBox comboBoxPeriod) {
		
		super(mainFrame);
		this.groupConfTable = groupConfTable;
		this.comboBoxPeriod = comboBoxPeriod;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Period period =  (Period)comboBoxPeriod.getSelectedItem();
			
			GroupCfgEmplsTableModel model = (GroupCfgEmplsTableModel) groupConfTable.getModel();
			int i = groupConfTable.getSelectedRow();
			
			groupConfTable.setRowEditable(-1);
			groupConfTable.setEnableEdit(false);
			
			if (period != null) {
				
				String nameRaw = (String) model.getValueAt(i, 1);
				String nameRaw1 = nameRaw.substring(0, nameRaw.indexOf(" "));
				String nameRaw2 = nameRaw.substring(nameRaw.indexOf(" ") + 1 , nameRaw.length());
				
				Query q = em.createQuery("from Employee as empl  where  empl.firstName = :firstName and empl.lastName = :lastName");
				q.setParameter("firstName", nameRaw1);
				q.setParameter("lastName", nameRaw2);
				List<Employee> allEmpl = (List<Employee>) q.getResultList();
				
				if (allEmpl != null && !allEmpl.isEmpty()) {
					
					Employee employee = allEmpl.get(0);
					
					Query q1 = em.createQuery(" from EmployeeSettings as settings  where  settings.period.id = :periodId and settings.employee.id = :emplId ");
					q1.setParameter("periodId", period.getId());
					q1.setParameter("emplId", employee.getId());
					List<EmployeeSettings> allSettings = (List<EmployeeSettings>) q.getResultList();
					if (allSettings != null && !allSettings.isEmpty()) {
						
						EmployeeSettings settings = allSettings.get(0);
						
						String v1 = (String) model.getValueAt(i, 6);
						String v2 = (String) model.getValueAt(i+1, 6);
						String v3 = (String) model.getValueAt(i+2, 6);
						
						settings.setPercentAll(parseValue(settings.getPercentAll(), v1));
						settings.setPercentGroup(parseValue(settings.getPercentGroup(), v2));
						settings.setPercentPersonal(parseValue(settings.getPercentPersonal(), v3));
						em.merge(settings);
						
						JOptionPane.showMessageDialog(mainFrame, 
								ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_INFO1)  + employee.getFirstName() + " " + employee.getLastName() +" !", 
								ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_INFO), JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
				groupConfTable.clearSelection();
				groupConfTable.validate();
				groupConfTable.repaint();
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
		
		try {
			double parsedDoub = Double.valueOf(newValStr);
			return BigDecimal.valueOf(parsedDoub);
		} catch (Exception e)  {
			 return initValue;
		}
	}
}
