package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;

public class ActivateEmployeeAL extends BaseActionListener {

	private Employee employee;
	private JButton button;
	private JComboBox<Employee> employeBox;

	public ActivateEmployeeAL(PrototypeMainFrame mainFrame,
			Employee employee, JButton button, JComboBox<Employee> employeBox) {

		super(mainFrame);
		this.button = button;
		this.employee = employee;
		this.employeBox = employeBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		EntityManagerFactory factory = null;
		EntityManager em = null;
		Set<String> matchCodes = null;
		try {

			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

			em = factory.createEntityManager();
			em.getTransaction().begin();

			employee.setIsActive("Y");
			em.merge(employee);
			
			
			Query qE = em.createQuery(" from Employee");
			((EmplComboBoxModel)employeBox.getModel()).addAll((List<Employee>) qE.getResultList());
			button.setVisible(false);
			button.getParent().revalidate();
			button.getParent().repaint();
			
		} catch (Exception e1) {
			Logger.error(e1);
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
