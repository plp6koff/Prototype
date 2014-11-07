package com.consultancygrid.trz.render;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.consultancygrid.trz.model.Employee;

public class EmployeeCustomRender implements ListCellRenderer {

	
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	 public Component getListCellRendererComponent (JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
			        isSelected, cellHasFocus);
		  
		  if (value instanceof Employee) {
			  
			  Employee tempEmployee = (Employee) value;
			  renderer.setText(tempEmployee.getFirstName() + " " +tempEmployee.getLastName());
			  
		  } else {
			  renderer.setText("Select employee ...");
		  }
		  
		  return renderer;
     }
}
