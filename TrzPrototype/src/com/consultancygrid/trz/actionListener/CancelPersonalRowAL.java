package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTable;

public class CancelPersonalRowAL extends BaseActionListener{

	private PersonalCfgEmplsTable personalConfTable;
	private JComboBox comboBoxEmployee;
	
	private HashMap<TrzStatic, JTextField> map ;
	private HashMap<Department, JTextField> mapDept;
	
	public CancelPersonalRowAL(PrototypeMainFrame mainFrame, PersonalCfgEmplsTable personalConfTable, JComboBox comboBoxEmployee) {
		super(mainFrame);
		this.personalConfTable = personalConfTable;
		this.comboBoxEmployee = comboBoxEmployee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			
		this.personalConfTable.clearSelection();
		this.personalConfTable.setEditingRow(-1);
		this.personalConfTable.setEnableEdit(false);
		this.personalConfTable.validate();
		this.personalConfTable.repaint();
	}
	
}
