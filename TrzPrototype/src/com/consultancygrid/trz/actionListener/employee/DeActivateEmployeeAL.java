package com.consultancygrid.trz.actionListener.employee;

import java.util.List;

import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;

public class DeActivateEmployeeAL extends BaseActionListener {

	private Employee employee;
	private JButton button;
	private JComboBox<Employee> employeBox1;
	private JComboBox<Employee> employeBox;

	public DeActivateEmployeeAL(PrototypeMainFrame mainFrame,
			Employee employee, JButton button, JComboBox<Employee> employeBox,JComboBox<Employee> employeBox1) {

		super(mainFrame);
		this.employee = employee;
		this.button = button;
		this.employeBox = employeBox;
		this.employeBox1 = employeBox1;
	}

	@Override
	protected void eventCore() {

		employee.setIsActive("N");
		em.merge(employee);

		Query qE = em.createQuery(" from Employee");
		((EmplComboBoxModel) employeBox.getModel()).addAll((List<Employee>) qE
				.getResultList());
		
		Query qE1 = em.createQuery(" select e from Employee as e where e.isActive = 'Y' order by e.firstName ");
		List<Employee> employees = (List<Employee>)qE.getResultList();
		((EmplComboBoxModel) employeBox1.getModel()).addAll((List<Employee>) qE1
				.getResultList());
		
		button.setVisible(false);
		button.getParent().revalidate();
		button.getParent().repaint();
	}

}
