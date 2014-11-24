package com.consultancygrid.trz.ui.combo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import com.consultancygrid.trz.model.Department;

public class DepartmentComboBoxModel implements ComboBoxModel<Department> {

	Department selectedDepartment;
	List<Department> comboBoxItemList;
	
	public DepartmentComboBoxModel(List<Department> comboBoxItemList) {
		
		this.comboBoxItemList = comboBoxItemList;
	}
	
	public DepartmentComboBoxModel() {
		comboBoxItemList = new ArrayList<Department>();
	}
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Department getElementAt(int arg0) {
		
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
	public Department getSelectedItem() {
		
		return selectedDepartment;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		if (arg0 instanceof Department) {
			this.selectedDepartment = (Department) arg0;
		}
      
	}
	
	public void addItem(Department Department) {
		
		comboBoxItemList.add(Department);
	}
	
	public void addAll(List<Department> Departments) {
		comboBoxItemList.clear();
		comboBoxItemList.addAll(Departments);
	}	
	
}
