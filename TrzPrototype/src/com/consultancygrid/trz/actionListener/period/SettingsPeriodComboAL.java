package com.consultancygrid.trz.actionListener.period;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.JComboBox;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.combo.DepartmentComboBoxModel;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.HibernateUtil;
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

		
		EntityManager em = null;
		EntityTransaction trx = null;
		try {
			
			em = HibernateUtil.getEntityManager();
			trx = em.getTransaction();
			trx.begin();;
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
