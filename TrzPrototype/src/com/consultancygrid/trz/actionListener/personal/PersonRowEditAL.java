package com.consultancygrid.trz.actionListener.personal;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class PersonRowEditAL extends BaseActionListener{

	private PersonalCfgEmplsTable personalConfTable;
	private JComboBox comboBoxEmployee;
	private JComboBox<String> comboBoxYear;
	
	private HashMap<TrzStatic, JTextField> map ;
	private HashMap<Department, JTextField> mapDept;
	
	public PersonRowEditAL(PrototypeMainFrame mainFrame, 
						   PersonalCfgEmplsTable personalConfTable, 
						   JComboBox comboBoxEmployee,
						   JComboBox<String> comboBoxYear) {
		super(mainFrame);
		this.personalConfTable = personalConfTable;
		this.comboBoxEmployee = comboBoxEmployee;
		this.comboBoxYear = comboBoxYear;
	}

	protected void eventCore() throws IOException {
			
			Employee employee =  (Employee)comboBoxEmployee.getSelectedItem();
			String year = (String) comboBoxYear.getSelectedItem();
			
			PersonalCfgEmplsTableModel model = (PersonalCfgEmplsTableModel) personalConfTable.getModel();
			int i = personalConfTable.getSelectedRow();
			if (i == -1) {
				JOptionPane.showMessageDialog(mainFrame, 
											 ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_WARN1), 
											 ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_WARN) , JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			JFrame popUp = new JFrame();
			popUp.setBounds(100, 100, 400, 650);
			popUp.setAlwaysOnTop(true);
			popUp.setResizable(true);
			//popUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			popUp.setTitle(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_EDIT_BTN) + " " + employee.getFirstName() + " " + employee.getLastName());
			popUp.setVisible(true);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBounds(0, 0, 400, 600);
			popUp.getContentPane().add(panel);
			
			JLabel l1 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL1));
			l1.setBounds(30, 30, 150, 60);
			panel.add(l1);
			JTextField tf1 = new JTextField();
			tf1.setBounds(200, 50, 100, 25);
			tf1.setText(model.getValueAt(i, 1) != null ?((BigDecimal)model.getValueAt(i, 1)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf1);
			
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL1, tf1);
			
			JLabel l6 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL3));
			l6.setBounds(30, 100, 150, 60);
			panel.add(l6);
			JTextField tf6 = new JTextField();
			tf6.setBounds(200, 120, 100, 25);
			tf6.setText(model.getValueAt(i, 3) != null ?((BigDecimal)model.getValueAt(i,3)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf6);
			
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL3, tf6);
			
			JLabel l2 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL10));
			l2.setBounds(30 , 170,  150, 60);
			panel.add(l2);
			JTextField tf2 = new JTextField();
			tf2.setBounds(200, 190, 100, 25);
			tf2.setText(model.getValueAt(i, 10) != null ?((BigDecimal)model.getValueAt(i, 10)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf2);
			
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL10, tf2);
			
			JLabel l3 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL13));
			l3.setBounds(30, 240, 150, 60);
			panel.add(l3);
			JTextField tf3 = new JTextField();
			tf3.setBounds(200, 260, 100, 25);
			tf3.setText(model.getValueAt(i, 13) != null ?((BigDecimal)model.getValueAt(i, 13)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf3);
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL13, tf3);
			
			
			JLabel l4 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL14));
			l4.setBounds(30, 310, 150, 60);
			panel.add(l4);
			JTextField tf4 = new JTextField();
			tf4.setBounds(200, 330, 100, 25);
			tf4.setText(model.getValueAt(i, 14) != null ? model.getValueAt(i, 14).toString() : "");
			panel.add(tf4);
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL14, tf4);
			
			JLabel l5 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL20));
			l5.setBounds(30, 380, 150, 25);
			panel.add(l5);
			JTextField tf5 = new JTextField();
			tf5.setBounds(200, 380, 100, 25);
			tf5.setText(model.getValueAt(i, 17) != null ? ((BigDecimal)model.getValueAt(i, 17)).toString() : BigDecimal.ZERO.toString());
			panel.add(tf5);
			
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL20, tf5);
			
			JLabel l7 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL22));
			l7.setBounds(30, 420, 100, 25);
			panel.add(l7);
			
			JTextArea tf7 = new JTextArea();
			tf7.setBounds(150, 420, 200, 60);
			tf7.setText((String)model.getValueAt(i, 22));
			panel.add(tf7);
			
			map.put(LabelsConstants.PERSONAL_CFG_HEADER_COL22, tf7);
			
			JButton saveBtn = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_SAVE_BTN));
			saveBtn.setBounds(50, 500, 100, 25);
			saveBtn.addActionListener(new PersonRowSaveAL(mainFrame, personalConfTable, comboBoxEmployee, comboBoxYear, map, popUp));
			panel.add(saveBtn);
			JButton cnclBtn = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.BUTT_CANCEL));
			cnclBtn.addActionListener(new PersonalRowCancelAL(mainFrame, personalConfTable, comboBoxEmployee, popUp));
			cnclBtn.setBounds(170, 500, 100, 25); 
			panel.add(cnclBtn);
			
			personalConfTable.validate();
			personalConfTable.repaint();
			
	}
	
}
