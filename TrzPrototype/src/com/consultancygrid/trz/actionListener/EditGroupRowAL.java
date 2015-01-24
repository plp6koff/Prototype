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

			
			HashMap<String, JTextField> map = new HashMap<String, JTextField>();
			
			
			String nameRaw = (String) model.getValueAt(i, 1);
			
			JFrame popUp = new JFrame();
			popUp.setBounds(100, 100, 800, 300);
			popUp.setAlwaysOnTop(true);
			popUp.setResizable(false);
			popUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			popUp.setTitle("Edit " + nameRaw);
			popUp.setVisible(true);
			
			JPanel panel = new JPanel();
			panel.setLayout(null);
			popUp.getContentPane().add(panel);
			
			JLabel l1 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE1) + "%");
			l1.setBounds(30, 30, 100, 25);
			panel.add(l1);
			JTextField tf1 = new JTextField();
			tf1.setBounds(150, 30, 100, 25);
			tf1.setText(((Double)model.getValueAt(i, 6)).toString());
			panel.add(tf1);
			
			map.put(LabelsConstants.GROUP_CONF_COL5_VALUE1, tf1);
			
			JLabel l4 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL7) + "-"
					+ ResourceLoaderUtil
					.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE1));
			l4.setBounds(300, 30, 250, 25);
			panel.add(l4);
			JTextField tf4 = new JTextField();
			tf4.setText(((Double)model.getValueAt(i, 7)).toString());
			tf4.setBounds(570, 30, 100, 25);
			panel.add(tf4);
			
			map.put(LabelsConstants.GROUP_CONF_COL5_VALUE1 + "-" + LabelsConstants.GROUP_CONF_HEADER_COL7, tf4);
			
			
			JLabel l2 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE2) + " %");
			l2.setBounds(30 , 70,  100, 25);
			panel.add(l2);
			JTextField tf2 = new JTextField();
			tf2.setBounds(150, 70, 100, 25);
			tf2.setText(((Double)model.getValueAt(i+1, 6)).toString());
			panel.add(tf2);
			
			map.put(LabelsConstants.GROUP_CONF_COL5_VALUE2, tf2);
			
			JLabel l3 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE3) + " %");
			l3.setBounds(30, 110, 100, 25);
			panel.add(l3);
			JTextField tf3 = new JTextField();
			tf3.setBounds(150, 110, 100, 25);
			tf3.setText(((Double)model.getValueAt(i+2, 6)).toString());
			panel.add(tf3);
			
			map.put(LabelsConstants.GROUP_CONF_COL5_VALUE3, tf3);
			
			JLabel l5 = new JLabel(ResourceLoaderUtil
					.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL7) + "-"
					+ ResourceLoaderUtil
					.getLabels(LabelsConstants.GROUP_CONF_COL5_VALUE2));
			l5.setBounds(300, 70, 250, 25);
			panel.add(l5);
			JTextField tf5 = new JTextField();
			tf5.setBounds(570, 70, 100, 25);
			tf5.setText(((Double)model.getValueAt(i+1, 7)).toString());
			panel.add(tf5);
			
			map.put(LabelsConstants.GROUP_CONF_COL5_VALUE2 + "-" + LabelsConstants.GROUP_CONF_HEADER_COL7, tf4);
			
			JButton saveBtn = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_SAVE_BTN));
			saveBtn.setBounds(230, 200, 100, 25);
			saveBtn.addActionListener(new SaveGroupRowAL(mainFrame, groupConfTable, comboBoxPeriod , map, popUp));
			panel.add(saveBtn);
			JButton cnclBtn = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.BUTT_CANCEL));
			cnclBtn.setBounds(380, 200, 100, 25);
			cnclBtn.addActionListener(new CancelGroupRowAL(mainFrame, groupConfTable,comboBoxPeriod, popUp));
			panel.add(cnclBtn);
			
			
			groupConfTable.revalidate();
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
