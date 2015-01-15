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
import static com.consultancygrid.trz.base.Constants.col7MW;
import static com.consultancygrid.trz.base.Constants.col9MW;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.JTableHeader;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.consultancygrid.trz.actionListener.CancelGroupRowAL;
import com.consultancygrid.trz.actionListener.CancelPersonalRowAL;
import com.consultancygrid.trz.actionListener.EditGroupRowAL;
import com.consultancygrid.trz.actionListener.EditPersonRowAL;
import com.consultancygrid.trz.actionListener.EmplsComboAL;
import com.consultancygrid.trz.actionListener.LoadFileAL;
import com.consultancygrid.trz.actionListener.OpenFileAL;
import com.consultancygrid.trz.actionListener.SavePeriodAL;
import com.consultancygrid.trz.actionListener.SavePersonRowAL;
import com.consultancygrid.trz.actionListener.SelectEndDatePeriodAL;
import com.consultancygrid.trz.actionListener.SettingsDepartmentComboAL;
import com.consultancygrid.trz.actionListener.SettingsEmployeeComboAL;
import com.consultancygrid.trz.actionListener.SettingsPeriodComboAL;
import com.consultancygrid.trz.actionListener.GroupTabPeriodComboAL;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.render.DepartmentCustomRender;
import com.consultancygrid.trz.render.EmployeeCustomRender;
import com.consultancygrid.trz.render.HeaderRenderer;
import com.consultancygrid.trz.render.PeriodCustomRender;
import com.consultancygrid.trz.ui.combo.DepartmentComboBoxModel;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.PersonalCfgEmplsTableModel;
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
		setBounds(100, 100, 1050, 600);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 0, 0);
		getContentPane().add(tabbedPane);
		
		Query qPeriod = em.createQuery(" from Period");
		List<Period> allPeriods = (List<Period>) qPeriod.getResultList();
		
		GroupCfgEmplsTable table = new GroupCfgEmplsTable(); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		drawInitialTab(tabbedPane);
		//First Tab
		drawFirstTab(tabbedPane);
		//Second Tab
		drawSecondTab(tabbedPane,table, allPeriods);
		
		JPanel panLinkPeriod2Empl = new JPanel(null);
		
		JTabbedPane tabbedPaneSettings = new JTabbedPane(JTabbedPane.TOP);

		JComboBox comboBoxPeriod = new JComboBox<>(new PeriodComboBoxModel(allPeriods));
		
		//Period Settings
		drawCreatePeriod(tabbedPane, table, tabbedPaneSettings, comboBoxPeriod, panLinkPeriod2Empl);
		//Period to Employee
		drawEmployee2Period(tabbedPaneSettings, comboBoxPeriod, panLinkPeriod2Empl);
		
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
	private void drawInitialTab(JTabbedPane tabbedPane) throws IOException {
		
		JPanel firstInnerPanel = new JPanel(null);         
		
		JButton openButton = new JButton("Open a File..."); 
		JButton loadButton = new JButton("Load a File...");
		
	    JFileChooser  fc = new JFileChooser();
	    JTextArea log = new JTextArea(5,20);
        log.setBounds(20,20,400, 20);
        log.setEditable(false);
	    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    openButton.setBounds(450, 20, 200, 20);
	    openButton.addActionListener(new OpenFileAL(this, fc, log));
	    
	    loadButton.setBounds(680, 20, 200, 20);
	    loadButton.addActionListener(new LoadFileAL(this, fc, log));
	    
	    firstInnerPanel.add(log);
	    firstInnerPanel.add(openButton);
	    firstInnerPanel.add(loadButton);
	    
	    tabbedPane.addTab("Load File Tab", firstInnerPanel);
	}
	
	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException 
	 */
	private void drawFirstTab(JTabbedPane tabbedPane) throws IOException {
		
		JPanel firstInnerPanel = new JPanel(null);         
		
		PersonalCfgEmplsTableModel personalCfgModel = new PersonalCfgEmplsTableModel();
		PersonalCfgEmplsTable personalConfTable = new PersonalCfgEmplsTable();
		personalConfTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Vector tableData = new Vector();		
		Query qE = em.createQuery(" from Employee");
		
		EmplComboBoxModel emplComboBoxModel = new EmplComboBoxModel((List<Employee>) qE.getResultList());

		JComboBox comboBoxEmployees = new JComboBox<>(emplComboBoxModel);
		
		comboBoxEmployees.setBounds(20, 10, 300, 20);
		comboBoxEmployees.setRenderer(new EmployeeCustomRender());
		
		JButton editRow = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_EDIT_BTN));
		editRow.setBounds(340, 10, 150, 20);
		editRow.addActionListener(new EditPersonRowAL(this, personalConfTable,comboBoxEmployees));
		firstInnerPanel.add(editRow);
		
		JButton saveRow = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_SAVE_BTN));
		saveRow.setBounds(510, 10, 150, 20);
		saveRow.addActionListener(new SavePersonRowAL(this, personalConfTable,comboBoxEmployees));
		firstInnerPanel.add(saveRow);
		
		JButton cancel = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.BUTT_CANCEL));
		cancel.setBounds(690, 10, 150, 20);
		cancel.addActionListener(new CancelPersonalRowAL(this, personalConfTable,comboBoxEmployees));
		firstInnerPanel.add(cancel);
		
		EmplsComboAL emplsComboAL = new EmplsComboAL(this, comboBoxEmployees, personalConfTable);
		comboBoxEmployees.addActionListener(emplsComboAL);
		
		JScrollPane pesonPanel = new JScrollPane(personalConfTable);
		pesonPanel.setLayout(new ScrollPaneLayout());
		JTableHeader header = personalConfTable.getTableHeader();
	    header.setDefaultRenderer(new HeaderRenderer(header.getDefaultRenderer()));
	    pesonPanel.setBounds(20, 100, 1050 , 500);
	    pesonPanel.setAutoscrolls(true);
		firstInnerPanel.setLayout(null);
		firstInnerPanel.add(comboBoxEmployees);
		firstInnerPanel.add(pesonPanel);
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CONF_TAB_LABEL), firstInnerPanel);
	}
	
	
	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException 
	 */
	private void drawSecondTab(JTabbedPane tabbedPane,GroupCfgEmplsTable table,List<Period> allPeriods) throws IOException {
		
		JPanel secondInnerPanel = new JPanel(null);
		secondInnerPanel.setLayout(null);
		table.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.GROUP_CONF_TABLE_TTP));
		PeriodComboBoxModel trzComboBoxModel = new PeriodComboBoxModel(allPeriods);

		JComboBox comboBox = new JComboBox<>(trzComboBoxModel);
		comboBox.setBounds(20, 10, 300, 20);
		comboBox.setRenderer(new PeriodCustomRender());
		secondInnerPanel.add(comboBox);
		comboBox.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.GROUP_CONF_CB_PERIOD_TTP));
		
		JButton editRow = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_EDIT_BTN));
		editRow.setBounds(340, 10, 150, 20);
		editRow.addActionListener(new EditGroupRowAL(this, table,comboBox));
		editRow.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.GROUP_CONF_EDIT_TTP));
		secondInnerPanel.add(editRow);
		JButton cancel = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.BUTT_CANCEL));
		cancel.setBounds(690, 10, 150, 20);
		cancel.addActionListener(new CancelGroupRowAL(this, table,comboBox));
		secondInnerPanel.add(cancel);
		
		JButton saveRow = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_SAVE_BTN));
		saveRow.setBounds(510, 10, 150, 20);
		//saveRow.addActionListener(new SaveRowAL(this, personalConfTable,comboBoxEmployees));
		secondInnerPanel.add(saveRow);
		
		GroupTabPeriodComboAL tPCAL = new GroupTabPeriodComboAL(this, comboBox, table);
		
		JScrollPane jscp = new JScrollPane(table);
		jscp.setLayout(new ScrollPaneLayout());
		jscp.setBounds(20, 100, 1000, 500);
		secondInnerPanel.add(jscp);
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.GROUP_TAB_LABEL), secondInnerPanel);
		
		comboBox.addActionListener(tPCAL);
	}
	
	/**
	 * This methods should add the first 3 combo boxes for the add period to employee
	 * @param tabbedPaneSettings
	 * @param allPeriods
	 * @throws IOException 
	 */
	private void drawEmployee2Period(JTabbedPane tabbedPaneSettings, 
								JComboBox comboBoxPeriod1,
								JPanel panLinkPeriod2Empl) throws IOException {
		
		JComboBox comboBoxEmployees1 = new JComboBox<>(new EmplComboBoxModel());
		
		JComboBox comboBoxDepartment1 = new JComboBox<>(new DepartmentComboBoxModel());
		
		comboBoxEmployees1.setBounds(560, 50, 200, 25);
		comboBoxEmployees1.setRenderer(new EmployeeCustomRender());
		comboBoxEmployees1.setEnabled(false);
		comboBoxEmployees1.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_CB_EMPL_TTIP));
		
		comboBoxPeriod1.setBounds(40, 50, 200, 25);
		comboBoxPeriod1.setRenderer(new PeriodCustomRender());
		comboBoxPeriod1.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_CB_PERIOD_TTIP));
		
		comboBoxDepartment1.setBounds(300, 50, 200, 25);
		comboBoxDepartment1.setRenderer(new DepartmentCustomRender());
		comboBoxDepartment1.setEnabled(false);
		comboBoxDepartment1.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_CB_DEP_TTIP));
		
	
		JPanel createFormPanel = new JPanel();
		createFormPanel.setLayout(null);
		createFormPanel.setBounds(10, 60 , 1000 , 500);
		createFormPanel.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_FORM_TTIP));
		JLabel lblEmpl1 = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_REVENUE));
		lblEmpl1.setBounds(50, 100 , 100, 25);
		createFormPanel.add(lblEmpl1);
		
		int count = 0;
		
		JTextField textFieldValue = new JTextField();
		textFieldValue.setBounds(150, 100 , 200, 25);
		textFieldValue.setText("0.0");	
		createFormPanel.add(textFieldValue,++count);
		JButton btnSavePeriod = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_SAVE));
		btnSavePeriod.setBounds(20, 300, 250 ,25);
		createFormPanel.add(btnSavePeriod,++count);
		JLabel lblFieldSettings = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_EMPL_SET));
		lblFieldSettings.setBounds(20, 150 , 200, 25);
		createFormPanel.add(lblFieldSettings,++count);
		
		addTextField(createFormPanel, LabelsConstants.SET_TAB_EMPL2PER_BRUTOSTAT, 50, 200, 200, count, 100);
		addTextField(createFormPanel, LabelsConstants.SET_TAB_EMPL2PER_BRUTOSANDARD, 350, 520, 200, count, 100);
		addTextField(createFormPanel, LabelsConstants.SET_TAB_EMPL2PER_AVANS, 50, 200, 230, count, 100);
		addTextField(createFormPanel, LabelsConstants.SET_TAB_EMPL2PER_PERCENT_ALL, 350, 520, 230, count, 200);
		addTextField(createFormPanel, LabelsConstants.SET_TAB_EMPL2PER_PERCENT_GROUP, 650, 820, 230, count, 100);
		addTextField(createFormPanel, LabelsConstants.SET_TAB_EMPL2PER_PERSONAL_PERCENT, 50, 200, 260, count, 200);
		addTextField(createFormPanel, LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_ALL, 350, 520, 260, count, 200);
		addTextField(createFormPanel, LabelsConstants.SET_TAB_EMPL2PER_ON_BOARD_GROUP, 650, 820, 260, count, 200);
		
		createFormPanel.setVisible(false);
		panLinkPeriod2Empl.add(createFormPanel);
		
		comboBoxEmployees1.addActionListener(
				new SettingsEmployeeComboAL(this,panLinkPeriod2Empl,comboBoxPeriod1,comboBoxDepartment1,comboBoxEmployees1,createFormPanel));
		
		panLinkPeriod2Empl.add(comboBoxEmployees1);
		comboBoxPeriod1.addActionListener(new SettingsPeriodComboAL(this, comboBoxPeriod1, comboBoxDepartment1, comboBoxEmployees1));
		panLinkPeriod2Empl.add(comboBoxPeriod1);
		comboBoxDepartment1.addActionListener(new SettingsDepartmentComboAL(this, comboBoxPeriod1, comboBoxDepartment1, comboBoxEmployees1));
		panLinkPeriod2Empl.add(comboBoxDepartment1);
		tabbedPaneSettings.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_EMPL2PER_HEADER), panLinkPeriod2Empl);
	}
	
	
	private JTextField addTextField(JPanel ancestor, String labelConstant, int xlbl, int xtxt  , int y , int count, int lblLght ) throws IOException {
		
		JLabel label = new JLabel(ResourceLoaderUtil.getLabels(labelConstant));
		label.setBounds(xlbl, y , lblLght, 25);
		ancestor.add(label,++count);
		JTextField text = new JTextField();
		text.setBounds(xtxt, y , 100, 25);
		text.setText("0.0");	
		ancestor.add(text, ++count);
		return text;
	}
	
	
	private JComboBox drawCreatePeriod(JTabbedPane tabbedPane, GroupCfgEmplsTable table, JTabbedPane tabbedPaneSettings, 
			JComboBox comboBoxPeriod1, JPanel panLinkPeriod2Empl) throws IOException {
		
		
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SETTINGS), null, tabbedPaneSettings, null);
		
		JPanel panSetCrtPeriod = new JPanel(null);

		tabbedPaneSettings.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_HEADER),panSetCrtPeriod);
		
		
		JLabel lblCode = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_CODE));
		lblCode.setBounds(40, 30, 150, 25);
		lblCode.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_CODE_TTIP));
		panSetCrtPeriod.add(lblCode);
		JTextField textFieldCode = new JTextField();
		textFieldCode.setColumns(10);
		panSetCrtPeriod.add(textFieldCode);
		textFieldCode.setBounds(250, 30, 150, 25);
		textFieldCode.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_CODE_TTIP));
		
		JLabel lblRevenue = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_REVENUE));
		lblRevenue.setBounds(450, 30, 150, 25);
		panSetCrtPeriod.add(lblRevenue);
		lblRevenue.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_REVENUE_TTIP));
		JTextField textFieldRevenue = new JTextField();
		textFieldRevenue.setColumns(10);
		textFieldRevenue.setBounds(600, 30, 150 , 25);
		panSetCrtPeriod.add(textFieldRevenue);
		textFieldRevenue.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_REVENUE_TTIP));
		
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
		datePanelEnd.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_END_DATE));
		datePickerEnd.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_END_DATE));
		JLabel lblEndDate = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.PERIOD_END_DATE));
		lblEndDate.setBounds(450, 80, 150, 25);
		panSetCrtPeriod.add(lblEndDate);
		datePickerEnd.setBounds(600, 80, 150 , 30);
		panSetCrtPeriod.add(datePickerEnd);
		
		//GroupTabPeriodComboAL tPCAL = new GroupTabPeriodComboAL(this, comboBox, table);
		
		JPanel createFormPanel = new JPanel();
		createFormPanel.setLayout(null);
		createFormPanel.setBounds(10, 85 , 600, 400);
		
		int y = 100;
		int delta = 30;
		
		Query qPeriodTrzStatic = em.createQuery(" from TrzStatic");
		List<TrzStatic> trzResult =	(List<TrzStatic>) qPeriodTrzStatic.getResultList();
		
		HashMap<TrzStatic, JTextField> map = new HashMap<TrzStatic, JTextField> ();
		for (TrzStatic singleStatic : trzResult) {
			
			JLabel lblPeriodSetting = new JLabel(singleStatic.getKeyDescription());
			lblPeriodSetting.setBounds(40, y , 250, 25);
			createFormPanel.add(lblPeriodSetting);
			JTextField textFieldValue = new JTextField();
			textFieldValue.setBounds(300, y, 150, 25);
			textFieldValue.setColumns(10);
			createFormPanel.add(textFieldValue);
			map.put(singleStatic, textFieldValue);
			y = y + delta;
		}
		
		y = y + 40;
		
		Query qPeriodDepartment = em.createQuery(" from Department");
		List<Department> deptResult =	(List<Department>) qPeriodDepartment.getResultList();
		
		HashMap<Department, JTextField> mapDept = new HashMap<Department, JTextField> ();
		for (Department singleDept : deptResult) {
			
			JLabel lblDept = new JLabel(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_REVENUE_DEPT)+ singleDept.getCode());
			lblDept.setBounds(40, y , 250, 25);
			createFormPanel.add(lblDept);
			
			JTextField textFieldValue = new JTextField();
			textFieldValue.setBounds(300, y, 150, 25);
			textFieldValue.setColumns(10);
			textFieldValue.setText("0.0");
			createFormPanel.add(textFieldValue);
			mapDept.put(singleDept, textFieldValue);
			y = y + delta;
		}
		
		JButton btnSavePeriod = new JButton( ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_SAVE));
		btnSavePeriod.setBounds(20, y + 40, 200 , 25);
		createFormPanel.add(btnSavePeriod);
		
		datePickerEnd.addActionListener(new SelectEndDatePeriodAL(this, map, mapDept, createFormPanel));
		datePickerEnd.setToolTipText(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_ENDDATE_TTIP));
		createFormPanel.setVisible(false);
		SavePeriodAL sPAL 
		    = new SavePeriodAL(this, 
		    		          datePickerStart, 
		    		          datePickerEnd, 
		    		          textFieldCode, 
		    		          textFieldRevenue, 
		    		          map,
		    		          mapDept, 
		    		          createFormPanel,
		    		          comboBoxPeriod1,
		    		          panLinkPeriod2Empl);
		panSetCrtPeriod.add(createFormPanel);
		btnSavePeriod.addActionListener(sPAL);
		
		return comboBoxPeriod1;
		
	}
}
