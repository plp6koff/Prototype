package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JTable;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.util.GroupTablPeriodLoaderUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;





import static com.consultancygrid.trz.base.Constants.*;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class PeriodGroupTabComboAL extends BaseActionListener {

	
	JComboBox comboBox;
	JTable table;
	
	public PeriodGroupTabComboAL(PrototypeMainFrame mainFrame,JComboBox comboBox,JTable table) {

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
