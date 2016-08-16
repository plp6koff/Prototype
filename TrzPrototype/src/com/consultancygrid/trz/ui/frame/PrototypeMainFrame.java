package com.consultancygrid.trz.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
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
import com.consultancygrid.trz.actionListener.group.PeriodDelRowAL;
import com.consultancygrid.trz.actionListener.loadFile.LoadCreatePeriodPanelAL;
import com.consultancygrid.trz.actionListener.loadFile.SaveFileAL;
import com.consultancygrid.trz.actionListener.period.SettingsPeriodComboAL;
import com.consultancygrid.trz.actionListener.personal.EmployeePersonTabComboAL;
import com.consultancygrid.trz.actionListener.personal.PersonRowEditAL;
import com.consultancygrid.trz.actionListener.statistic.LoadStatistic1AL;
import com.consultancygrid.trz.actionListener.statistic.LoadStatistic1MiniAL;
import com.consultancygrid.trz.actionListener.statistic.LoadStatistic2AL;
import com.consultancygrid.trz.actionListener.statistic.LoadStatistic3AL;
import com.consultancygrid.trz.actionListener.statistic.LoadStatistic4AL;
import com.consultancygrid.trz.actionListener.statistic.LoadStatistic5AL;
import com.consultancygrid.trz.actionListener.targetPeriod.TrgPrdLvlsCfgComboAL;
import com.consultancygrid.trz.actionListener.targetPeriod.TrgPrdSaveAL;
import com.consultancygrid.trz.actionListener.targetPeriod.TrgPrdSettingsComboAL;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TargetPeriod;
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
import com.consultancygrid.trz.ui.table.personal.statistic1.PrsStat1CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic1.mini.PrsStat1MiniCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic2.PrsStat2CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic3.PrsStat3CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic4.PrsStat4CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic5.PrsStat5CfgEmplsTable;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTable;
import com.consultancygrid.trz.util.HibernateUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class PrototypeMainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3800210267510018282L;

	private JTextField textField;
	private JComboBox<Employee> comboBoxEmployees;
	private static EntityManager  em ;
	private static EntityTransaction tx;
	/**
	 * Launch the application.
	 */
	

	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					HibernateUtil.initiate();
					em = HibernateUtil.getEntityManager();
					tx = em.getTransaction();
					tx.begin();
					PrototypeMainFrame frame = new PrototypeMainFrame();
					ImageIcon myAppImage = new ImageIcon("/resources/imgs/img.png");
					if(myAppImage != null) {
						myAppImage =  new ImageIcon(getClass().getResource("/resources/imgs/img.png"));
					}
					frame.setIconImage(myAppImage.getImage());
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter()
					{
					    public void windowClosing(WindowEvent e)
					    {
					        JFrame frame = (JFrame)e.getSource();
					       
					        int result = JOptionPane.showConfirmDialog(
					            frame,
					            "Are you sure you want to exit the application?",
					            "Exit Application",
					            JOptionPane.YES_NO_OPTION);
					 
					        if (result == JOptionPane.YES_OPTION) {
					            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								HibernateUtil.close();
					        }   
					    }
					});
				} catch (Exception e) {
					if (tx != null && tx.isActive()) {
						tx.rollback();
					}
				} finally {	
					if (tx != null && tx.isActive()) {
						tx.commit();
					}
					System.out.println("Main EM is closing");
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
		setTitle("T-R-Z");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1480, 1000);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 0, 0);
		
		
		getContentPane().add(tabbedPane);

		// First Tab
		Query qE = em.createQuery(" select e from Employee as e where e.isActive = 'Y' order by e.firstName ");
		List<Employee> employees = (List<Employee>) qE.getResultList();

		Query qPeriod = em.createQuery(" from Period order by code desc");
		List<Period> allPeriods = (List<Period>) qPeriod.getResultList();
		
		
		EmplComboBoxModel emplComboBoxModel = new EmplComboBoxModel(employees);
		JComboBox comboBoxEmployees = new JComboBox<>(emplComboBoxModel);
		PersonalCfgEmplsTable personalConfTable = new PersonalCfgEmplsTable();
		personalConfTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		comboBoxEmployees.setBounds(50, 40, 300, 25);
		comboBoxEmployees.setRenderer(new EmployeeCustomRender());
		
		String[] yearStrings = { "","2012", "2013", "2014", "2015", "2016","2017","2018","2019","2020" };
		JComboBox<String> yearsCB = new JComboBox<String>(yearStrings);
		yearsCB.setBounds(360, 40, 300, 25);
		//Draw
		drawFirstTab(tabbedPane, comboBoxEmployees, yearsCB, personalConfTable);
		
		//######### Second Tab ####################
		PeriodComboBoxModel trzComboBoxModel = new PeriodComboBoxModel(allPeriods);
		JComboBox periodsComboBox = new JComboBox<>(trzComboBoxModel);
		//Table1  Departments
		GroupCfgEmplsTable departTable = new GroupCfgEmplsTable();
		//Table1 No Departments
		GroupCfgEmplsTable departTable2 = new GroupCfgEmplsTable();
		//Draw
		drawSecondTab(tabbedPane, departTable, departTable2, periodsComboBox, comboBoxEmployees, personalConfTable);
		
		JTabbedPane tabbedPaneStatistics = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneStatistics.setBounds(0, 0, 0, 0);
		tabbedPane.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB), null, tabbedPaneStatistics, null);
		ImageIcon myAppImage = new ImageIcon("/resources/imgs/empl.png");
		if(myAppImage != null) {
			myAppImage =  new ImageIcon(getClass().getResource("/resources/imgs/empl.png"));
		}
		tabbedPane.setIconAt(0, myAppImage);
		
		ImageIcon myAppImageGrp = new ImageIcon("/resources/imgs/group.png");
		if(myAppImageGrp != null) {
			myAppImageGrp =  new ImageIcon(getClass().getResource("/resources/imgs/group.png"));
		}
		tabbedPane.setIconAt(1, myAppImageGrp);
		ImageIcon myAppStat = new ImageIcon("/resources/imgs/statistic.png");
		if(myAppStat != null) {
			myAppStat =  new ImageIcon(getClass().getResource("/resources/imgs/statistic.png"));
		}
		tabbedPane.setIconAt(2, myAppStat);
		
		drawStatistic31FirstTab(tabbedPaneStatistics, trzComboBoxModel);
		drawStatistic3MiniFirstTab(tabbedPaneStatistics, trzComboBoxModel);
		drawStatistic32FirstTab(tabbedPaneStatistics, trzComboBoxModel, emplComboBoxModel);
		drawStat33Tab(tabbedPaneStatistics, trzComboBoxModel);		
		drawStat34Tab(tabbedPaneStatistics, trzComboBoxModel, emplComboBoxModel);
		drawStat35Tab(tabbedPaneStatistics, trzComboBoxModel, emplComboBoxModel);
		
		
		//Rest
		JPanel panLinkPeriod2Empl = new JPanel(null);
		JTabbedPane tabbedPaneSettings = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab(
				ResourceLoaderUtil.getLabels(LabelsConstants.SETTINGS), null,
				tabbedPaneSettings, null);
		ImageIcon myAppImageSett = new ImageIcon("/resources/imgs/settings.png");
		if(myAppImageSett != null) {
			myAppImageSett =  new ImageIcon(getClass().getResource("/resources/imgs/settings.png"));
		}
		tabbedPane.setIconAt(3, myAppImageSett);
		/**
		 * Settings
		 */
		JComboBox comboBoxPeriod = new JComboBox<>(new PeriodComboBoxModel(allPeriods));
		
		//###########Load file####################
		drawLoadFileSettingsTab(tabbedPaneSettings, comboBoxPeriod, departTable, departTable2);
		
		//Deactivation user
		Query qEAll = em.createQuery(" from Employee as e order by e.firstName ");
		List<Employee> allEmpls = (List<Employee>) qEAll.getResultList();
		
		Object[] dataheader ={ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_HEAD1),
							  ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_HEAD2)};
		Object[][] data = new Object[allEmpls.size()][2];
		DefaultTableModel emplActTModel = new DefaultTableModel();
		int i = 0;
		for (Employee empl : allEmpls) {
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
		drawEmployeeDeactivation(tabbedPaneSettings,emplDeactvTable, comboBoxEmployees);
		//drawCreatePeriod(tabbedPane, departTable, tabbedPaneSettings,  comboBoxPeriod, panLinkPeriod2Empl);
		
		//Create Employee
		drawCreateEmployee(tabbedPaneSettings, allPeriods, comboBoxEmployees);
		//Employee 2 Period
		//drawEmployee2Period(tabbedPaneSettings, comboBoxPeriod,  panLinkPeriod2Empl);
		Query qTargets = em.createQuery(" from TargetPeriod order by code desc");
		List<TargetPeriod> allTargets = (List<TargetPeriod>) qTargets.getResultList();
		JComboBox comboTargetsCTL = new JComboBox<>(new TargetComboBoxModel(allTargets));
		JComboBox comboTargetsP2E = new JComboBox<>(new TargetComboBoxModel(allTargets));
		
		
		drawCreateTarget(tabbedPaneSettings, comboTargetsCTL, comboTargetsP2E);
		drawCreateTargetLevels(tabbedPaneSettings, comboTargetsCTL);
		drawEmployee2TargetPeriod(tabbedPaneSettings, comboTargetsP2E,  emplComboBoxModel);
		ImageIcon targetImage = new ImageIcon("/resources/imgs/target.png");
		if(targetImage != null) {
			targetImage =  new ImageIcon(getClass().getResource("/resources/imgs/target.png"));
		}
		tabbedPaneSettings.setIconAt(3, targetImage);
		tabbedPaneSettings.setIconAt(4, targetImage);
		tabbedPaneSettings.setIconAt(5, targetImage);
		PeriodComboBoxModel trzCombo2DelBoxModel = new PeriodComboBoxModel(allPeriods);
		JComboBox<Period> prds2DelComboBox = new JComboBox<Period>(trzCombo2DelBoxModel);
		//TODO Add period initial one
		drawDeletePeriod(tabbedPaneSettings,
				prds2DelComboBox, periodsComboBox, departTable, departTable2);
		
	}

	
	
	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawStatistic31FirstTab(JTabbedPane tabbedPane, 
			PeriodComboBoxModel trzComboBoxModel) throws IOException {

		
		
		
		JPanel frstStatPanel = new JPanel(null);
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		JComboBox periodsComboBox1 = new JComboBox<>(trzComboBoxModel);
		
		PrsStat1CfgEmplsTable pStatCfgEmpls = new PrsStat1CfgEmplsTable();
		pStatCfgEmpls.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		periodsComboBox1.setBounds(150, 40, 300, 25);
		periodsComboBox1.setRenderer(new PeriodCustomRender());
		
		JButton loadButt = new JButton(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB_LOAD_BUTT));
		loadButt.setEnabled(true);
		loadButt.setBounds(650, 40, 300, 25);
		loadButt.addActionListener(new LoadStatistic1AL(this, frstStatPanel, periodsComboBox1, pStatCfgEmpls, fc));
		frstStatPanel.add(loadButt);
		
		JScrollPane pesonPanel = new JScrollPane(pStatCfgEmpls);
		pesonPanel.setLayout(new ScrollPaneLayout());
		pesonPanel.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 110;
				return d;
			}
		});
		JTableHeader header = pStatCfgEmpls.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(header
				.getDefaultRenderer()));

		pesonPanel.setBounds(20, 150, 1400, 400);
		pesonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pesonPanel.setAutoscrolls(true);
		pesonPanel.setBorder(BorderFactory.createTitledBorder(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + " 3.1."));
		
		frstStatPanel.setLayout(null);
		frstStatPanel.add(periodsComboBox1);
		frstStatPanel.add(pesonPanel);
		tabbedPane.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + " 3.1.", frstStatPanel);
	}
	
	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawStatistic3MiniFirstTab(JTabbedPane tabbedPane, 
			PeriodComboBoxModel trzComboBoxModel) throws IOException {
		
		
		JPanel frstStatPanel = new JPanel(null);
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		JComboBox periodsComboBox1 = new JComboBox<>(trzComboBoxModel);
		
		PrsStat1MiniCfgEmplsTable pStatMiniCfgEmpls = new PrsStat1MiniCfgEmplsTable();
		pStatMiniCfgEmpls.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		periodsComboBox1.setBounds(150, 40, 300, 25);
		periodsComboBox1.setRenderer(new PeriodCustomRender());
		

		JButton loadMiniButt = new JButton(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB_LOAD_BUTT) + " Short");
		loadMiniButt.setEnabled(true);
		loadMiniButt.setBounds(650, 40, 300, 25);
		loadMiniButt.addActionListener(new LoadStatistic1MiniAL(this, frstStatPanel, periodsComboBox1, pStatMiniCfgEmpls, fc));
		frstStatPanel.add(loadMiniButt);
			
		JScrollPane pesonPanelMini = new JScrollPane(pStatMiniCfgEmpls);
		pesonPanelMini.setLayout(new ScrollPaneLayout());
		pesonPanelMini.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 110;
				return d;
			}
		});
		JTableHeader headerMini = pStatMiniCfgEmpls.getTableHeader();
		headerMini.setDefaultRenderer(new HeaderRenderer(headerMini
				.getDefaultRenderer()));

		pesonPanelMini.setBounds(20, 100, 1000, 600);
		pesonPanelMini.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pesonPanelMini.setAutoscrolls(true);
		pesonPanelMini.setBorder(BorderFactory.createTitledBorder(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + " 3.1. Mini"));
		
		frstStatPanel.setLayout(null);
		frstStatPanel.add(periodsComboBox1);
		frstStatPanel.add(pesonPanelMini);
		tabbedPane.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + " 3.1. Mini", frstStatPanel);
	}
	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawStatistic32FirstTab(JTabbedPane tabbedPane, 
			PeriodComboBoxModel trzComboBoxModel,
			EmplComboBoxModel emplComboBoxModel) throws IOException {

		
		JPanel frstStatPane2 = new JPanel(null);
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		JComboBox comboBoxEmployees2 = new JComboBox<>(emplComboBoxModel);
		comboBoxEmployees2.setBounds(150, 40, 300, 25);
		comboBoxEmployees2.setRenderer(new EmployeeCustomRender());
		
		PrsStat2CfgEmplsTable pStatCfgEmpls = new PrsStat2CfgEmplsTable();
		pStatCfgEmpls.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
				
		String[] yearStrings = { "","2012", "2013", "2014", "2015", "2016","2017","2018","2019","2020" };
		JComboBox<String> yearsCB = new JComboBox<String>(yearStrings);
		yearsCB.setBounds(150, 80, 300, 25);
		
		
		JButton loadButt = new JButton(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB_LOAD_BUTT));
		loadButt.setEnabled(true);
		loadButt.setBounds(650, 40, 150, 25);
		loadButt.addActionListener(new LoadStatistic2AL(this, frstStatPane2,  yearsCB,comboBoxEmployees2, pStatCfgEmpls, fc));
		frstStatPane2.add(loadButt);
		
		
		JScrollPane pesonPanel = new JScrollPane(pStatCfgEmpls);
		pesonPanel.setLayout(new ScrollPaneLayout());
		pesonPanel.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 110;
				return d;
			}
		});
		JTableHeader header = pStatCfgEmpls.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(header
				.getDefaultRenderer()));

		pesonPanel.setBounds(20, 150, 1200, 400);
		pesonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pesonPanel.setAutoscrolls(true);
		pesonPanel.setBorder(BorderFactory.createTitledBorder(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + " 3.2."));
		
		
		frstStatPane2.setLayout(null);
		frstStatPane2.add(comboBoxEmployees2);
		frstStatPane2.add(yearsCB);
		frstStatPane2.add(pesonPanel);
		tabbedPane.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + "- 3.2.", frstStatPane2);
	}
	
	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawStat33Tab(JTabbedPane tabbedPane, 
			PeriodComboBoxModel trzComboBoxModel) throws IOException {
		
		
		
		JPanel stat3Panel = new JPanel(null);
		stat3Panel.setBounds(5, 5, 1300, 600);
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		
		JComboBox periodsComboBox33 = new JComboBox<>(trzComboBoxModel);
		periodsComboBox33.setBounds(40, 100, 300, 25);
		periodsComboBox33.setRenderer(new PeriodCustomRender());
		
		PrsStat3CfgEmplsTable prsStat3Table = new PrsStat3CfgEmplsTable();
		prsStat3Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		
		JButton loadButt = new JButton(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB_LOAD_BUTT));
		loadButt.setEnabled(true);
		loadButt.setBounds(650, 40, 150, 25);
		
		loadButt.addActionListener(new LoadStatistic3AL(this, stat3Panel, periodsComboBox33, prsStat3Table, fc));
		stat3Panel.add(loadButt);
		
		JScrollPane pesonPanel = new JScrollPane(prsStat3Table);
		pesonPanel.setLayout(new ScrollPaneLayout());
		pesonPanel.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 110;
				return d;
			}
		});
		JTableHeader header = prsStat3Table.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(header
				.getDefaultRenderer()));

		pesonPanel.setBounds(40, 150, 1200, 600);
		pesonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pesonPanel.setAutoscrolls(true);
		pesonPanel.setBorder(BorderFactory.createTitledBorder(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + " 3.3."));
		stat3Panel.setLayout(null);
		stat3Panel.add(periodsComboBox33);
		stat3Panel.add(pesonPanel);
		tabbedPane.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + "- 3.3.", stat3Panel);
	}
	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawStat34Tab(JTabbedPane tabbedPane, 
			PeriodComboBoxModel trzComboBoxModel,
			EmplComboBoxModel emplComboBoxModel) throws IOException {
		
		
		
		JPanel stat4Panel = new JPanel(null);
		stat4Panel.setBounds(5, 5, 1000, 150);
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JComboBox comboBoxEmployees34 = new JComboBox<>(emplComboBoxModel);
		comboBoxEmployees34.setBounds(150, 40, 300, 25);
		comboBoxEmployees34.setRenderer(new EmployeeCustomRender());
		
		JComboBox periodsComboBox34 = new JComboBox<>(trzComboBoxModel);
		periodsComboBox34.setBounds(150, 100, 300, 25);
		periodsComboBox34.setRenderer(new PeriodCustomRender());
		
		PrsStat4CfgEmplsTable pStatCfgEmpls = new PrsStat4CfgEmplsTable();
		pStatCfgEmpls.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		
		JButton loadButt = new JButton(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB_LOAD_BUTT));
		loadButt.setEnabled(true);
		loadButt.setBounds(650, 40, 150, 25);
		
		PrsStat4CfgEmplsTable prsStat4Table = new PrsStat4CfgEmplsTable();
		loadButt.addActionListener(new LoadStatistic4AL(this, stat4Panel, periodsComboBox34, comboBoxEmployees34, prsStat4Table, fc));
		stat4Panel.add(loadButt);
		
		JScrollPane pesonPanel = new JScrollPane(prsStat4Table);
		pesonPanel.setLayout(new ScrollPaneLayout());
		pesonPanel.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 110;
				return d;
			}
		});
		JTableHeader header = prsStat4Table.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(header
				.getDefaultRenderer()));

		pesonPanel.setBounds(20, 150, 1200, 600);
		pesonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pesonPanel.setAutoscrolls(true);
		pesonPanel.setBorder(BorderFactory.createTitledBorder(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + " 3.4."));
		stat4Panel.setLayout(null);
		stat4Panel.add(comboBoxEmployees34);
		stat4Panel.add(periodsComboBox34);
		stat4Panel.add(pesonPanel);
		tabbedPane.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + " - 3.4.", stat4Panel);
	}

	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawStat35Tab(JTabbedPane tabbedPane, 
			PeriodComboBoxModel trzComboBoxModel,
			EmplComboBoxModel emplComboBoxModel) throws IOException {
		
		
		
		JPanel stat5Panel = new JPanel(null);
		stat5Panel.setBounds(5, 5, 1000, 150);
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JComboBox comboBoxEmployees1 = new JComboBox<>(emplComboBoxModel);
		comboBoxEmployees1.setBounds(150, 40, 300, 25);
		comboBoxEmployees1.setRenderer(new EmployeeCustomRender());
		
		JComboBox periodsComboBox1 = new JComboBox<>(trzComboBoxModel);
		periodsComboBox1.setBounds(150, 100, 300, 25);
		periodsComboBox1.setRenderer(new PeriodCustomRender());
		
		PrsStat1CfgEmplsTable pStatCfgEmpls = new PrsStat1CfgEmplsTable();
		pStatCfgEmpls.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		
		JButton loadButt = new JButton(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB_LOAD_BUTT));
		loadButt.setEnabled(true);
		loadButt.setBounds(650, 40, 200, 25);
		
		PrsStat5CfgEmplsTable prsStat5Table = new PrsStat5CfgEmplsTable();
		loadButt.addActionListener(new LoadStatistic5AL(this, stat5Panel, periodsComboBox1, comboBoxEmployees1, prsStat5Table, fc));
		stat5Panel.add(loadButt);
		
		JScrollPane pesonPanel = new JScrollPane(prsStat5Table);
		pesonPanel.setLayout(new ScrollPaneLayout());
		pesonPanel.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 110;
				return d;
			}
		});
		JTableHeader header = prsStat5Table.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(header
				.getDefaultRenderer()));

		pesonPanel.setBounds(20, 150, 1200, 600);
		pesonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pesonPanel.setAutoscrolls(true);
		pesonPanel.setBorder(BorderFactory.createTitledBorder(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + " 3.5."));
		stat5Panel.setLayout(null);
		stat5Panel.add(comboBoxEmployees1);
		stat5Panel.add(periodsComboBox1);
		stat5Panel.add(pesonPanel);
		tabbedPane.addTab(ResourceLoaderUtil
				.getLabels(LabelsConstants.STAT_TAB) + "- 3.5.", stat5Panel);
	}
	
	
	/**
	 * 
	 * @param comboBox
	 * @param tabbedPane
	 * @throws IOException
	 */
	private void drawFirstTab(JTabbedPane tabbedPane, 
				JComboBox comboBoxEmployees, 
				JComboBox<String> comboBoxYear, 
				PersonalCfgEmplsTable personalConfTable) throws IOException {

		JPanel firstInnerPanel = new JPanel(null);
		JFileChooser fc = new JFileChooser();
		
		JButton loadPersonalButt = new JButton("Load");
		loadPersonalButt.setEnabled(true);
		loadPersonalButt.setBounds(700, 40, 100, 25);
		firstInnerPanel.add(loadPersonalButt);
		
		JButton saveButt = new JButton("Export as PDF ...");
		saveButt.setEnabled(true);
		saveButt.setBounds(900, 40, 150, 25);
		
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveButt.addActionListener(new SaveFileAL(this, fc, null,  comboBoxEmployees, personalConfTable));
		firstInnerPanel.add(saveButt);
		JButton editRow = new JButton(
				ResourceLoaderUtil
						.getLabels(LabelsConstants.PERSONAL_CFG_EDIT_BTN));
		editRow.setBounds(900, 70, 150, 25);
		editRow.addActionListener(
				new PersonRowEditAL(this,
									personalConfTable,
									comboBoxEmployees,
									comboBoxYear));
		firstInnerPanel.add(editRow);

		EmployeePersonTabComboAL emplsComboAL 
				= new EmployeePersonTabComboAL(
							this, 
							comboBoxEmployees,
							comboBoxYear,
							personalConfTable);
		loadPersonalButt.addActionListener(emplsComboAL);
		//comboBoxEmployees.addActionListener(emplsComboAL);

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

		pesonPanel.setBounds(20, 100, 1400, 700);
		pesonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pesonPanel.setAutoscrolls(true);
		pesonPanel.setBorder(BorderFactory.createTitledBorder("Employees"));
		firstInnerPanel.setLayout(null);
		firstInnerPanel.add(comboBoxEmployees);
		firstInnerPanel.add(comboBoxYear);
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
								JComboBox comboBox, 
								JComboBox comboBoxEmployee,
								PersonalCfgEmplsTable personalConfTable) throws IOException {
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTableHeader header = table.getTableHeader();
		header.setDefaultRenderer(new HeaderRenderer(header.getDefaultRenderer()));
		
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTableHeader header2 = table2.getTableHeader();
		header2.setDefaultRenderer(new HeaderRenderer(header2.getDefaultRenderer()));
		
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
		editRow.addActionListener(new GroupEditRowAL(this, table, table2, comboBox, comboBoxEmployee, tabbedPaneDept, personalConfTable));
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

	private void drawEmployeeDeactivation(JTabbedPane tabbedPaneSettings,  EmployeeActiveTable table, JComboBox<Employee> comboBoxEmployees) throws IOException {

		JPanel panel = new JPanel();
		//table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setBounds(10, 10, 400, 600);
		JScrollPane editEmployeePanel = new JScrollPane(table);
		panel.add(editEmployeePanel);
		editEmployeePanel.setBounds(40, 40, 500, 600);
		JButton saveButton = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.SET_CRT_EMPL_SAVE));
		saveButton.setBounds(100, 140, 200, 30);
		saveButton.addActionListener(new EmployeeDeactivationTabComboAL(this, table, comboBoxEmployees));
		panel.add(saveButton);
		tabbedPaneSettings.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_TAB), 
								panel);
		
		ImageIcon activeImage = new ImageIcon("/resources/imgs/activate.png");
		if(activeImage != null) {
			activeImage =  new ImageIcon(getClass().getResource("/resources/imgs/activate.png"));
		}
		tabbedPaneSettings.setIconAt(1, activeImage);
		
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
		tabbedPaneSettings.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SET_CRT_EMPL_FILE_TAB),	createFormPanel);
		
		
		ImageIcon emplImage = new ImageIcon("/resources/imgs/empl.png");
		if(emplImage != null) {
			emplImage =  new ImageIcon(getClass().getResource("/resources/imgs/empl.png"));
		}
		tabbedPaneSettings.setIconAt(2, emplImage);
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
	
	
	private void drawDeletePeriod(
			JTabbedPane tabbedPaneSettings,
			JComboBox<Period> comboPeriods, 
			JComboBox<Period> comboPeriods2,
			GroupCfgEmplsTable departTable,
			GroupCfgEmplsTable departTable2)
			throws IOException {


		JPanel panSetDelete = new JPanel();
		panSetDelete.setLayout(null);
		panSetDelete.setBounds(10, 60, 1000, 500);
		
		
		comboPeriods.setBounds(150, 40, 300, 25);
		comboPeriods.setRenderer(new PeriodCustomRender());
		panSetDelete.add(comboPeriods);
		//Save Button
		JButton deleteRow = new JButton("Delete");
		deleteRow.setBounds(490, 40, 150, 25);
		deleteRow.addActionListener(
				new PeriodDelRowAL(this,  
						comboPeriods, 
						comboPeriods2,
						departTable,
						departTable2));
		panSetDelete.add(deleteRow);
		tabbedPaneSettings.addTab("Delete Period", panSetDelete);
		
		ImageIcon delImage = new ImageIcon("/resources/imgs/delete.png");
		if(delImage != null) {
			delImage =  new ImageIcon(getClass().getResource("/resources/imgs/delete.png"));
		}
		tabbedPaneSettings.setIconAt(6, delImage);
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
				JComboBox<Period> comboBoxPeriod,
				GroupCfgEmplsTable departTable,
				GroupCfgEmplsTable departTable2) 
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
		LoadCreatePeriodPanelAL loadAl = new LoadCreatePeriodPanelAL(this, textFieldCode, createPeriodPanel, comboBoxPeriod, departTable, departTable2);
		loadPanel.addActionListener(loadAl);
		loadPanel.setBounds(430, 50, 200, 20);
		createPeriodPanel.add(loadPanel,2);
		createPeriodPanel.setBorder(BorderFactory
				.createTitledBorder(ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_HEADER)));
		tabbedPaneSettings.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SET_LOAD_FILE_TAB), createPeriodPanel);
		
		ImageIcon fileImage = new ImageIcon("/resources/imgs/file.png");
		if(fileImage != null) {
			fileImage =  new ImageIcon(getClass().getResource("/resources/imgs/file.png"));
		}
		tabbedPaneSettings.setIconAt(0, fileImage);
		
		

	}


	
	
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
