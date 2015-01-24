package com.consultancygrid.trz.ui.combo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import org.hibernate.dialect.FirebirdDialect;

import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;

public class EmplComboBoxModel implements ComboBoxModel<Employee> {

	Employee selectedEmployee;
	List<Employee> comboBoxItemList;
	
	public EmplComboBoxModel(List<Employee> comboBoxItemList) {
		
		this.comboBoxItemList = comboBoxItemList;
	}
	
	public EmplComboBoxModel() {
		comboBoxItemList = new ArrayList<Employee>();
	}
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee getElementAt(int arg0) {
		
		return comboBoxItemList.get(arg0);
	}

	@Override
	public int getSize() {
		
		return comboBoxItemList.size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee getSelectedItem() {
		
		return selectedEmployee;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		if (arg0 instanceof Employee) {
			this.selectedEmployee = (Employee) arg0;
		}
      
	}
	
	public void addItem(Employee employee) {
		
		comboBoxItemList.add(employee);
	}
	
	public void addAll(List<Employee> employees) {
		comboBoxItemList.clear();
		comboBoxItemList.addAll(employees);
	}	
	
	public void clear() {
		comboBoxItemList.clear();
	}	
	
	
}
