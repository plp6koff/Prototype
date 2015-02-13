package com.consultancygrid.trz.actionListener.loadFile;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.PDFCreatorUtil;

public class SaveFileAL extends BaseActionListener {

	static private final String newline = "\n";
	private JFileChooser fc;
	private PersonalCfgEmplsTable personalConfTable;
	private JTextArea log;
	private JLabel labelProcessing;
	private JPanel firstInnerPanel;
	private JComboBox comboBoxEmployee;

	public SaveFileAL(PrototypeMainFrame mainFrame, JFileChooser fc,
			JTextArea log, JComboBox comboBoxEmployee,
			PersonalCfgEmplsTable personalConfTable) {

		super(mainFrame);
		this.fc = fc;
		this.log = log;
		this.comboBoxEmployee = comboBoxEmployee;
		this.personalConfTable = personalConfTable;
	}

	public void actionPerformed(ActionEvent e) {

		EmplComboBoxModel emplMod = ((EmplComboBoxModel) comboBoxEmployee
				.getModel());
		Employee employee = emplMod.getSelectedItem();
		PersonalCfgEmplsTableModel model = (PersonalCfgEmplsTableModel) personalConfTable
				.getModel();
		int i = personalConfTable.getSelectedRow();
		if (i < 0) {
			i = 0;
		}
		EmployeeSalary emplSallary = model.getEmplSals().get(i);
		fc.showSaveDialog(mainFrame);
		File selectedPath = fc.getSelectedFile();
		Department department = null;
		if (emplSallary.getEmployee().getEmplDeptPeriods().iterator().hasNext()) {
			EmplDeptPeriod edp = emplSallary.getEmployee().getEmplDeptPeriods().iterator().next();
			department = edp.getDepartment();
		}
		File resultPdf = PDFCreatorUtil.createPDF(emplSallary, department, 	selectedPath.getAbsolutePath());
	}

}
