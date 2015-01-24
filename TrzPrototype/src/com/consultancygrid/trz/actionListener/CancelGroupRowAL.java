package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;
import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class CancelGroupRowAL extends BaseActionListener{

	private GroupCfgEmplsTable groupConfTable;
	private JComboBox comboBoxPeriod;
	private JFrame frame;
	
	public CancelGroupRowAL(PrototypeMainFrame mainFrame, GroupCfgEmplsTable groupConfTable, JComboBox comboBoxPeriod, JFrame frame) {
		super(mainFrame);
		this.groupConfTable = groupConfTable;
		this.comboBoxPeriod = comboBoxPeriod;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			
		this.frame.setVisible(false);;
		this.groupConfTable.setEditingRow(-1);
		this.groupConfTable.clearSelection();
		this.groupConfTable.revalidate();
		this.groupConfTable.repaint();
	}
	
}
