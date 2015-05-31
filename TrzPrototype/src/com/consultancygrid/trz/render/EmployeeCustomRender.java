package com.consultancygrid.trz.render;

import java.awt.Component;
import java.io.IOException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class EmployeeCustomRender implements ListCellRenderer {

	
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	 public Component getListCellRendererComponent (JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
			        isSelected, cellHasFocus);
		  
		  if (value instanceof Employee) {
			  
			  Employee tempEmployee = (Employee) value;
			  if (tempEmployee.getId() == null) {
				  try {
					renderer.setText(ResourceLoaderUtil.getLabels(LabelsConstants.COMBO_EMPL));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  } else {
				  renderer.setText(tempEmployee.getFirstName() + " " +tempEmployee.getLastName());
			  }
			  
		  } else {
			  try {
				renderer.setText(ResourceLoaderUtil.getLabels(LabelsConstants.COMBO_EMPL));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  
		  return renderer;
     }
}
