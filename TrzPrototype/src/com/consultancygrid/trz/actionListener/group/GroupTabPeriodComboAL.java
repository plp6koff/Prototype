package com.consultancygrid.trz.actionListener.group;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JComboBox;
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

	
	JComboBox comboBox;
	JTable table;
	
	public GroupTabPeriodComboAL(PrototypeMainFrame mainFrame,JComboBox comboBox,JTable table) {

		super(mainFrame);
		this.comboBox = comboBox;
		this.table = table;
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
			GroupTablPeriodLoaderUtil grTabPeriodLoaderUtil 
					= new GroupTablPeriodLoaderUtil();
			grTabPeriodLoaderUtil.loadData(period, em, tableData);

			if (comboBox.getModel().getSelectedItem() != null) {
				
				GroupCfgEmplsTableModel currentModel = (GroupCfgEmplsTableModel) table.getModel();  
				currentModel.setData(tableData);
				table.setModel(currentModel);
				mainFrame.validate();
			}
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.close();
			}
		}

	}
	

}
