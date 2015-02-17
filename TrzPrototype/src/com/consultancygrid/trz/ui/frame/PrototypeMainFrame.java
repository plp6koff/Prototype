package com.consultancygrid.trz.ui.frame;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.consultancygrid.trz.actionListener.SettingsDepartmentComboAL;
import com.consultancygrid.trz.actionListener.employee.EmployeeDeactivationTabComboAL;
import com.consultancygrid.trz.actionListener.employee.SaveEmployeeAL;
import com.consultancygrid.trz.actionListener.employee.SettingsEmployeeComboAL;
import com.consultancygrid.trz.actionListener.group.GroupEditRowAL;
import com.consultancygrid.trz.actionListener.group.GroupTabPeriodComboAL;
import com.consultancygrid.trz.actionListener.loadFile.LoadCreatePeriodPanelAL;
import com.consultancygrid.trz.actionListener.loadFile.LoadFileAL;
import com.consultancygrid.trz.actionListener.loadFile.OpenFileAL;
import com.consultancygrid.trz.actionListener.loadFile.SaveFileAL;
import com.consultancygrid.trz.actionListener.period.SavePeriodAL;
import com.consultancygrid.trz.actionListener.period.SettingsPeriodComboAL;
import com.consultancygrid.trz.actionListener.personal.PersonRowEditAL;
import com.consultancygrid.trz.actionListener.personal.EmployeePersonTabComboAL;
import com.consultancygrid.trz.actionListener.targetPeriod.TrgPrdSaveAL;
import com.consultancygrid.trz.actionListener.targetPeriod.TrgPrdSettingsComboAL;
import com.consultancygrid.trz.actionListener.targetPeriod.TrgPrdLvlsCfgComboAL;
import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TargetPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.render.DepartmentCustomRender;
import com.consultancygrid.trz.render.EmployeeCustomRender;
import com.consultancygrid.trz.render.HeaderRenderer;
import com.consultancygrid.trz.render.PeriodCustomRender;
import com.consultancygrid.trz.render.TargetPeriodCustomRender;
import com.consultancygrid.trz.ui.combo.DepartmentComboBoxModel;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.combo.TargetComboBoxModel;
import com.consultancygrid.trz.ui.table.employee.EmployeeActiveTable;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTable;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class PrototypeMainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3800210267510018282L;

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	private static EntityManager em = factory.createEntityManager();
	private JTextField textField;
	private JComboBox<Employee> comboBoxEmployees;
	

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
		setBounds(100, 100, 1280, 1000);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 0, 0);
		getContentPane().add(tabbedPane);

		// First Tab
		PersonalCfgEmplsTableModel personalCfgModel = new PersonalCfgEmplsTableModel();
		Vector tableData = new Vector();
		Query qE = em.createQuery(" from Employee as e order by e.firstName");
		List<Employee> employees = (List<Employee>)qE.getResultList();
		
		
		EmplComboBoxModel emplComboBoxModel = new EmplComboBoxModel(employees);
		JComboBox comboBoxEmployees = new JComboBox<>(emplComboBoxModel);
		//Draw
		drawFirstTab(tabbedPane, comboBoxEmployees);
		
		// Second Tab
		Query qPeriod = em.createQuery(" from Period order by code desc");
		List<Period> allPeriods = (List<Period>) qPeriod.getResultList();
		GroupCfgEmplsTable departTable = new GroupCfgEmplsTable();
		departTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		GroupCfgEmplsTable departTable2 = new GroupCfgEmplsTable();
		departTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTableHeader header = departTable.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(header.getDefaultRenderer()));
		JTableHeader header2 = departTable2.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(header2.getDefaultRenderer()));
		PeriodComboBoxModel trzComboBoxModel = new PeriodComboBoxModel(allPeriods);
		JComboBox periodsComboBox = new JComboBox<>(trzComboBoxModel);
		//Draw
		drawSecondTab(tabbedPane, departTable, departTable2, periodsComboBox);

		//Rest
		JPanel panLinkPeriod2Empl = new JPanel(null);
		JTabbedPane tabbedPaneSettings = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab(
				ResourceLoaderUtil.getLabels(LabelsConstants.SETTINGS), null,
				tabbedPaneSettings, null);
		
		/**
		 * Settings
		 */
		JComboBox comboBoxPeriod = new JComboBox<>(new PeriodComboBoxModel(allPeriods));
		
		//Load file
		drawLoadFileSettingsTab(tabbedPaneSettings, comboBoxPeriod);
		
		//Deactivation user
		Object[] dataheader ={ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_HEAD1),
							  ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_HEAD2)};
		Object[][] data = new Object[employees.size()][2];
		DefaultTableModel emplActTModel = new DefaultTableModel();
		int i = 0;
		for (Employee empl : (List<Employee>) qE.getResultList()) {
			Object[] dataInternal = new Object[2];
			String name = empl.getFirstName() + " " + empl.getLastName();
			Boolean isActive = ("Y".equals(empl.getIsActive()));
			dataInternal[0] = name;
			dataInternal[1] = isActive;
			data[i] = dataInternal;
			i++;
			
		}
		emplActTModel.setDataVector(data, dataheader);
		EmployeeActiveTable emplDeactvTable = new EmployeeActiveTable(emplActTModel);
		drawEmployeeDeactivation(tabbedPaneSettings,emplDeactvTable);
		//drawCreatePeriod(tabbedPane, departTable, tabbedPaneSettings,  comboBoxPeriod, panLinkPeriod2Empl);
		
		//Create Employee
		drawCreateEmployee(tabbedPaneSettings, allPeriods, comboBoxEmployees);
		//Employee 2 Period
		drawEmployee2Period(tabbedPaneSettings, comboBoxPeriod,  panLinkPeriod2Empl);
		Query qTargets = em.createQuery(" from TargetPeriod order by code desc");
		List<TargetPeriod> allTargets = (List<TargetPeriod>) qTargets.getResultList();
		JComboBox comboTargetsCTL = new JComboBox<>(new TargetComboBoxModel(allTargets));
		JComboBox comboTargetsP2E = new JComboBox<>(new TargetComboBoxModel(allTargets));
		drawCreateTarget(tabbedPaneSettings, comboTargetsCTL, comboTargetsP2E);
		drawCreateTargetLevels(tabbedPaneSettings, comboTargetsCTL);
		drawEmployee2TargetPeriod(tabbedPaneSettings, comboTargetsP2E,  emplComboBoxModel);
		em.getTransaction().commit();
		em.close();
		factory.close();
	}

	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawFirstTab(JTabbedPane tabbedPane, JComboBox comboBoxEmployees) throws IOException {

		JPanel firstInnerPanel = new JPanel(null);
		JFileChooser fc = new JFileChooser();
		PersonalCfgEmplsTable personalConfTable = new PersonalCfgEmplsTable();
		personalConfTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		comboBoxEmployees.setBounds(150, 40, 300, 25);
		comboBoxEmployees.setRenderer(new EmployeeCustomRender());
		JButton saveButt = new JButton("Export as PDF ...");
		saveButt.setEnabled(true);
		saveButt.setBounds(650, 40, 150, 25);
		
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveButt.addActionListener(new SaveFileAL(this, fc, null,  comboBoxEmployees, personalConfTable));
		firstInnerPanel.add(saveButt);
		JButton editRow = new JButton(
				ResourceLoaderUtil
						.getLabels(LabelsConstants.PERSONAL_CFG_EDIT_BTN));
		editRow.setBounds(490, 40, 150, 25);
		editRow.addActionListener(new PersonRowEditAL(this, personalConfTable,
				comboBoxEmployees));
		firstInnerPanel.add(editRow);

		EmployeePersonTabComboAL emplsComboAL = new EmployeePersonTabComboAL(
				this, comboBoxEmployees, personalConfTable);
		comboBoxEmployees.addActionListener(emplsComboAL);

		JScrollPane pesonPanel = new JScrollPane(personalConfTable);
		pesonPanel.setLayout(new ScrollPaneLayout());
		pesonPanel.setColumnHeader(new JViewport() {
			
			
			
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 110;
				return d;
			}
		});
		JTableHeader header = personalConfTable.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(header
				.getDefaultRenderer()));

		pesonPanel.setBounds(20, 150, 1200, 600);
		pesonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pesonPanel.setAutoscrolls(true);
		pesonPanel.setBorder(BorderFactory.createTitledBorder("Employees"));
		firstInnerPanel.setLayout(null);
		firstInnerPanel.add(comboBoxEmployees);
		firstInnerPanel.add(pesonPanel);
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CONF_TAB_LABEL),
					firstInnerPanel);
	}

	private static JComponent makeTitledPanel(String title, JComponent c) {
		JPanel p = new JPanel(new BorderLayout());
		p.add(c);
		p.setBorder(BorderFactory.createTitledBorder(title));
		return p;
	}

	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawSecondTab(JTabbedPane tabbedPane,
								GroupCfgEmplsTable table, 
								GroupCfgEmplsTable table2, 
								JComboBox comboBox) throws IOException {
		//Main panel
		JPanel secondInnerPanel = new JPanel(null);
		//Tabbed panel
		JTabbedPane tabbedPaneDept = new JTabbedPane();
		tabbedPaneDept.setBounds(100, 100, 1000, 700);
		secondInnerPanel.add(tabbedPaneDept);
		//Scroll 1
		JScrollPane depTablesPanel1 = new JScrollPane(table);
		depTablesPanel1.setLayout(new ScrollPaneLayout());
		//depTablesPanel1.setBounds(150, 100, 910, 600);
		depTablesPanel1.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 110;
				return d;
			}
		});
		//Scroll 2
		JScrollPane depTablesPanel2 = new JScrollPane(table2);
		depTablesPanel2.setLayout(new ScrollPaneLayout());
		//depTablesPanel2.setBounds(150, 700, 910, 600);
		depTablesPanel2.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 110;
				return d;
			}
		});
		secondInnerPanel.setLayout(null);
		table.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_TABLE_TTP));
		comboBox.setBounds(150, 40, 300, 25);
		comboBox.setRenderer(new PeriodCustomRender());
		secondInnerPanel.add(comboBox);
		//Save Button
		JButton editRow = new JButton(
				ResourceLoaderUtil
						.getLabels(LabelsConstants.PERSONAL_CFG_EDIT_BTN));
		editRow.setBounds(490, 40, 150, 25);
		editRow.addActionListener(new GroupEditRowAL(this, table, table2, comboBox, tabbedPaneDept));
		secondInnerPanel.add(editRow);
		//Action listener
		GroupTabPeriodComboAL tPCAL = new GroupTabPeriodComboAL(this, comboBox, table, table2);
		
		
		depTablesPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		depTablesPanel1.setAutoscrolls(true);
		tabbedPaneDept.addTab("Departments",depTablesPanel1);
		
		
		
		depTablesPanel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		depTablesPanel2.setAutoscrolls(true);
		tabbedPaneDept.addTab("No Departments",depTablesPanel2);
		
		
		
		tabbedPane.addTab(
				ResourceLoaderUtil.getLabels(LabelsConstants.GROUP_TAB_LABEL),
				secondInnerPanel);

		comboBox.addActionListener(tPCAL);
	}

	/**
	 * This methods should add the first 3 combo boxes for the add period to
	 * employee
	 * 
	 * @param tabbedPaneSettings
	 * @param allPeriods
	 * @throws IOException
	 */
	private void drawEmployee2Period(JTabbedPane tabbedPaneSettings,
			JComboBox comboBoxPeriod1, JPanel panLinkPeriod2Empl)
			throws IOException {

		JComboBox comboBoxEmployees1 = new JComboBox<>(new EmplComboBoxModel());

		JComboBox comboBoxDepartment1 = new JComboBox<>(
				new DepartmentComboBoxModel());

		comboBoxEmployees1.setBounds(560, 50, 200, 25);
		comboBoxEmployees1.setRenderer(new EmployeeCustomRender());
		comboBoxEmployees1.setEnabled(false);
		comboBoxEmployees1.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.SET_TAB_EMPL2PER_CB_EMPL_TTIP));

		comboBoxPeriod1.setBounds(40, 50, 200, 25);
		comboBoxPeriod1.setRenderer(new PeriodCustomRender());
		comboBoxPeriod1.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.SET_TAB_EMPL2PER_CB_PERIOD_TTIP));

		comboBoxDepartment1.setBounds(300, 50, 200, 25);
		comboBoxDepartment1.setRenderer(new DepartmentCustomRender());
		comboBoxDepartment1.setEnabled(false);
		comboBoxDepartment1.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.SET_TAB_EMPL2PER_CB_DEP_TTIP));

		JPanel createFormPanel = new JPanel();
		createFormPanel.setLayout(null);
		createFormPanel.setBounds(10, 60, 1000, 500);
		createFormPanel.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.SET_TAB_EMPL2PER_FORM_TTIP));
		JLabel lblEmpl1 = new JLabel(
				ResourceLoaderUtil
						.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_REVENUE));
		lblEmpl1.setBounds(50, 100, 100, 25);
		createFormPanel.add(lblEmpl1);

		int count = 0;

		JTextField textFieldValue = new JTextField();
		textFieldValue.setBounds(150, 100, 200, 25);
		textFieldValue.setText("0.0");
		createFormPanel.add(textFieldValue, ++count);
		JButton btnSavePeriod = new JButton(
				ResourceLoaderUtil
						.getLabels(LabelsConstants.SET_TAB_EMPL2PER_SAVE));
		btnSavePeriod.setBounds(20, 300, 250, 25);
		createFormPanel.add(btnSavePeriod, ++count);
		JLabel lblFieldSettings = new JLabel(
				ResourceLoaderUtil
						.getLabels(LabelsConstants.SET_TAB_EMPL2PER_EMPL_SET));
		lblFieldSettings.setBounds(20, 150, 200, 25);
		createFormPanel.add(lblFieldSettings, ++count);

		addTextField(createFormPanel,
				LabelsConstants.SET_TAB_EMPL2PER_BRUTOSTAT, 50, 200, 200,
				count, 100);
		addTextField(createFormPanel,
				LabelsConstants.SET_TAB_EMPL2PER_BRUTOSANDARD, 350, 520, 200,
				count, 100);
		addTextField(createFormPanel, LabelsConstants.SET_TAB_EMPL2PER_AVANS,
				50, 200, 230, count, 100);
		addTextField(createFormPanel,
				LabelsConstants.SET_TAB_EMPL2PER_PERCENT_ALL, 350, 520, 230,
				count, 200);
		addTextField(createFormPanel,
				LabelsConstants.SET_TAB_EMPL2PER_PERCENT_GROUP, 650, 820, 230,
				count, 100);
		addTextField(createFormPanel,
				LabelsConstants.SET_TAB_EMPL2PER_PERSONAL_PERCENT, 50, 200,
				260, count, 200);
		addTextField(createFormPanel,
				LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_ALL, 350, 520, 260,
				count, 200);
		addTextField(createFormPanel,
				LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_GROUP, 650, 820, 260,
				count, 200);

		createFormPanel.setVisible(false);
		panLinkPeriod2Empl.add(createFormPanel);

		comboBoxEmployees1.addActionListener(new SettingsEmployeeComboAL(this,
				panLinkPeriod2Empl, comboBoxPeriod1, comboBoxDepartment1,
				comboBoxEmployees1, createFormPanel));

		panLinkPeriod2Empl.add(comboBoxEmployees1);
		comboBoxPeriod1.addActionListener(new SettingsPeriodComboAL(this,
				comboBoxPeriod1, comboBoxDepartment1, comboBoxEmployees1));
		panLinkPeriod2Empl.add(comboBoxPeriod1);
		comboBoxDepartment1
				.addActionListener(new SettingsDepartmentComboAL(this,
						comboBoxPeriod1, comboBoxDepartment1,
						comboBoxEmployees1));
		panLinkPeriod2Empl.add(comboBoxDepartment1);
		tabbedPaneSettings.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.SET_TAB_EMPL2PER_HEADER),
				panLinkPeriod2Empl);
	}

	private void drawEmployeeDeactivation(JTabbedPane tabbedPaneSettings, EmployeeActiveTable table) throws IOException {

		JPanel panel = new JPanel();
		//table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setBounds(10, 10, 400, 600);
		JScrollPane editEmployeePanel = new JScrollPane(table);
		panel.add(editEmployeePanel);
		editEmployeePanel.setBounds(40, 40, 500, 600);
		JButton saveButton = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.SET_CRT_EMPL_SAVE));
		saveButton.setBounds(100, 140, 200, 30);
		saveButton.addActionListener(new EmployeeDeactivationTabComboAL(this, table));
		panel.add(saveButton);
		tabbedPaneSettings.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_TAB), 
								panel);
		
	}
	
	private void drawCreateEmployee(JTabbedPane tabbedPaneSettings, List<Period> periods, JComboBox employeesCombo)
			throws IOException {


		JPanel createFormPanel = new JPanel();
		createFormPanel.setLayout(null);
		createFormPanel.setBounds(10, 60, 1000, 500);
		
		
		Map<String, JTextField> map = new HashMap<String, JTextField>();

		JLabel l1 = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_CRT_EMPL_FNAME));
		l1.setBounds(30, 50, 150, 25);
		createFormPanel.add(l1);
		JTextField tf1 = new JTextField();
		tf1.setBounds(200, 50, 100, 25);
		createFormPanel.add(tf1);
		
		map.put(LabelsConstants.SET_CRT_EMPL_FNAME, tf1);
		
		
		JLabel l2 = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_CRT_EMPL_LNAME));
		l2.setBounds(30 , 100,  150, 25);
		createFormPanel.add(l2);
		JTextField tf2 = new JTextField();
		tf2.setBounds(200, 100, 100, 25);
		createFormPanel.add(tf2);
		
		map.put(LabelsConstants.SET_CRT_EMPL_LNAME, tf2);
		
		JLabel l3 = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_CRT_EMPL_MATCHODE));
		l3.setBounds(30, 150, 150, 25);
		createFormPanel.add(l3);
		JTextField tf3 = new JTextField();
		tf3.setBounds(200, 150, 100, 25);
		createFormPanel.add(tf3);
		map.put(LabelsConstants.SET_CRT_EMPL_MATCHODE, tf3);
		
		JButton btnSavePeriod = new JButton(
				ResourceLoaderUtil.getLabels(LabelsConstants.SET_CRT_EMPL_SAVE));
		btnSavePeriod.setBounds(30, 200, 200, 25);
		btnSavePeriod.addActionListener(new SaveEmployeeAL(this, createFormPanel,  map, periods, employeesCombo));
		createFormPanel.add(btnSavePeriod);
		tabbedPaneSettings.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SET_LOAD_FILE_TAB),	createFormPanel);
	}
	
	
	private void drawCreateTarget(JTabbedPane tabbedPaneSettings,JComboBox comboTargetsCTL,	JComboBox comboTargetsP2E)
			throws IOException {


		JPanel panSetCrtTrg = new JPanel();
		panSetCrtTrg.setLayout(null);
		panSetCrtTrg.setBounds(10, 60, 1000, 500);
		
		
		Map<String, JTextField> map = new HashMap<String, JTextField>();

		JLabel l1 = new JLabel(ResourceLoaderUtil
				.getLabels(LabelsConstants.TRG_SETT_TAB_CREATE_CODE));
		l1.setBounds(40, 50, 150, 25);
		panSetCrtTrg.add(l1);
		JTextField tf1 = new JTextField();
		tf1.setBounds(200, 50, 100, 25);
		panSetCrtTrg.add(tf1);
		Properties p = new Properties();
		p.put("text.today",
				ResourceLoaderUtil.getLabels(LabelsConstants.CALENDAR_TODAY));
		p.put("text.month",
				ResourceLoaderUtil.getLabels(LabelsConstants.CALENDAR_MONTH));
		p.put("text.year",
				ResourceLoaderUtil.getLabels(LabelsConstants.CALENDAR_YEAR));
		
		UtilDateModel modelStart = new UtilDateModel();
		modelStart.setDate(2014, 01, 01);
		JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, p);
		JDatePickerImpl datePickerStart = new JDatePickerImpl(datePanelStart,
				new DateLabelFormatter());
		datePickerStart.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERIOD_START_DATE));
		JLabel lblStartDate = new JLabel(
				ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_START_DATE));
		lblStartDate.setBounds(40, 100, 150, 25);
		panSetCrtTrg.add(lblStartDate);
		datePickerStart.setBounds(200, 100, 150, 30);
		panSetCrtTrg.add(datePickerStart);
		// End
		UtilDateModel modelEnd = new UtilDateModel();
		modelEnd.setDate(2014, 01, 02);
		JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, p);
		JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanelEnd,
				new DateLabelFormatter());
		datePanelEnd.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERIOD_END_DATE));
		datePickerEnd.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERIOD_END_DATE));
		JLabel lblEndDate = new JLabel(
				ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_END_DATE));
		lblEndDate.setBounds(450, 100, 150, 25);
		panSetCrtTrg.add(lblEndDate);
		datePickerEnd.setBounds(610, 100, 150, 30);
		panSetCrtTrg.add(datePickerEnd);
		
		
		JLabel l2 = new JLabel(ResourceLoaderUtil
				.getLabels(LabelsConstants.TRG_SETT_TAB_CREATE_LENGHT));
		l2.setBounds(40 , 150,  150, 25);
		panSetCrtTrg.add(l2);
		JTextField tf2 = new JTextField();
		tf2.setBounds(200, 150, 100, 25);
		panSetCrtTrg.add(tf2);
		
		
		JButton btnSavePeriod = new JButton(ResourceLoaderUtil
				.getLabels(LabelsConstants.TRG_SETT_TAB_CREATE_SAVE));
		btnSavePeriod.setBounds(30, 200, 200, 25);
		btnSavePeriod.addActionListener(new TrgPrdSaveAL(this, tf1, tf2, datePickerStart, datePickerEnd, comboTargetsCTL, comboTargetsP2E));
		panSetCrtTrg.add(btnSavePeriod);
		tabbedPaneSettings.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.TRG_SETT_TAB_CREATE), panSetCrtTrg);
	}




	private JTextField addTextField(JPanel ancestor, String labelConstant,
			int xlbl, int xtxt, int y, int count, int lblLght)
			throws IOException {

		JLabel label = new JLabel(ResourceLoaderUtil.getLabels(labelConstant));
		label.setBounds(xlbl, y, lblLght, 25);
		ancestor.add(label, ++count);
		JTextField text = new JTextField();
		text.setBounds(xtxt, y, 100, 25);
		text.setText("0.0");
		ancestor.add(text, ++count);
		return text;
	}

	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawLoadFileSettingsTab(
				JTabbedPane tabbedPaneSettings,
				JComboBox<Period> comboBoxPeriod) 
						throws IOException {

		
		JPanel createPeriodPanel = new JPanel(null);
		createPeriodPanel.setBounds(5, 5, 1000, 150);
		
		JLabel lblCode = new JLabel(
				ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_CODE));
		lblCode.setBounds(40, 50, 100, 25);
		lblCode.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_CODE_TTIP));
		createPeriodPanel.add(lblCode,0);
		JTextField textFieldCode = new JTextField();
		textFieldCode.setColumns(10);
		createPeriodPanel.add(textFieldCode,1);
		textFieldCode.setBounds(220, 50, 150, 25);
		textFieldCode.setText("");
		textFieldCode.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_CODE_TTIP));
		JButton loadPanel = new JButton("Start creation .. ");
		LoadCreatePeriodPanelAL loadAl = new LoadCreatePeriodPanelAL(this, textFieldCode, createPeriodPanel, comboBoxPeriod);
		loadPanel.addActionListener(loadAl);
		loadPanel.setBounds(430, 50, 200, 20);
		createPeriodPanel.add(loadPanel,2);
		createPeriodPanel.setBorder(BorderFactory
				.createTitledBorder(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_HEADER)));
		tabbedPaneSettings.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SET_LOAD_FILE_TAB), createPeriodPanel);

	}

//	private JComboBox drawCreatePeriod(JTabbedPane tabbedPane,
//			GroupCfgEmplsTable table, JTabbedPane tabbedPaneSettings,
//			JComboBox comboBoxPeriod1, JPanel panLinkPeriod2Empl)
//			throws IOException {
//
//
//		JPanel panSetCrtPeriod = new JPanel(null);
//
//		tabbedPaneSettings.addTab(ResourceLoaderUtil
//				.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_HEADER),
//				panSetCrtPeriod);
//
//		JLabel lblCode = new JLabel(
//				ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_CODE));
//		lblCode.setBounds(40, 30, 150, 25);
//		lblCode.setToolTipText(ResourceLoaderUtil
//				.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_CODE_TTIP));
//		panSetCrtPeriod.add(lblCode);
//		JTextField textFieldCode = new JTextField();
//		textFieldCode.setColumns(10);
//		panSetCrtPeriod.add(textFieldCode);
//		textFieldCode.setBounds(250, 30, 150, 25);
//		textFieldCode.setToolTipText(ResourceLoaderUtil
//				.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_CODE_TTIP));
//
//		JLabel lblRevenue = new JLabel(
//				ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_REVENUE));
//		lblRevenue.setBounds(450, 30, 150, 25);
//		panSetCrtPeriod.add(lblRevenue);
//		lblRevenue.setToolTipText(ResourceLoaderUtil
//				.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_REVENUE_TTIP));
//		JTextField textFieldRevenue = new JTextField();
//		textFieldRevenue.setColumns(10);
//		textFieldRevenue.setBounds(600, 30, 150, 25);
//		panSetCrtPeriod.add(textFieldRevenue);
//		textFieldRevenue.setToolTipText(ResourceLoaderUtil
//				.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_REVENUE_TTIP));
//
//		// Model Properties
//		Properties p = new Properties();
//		p.put("text.today",
//				ResourceLoaderUtil.getLabels(LabelsConstants.CALENDAR_TODAY));
//		p.put("text.month",
//				ResourceLoaderUtil.getLabels(LabelsConstants.CALENDAR_MONTH));
//		p.put("text.year",
//				ResourceLoaderUtil.getLabels(LabelsConstants.CALENDAR_YEAR));
//		// Start
//		UtilDateModel modelStart = new UtilDateModel();
//		modelStart.setDate(2014, 01, 01);
//		JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, p);
//		JDatePickerImpl datePickerStart = new JDatePickerImpl(datePanelStart,
//				new DateLabelFormatter());
//		datePickerStart.setToolTipText(ResourceLoaderUtil
//				.getLabels(LabelsConstants.PERIOD_START_DATE));
//		JLabel lblStartDate = new JLabel(
//				ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_START_DATE));
//		lblStartDate.setBounds(40, 80, 150, 25);
//		panSetCrtPeriod.add(lblStartDate);
//		datePickerStart.setBounds(250, 80, 150, 30);
//		panSetCrtPeriod.add(datePickerStart);
//		// End
//		UtilDateModel modelEnd = new UtilDateModel();
//		modelEnd.setDate(2014, 01, 02);
//		JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, p);
//		JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanelEnd,
//				new DateLabelFormatter());
//		datePanelEnd.setToolTipText(ResourceLoaderUtil
//				.getLabels(LabelsConstants.PERIOD_END_DATE));
//		datePickerEnd.setToolTipText(ResourceLoaderUtil
//				.getLabels(LabelsConstants.PERIOD_END_DATE));
//		JLabel lblEndDate = new JLabel(
//				ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_END_DATE));
//		lblEndDate.setBounds(450, 80, 150, 25);
//		panSetCrtPeriod.add(lblEndDate);
//		datePickerEnd.setBounds(600, 80, 150, 30);
//		panSetCrtPeriod.add(datePickerEnd);
//
//		// GroupTabPeriodComboAL tPCAL = new GroupTabPeriodComboAL(this,
//		// comboBox, table);
//
//		JPanel createFormPanel = new JPanel();
//		createFormPanel.setLayout(null);
//		createFormPanel.setBounds(10, 85, 600, 400);
//
//		int y = 100;
//		int delta = 30;
//
//		Query qPeriodTrzStatic = em.createQuery(" from TrzStatic");
//		List<TrzStatic> trzResult = (List<TrzStatic>) qPeriodTrzStatic
//				.getResultList();
//
//		HashMap<TrzStatic, JTextField> map = new HashMap<TrzStatic, JTextField>();
//		for (TrzStatic singleStatic : trzResult) {
//
//			JLabel lblPeriodSetting = new JLabel(
//					singleStatic.getKeyDescription());
//			lblPeriodSetting.setBounds(40, y, 250, 25);
//			createFormPanel.add(lblPeriodSetting);
//			JTextField textFieldValue = new JTextField();
//			textFieldValue.setBounds(300, y, 150, 25);
//			textFieldValue.setColumns(10);
//			createFormPanel.add(textFieldValue);
//			map.put(singleStatic, textFieldValue);
//			y = y + delta;
//		}
//
//		y = y + 40;
//
//		Query qPeriodDepartment = em.createQuery(" from Department");
//		List<Department> deptResult = (List<Department>) qPeriodDepartment
//				.getResultList();
//
//		HashMap<Department, JTextField> mapDept = new HashMap<Department, JTextField>();
//		for (Department singleDept : deptResult) {
//
//			JLabel lblDept = new JLabel(
//					ResourceLoaderUtil
//							.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_REVENUE_DEPT)
//							+ singleDept.getCode());
//			lblDept.setBounds(40, y, 250, 25);
//			createFormPanel.add(lblDept);
//
//			JTextField textFieldValue = new JTextField();
//			textFieldValue.setBounds(300, y, 150, 25);
//			textFieldValue.setColumns(10);
//			textFieldValue.setText("0.0");
//			createFormPanel.add(textFieldValue);
//			mapDept.put(singleDept, textFieldValue);
//			y = y + delta;
//		}
//
//		JButton btnSavePeriod = new JButton(
//				ResourceLoaderUtil
//						.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_SAVE));
//		btnSavePeriod.setBounds(20, y + 40, 200, 25);
//		createFormPanel.add(btnSavePeriod);
//		createFormPanel.setVisible(false);
//		SavePeriodAL sPAL = new SavePeriodAL(this, datePickerStart,
//				datePickerEnd, textFieldCode, textFieldRevenue, map, mapDept,
//				createFormPanel, comboBoxPeriod1, panLinkPeriod2Empl);
//		panSetCrtPeriod.add(createFormPanel);
//		btnSavePeriod.addActionListener(sPAL);
//
//		return comboBoxPeriod1;
//
//	}
	
	
	private void drawCreateTargetLevels(JTabbedPane tabbedPaneSettings,JComboBox comboTargetsCTL)
			throws IOException {

		
		
		JPanel createFormPanel = new JPanel();
		createFormPanel.setLayout(null);
		createFormPanel.setBounds(10, 10, 1000, 500);
		comboTargetsCTL.setBounds(40, 40, 200, 25);
		comboTargetsCTL.setRenderer(new TargetPeriodCustomRender());
		comboTargetsCTL.setToolTipText("Select target period!");
		createFormPanel.add(comboTargetsCTL);
		
		TargetLevelCfgTable table = new TargetLevelCfgTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.removeColumn(table.getColumnModel().getColumn(0));
		JScrollPane trzLevelPanel = new JScrollPane(table);
		trzLevelPanel.setBounds(40, 100, 200, 200);
		trzLevelPanel.setLayout(new ScrollPaneLayout());
		createFormPanel.add(trzLevelPanel);
		
		comboTargetsCTL.addActionListener(new TrgPrdLvlsCfgComboAL(this, createFormPanel, table,comboTargetsCTL));

		tabbedPaneSettings.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.TRG_SETT_TAB_LVLS_ADD), createFormPanel);
	

	}
	
	/**
	 * This methods should add the first 3 combo boxes for the add period to
	 * employee
	 * 
	 * @param tabbedPaneSettings
	 * @param allPeriods
	 * @throws IOException
	 */
	private void drawEmployee2TargetPeriod(JTabbedPane tabbedPaneSettings,
			JComboBox comboTargetBox,
			EmplComboBoxModel emplComboBoxModel)
			throws IOException {

		JComboBox comboBoxEmployees = new JComboBox<>(emplComboBoxModel);

		JPanel createFormPanel = new JPanel();
		createFormPanel.setLayout(null);
		createFormPanel.setBounds(10, 60, 1000, 500);

		comboBoxEmployees.setBounds(40, 50, 200, 25);
		comboBoxEmployees.setRenderer(new EmployeeCustomRender());
		comboBoxEmployees.setToolTipText(ResourceLoaderUtil
				.getLabels(LabelsConstants.TRG_SETT_TAB_EMPL2TRG));
		createFormPanel.add(comboBoxEmployees);
		comboTargetBox.setBounds(400, 50, 200, 25);
		comboTargetBox.setRenderer(new TargetPeriodCustomRender());
		comboTargetBox.setToolTipText("Select target!");
		createFormPanel.add(comboTargetBox);
		comboTargetBox.addActionListener(new TrgPrdSettingsComboAL(this, createFormPanel, comboTargetBox, comboBoxEmployees));
		tabbedPaneSettings.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.TRG_SETT_TAB_EMPL2TRG), createFormPanel);
	}

}
