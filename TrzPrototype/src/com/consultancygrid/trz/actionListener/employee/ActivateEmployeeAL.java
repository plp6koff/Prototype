package com.consultancygrid.trz.actionListener.employee;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;

import com.consultancygrid.trz.actionListener.BaseActionListener;
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
	protected void eventCore() {

		
		employee.setIsActive("Y");
		em.merge(employee);
		
		Query qE = em.createQuery(" from Employee");
		((EmplComboBoxModel)employeBox.getModel()).addAll((List<Employee>) qE.getResultList());
		button.setVisible(false);
		button.getParent().revalidate();
		button.getParent().repaint();
	}
	

}
