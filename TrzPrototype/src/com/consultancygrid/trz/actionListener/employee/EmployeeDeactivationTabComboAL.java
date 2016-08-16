package com.consultancygrid.trz.actionListener.employee;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.HibernateUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class EmployeeDeactivationTabComboAL extends BaseActionListener {

	private JTable table;
	private JComboBox<Employee> employeBox;

	public EmployeeDeactivationTabComboAL(PrototypeMainFrame mainFrame,
			JTable table, JComboBox<Employee> employeBox) {

		super(mainFrame);
		this.table = table;
		this.employeBox = employeBox;
	}

	public void actionPerformed(ActionEvent e) {
		
		EntityManager em = null;
		EntityTransaction trx = null;
		Set<String> matchCodes = null;
		try {

			em = HibernateUtil.getEntityManager();
			trx = em.getTransaction();
			trx.begin();

			DefaultTableModel employeeModel = (DefaultTableModel) table
					.getModel();
			Vector tableData = employeeModel.getDataVector();

			for (Object singleObject : tableData) {

				Vector vec = (Vector) singleObject;

				String name = (String) vec.get(0);
				String firstName = name.substring(0, name.indexOf(" "));
				String lastName = name.substring(name.indexOf(" ") + 1);
				Boolean isActive = (Boolean) vec.get(1);

				Query qE = em
						.createQuery(" from Employee where firstName = :f and lastName = :l");
				qE.setParameter("f", firstName);
				qE.setParameter("l", lastName);
				List<Employee> employees = (List<Employee>) qE.getResultList();
				Employee emp = employees.get(0);
				emp.setIsActive(isActive ? "Y" : "N");
				em.merge(emp);
				
			}
			JOptionPane.showMessageDialog(mainFrame,
					ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_SAVE_OK),
					ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_INFO),
					JOptionPane.INFORMATION_MESSAGE);
			EmplComboBoxModel ecbm = (EmplComboBoxModel) employeBox.getModel();
			Query qE = em.createQuery(" select e from Employee as e where e.isActive = 'Y' order by e.firstName ");
			ecbm.clear();
			ecbm.addAll((List<Employee>) qE.getResultList());
			employeBox.setSelectedItem("Default");
			employeBox.revalidate();
			employeBox.repaint();

		} catch (Exception e1) {
			
			Logger.error(e1);
			try {
				JOptionPane.showMessageDialog(mainFrame,
						ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_SAVE_KO),
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR),
						JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (trx!= null && trx.isActive()) {
				trx.rollback();
			}
		} finally {
			if (trx!= null && trx.isActive()) {
				trx.commit();
			}
		}

	}

}
