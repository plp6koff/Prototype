package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;
import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.AvgInputData;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.DepartmentRevenue;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.InputData;
import com.consultancygrid.trz.model.InputFileType;
import com.consultancygrid.trz.model.MatchcodeList;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.model.UserDepartment;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.CSVReaderUtil;
import com.consultancygrid.trz.util.EmployeeSalaryUtil;
import com.consultancygrid.trz.util.EmployeeSettingsUtil;
import com.consultancygrid.trz.util.PDFCreatorUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class SaveFileAL extends BaseActionListener {

	static private final String newline = "\n";
	private JFileChooser fc;
	private PersonalCfgEmplsTable personalConfTable;
	private JTextArea log;
	private JLabel labelProcessing;
	private JPanel firstInnerPanel;
	private JComboBox comboBoxEmployee;
	
	public SaveFileAL(PrototypeMainFrame mainFrame, JFileChooser fc,
			JTextArea log,  JComboBox comboBoxEmployee, PersonalCfgEmplsTable personalConfTable) {

		super(mainFrame);
		this.fc = fc;
		this.log = log;
		this.comboBoxEmployee = comboBoxEmployee;
		this.personalConfTable = personalConfTable;
	}

	public void actionPerformed(ActionEvent e) {

		
		EmplComboBoxModel emplMod = ((EmplComboBoxModel)comboBoxEmployee.getModel());
		Employee employee =  emplMod.getSelectedItem();
		PersonalCfgEmplsTableModel model = (PersonalCfgEmplsTableModel) personalConfTable.getModel();
		int i = personalConfTable.getSelectedRow();
		if ( i < 0) {
			i = 0;
		}
		EmployeeSalary emplSallary = model.getEmplSals().get(i);
		fc.showSaveDialog(mainFrame);
		File selectedPath = fc.getSelectedFile();
		File resultPdf = PDFCreatorUtil.createPDF(emplSallary, selectedPath.getAbsolutePath());
	}


}
