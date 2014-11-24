package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JTable;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.ui.combo.DepartmentComboBoxModel;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.TrzComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

import static com.consultancygrid.trz.base.Constants.*;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class SettingsDepartmentComboAL extends BaseActionListener {

	
	JComboBox comboBoxPeriod;
	JComboBox comboBoxDepartment;
	JComboBox comboBoxEmployee;
	
	public SettingsDepartmentComboAL(PrototypeMainFrame mainFrame,JComboBox comboBoxPeriod,JComboBox comboBoxDepartment,JComboBox comboBoxEmpl) {

		super(mainFrame);
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxDepartment = comboBoxDepartment;
		this.comboBoxEmployee = comboBoxEmpl;
	}

	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();

			em.getTransaction().begin();

			Period period = ((Period) comboBoxPeriod.getModel().getSelectedItem());
			Department department = ((Department) comboBoxDepartment.getModel().getSelectedItem());

			Query q = em.createQuery(" select emplDeptP.employee from EmplDeptPeriod as emplDeptP  where  emplDeptP.period.id = :periodId and emplDeptP.department.id = :deptId");
			q.setParameter("periodId", period.getId());
			q.setParameter("deptId", department.getId());
			List<Employee> allRelatedEmpl = (List<Employee>) q.getResultList();
		
		    Query q1 = em.createQuery("from Employee");
		    List<Employee> allEmpls = (List<Employee>) q1.getResultList();
		    
			List<Employee> allEmplsToUse = new ArrayList<Employee>(allEmpls);
			//TODO optimize
			for (Employee  empl : allEmpls) {
				for (Employee relatedEmpl : allRelatedEmpl) {
					if (relatedEmpl.getId().equals(empl.getId())){
						allEmplsToUse.remove(relatedEmpl);
					}
				}
			}
			
			((EmplComboBoxModel)this.comboBoxEmployee.getModel()).addAll(allEmplsToUse);
			this.comboBoxEmployee.setEnabled(true);
			this.comboBoxEmployee.validate();
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
