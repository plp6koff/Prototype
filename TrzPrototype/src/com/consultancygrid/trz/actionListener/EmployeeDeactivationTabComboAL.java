package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.EmployeeActiveTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class EmployeeDeactivationTabComboAL extends BaseActionListener {

	JTable table;

	public EmployeeDeactivationTabComboAL(PrototypeMainFrame mainFrame,
			JTable table) {

		super(mainFrame);
		this.table = table;
	}

	public void actionPerformed(ActionEvent e) {
		EntityManagerFactory factory = null;
		EntityManager em = null;
		Set<String> matchCodes = null;
		try {

			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

			em = factory.createEntityManager();
			em.getTransaction().begin();

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
			if (em != null && em.isOpen()) {
				em.getTransaction().rollback();
				em.close();
			}

		} finally {
			if (em != null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
			}
		}

	}

}
