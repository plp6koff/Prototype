package com.consultancygrid.trz.actionListener.personal;

import java.io.IOException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Employee;
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

	protected void eventCore() throws IOException {
		
			
			PersonalCfgEmplsTableModel currentModel = ((PersonalCfgEmplsTableModel)table.getModel());
			Employee employee = ((Employee) comboBoxEmployee.getModel().getSelectedItem());
			String year = ((String) comboBoxYear.getModel().getSelectedItem());
			
			Vector tableData  = new Vector();
			EmplsSettingsLoadUtil emplsComboUtil = new EmplsSettingsLoadUtil();
			
			if (comboBoxEmployee.getModel().getSelectedItem() != null) {
				
				emplsComboUtil.load(employee, year, em, tableData, currentModel, true);
				currentModel.setData(tableData);
				table.setModel(currentModel);
				mainFrame.validate();
			}

	}

}
