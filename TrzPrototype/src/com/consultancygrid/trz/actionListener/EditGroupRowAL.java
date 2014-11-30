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

public class EditGroupRowAL extends BaseActionListener{

	private GroupCfgEmplsTable groupConfTable;
	private JComboBox comboBoxPeriod;
	
	private HashMap<TrzStatic, JTextField> map ;
	private HashMap<Department, JTextField> mapDept;
	
	public EditGroupRowAL(PrototypeMainFrame mainFrame, GroupCfgEmplsTable groupConfTable, JComboBox comboBoxPeriod) {
		super(mainFrame);
		this.groupConfTable = groupConfTable;
		this.comboBoxPeriod = comboBoxPeriod;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Period period =  (Period)comboBoxPeriod.getSelectedItem();
			
			GroupCfgEmplsTableModel model = (GroupCfgEmplsTableModel) groupConfTable.getModel();
			int i = groupConfTable.getSelectedRow();
//			if (i == -1) {
//				JOptionPane.showMessageDialog(mainFrame, 
//											 ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_WARN1), 
//											 ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_WARN) , JOptionPane.WARNING_MESSAGE);
//				return;
//			}
			groupConfTable.setRowEditable(i);
			groupConfTable.setEnableEdit(true);
			groupConfTable.validate();
			groupConfTable.repaint();
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
			}
		}	
	}
	
}
