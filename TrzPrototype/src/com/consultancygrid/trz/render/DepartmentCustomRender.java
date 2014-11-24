package com.consultancygrid.trz.render;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.consultancygrid.trz.model.Department;

public class DepartmentCustomRender implements ListCellRenderer {

	
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	 public Component getListCellRendererComponent (JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
			        isSelected, cellHasFocus);
		  
		  if (value instanceof Department) {
			  
			  Department tempDepartment = (Department) value;
			  renderer.setText("Department : " + tempDepartment.getCode());
			  
		  } else {
			  renderer.setText("Select Department ...");
		  }
		  
		  return renderer;
     }
}
