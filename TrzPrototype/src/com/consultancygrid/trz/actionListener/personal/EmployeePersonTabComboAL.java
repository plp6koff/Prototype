package com.consultancygrid.trz.actionListener.personal;

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
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.EmplsSettingsLoadUtil;
/**
 * ACtion Listener for code list
 * 
 * @author Anton Palapeshkov
 *
 */

public class EmployeePersonTabComboAL extends BaseActionListener {

	
	JComboBox<Employee> comboBoxEmployee;
	JComboBox<String> comboBoxYear;
	JTable table;
	
	public EmployeePersonTabComboAL(PrototypeMainFrame mainFrame, 
			JComboBox<Employee> comboBoxE, 
			JComboBox<String> comboBoxY, 
			JTable table) {

		super(mainFrame);
		this.comboBoxEmployee = comboBoxE;
		this.comboBoxYear = comboBoxY;
		this.table = table;
	}

	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();

			em.getTransaction().begin();

			PersonalCfgEmplsTableModel currentModel = ((PersonalCfgEmplsTableModel)table.getModel());
			
			Employee employee = ((Employee) comboBoxEmployee.getModel().getSelectedItem());
			String year = ((String) comboBoxYear.getModel().getSelectedItem());
			
			Vector tableData  = new Vector();
			EmplsSettingsLoadUtil emplsComboUtil = new EmplsSettingsLoadUtil();
			
			if (comboBoxEmployee.getModel().getSelectedItem() != null) {
				
				emplsComboUtil.load(employee, year, em, tableData, currentModel);
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
