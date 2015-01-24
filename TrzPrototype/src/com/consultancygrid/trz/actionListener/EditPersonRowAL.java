package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.EmplsSettingsLoadUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class EditPersonRowAL extends BaseActionListener{

	private PersonalCfgEmplsTable personalConfTable;
	private JComboBox comboBoxEmployee;
	
	private HashMap<TrzStatic, JTextField> map ;
	private HashMap<Department, JTextField> mapDept;
	
	public EditPersonRowAL(PrototypeMainFrame mainFrame, PersonalCfgEmplsTable personalConfTable, JComboBox comboBoxEmployee) {
		super(mainFrame);
		this.personalConfTable = personalConfTable;
		this.comboBoxEmployee = comboBoxEmployee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Employee employee =  (Employee)comboBoxEmployee.getSelectedItem();
			
			PersonalCfgEmplsTableModel model = (PersonalCfgEmplsTableModel) personalConfTable.getModel();
			int i = personalConfTable.getSelectedRow();
			if (i == -1) {
				JOptionPane.showMessageDialog(mainFrame, 
											 ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_WARN1), 
											 ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_WARN) , JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			JFrame popUp = new JFrame();
			popUp.setBounds(100, 100, 350, 340);
			popUp.setAlwaysOnTop(true);
			popUp.setResizable(false);
			//popUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			popUp.setTitle(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_EDIT_BTN) + " " + employee.getFirstName() + " " + employee.getLastName());
			popUp.setVisible(true);
			
			HashMap<String, JTextField> map = new HashMap<String, JTextField>();
			
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBounds(0, 0, 500, 400);
			popUp.getContentPane().add(panel);
			
			JLabel l1 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL1));
			l1.setBounds(30, 30, 150, 25);
			panel.add(l1);
			JTextField tf1 = new JTextField();
			tf1.setBounds(200, 30, 100, 25);
			tf1.setText(model.getValueAt(i, 1) != null ?((BigDecimal)model.getValueAt(i, 1)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf1);
			
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL1, tf1);
			
			
			JLabel l2 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL7));
			l2.setBounds(30 , 70,  150, 25);
			panel.add(l2);
			JTextField tf2 = new JTextField();
			tf2.setBounds(200, 70, 100, 25);
			tf2.setText(model.getValueAt(i, 7) != null ?((BigDecimal)model.getValueAt(i, 7)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf2);
			
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL7, tf2);
			
			JLabel l3 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL17));
			l3.setBounds(30, 110, 150, 25);
			panel.add(l3);
			JTextField tf3 = new JTextField();
			tf3.setBounds(200, 110, 100, 25);
			tf3.setText(model.getValueAt(i, 17) != null ?((BigDecimal)model.getValueAt(i, 17)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf3);
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL17, tf3);
			
			JLabel l4 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL21));
			l4.setBounds(30, 150, 150, 25);
			panel.add(l4);
			JTextField tf4 = new JTextField();
			tf4.setBounds(200, 150, 100, 25);
			tf4.setText(model.getValueAt(i, 21) != null ?((BigDecimal)model.getValueAt(i, 21)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf4);
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL21, tf4);
			
			
			JLabel l5 = new JLabel( ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL24));
			l5.setBounds(30, 190, 150, 25);
			panel.add(l5);
			JTextField tf5 = new JTextField();
			tf5.setBounds(200, 190, 100, 25);
			tf5.setText(model.getValueAt(i, 24) != null ?((BigDecimal)model.getValueAt(i, 17)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf5);
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL24, tf5);
			
			
			JLabel l6 = new JLabel( ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL25));
			l6.setBounds(30, 230, 150, 25);
			panel.add(l6);
			JTextField tf6 = new JTextField();
			tf6.setBounds(200, 230, 100, 25);
			tf6.setText(model.getValueAt(i, 25) != null ?((BigDecimal)model.getValueAt(i, 21)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf6);
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL25, tf6);
			
			JButton saveBtn = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_SAVE_BTN));
			saveBtn.setBounds(50, 270, 100, 25);
			saveBtn.addActionListener(new SavePersonRowAL(mainFrame, personalConfTable, comboBoxEmployee, map, popUp));
			panel.add(saveBtn);
			JButton cnclBtn = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.BUTT_CANCEL));
			cnclBtn.addActionListener(new CancelPersonalRowAL(mainFrame, personalConfTable, comboBoxEmployee, popUp));
			cnclBtn.setBounds(170, 270, 100, 25); 
			panel.add(cnclBtn);
			
			EmplsSettingsLoadUtil emplsComboUtil = new EmplsSettingsLoadUtil();
			//emplsComboUtil.load(employee, em, model);
			
			personalConfTable.validate();
			personalConfTable.repaint();
			
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
