package com.consultancygrid.trz.ui.frame;
//package com.consultancygrid.trz.ui;
//
//
//import java.awt.EventQueue;
//import java.util.List;
//import java.util.Vector;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.Query;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.JTabbedPane;
//import javax.swing.JTable;
//
//import com.consultancygrid.trz.model.Department;
//import com.consultancygrid.trz.model.EmplDeptPeriod;
//import com.consultancygrid.trz.model.Employee;
//import com.consultancygrid.trz.model.EmployeeSettings;
//
//import java.awt.Font;
//
//public class MainFrame extends JFrame {
//
//	
//	private static final String PERSISTENCE_UNIT_NAME = "trzUnit";
//	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//	private static EntityManager em = factory.createEntityManager();
//
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//	    
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainFrame frame = new MainFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public MainFrame() {
//		
//		 setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
//		 setTitle("Trz");
//		
//		 em.getTransaction().begin();
//		 
//		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		 setBounds(100, 100, 689, 402);
//		 
//		 
//		 
//		 JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
//		 tabbedPane.setBounds(0, 0, 0, 0);
//		 getContentPane().add(tabbedPane);
//		 
//		 JPanel firstInnerPanel = new JPanel(null);
//		 tabbedPane.addTab("Personal Configurations", firstInnerPanel);
//		 
//		 JPanel secondInnerPanel = new JPanel(null);
//		 secondInnerPanel.setLayout(null);
//		 
//		 Query q = em.createQuery(" from Department");
//		 int y = 100;
//		 int rowheight = 40;
//		 Vector<String> tableHeaders = new Vector<String>();
//		 tableHeaders.add("Name"); 
//		 tableHeaders.add("Is Active");
//		 tableHeaders.add("Settings");
//		 
//		 for (Department d : (List<Department>)q.getResultList()){
//			 
//			 int  tempHeight = (rowheight * d.getEmplDeptPeriods().size());
//			 JTable tempTable = new JTable();
//			 tempTable.setName(d.getLabel());
//			 tempTable.setAutoResizeMode(HEIGHT);
//			 tempTable.setBorder(new EmptyBorder(5, 5, 5, 5));
//			 displayResult(tempTable, d, tableHeaders);
//			 
//			 JScrollPane jscp = new JScrollPane(tempTable);
//			 jscp.setBounds(22, y, 402, tempHeight);
//			 
//			 secondInnerPanel.add(jscp);
//			 y= y + tempHeight + 50;
//		 }
//		 
//		 tabbedPane.addTab("Group Configurations", secondInnerPanel );
//		 
//		 
//		 JPanel thirdInnerPanel = new JPanel(null);
//		 tabbedPane.addTab("Salaries by month Configurations", thirdInnerPanel);
//		 
//		 em.getTransaction().commit();
//	     em.close();
//	     factory.close();
//	}
//	
//	
//	private void displayResult(JTable table, Department department, Vector<String> tableHeaders) {
//		Vector tableData = new Vector();
//	    Vector tableData1 = new Vector();
//	    
//         for(EmplDeptPeriod emplDeptPeriod : department.getEmplDeptPeriods()) {	
//	    	Employee employee = emplDeptPeriod.getEmployee(); 
//	    			//(Employee)q1.getSingleResult();
//	    	Vector<Object> oneRow = new Vector<Object>();
//	    	oneRow.add(employee.getFirstName() + "" + employee.getLastName());
//	    	oneRow.add(employee.getIsActive());
//	    	
//	    	Vector<String> tableHeaders1 = new Vector<String>();
//	    	tableHeaders1.add("Bruto1"); 
//	    	tableHeaders1.add("Bruto Standart");
//
//	    	JScrollPane tempScrollPanel = new  JScrollPane();
//	    	JTable tempTable1 = new JTable();
//	    	for (EmployeeSettings emplSet : employee.getEmployeeSettingses()) {
//	    	
//	    		Vector<Object> oneRow1 = new Vector<Object>();
//	    		oneRow1.add(emplSet.getBrutoPoShtat().toPlainString());
//	    		oneRow1.add(emplSet.getBrutoStandart().toPlainString());
//	    		tempTable1.setModel(new DefaultTableModel(tableData1, tableHeaders1));
//	    	}
//	    	
//	    	tempScrollPanel.add(tempTable1);
//	    	oneRow.add(tempScrollPanel);
//	    	
//	    	//neRow.add(actor.getLastUpdate());
//	    	tableData.add(oneRow);
//	    }
//	    table.setModel(new DefaultTableModel(tableData, tableHeaders));
//	}
//
//}
