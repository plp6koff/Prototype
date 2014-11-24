package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.TrzComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

import static com.consultancygrid.trz.base.Constants.*;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class SettingsEmployeeComboAL extends BaseActionListener {

	
	private JPanel panLinkPeriod2Empl;
	private JComboBox comboBoxPeriod;
	private JComboBox comboBoxDepartment;
	private JComboBox comboBoxEmployee;
	private JTabbedPane tabbedPaneSettings;
	
	public SettingsEmployeeComboAL(PrototypeMainFrame mainFrame,JPanel panLinkPeriod2Empl,JComboBox comboBoxPeriod,JComboBox comboBoxDepartment,JComboBox comboBoxEmpl,JTabbedPane tabbedPaneSettings) {

		super(mainFrame);
		this.panLinkPeriod2Empl = panLinkPeriod2Empl;
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxDepartment = comboBoxDepartment;
		this.comboBoxEmployee = comboBoxEmpl;
		this.tabbedPaneSettings = tabbedPaneSettings;
	}

	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();

			em.getTransaction().begin();

			Employee empl = ((Employee) comboBoxEmployee.getModel().getSelectedItem());

			JLabel lblEmpl1 = new JLabel("Revenue:");
			lblEmpl1.setBounds(50, 100 , 100, 25);
			lblEmpl1.setText("Revenue : ");
			panLinkPeriod2Empl.add(lblEmpl1);
			
			JTextField textFieldValue = new JTextField();
			textFieldValue.setBounds(150, 100 , 200, 25);
			textFieldValue.setText("0.0");
			panLinkPeriod2Empl.add(textFieldValue);
			
			
			JButton btnSavePeriod = new JButton("Save");
			btnSavePeriod.setBounds(20, 300, 100 ,25);
			panLinkPeriod2Empl.add(btnSavePeriod);
			
			JLabel lblFieldSettings = new JLabel("Employee settings");
			lblFieldSettings.setBounds(20, 150 , 200, 25);
			panLinkPeriod2Empl.add(lblFieldSettings);
			
			
			JLabel lblBrutoStat = new JLabel("Bruto Stat :");
			lblBrutoStat.setBounds(50, 200 , 100, 25);
			panLinkPeriod2Empl.add(lblBrutoStat);
			JTextField textBrutoStat = new JTextField();
			textBrutoStat.setBounds(150, 200 , 100, 25);
			panLinkPeriod2Empl.add(textBrutoStat);
			
			JLabel lblBrutoStad = new JLabel("Bruto Standart :");
			lblBrutoStad.setBounds(300, 200 , 100, 25);
			panLinkPeriod2Empl.add(lblBrutoStad);
			JTextField textBrutoStad = new JTextField();
			textBrutoStad.setBounds(430, 200 , 100, 25);
			panLinkPeriod2Empl.add(textBrutoStad);
			
			JLabel lblAvans = new JLabel("Avans :");
			lblAvans.setBounds(50, 230 , 100, 25);
			panLinkPeriod2Empl.add(lblAvans);
			JTextField textBrutoAvans = new JTextField();
			textBrutoAvans.setBounds(150, 230 , 100, 25);
			panLinkPeriod2Empl.add(textBrutoAvans);
			
			JLabel lblBrutoPercentAll = new JLabel("Percent All :");
			lblBrutoPercentAll.setBounds(300, 230 , 200, 25);
			panLinkPeriod2Empl.add(lblBrutoPercentAll);
			JTextField textPercentAll = new JTextField();
			textPercentAll.setBounds(430, 230 , 100, 25);
			panLinkPeriod2Empl.add(textPercentAll);
			
			
			JLabel lblPercentGroup = new JLabel("Percent group :");
			lblPercentGroup.setBounds(50, 260 , 100, 25);
			panLinkPeriod2Empl.add(lblPercentGroup);
			JTextField textPercentGroup = new JTextField();
			textPercentGroup.setBounds(150, 260 , 100, 25);
			panLinkPeriod2Empl.add(textPercentGroup);
			
			JLabel lblPercentPerson = new JLabel("Percent Personal :");
			lblPercentPerson.setBounds(300, 260 , 200, 25);
			panLinkPeriod2Empl.add(lblPercentPerson);
			JTextField textPercentPerson = new JTextField();
			textPercentPerson.setBounds(430, 260 , 100, 25);
			panLinkPeriod2Empl.add(textPercentPerson);
			
			JLabel lblOnBoard = new JLabel("On Boarding percentage :");
			lblOnBoard.setBounds(550, 260 , 200, 25);
			panLinkPeriod2Empl.add(lblOnBoard);
			JTextField textOnBoard = new JTextField();
			textOnBoard.setBounds(700, 260 , 100, 25);
			panLinkPeriod2Empl.add(textOnBoard);
			
//			JLabel lblFieldSalary = new JLabel("Employee salary:");
//			lblFieldSalary.setBounds(20, 300 , 200, 25);
//			panLinkPeriod2Empl.add(lblFieldSalary);
			
			
			this.panLinkPeriod2Empl.validate();
			this.panLinkPeriod2Empl.repaint();
			this.tabbedPaneSettings.validate();
			this.mainFrame.validate();
			
			
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.close();
			}
		}
	}
}
