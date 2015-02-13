package com.consultancygrid.trz.actionListener.personal;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTable;

public class PersonalRowCancelAL extends BaseActionListener{

	private PersonalCfgEmplsTable personalConfTable;
	private JComboBox comboBoxEmployee;
	private JFrame popUp;
	
	public PersonalRowCancelAL(PrototypeMainFrame mainFrame, PersonalCfgEmplsTable personalConfTable, JComboBox comboBoxEmployee, JFrame popUp) {
		super(mainFrame);
		this.personalConfTable = personalConfTable;
		this.comboBoxEmployee = comboBoxEmployee;
		this.popUp = popUp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			
		this.popUp.setVisible(false);
		this.personalConfTable.clearSelection();
		this.personalConfTable.setEditingRow(-1);
		this.personalConfTable.validate();
		this.personalConfTable.repaint();
	}
	
}
