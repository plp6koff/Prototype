package com.consultancygrid.trz.actionListener.group;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.util.GroupTablPeriodLoaderUtil;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class GroupTabPeriodComboAL extends BaseActionListener {

	
	private JComboBox comboBox;
	private JTable table;
	private JTable table2;
	
	public GroupTabPeriodComboAL(PrototypeMainFrame mainFrame,JComboBox comboBox,JTable table, JTable table2) {

		super(mainFrame);
		this.comboBox = comboBox;
		this.table = table;
		this.table2 = table2;
	}

	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();

			em.getTransaction().begin();

			//secondInnerPanel = (JPanel)((JTabbedPane)mainFrame.getContentPane().getComponents()[0]).getComponent(1) ;
			Period period = ((Period) comboBox.getModel().getSelectedItem());
			
			Vector tableData = new Vector();
			Vector tableData2 = new Vector();
			GroupTablPeriodLoaderUtil grTabPeriodLoaderUtil = new GroupTablPeriodLoaderUtil();
			Set<UUID> employeeSetingsIds = grTabPeriodLoaderUtil.loadData(period, em, tableData);
			grTabPeriodLoaderUtil.loadData2(period, em, tableData2, employeeSetingsIds);

			if (comboBox.getModel().getSelectedItem() != null) {
				
				GroupCfgEmplsTableModel currentModel = (GroupCfgEmplsTableModel) table.getModel();  
				currentModel.setData(tableData);
				table.setModel(currentModel);
				
				GroupCfgEmplsTableModel currentModel2 = (GroupCfgEmplsTableModel) table2.getModel();  
				currentModel2.setData(tableData2);
				table2.setModel(currentModel2);
				mainFrame.validate();
				
				
				
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
	

}
