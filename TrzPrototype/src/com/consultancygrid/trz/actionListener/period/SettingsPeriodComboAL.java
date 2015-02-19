package com.consultancygrid.trz.actionListener.period;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.combo.DepartmentComboBoxModel;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class SettingsPeriodComboAL extends BaseActionListener {

	
	private JComboBox comboBoxPeriod;
	private JComboBox comboBoxEmployee;
	private JComboBox comboBoxDepartment;
	
	public SettingsPeriodComboAL(PrototypeMainFrame mainFrame,JComboBox comboBoxPeriod,JComboBox comboBoxDepartment, JComboBox comboBoxEmployee) {

		super(mainFrame);
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxEmployee = comboBoxEmployee;
		this.comboBoxDepartment = comboBoxDepartment;
	}

	public void actionPerformed(ActionEvent e) {

		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();

			em.getTransaction().begin();

			Period period = ((Period) comboBoxPeriod.getModel().getSelectedItem());

			Query q = em.createQuery(" select distinct rDP.department from RevenueDeptPeriod as rDP  where  rDP.period.id = :periodId");
			q.setParameter("periodId", period.getId());
			List<Department> allDepartments = (List<Department>) q.getResultList();
		
			((EmplComboBoxModel)this.comboBoxEmployee.getModel()).clear();
			this.comboBoxDepartment.setEnabled(false);
			this.comboBoxDepartment.repaint();
			
			((DepartmentComboBoxModel)this.comboBoxDepartment.getModel()).addAll(allDepartments);
			this.comboBoxDepartment.setEnabled(true);
			this.comboBoxDepartment.validate();
			mainFrame.validate();
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.close();
			}
		}
	}
}
