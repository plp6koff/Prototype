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


public class PrototypeMainFrame extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3800210267510018282L;

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	private static EntityManager em = factory.createEntityManager();
	private JTextField textField;
	private SpringLayout springLayout;
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

		UtilDateModel model1 = new UtilDateModel();
		model1.setDate(20,04,2014);
		
		Properties p = new Properties();
		p.put("text.today", "Dnes");
		p.put("text.month", "Mesec");
		p.put("text.year", "Ygodina");
		springLayout = new SpringLayout();
		JDatePanelImpl datePanel = new JDatePanelImpl(model1, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
//		springLayout.putConstraint(SpringLayout.NORTH, datePicker.getJFormattedTextField(), 0, SpringLayout.NORTH, datePicker);
//		springLayout.putConstraint(SpringLayout.EAST, datePicker.getJFormattedTextField(), 120, SpringLayout.WEST, datePicker);
//		springLayout = (SpringLayout) datePicker.getLayout();
//		springLayout.putConstraint(SpringLayout.WEST, datePicker.getJFormattedTextField(), 0, SpringLayout.WEST, datePicker);
		
		datePicker.setBounds(150, 44, 146, 23);
		datePicker.setToolTipText("Select start Date");
		
		
		
		JPanel thirdInnerPanel = new JPanel(null);
		thirdInnerPanel.add(datePicker);
		JTextField field = new JTextField();
		thirdInnerPanel.add(field);
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SALARY_BY_MOF_TAB_LABEL), thirdInnerPanel);
		
		textField = new JTextField();
		textField.setBounds(150, 13, 146, 20);
		thirdInnerPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblRevenuel = new JLabel("Code");
		lblRevenuel.setBounds(31, 19, 84, 14);
		thirdInnerPanel.add(lblRevenuel);
		
		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(31, 53, 84, 14);
		thirdInnerPanel.add(lblStartDate);
		
		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(378, 53, 46, 14);
		thirdInnerPanel.add(lblEndDate);
		
		
		UtilDateModel model2 = new UtilDateModel();
		model2.setDate(20,04,2014);
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
//		springLayout.putConstraint(SpringLayout.NORTH, datePicker2.getJFormattedTextField(), 0, SpringLayout.NORTH, datePicker2);
//		springLayout.putConstraint(SpringLayout.EAST, datePicker2.getJFormattedTextField(), 120, SpringLayout.WEST, datePicker2);
//		springLayout = (SpringLayout) datePicker2.getLayout();
//		springLayout.putConstraint(SpringLayout.WEST, datePicker2.getJFormattedTextField(), 0, SpringLayout.WEST, datePicker2);
		
		datePicker2.setBounds(440, 44, 146, 23);
		datePicker2.setToolTipText("Select start Date");
		thirdInnerPanel.add(datePicker2);
		
		JLabel lblRevenue = new JLabel("Revenue");
		lblRevenue.setBounds(378, 13, 46, 14);
		thirdInnerPanel.add(lblRevenue);
		
		textField_1 = new JTextField();
		textField_1.setBounds(440, 13, 146, 20);
		thirdInnerPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnSavePeriod = new JButton("Save Period");
		btnSavePeriod.setBounds(717, 44, 89, 23);
		
		
		
		thirdInnerPanel.add(btnSavePeriod);
		
		Query qPeriodTrzStatic = em.createQuery(" from TrzStatic");
		List<TrzStatic> trzResult =	(List<TrzStatic>) qPeriodTrzStatic.getResultList();
		
		
		TimePeriodComboAL tPCAL = new TimePeriodComboAL(this, comboBox, table);
		comboBox.addActionListener(tPCAL);
		
		int y = 200;
		int delta = 50;
		
		HashMap<TrzStatic, JTextField> map = new HashMap<TrzStatic, JTextField> ();
		for (TrzStatic singleStatic : trzResult) {
			y = y + delta;
			int tempY = y; 
			JLabel lblPeriodSetting = new JLabel("Period Setting " + singleStatic.getKeyDescription());
			lblPeriodSetting.setBounds(39, tempY , 300, 14);
			thirdInnerPanel.add(lblPeriodSetting);
			
			
			JTextField textFieldValue = new JTextField();
			textFieldValue.setBounds(400, tempY, 146, 23);
			textFieldValue.setColumns(10);
			thirdInnerPanel.add(textFieldValue);
			
			map.put(singleStatic, textFieldValue);
		}
		
		
		
		
		SavePeriodAL sPAL = new SavePeriodAL(this, datePicker, datePicker2, textField, textField_1,map);
		btnSavePeriod.addActionListener(sPAL);      

		em.getTransaction().commit();
		em.close();
		factory.close();
	}
}
