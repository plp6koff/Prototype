package com.consultancygrid.trz.ui.frame;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;
import static com.consultancygrid.trz.base.Constants.col0MW;
import static com.consultancygrid.trz.base.Constants.col10MW;
import static com.consultancygrid.trz.base.Constants.col1MW;
import static com.consultancygrid.trz.base.Constants.col2MW;
import static com.consultancygrid.trz.base.Constants.col3MW;
import static com.consultancygrid.trz.base.Constants.col4MW;
import static com.consultancygrid.trz.base.Constants.col5MW;
import static com.consultancygrid.trz.base.Constants.col6MW;
import static com.consultancygrid.trz.base.Constants.col9MW;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.consultancygrid.trz.actionListener.EmplsComboAL;
import com.consultancygrid.trz.actionListener.SavePeriodAL;
import com.consultancygrid.trz.actionListener.TimePeriodComboAL;
import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.render.EmployeeCustomRender;
import com.consultancygrid.trz.render.PeriodCustomRender;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.TrzComboBoxModel;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;
import javax.swing.JSeparator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JToggleButton;


public class PrototypeMainFrame extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3800210267510018282L;

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	private static EntityManager em = factory.createEntityManager();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrototypeMainFrame frame = new PrototypeMainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public PrototypeMainFrame() throws IOException {

		setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		setTitle("Trz");

		em.getTransaction().begin();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 900);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 0, 0);
		getContentPane().add(tabbedPane);
		PersonalCfgEmplsTableModel personalCfgModel = new PersonalCfgEmplsTableModel();
		PersonalCfgEmplsTable personalConfTable = new PersonalCfgEmplsTable();
		Vector tableData = new Vector();		
		Query qE = em.createQuery(" from Employee");
				
        JScrollPane pesonPanel = new JScrollPane(personalConfTable);
        pesonPanel.setBounds(20, 100, col6MW*24, 300);
		
		
		EmplComboBoxModel emplComboBoxModel = new EmplComboBoxModel((List<Employee>) qE.getResultList());

		JComboBox comboBoxEmployees = new JComboBox<>(emplComboBoxModel);
		
		comboBoxEmployees.setBounds(20, 10, 300, 20);
		comboBoxEmployees.setRenderer(new EmployeeCustomRender());
		
		EmplsComboAL emplsComboAL = new EmplsComboAL(this, comboBoxEmployees, personalConfTable);
		comboBoxEmployees.addActionListener(emplsComboAL);
		
		JPanel firstInnerPanel = new JPanel(null);
		firstInnerPanel.setLayout(null);
		
		
		firstInnerPanel.setLayout(null);
		firstInnerPanel.add(comboBoxEmployees);
		firstInnerPanel.add(pesonPanel);
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CONF_TAB_LABEL), firstInnerPanel);
		
		
		JPanel secondInnerPanel = new JPanel(null);
		secondInnerPanel.setLayout(null);

		Query qPeriod = em.createQuery(" from Period");

		TrzComboBoxModel trzComboBoxModel = new TrzComboBoxModel(
				(List<Period>) qPeriod.getResultList());

		JComboBox comboBox = new JComboBox<>(trzComboBoxModel);
		
		comboBox.setBounds(20, 10, 300, 20);
		comboBox.setRenderer(new PeriodCustomRender());
		secondInnerPanel.add(comboBox);
		
		GroupCfgEmplsTable table = new GroupCfgEmplsTable(); 

		JScrollPane jscp = new JScrollPane(table);
		jscp.setBounds(20, 100, col0MW + col1MW + col2MW + col3MW + col4MW + col5MW + col6MW
		// TODO comment until it is required
		//		+ col7MW + col8MW 
				+ col9MW + col10MW + 100, 600);
		secondInnerPanel.add(jscp);
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.GROUP_TAB_LABEL), secondInnerPanel);

		
		//Third panel 
		JPanel thirdInnerPanel = new JPanel(null);
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SALARY_BY_MOF_TAB_LABEL), thirdInnerPanel);
		//thirdInnerPanel.addTab();
		
		JTabbedPane tabbedPaneSettings = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SETTINGS), null, tabbedPaneSettings, null);
		
		JPanel panSetCrtPeriod = new JPanel(null);

		tabbedPaneSettings.addTab("Create Period",panSetCrtPeriod);
		
		
		JLabel lblCode = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_CODE));
		lblCode.setBounds(40, 30, 150, 25);
		panSetCrtPeriod.add(lblCode);
		JTextField textFieldCode = new JTextField();
		textFieldCode.setColumns(10);
		panSetCrtPeriod.add(textFieldCode);
		textFieldCode.setBounds(250, 30, 150, 25);
		
		JLabel lblRevenue = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_REVENUE));
		lblRevenue.setBounds(450, 30, 150, 25);
		panSetCrtPeriod.add(lblRevenue);
		JTextField textFieldRevenue = new JTextField();
		textFieldRevenue.setColumns(10);
		textFieldRevenue.setBounds(600, 30, 150 , 25);
		panSetCrtPeriod.add(textFieldRevenue);
		
		//Model Properties
		Properties p = new Properties();
		p.put("text.today", ResourceLoaderUtil.getLabels(LabelsConstants.CALENDAR_TODAY));
		p.put("text.month", ResourceLoaderUtil.getLabels(LabelsConstants.CALENDAR_MONTH));
		p.put("text.year", ResourceLoaderUtil.getLabels(LabelsConstants.CALENDAR_YEAR));
		//Start
		UtilDateModel modelStart = new UtilDateModel();
		modelStart.setDate(2014,01,01);
		JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, p);
		JDatePickerImpl datePickerStart = new JDatePickerImpl(datePanelStart, new DateLabelFormatter());
		datePickerStart.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_START_DATE));
		JLabel lblStartDate = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_START_DATE));
		lblStartDate.setBounds(40, 80, 150, 25);
		panSetCrtPeriod.add(lblStartDate);
		datePickerStart.setBounds(250, 80, 150, 30);
		panSetCrtPeriod.add(datePickerStart);
		//End
		UtilDateModel modelEnd = new UtilDateModel();
		modelEnd.setDate(2014,01,02);
		JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, p);
		JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanelEnd, new DateLabelFormatter());
		datePickerEnd.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_END_DATE));
		JLabel lblEndDate = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_END_DATE));
		lblEndDate.setBounds(450, 80, 150, 25);
		panSetCrtPeriod.add(lblEndDate);
		datePickerEnd.setBounds(600, 80, 150 , 30);
		panSetCrtPeriod.add(datePickerEnd);
		
		TimePeriodComboAL tPCAL = new TimePeriodComboAL(this, comboBox, table);
		
		
		int y = 120;
		int delta = 50;

		Query qPeriodTrzStatic = em.createQuery(" from TrzStatic");
		List<TrzStatic> trzResult =	(List<TrzStatic>) qPeriodTrzStatic.getResultList();
		HashMap<TrzStatic, JTextField> map = new HashMap<TrzStatic, JTextField> ();
		for (TrzStatic singleStatic : trzResult) {
			y = y + delta;
			int tempY = y; 
			JLabel lblPeriodSetting = new JLabel(singleStatic.getKeyDescription());
			lblPeriodSetting.setBounds(40, tempY , 300, 14);
			panSetCrtPeriod.add(lblPeriodSetting);
			
			
			JTextField textFieldValue = new JTextField();
			textFieldValue.setBounds(400, tempY, 146, 23);
			textFieldValue.setColumns(10);
			panSetCrtPeriod.add(textFieldValue);
			
			map.put(singleStatic, textFieldValue);
		}
		JButton btnSavePeriod = new JButton("Save");
		btnSavePeriod.setBounds(20, y + (trzResult.size()*50), 100,20);
		panSetCrtPeriod.add(btnSavePeriod);
		SavePeriodAL sPAL = new SavePeriodAL(this, datePickerStart, datePickerEnd, textFieldCode, textFieldRevenue, map);
		
		btnSavePeriod.addActionListener(sPAL);      
		comboBox.addActionListener(tPCAL);

		em.getTransaction().commit();
		em.close();
		factory.close();
	}
}
