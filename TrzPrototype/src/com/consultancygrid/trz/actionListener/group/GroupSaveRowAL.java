package com.consultancygrid.trz.actionListener.group;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.util.GroupTablPeriodLoaderUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class GroupSaveRowAL extends BaseActionListener{

	private GroupCfgEmplsTable groupConfTable1;
	private GroupCfgEmplsTable groupConfTable2;
	private JComboBox comboBoxPeriod;
	private JFrame frame ; 
	private JTabbedPane tabbedPaneDep;
	
	private HashMap<String, JTextField> map;
	
	public GroupSaveRowAL(PrototypeMainFrame mainFrame, GroupCfgEmplsTable groupConfTable1, GroupCfgEmplsTable groupConfTable2, 
			JComboBox comboBoxPeriod,  
			HashMap<String, JTextField> map,
			JFrame frame,
			JTabbedPane tabbedPaneDep) {
		
		super(mainFrame);
		this.groupConfTable1 = groupConfTable1;
		this.groupConfTable2 = groupConfTable2;
		this.comboBoxPeriod = comboBoxPeriod;
		this.map = map;
		this.frame = frame;
		this.tabbedPaneDep = tabbedPaneDep;
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
			
			GroupCfgEmplsTableModel model = null;
			GroupCfgEmplsTable groupConfTable = null;
			int i = -1;
			if (tabbedPaneDep.getSelectedIndex() == 0) {
				
				groupConfTable = groupConfTable1;
			} else if (tabbedPaneDep.getSelectedIndex() == 1) {
				groupConfTable = groupConfTable2;
			}
			i = groupConfTable.getSelectedRow();
			model = (GroupCfgEmplsTableModel) groupConfTable.getModel();
			
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
					List<EmployeeSettings> allSettings = (List<EmployeeSettings>) q1.getResultList();
					if (allSettings != null && !allSettings.isEmpty()) {
						
						EmployeeSettings settings = allSettings.get(0);
						
						String percentAll = map.get(LabelsConstants.GROUP_CONF_COL5_VALUE1).getText(); 
						String percentGroup  = map.get(LabelsConstants.GROUP_CONF_COL5_VALUE2).getText(); 
						String percentPersonal = map.get(LabelsConstants.GROUP_CONF_COL5_VALUE3).getText();  
						String onBoardAll =  map.get(LabelsConstants.GROUP_CONF_COL5_VALUE1 + "-" + LabelsConstants.GROUP_CONF_HEADER_COL7).getText();
						String onBoardGroup =  map.get(LabelsConstants.GROUP_CONF_COL5_VALUE2 + "-" + LabelsConstants.GROUP_CONF_HEADER_COL7).getText();
						
						settings.setPercentAll(parseValue(settings.getPercentAll(), percentAll));
						settings.setPercentGroup(parseValue(settings.getPercentGroup(), percentGroup));
						settings.setPercentPersonal(parseValue(settings.getPercentPersonal(), percentPersonal));
						
						settings.setPersonAllOnboardingPercent(parseValue(settings.getPersonAllOnboardingPercent(), onBoardAll));
						settings.setPersonGroupOnboardingPercent(parseValue(settings.getPersonGroupOnboardingPercent(), onBoardGroup));
						
						em.merge(settings);
						
					}
				}
				
				frame.setVisible(false);
				
				
				Vector tableData = new Vector();
				Vector tableData2 = new Vector();
				GroupTablPeriodLoaderUtil grTabPeriodLoaderUtil = new GroupTablPeriodLoaderUtil();
				Set<UUID> employeeSetingsIds = grTabPeriodLoaderUtil.loadData(period, em, tableData);
				grTabPeriodLoaderUtil.loadData2(period, em, tableData2, employeeSetingsIds);

				if (comboBoxPeriod.getModel().getSelectedItem() != null) {
					
					GroupCfgEmplsTableModel currentModel = (GroupCfgEmplsTableModel) groupConfTable1.getModel();  
					currentModel.setData(tableData);
					groupConfTable1.setModel(currentModel);
					
					GroupCfgEmplsTableModel currentModel2 = (GroupCfgEmplsTableModel) groupConfTable2.getModel();  
					currentModel2.setData(tableData2);
					groupConfTable2.setModel(currentModel2);
					mainFrame.validate();
					
					
				}
				
				groupConfTable2.revalidate();
				groupConfTable2.repaint();
				groupConfTable1.revalidate();
				groupConfTable1.repaint();
				
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
