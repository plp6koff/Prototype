package com.consultancygrid.trz.actionListener.targetPeriod;

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

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.TargetPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTable;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class TrgPrdLvlAddRowAL extends BaseActionListener{

	private TargetLevelCfgTable tLvlConfTable;
	private JComboBox comboBoxTargetPeriod;
	private HashMap<TrzStatic, JTextField> map ;
	
	public TrgPrdLvlAddRowAL(PrototypeMainFrame mainFrame,JComboBox comboBoxTargetPeriod, TargetLevelCfgTable tLvlConfTable) {
		super(mainFrame);
		this.tLvlConfTable = tLvlConfTable;
		this.comboBoxTargetPeriod = comboBoxTargetPeriod;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			TargetPeriod targetPeriod =  (TargetPeriod)comboBoxTargetPeriod.getSelectedItem();
			
			TargetLevelCfgTableModel model = (TargetLevelCfgTableModel) tLvlConfTable.getModel();
			int i = tLvlConfTable.getSelectedRow();
			String percent = (String)tLvlConfTable.getModel().getValueAt(i, 1);
			String bonus = (String)tLvlConfTable.getModel().getValueAt(i, 2);
			
			HashMap<String, JTextField> map = new HashMap<String, JTextField>();
			
			
			JFrame popUp = new JFrame();
			popUp.setBounds(100, 100, 500, 300);
			popUp.setAlwaysOnTop(true);
			popUp.setResizable(false);
			popUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			popUp.setTitle("Edit Target level ");
			popUp.setVisible(true);
			
			JPanel panel = new JPanel();
			panel.setLayout(null);
			popUp.getContentPane().add(panel);
			
			JLabel l1 = new JLabel("Target " + "%");
			l1.setBounds(30, 30, 100, 25);
			panel.add(l1);
			JTextField tf1 = new JTextField();
			tf1.setBounds(150, 30, 100, 25);
			tf1.setText(percent);
			panel.add(tf1);
			
			map.put("PERCENT", tf1);
			
			JLabel l2 = new JLabel("BONUS");
			l2.setBounds(30, 80, 100, 25);
			panel.add(l2);
			JTextField tf2 = new JTextField();
			tf2.setText(bonus);
			tf2.setBounds(150, 80, 100, 25);
			panel.add(tf2);
			
			map.put("BONUS", tf2);
			
			
			JButton saveBtn = new JButton("Add");
			saveBtn.setBounds(30, 150, 100, 25);
			saveBtn.addActionListener(new TrgPrdLvlSaveAddRowAL(mainFrame, tLvlConfTable, comboBoxTargetPeriod , map, popUp));
			panel.add(saveBtn);
			JButton cnclBtn = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.BUTT_CANCEL));
			cnclBtn.setBounds(150, 150, 100, 25);
			cnclBtn.addActionListener(new TrgPrdLvlCancelRowAL(mainFrame, tLvlConfTable,comboBoxTargetPeriod, popUp));
			panel.add(cnclBtn);
			
			comboBoxTargetPeriod.revalidate();
			comboBoxTargetPeriod.repaint();
			
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
