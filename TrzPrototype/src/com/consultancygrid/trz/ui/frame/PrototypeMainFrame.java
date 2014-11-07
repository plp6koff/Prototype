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
import static com.consultancygrid.trz.base.Constants.col8MW;
import static com.consultancygrid.trz.base.Constants.col9MW;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.consultancygrid.trz.actionListener.EmplsComboAL;
import com.consultancygrid.trz.actionListener.TimePeriodComboAL;
import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
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

		JPanel thirdInnerPanel = new JPanel(null);
		tabbedPane.addTab(ResourceLoaderUtil.getLabels(LabelsConstants.SALARY_BY_MOF_TAB_LABEL), thirdInnerPanel);
		
		TimePeriodComboAL tPCAL = new TimePeriodComboAL(this, comboBox, table);
		comboBox.addActionListener(tPCAL);

		em.getTransaction().commit();
		em.close();
		factory.close();
	}

}
